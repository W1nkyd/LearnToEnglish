package com.example.prakt2secondsemestr;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class FirestoreInitializerSentence {

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference sentencesRef = db.collection("sentences");

    public void addSentences() {
        List<String> sentences = new ArrayList<>();
        sentences.add("The quick brown fox jumps over the lazy dog.");
        sentences.add("A journey of a thousand miles begins with a single step.");
        sentences.add("To be or not to be, that is the question.");
        sentences.add("All that glitters is not gold.");
        sentences.add("The pen is mightier than the sword.");
        sentences.add("Actions speak louder than words.");
        sentences.add("A picture is worth a thousand words.");
        sentences.add("When in Rome, do as the Romans do.");
        sentences.add("The early bird catches the worm.");
        sentences.add("Fortune favors the brave.");
        sentences.add("Beauty is in the eye of the beholder.");
        sentences.add("Necessity is the mother of invention.");
        sentences.add("Birds of a feather flock together.");
        sentences.add("A watched pot never boils.");
        sentences.add("You can’t judge a book by its cover.");
        sentences.add("A penny saved is a penny earned.");
        sentences.add("Absence makes the heart grow fonder.");
        sentences.add("The best things in life are free.");
        sentences.add("Don’t put all your eggs in one basket.");
        sentences.add("Every cloud has a silver lining.");
        sentences.add("Time heals all wounds.");
        sentences.add("What doesn’t kill you makes you stronger.");
        sentences.add("The grass is always greener on the other side.");
        sentences.add("Practice makes perfect.");
        sentences.add("Honesty is the best policy.");
        sentences.add("Better late than never.");
        sentences.add("Two heads are better than one.");
        sentences.add("When the going gets tough, the tough get going.");
        sentences.add("An apple a day keeps the doctor away.");
        sentences.add("Good things come to those who wait.");

        for (String sentence : sentences) {
            SentenceModel sentenceModel = new SentenceModel(sentence);
            Task<DocumentReference> documentReferenceTask = sentencesRef.add(sentenceModel)
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
