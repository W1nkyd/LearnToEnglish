package com.example.prakt2secondsemestr;

public class WordModel {
    private String englishWord;
    private String russianTranslation;

    public WordModel() {

    }

    public WordModel(String englishWord, String russianTranslation) {
        this.englishWord = englishWord;
        this.russianTranslation = russianTranslation;
    }

    public String getEnglishWord() {
        return englishWord;
    }

    public void setEnglishWord(String englishWord) {
        this.englishWord = englishWord;
    }

    public String getRussianTranslation() {
        return russianTranslation;
    }

    public void setRussianTranslation(String russianTranslation) {
        this.russianTranslation = russianTranslation;
    }
}
