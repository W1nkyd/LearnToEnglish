package com.example.prakt2secondsemestr;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import android.os.Build;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.util.ArrayList;
import java.util.List;

@RunWith(RobolectricTestRunner.class)
@Config(sdk = {Build.VERSION_CODES.R, Build.VERSION_CODES.S}, manifest = Config.NONE)
public class ExampleUnitTest {

    private Window_first_quest windowFirstQuest;
    private FirebaseFirestore mockFirestore;
    private CollectionReference mockCollectionReference;
    private Task<QuerySnapshot> mockTask;
    private QuerySnapshot mockQuerySnapshot;
    private QueryDocumentSnapshot mockDocumentSnapshot;
    private TextView mockRandomWordTextView;
    private EditText mockTranslationEditText;
    private TextView mockScoreTextView;

    @Before
    public void setUp() {
        windowFirstQuest = new Window_first_quest();
        mockFirestore = mock(FirebaseFirestore.class);
        mockCollectionReference = mock(CollectionReference.class);
        mockTask = mock(Task.class);
        mockQuerySnapshot = mock(QuerySnapshot.class);
        mockDocumentSnapshot = mock(QueryDocumentSnapshot.class);
        mockRandomWordTextView = mock(TextView.class);
        mockTranslationEditText = mock(EditText.class);
        mockScoreTextView = mock(TextView.class);

        windowFirstQuest.db = mockFirestore;
        windowFirstQuest.wordsRef = mockCollectionReference;

        windowFirstQuest.randomWordTextView = mockRandomWordTextView;
        windowFirstQuest.translationEditText = mockTranslationEditText;
        windowFirstQuest.scoreTextView = mockScoreTextView;

        when(mockCollectionReference.get()).thenReturn(mockTask);
        doAnswer(invocation -> {
            OnCompleteListener<QuerySnapshot> listener = invocation.getArgument(0);
            listener.onComplete(mockTask);
            return null;
        }).when(mockTask).addOnCompleteListener(any(OnCompleteListener.class));

        when(mockTask.isSuccessful()).thenReturn(true);

        List<DocumentSnapshot> documents = new ArrayList<>();
        documents.add(mockDocumentSnapshot);

        when(mockQuerySnapshot.getDocuments()).thenReturn(documents);
        when(mockTask.getResult()).thenReturn(mockQuerySnapshot);
        when(mockDocumentSnapshot.toObject(WordModel.class)).thenReturn(new WordModel("word", "слово"));
    }

    @Test
    public void testLoadWordsFromFirestore() {
        windowFirstQuest.loadWordsFromFirestore();
        assertEquals(1, windowFirstQuest.wordList.size());
        assertEquals("word", windowFirstQuest.wordList.get(0).getEnglishWord());
        assertEquals("слово", windowFirstQuest.wordList.get(0).getRussianTranslation());
    }

    @Test
    public void testShowNextWord() {
        List<WordModel> testWordList = new ArrayList<>();
        testWordList.add(new WordModel("test", "тест"));
        windowFirstQuest.wordList = testWordList;

        windowFirstQuest.showNextWord();

        verify(windowFirstQuest.randomWordTextView).setText("test");
    }

    @Test
    public void testCheckTranslationCorrect() {
        List<WordModel> testWordList = new ArrayList<>();
        testWordList.add(new WordModel("test", "тест"));
        windowFirstQuest.wordList = testWordList;
        windowFirstQuest.currentIndex = 0;

        when(windowFirstQuest.translationEditText.getText().toString()).thenReturn("тест");

        windowFirstQuest.checkTranslation();

        assertEquals(1, windowFirstQuest.score);
        verify(windowFirstQuest.scoreTextView).setText("Score: 1");
    }

    @Test
    public void testCheckTranslationIncorrect() {
        List<WordModel> testWordList = new ArrayList<>();
        testWordList.add(new WordModel("test", "тест"));
        windowFirstQuest.wordList = testWordList;
        windowFirstQuest.currentIndex = 0;

        when(windowFirstQuest.translationEditText.getText().toString()).thenReturn("неверно");

        windowFirstQuest.checkTranslation();

        assertEquals(-1, windowFirstQuest.score);
        verify(windowFirstQuest.scoreTextView).setText("Score: -1");
    }
}
