package com.example.prakt2secondsemestr;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SpellingTaskActivity extends AppCompatActivity {

    private EditText spellingInput;
    private TextView spellingResult;
    private TextView correctSentenceView;
    private Button btnCheckSpelling;
    private Button btnBackToSelection;

    private List<String> sentences = new ArrayList<>();
    private String correctSentence;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference sentencesRef = db.collection("sentences");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spelling_task);

        spellingInput = findViewById(R.id.spelling_input);
        spellingResult = findViewById(R.id.spelling_result);
        correctSentenceView = findViewById(R.id.correct_sentence);
        btnCheckSpelling = findViewById(R.id.btn_check_spelling);
        btnBackToSelection = findViewById(R.id.btn_back_to_selection_from_spelling);

        loadSentencesFromFirestore();

        btnCheckSpelling.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkSpelling();
            }
        });

        btnBackToSelection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SpellingTaskActivity.this, TaskSelectionActivity.class));
            }
        });

        spellingInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void afterTextChanged(Editable editable) {
                checkSpelling();
            }
        });
    }

    private void loadSentencesFromFirestore() {
        sentencesRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        String sentence = document.getString("sentence");
                        sentences.add(sentence);
                    }
                    selectRandomSentence();
                } else {
                    Toast.makeText(SpellingTaskActivity.this, "Ошибка загрузки данных", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void selectRandomSentence() {
        if (!sentences.isEmpty()) {
            Random random = new Random();
            correctSentence = sentences.get(random.nextInt(sentences.size()));
            correctSentenceView.setText(correctSentence);
        }
    }

    private void checkSpelling() {
        String inputText = spellingInput.getText().toString();
        SpannableString spannableString = new SpannableString(inputText);

        int correctCount = 0;
        int halfCorrectCount = 0;

        for (int i = 0; i < Math.min(inputText.length(), correctSentence.length()); i++) {
            if (inputText.charAt(i) == correctSentence.charAt(i)) {
                correctCount++;
            } else if (inputText.charAt(i) != ' ' && correctSentence.contains(Character.toString(inputText.charAt(i)))) {
                halfCorrectCount++;
                spannableString.setSpan(new ForegroundColorSpan(Color.BLUE), i, i + 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            } else {
                spannableString.setSpan(new ForegroundColorSpan(Color.RED), i, i + 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
        }

        int score = correctCount * 2 + halfCorrectCount;
        spellingResult.setText(spannableString);
        Toast.makeText(this, "Ваш счёт: " + score, Toast.LENGTH_SHORT).show();

        if (inputText.equals(correctSentence)) {
            Toast.makeText(this, "Правильно! Переход к следующему предложению.", Toast.LENGTH_SHORT).show();
            selectRandomSentence();
            spellingInput.setText("");
            spellingResult.setText("");
        }
    }
}
