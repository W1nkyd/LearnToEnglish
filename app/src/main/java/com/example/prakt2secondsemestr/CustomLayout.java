package com.example.prakt2secondsemestr;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;

import java.util.ArrayList;

public class CustomLayout extends LinearLayout {

    private static final String TAG = "CustomLayout";
    private ArrayList<View> words = new ArrayList<>();

    public CustomLayout(Context context) {
        super(context);
        setOrientation(LinearLayout.HORIZONTAL);
    }

    public void push(View word) {
        words.add(word);
        addView(word);
    }

    public void removeViewCustomLayout(View view) {
        CustomWord customWord = new CustomWord(getContext(), "");
        customWord.setBackgroundColor(getResources().getColor(R.color.empty_view));

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(view.getWidth(), view.getHeight());
        params.setMargins(15, 15, 15, 0);

        customWord.setLayoutParams(params);

        removeView(view);
        addView(customWord, words.indexOf(view));
    }

    public void addViewAt(View view) {
        if (getChildAt(words.indexOf(view)) != null) {
            removeViewAt(words.indexOf(view));
            addView(view, words.indexOf(view));
        } else {
            addView(view, getChildCount());
        }
    }

    public boolean empty() {
        return words.isEmpty();
    }
}
