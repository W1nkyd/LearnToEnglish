package com.example.prakt2secondsemestr;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class main extends AppCompatActivity {

    private TextView randomWordTextView;
    private EditText translationEditText;
    private Button nextWordButton;
    private Button checkButton;

    private Button nextButton;
    private int score = 0;
    private TextView scoreTextView;

    private List<String> englishWords;
    private List<String> russianTranslations;

    private Random random;

    private int currentIndex;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toast.makeText(main.this, "привет я коза", Toast.LENGTH_SHORT).show();

        random = new Random();
        englishWords = new ArrayList<>();
        russianTranslations = new ArrayList<>();

        randomWordTextView = findViewById(R.id.randomword_firstquest);
        translationEditText = findViewById(R.id.writeword);
        nextWordButton = findViewById(R.id.next_word);
        checkButton = findViewById(R.id.check);
        scoreTextView = findViewById(R.id.scoreTextView);
        nextButton = findViewById(R.id.next);

        nextWordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showNextWord();
            }
        });

        checkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkTranslation();
            }
        });

        loadWordsFromFirebase();

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { NextQuest(); }
        });
    }









    private void loadWordsFromFirebase() {
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();
        StorageReference wordsRef = storageRef.child("words.txt");

        wordsRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                try {
                    InputStream inputStream = getContentResolver().openInputStream(uri);
                    InputStreamReader streamReader = new InputStreamReader(inputStream);
                    BufferedReader reader = new BufferedReader(streamReader);
                    StringBuilder stringBuilder = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        stringBuilder.append(line);
                    }
                    String txtData = stringBuilder.toString();
                    parseTxtData(txtData);
                    showNextWord();
                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(main.this, "Ошибка чтения данных", Toast.LENGTH_SHORT).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Обработка ошибок
                Toast.makeText(main.this, "Ошибка загрузки данных", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void parseTxtData(String txtData) {
        String[] lines = txtData.split("\n");
        for (String line : lines) {
            String[] parts = line.split(",");
            if (parts.length == 2) {
                englishWords.add(parts[0].trim());
                russianTranslations.add(parts[1].trim());
            }
        }
    }

    private void showNextWord() {
        if (englishWords.size() > 0 && russianTranslations.size() > 0) {
            currentIndex = random.nextInt(englishWords.size());
            randomWordTextView.setText(englishWords.get(currentIndex));
            translationEditText.setText("");
        }
    }
    private void NextQuest(){
        Toast.makeText(this, "Переход на другую страницу", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(main.this, WordTaskActivity.class);
        startActivity(intent);
    }

    private void checkTranslation() {
        if (englishWords.size() == 0 || russianTranslations.size() == 0) {
            return;
        }
        String userTranslation = translationEditText.getText().toString().trim();
        if (userTranslation.equalsIgnoreCase(russianTranslations.get(currentIndex))) {
            score++;
            scoreTextView.setText("Score: " + score);
        } else {
            Toast.makeText(this, "Неправильный ответ", Toast.LENGTH_SHORT).show();
            scoreTextView.setText("Score: " + (score - 1));
        }
        showNextWord();
    }
}
