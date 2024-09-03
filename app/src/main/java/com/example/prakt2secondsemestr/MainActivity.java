package com.example.prakt2secondsemestr;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.prakt2secondsemestr.databinding.ActivityMainBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity extends AppCompatActivity {

    private EditText loginInput, passwordInput;
    private Button regBtn, loginBtn, task;
    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private ActivityMainBinding binding;
    private static final int MAIN_ITEM_ID = R.id.Main;
    private static final int PROFILE_ITEM_ID = R.id.profile;
    private static final int TABLE_ITEM_ID = R.id.table;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference usersRef = db.collection("users");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.BottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == MAIN_ITEM_ID) {

            } else if (itemId == PROFILE_ITEM_ID) {
                Intent intent = new Intent(MainActivity.this, profile.class);
                startActivity(intent);
            } else if (itemId == TABLE_ITEM_ID) {

            }
            return true;
        });

        loginInput = findViewById(R.id.login_input);
        passwordInput = findViewById(R.id.password_input);
        regBtn = findViewById(R.id.reg_btn);
        loginBtn = findViewById(R.id.login_btn);

        regBtn.setOnClickListener(view -> validateAndProcessUser(true));
        loginBtn.setOnClickListener(view -> validateAndProcessUser(false));
    }

    private void validateAndProcessUser(boolean isRegistration) {
        String email = loginInput.getText().toString().trim();
        String password = passwordInput.getText().toString().trim();

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            loginInput.setError("Неверный формат email");
            return;
        }
        if (password.length() < 6) {
            passwordInput.setError("Пароль слишком короткий");
            return;
        }

        if (isRegistration) {
            firebaseRegistration(email, password);
        } else {
            loginUser(email, password);
        }
    }

    private void firebaseRegistration(String email, String password) {
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnSuccessListener(this, authResult -> {
                    FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                    if (firebaseUser != null) {
                        String currentUserEmail = firebaseUser.getEmail();
                        User newUser = new User(currentUserEmail, firebaseUser.getUid());
                        usersRef.document(firebaseUser.getUid()).set(newUser)
                                .addOnSuccessListener(aVoid -> {
                                    Toast.makeText(MainActivity.this, "Пользователь " + currentUserEmail + " зарегистрирован", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(MainActivity.this, TaskSelectionActivity.class));
                                    finish();
                                })
                                .addOnFailureListener(e -> Toast.makeText(MainActivity.this, "Ошибка сохранения данных пользователя: " + e.getMessage(), Toast.LENGTH_SHORT).show());
                    }
                })
                .addOnFailureListener(e -> Toast.makeText(MainActivity.this, "Ошибка регистрации: " + e.getMessage(), Toast.LENGTH_SHORT).show());
    }

    private void loginUser(String email, String password) {
        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnSuccessListener(this, authResult -> {
                    FirebaseUser currentUser = firebaseAuth.getCurrentUser();
                    if (currentUser != null) {
                        Toast.makeText(MainActivity.this, "Пользователь " + currentUser.getEmail() + " авторизован", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(MainActivity.this, TaskSelectionActivity.class));
                    }
                })
                .addOnFailureListener(e -> Toast.makeText(MainActivity.this, "Ошибка авторизации: " + e.getMessage(), Toast.LENGTH_SHORT).show());
    }
}
