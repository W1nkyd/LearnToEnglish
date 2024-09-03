package com.example.prakt2secondsemestr;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class FirestoreInitializerQuestions {

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference questionsRef = db.collection("questions");

    public void addQuestions() {
        List<QuestionModel> questions = new ArrayList<>();
        questions.add(new QuestionModel("Она ест яблоки", "She eats apples"));
        questions.add(new QuestionModel("Он ест", "He eats"));
        questions.add(new QuestionModel("Вы девушка", "You are a woman"));
        questions.add(new QuestionModel("Вы парень", "You are a boy"));
        questions.add(new QuestionModel("Что происходит", "What is happening"));
        questions.add(new QuestionModel("Я парень", "I am a boy"));
        questions.add(new QuestionModel("Она учитель", "She is a teacher"));
        questions.add(new QuestionModel("Это собака", "This is a dog"));
        questions.add(new QuestionModel("Они студенты", "They are students"));
        questions.add(new QuestionModel("Мы друзья", "We are friends"));
        questions.add(new QuestionModel("Это книга", "This is a book"));
        questions.add(new QuestionModel("Это дом", "This is a house"));
        questions.add(new QuestionModel("Это машина", "This is a car"));
        questions.add(new QuestionModel("Ты врач", "You are a doctor"));
        questions.add(new QuestionModel("Я студент", "I am a student"));
        questions.add(new QuestionModel("Он программист", "He is a programmer"));
        questions.add(new QuestionModel("Она медсестра", "She is a nurse"));
        questions.add(new QuestionModel("Это кошка", "This is a cat"));
        questions.add(new QuestionModel("Мы семья", "We are a family"));
        questions.add(new QuestionModel("Они коллеги", "They are colleagues"));
        questions.add(new QuestionModel("Это дерево", "This is a tree"));
        questions.add(new QuestionModel("Ты инженер", "You are an engineer"));
        questions.add(new QuestionModel("Я учитель", "I am a teacher"));
        questions.add(new QuestionModel("Он водитель", "He is a driver"));
        questions.add(new QuestionModel("Она студентка", "She is a student"));
        questions.add(new QuestionModel("Это здание", "This is a building"));
        questions.add(new QuestionModel("Это город", "This is a city"));
        questions.add(new QuestionModel("Это река", "This is a river"));
        questions.add(new QuestionModel("Ты художник", "You are an artist"));
        questions.add(new QuestionModel("Я музыкант", "I am a musician"));

        for (QuestionModel question : questions) {
            questionsRef.add(question)
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {

                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                        }
                    });
        }
    }
}
