package com.example.prakt2secondsemestr;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class FirestoreService {

    private FirebaseFirestore db;

    public FirestoreService(FirebaseFirestore db) {
        this.db = db;
    }

    public void loadWordsFromFirestore(CollectionReference wordsRef, OnCompleteListener<QuerySnapshot> listener) {
        wordsRef.get().addOnCompleteListener(listener);
    }

    public List<WordModel> convertSnapshotToWordModels(QuerySnapshot snapshot) {
        List<WordModel> wordModels = new ArrayList<>();
        for (QueryDocumentSnapshot document : snapshot) {
            WordModel wordModel = document.toObject(WordModel.class);
            wordModels.add(wordModel);
        }
        return wordModels;
    }
}

