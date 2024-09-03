package com.example.prakt2secondsemestr;

import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.prakt2secondsemestr.databinding.ActivityWordTaskBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class WordTaskActivity extends AppCompatActivity {

    private static final String TAG = "WordTaskActivity";

    private ActivityWordTaskBinding binding;
    private CustomWord customWord;
    private CustomLayout customLayout;
    private QuestionModel questionModel;
    private List<QuestionModel> questionList = new ArrayList<>();
    private ArrayList<String> words = new ArrayList<>();
    private Random random = new Random();

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference questionsRef = db.collection("questions");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityWordTaskBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initCustomLayout();
        loadQuestionsFromFirestore();
        checkAnswer();
    }

    private class TouchListener implements View.OnTouchListener {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            if (motionEvent.getAction() == MotionEvent.ACTION_DOWN && !customLayout.empty()) {
                customWord = (CustomWord) view;
                customWord.goToViewGroup(customLayout, binding.sentenceLine);
                checkChildCount();
                return true;
            }
            return false;
        }
    }

    private void loadQuestionsFromFirestore() {
        questionsRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        QuestionModel question = document.toObject(QuestionModel.class);
                        questionList.add(question);
                    }
                    randomizeInitialData();
                    binding.question.setText(questionModel.getQuestion());
                    randomizeCustomWords();
                } else {
                    Toast.makeText(WordTaskActivity.this, "Ошибка загрузки данных", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void initCustomLayout() {
        customLayout = new CustomLayout(this);
        customLayout.setGravity(Gravity.CENTER);

        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.BELOW, R.id.frame_layout);
        params.topMargin = 235;

        binding.mainLayout.addView(customLayout, params);
    }

    private void checkAnswer() {
        binding.checkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StringBuilder answer = new StringBuilder();
                if (binding.checkButton.getText().equals("check")) {
                    for (int i = 0; i < binding.sentenceLine.getChildCount(); i++) {
                        customWord = (CustomWord) binding.sentenceLine.getChildAt(i);
                        answer.append(customWord.getText().toString()).append(" ");
                    }

                    if (answer.toString().trim().equals(questionModel.getAnswer())) {
                        Toast.makeText(WordTaskActivity.this, "Правильно!", Toast.LENGTH_SHORT).show();
                        binding.taskProgressBar.setProgress(binding.taskProgressBar.getProgress() + 10);
                        binding.checkButton.setText("продолжить");
                        lockViews();
                    } else {
                        Toast.makeText(WordTaskActivity.this, "Неправильно. \n" + questionModel.getAnswer(), Toast.LENGTH_SHORT).show();
                        binding.taskProgressBar.setProgress(binding.taskProgressBar.getProgress() - 10);
                        binding.checkButton.setText("продолжить");
                        lockViews();
                    }
                } else if (binding.checkButton.getText().equals("продолжить")) {
                    recreate();
                }
            }
        });
    }

    private void checkChildCount() {
        if (binding.sentenceLine.getChildCount() > 0) {
            binding.checkButton.getBackground().setColorFilter(
                    ContextCompat.getColor(this, R.color.green_button),
                    PorterDuff.Mode.MULTIPLY);
            binding.checkButton.setEnabled(true);
        } else {
            binding.checkButton.getBackground().setColorFilter(
                    ContextCompat.getColor(this, R.color.grey_button),
                    PorterDuff.Mode.MULTIPLY);
            binding.checkButton.setEnabled(false);
        }
    }

    private void lockViews() {
        for (int i = 0; i < binding.sentenceLine.getChildCount(); i++) {
            customWord = (CustomWord) binding.sentenceLine.getChildAt(i);
            customWord.setEnabled(false);
        }
        for (int i = 0; i < customLayout.getChildCount(); i++) {
            customWord = (CustomWord) customLayout.getChildAt(i);
            customWord.setEnabled(false);
        }
    }

    private void randomizeInitialData() {
        if (!questionList.isEmpty()) {
            int randomIndex = random.nextInt(questionList.size());
            questionModel = questionList.get(randomIndex);
        }
    }

    private void randomizeCustomWords() {
        if (questionModel != null) {
            ArrayList<CustomWord> customWords = new ArrayList<>();
            String[] wordsFromSentence = questionModel.getAnswer().split(" ");
            Collections.addAll(words, wordsFromSentence);

            int sentenceWordsCount = wordsFromSentence.length;
            int leftSize = 8 - sentenceWordsCount;
            int leftRandom = random.nextInt(leftSize) + 2;

            while (words.size() - leftSize < leftRandom) {
                addArrayWords();
            }

            Collections.shuffle(words);

            for (String word : words) {
                CustomWord customWord = new CustomWord(getApplicationContext(), word);
                customWord.setOnTouchListener(new TouchListener());
                customLayout.push(customWord);
            }
        }
    }

    private void addArrayWords() {
        String[] wordsFromAnswerArray = questionList.get(random.nextInt(questionList.size())).getAnswer().split(" ");
        for (int i = 0; i < 2; i++) {
            String word = wordsFromAnswerArray[random.nextInt(wordsFromAnswerArray.length)];
            if (!words.contains(word)) {
                words.add(word);
            }
        }
    }
}
