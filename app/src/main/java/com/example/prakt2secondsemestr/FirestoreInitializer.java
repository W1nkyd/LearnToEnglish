package com.example.prakt2secondsemestr;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class FirestoreInitializer {

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference wordsRef = db.collection("words");

    public void addWords() {
        List<WordModel> words = new ArrayList<>();
        words.add(new WordModel("as", "как"));
        words.add(new WordModel("I", "я"));
        words.add(new WordModel("his", "его"));
        words.add(new WordModel("that", "что / тот"));
        words.add(new WordModel("he", "он"));
        words.add(new WordModel("was", "был"));
        words.add(new WordModel("for", "для"));
        words.add(new WordModel("on", "на"));
        words.add(new WordModel("are", "являются"));
        words.add(new WordModel("with", "с"));
        words.add(new WordModel("they", "они"));
        words.add(new WordModel("be", "быть"));
        words.add(new WordModel("at", "на"));
        words.add(new WordModel("one", "один"));
        words.add(new WordModel("have", "иметь"));
        words.add(new WordModel("this", "это"));
        words.add(new WordModel("from", "из"));
        words.add(new WordModel("by", "от"));
        words.add(new WordModel("hot", "горячий / жарко"));
        words.add(new WordModel("word", "слово"));
        words.add(new WordModel("but", "но"));
        words.add(new WordModel("what", "что"));
        words.add(new WordModel("some", "некоторый"));
        words.add(new WordModel("is", "является"));
        words.add(new WordModel("it", "это"));
        words.add(new WordModel("you", "ты"));
        words.add(new WordModel("or", "или"));
        words.add(new WordModel("had", "имел"));
        words.add(new WordModel("the", ""));
        words.add(new WordModel("of", "из / о"));
        words.add(new WordModel("to", "в / к"));
        words.add(new WordModel("and", "и"));
        words.add(new WordModel("a", ""));
        words.add(new WordModel("in", "в"));
        words.add(new WordModel("we", "мы"));
        words.add(new WordModel("can", "может"));
        words.add(new WordModel("out", "из / вне"));
        words.add(new WordModel("other", "другой"));
        words.add(new WordModel("were", "были"));
        words.add(new WordModel("which", "который"));
        words.add(new WordModel("do", "делать"));
        words.add(new WordModel("their", "их"));
        words.add(new WordModel("time", "время"));
        words.add(new WordModel("if", "если"));
        words.add(new WordModel("will", "будет"));
        words.add(new WordModel("how", "как"));
        words.add(new WordModel("said", "говорить"));
        words.add(new WordModel("an", ""));
        words.add(new WordModel("each", "каждый"));

        for (WordModel word : words) {
            wordsRef.add(word)
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

