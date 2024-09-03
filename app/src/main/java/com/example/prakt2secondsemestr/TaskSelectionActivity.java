
package com.example.prakt2secondsemestr;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class TaskSelectionActivity extends AppCompatActivity {

    private Button btnWordTask;
    private Button btnTranslationTask;
    private Button btnSpellingTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_selection);

        btnWordTask = findViewById(R.id.btn_word_task);
        btnTranslationTask = findViewById(R.id.btn_translation_task);
        btnSpellingTask = findViewById(R.id.btn_spelling_task);

        btnWordTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(TaskSelectionActivity.this, WordTaskActivity.class));
                FirestoreInitializerQuestions qu = new FirestoreInitializerQuestions();
                qu.addQuestions();
            }
        });

        btnTranslationTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(TaskSelectionActivity.this, Window_first_quest.class));
                FirestoreInitializer dfdf = new FirestoreInitializer();
                dfdf.addWords();
            }
        });

        btnSpellingTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(TaskSelectionActivity.this, SpellingTaskActivity.class));
                FirestoreInitializerSentence fi = new FirestoreInitializerSentence();
                fi.addSentences();
            }
        });
    }
}
