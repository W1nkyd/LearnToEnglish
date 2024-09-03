package com.example.prakt2secondsemestr;

import android.content.Context;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.LinearLayout;

import androidx.appcompat.content.res.AppCompatResources;
import androidx.appcompat.widget.AppCompatTextView;

public class CustomWord extends AppCompatTextView {

    private static final String TAG = "CustomWord";
    private String word;

    public CustomWord(Context context, String word) {
        super(context);
        this.word = word;

        setText(word);

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(15, 15, 15, 0);

        setTextColor(getResources().getColor(R.color.custom_view_text_color));
        setLayoutParams(layoutParams);
        setTextAlignment(TEXT_ALIGNMENT_CENTER);
        setTextSize(20);

        setBackground(AppCompatResources.getDrawable(getContext(), R.drawable.custom_word_border));
    }

    public void goToViewGroup(CustomLayout customLayout, LinearLayout sentenceLayout) {
        ViewParent parent = getParent();

        if (parent instanceof CustomLayout) {
            customLayout.removeViewCustomLayout(this);
            sentenceLayout.addView(this);
        } else {
            sentenceLayout.removeView(this);
            customLayout.addViewAt(this);
        }
    }
}
