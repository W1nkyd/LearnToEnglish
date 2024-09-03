package com.example.prakt2secondsemestr;

import android.content.Intent;
import android.os.Bundle;
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

public class Window_first_quest extends AppCompatActivity {

    protected TextView randomWordTextView;
    protected EditText translationEditText;
    protected Button nextWordButton;
    protected Button checkButton;
    protected Button btnBackToSelection;
    protected int score = 0;
    protected TextView scoreTextView;

    protected List<WordModel> wordList = new ArrayList<>();
    protected Random random;
    protected int currentIndex;

    protected FirebaseFirestore db = FirebaseFirestore.getInstance();
    protected CollectionReference wordsRef = db.collection("words");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.first_quest_window);

        random = new Random();

        randomWordTextView = findViewById(R.id.randomword_firstquest);
        translationEditText = findViewById(R.id.writeword);
        nextWordButton = findViewById(R.id.next_word);
        checkButton = findViewById(R.id.check);
        scoreTextView = findViewById(R.id.scoreTextView);
        btnBackToSelection = findViewById(R.id.btn_back_to_selection);

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

        btnBackToSelection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Window_first_quest.this, TaskSelectionActivity.class));
            }
        });

        FirestoreInitializer firestoreInitializer = new FirestoreInitializer();
        firestoreInitializer.addWords();

        loadWordsFromFirestore();
    }

    protected void loadWordsFromFirestore() {
        wordsRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        WordModel word = document.toObject(WordModel.class);
                        wordList.add(word);
                    }
                    showNextWord();
                } else {
                    Toast.makeText(Window_first_quest.this, "Ошибка загрузки данных", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    protected void showNextWord() {
        if (!wordList.isEmpty()) {
            currentIndex = random.nextInt(wordList.size());
            WordModel wordModel = wordList.get(currentIndex);
            randomWordTextView.setText(wordModel.getEnglishWord());
            translationEditText.setText("");
        }
    }

    protected void checkTranslation() {
        if (wordList.isEmpty()) {
            return;
        }
        String userTranslation = translationEditText.getText().toString().trim();
        if (userTranslation.equalsIgnoreCase(wordList.get(currentIndex).getRussianTranslation())) {
            score++;
            Toast.makeText(this, "Правильный ответ", Toast.LENGTH_SHORT).show();
        } else {
            score--;
            Toast.makeText(this, "Неправильный ответ", Toast.LENGTH_SHORT).show();
        }
        updateScore();
        showNextWord();
    }

    protected void updateScore() {
        scoreTextView.setText("Score: " + score);
    }
}
