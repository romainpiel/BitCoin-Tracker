package com.romainpiel.bitcointracker.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.ProgressBar;

import com.romainpiel.bitcointracker.R;
import com.romainpiel.bitcointracker.view.drawable.MaterialProgressDrawable;

public class MaterialProgressBar extends ProgressBar {

    public MaterialProgressBar(Context context) {
        super(context);
        init(context, null, 0, 0);
    }

    public MaterialProgressBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0, 0);
    }

    public MaterialProgressBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr, 0);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public MaterialProgressBar(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs, defStyleAttr, defStyleRes);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        MaterialProgressDrawable indeterminateDrawable = new MaterialProgressDrawable(context, this);
        indeterminateDrawable.setBackgroundColor(context.getResources().getColor(android.R.color.transparent));
        indeterminateDrawable.setAlpha(255);
        indeterminateDrawable.updateSizes(MaterialProgressDrawable.LARGE);
        if (attrs != null) {
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.MaterialProgressBar, defStyleAttr, defStyleRes);
            int indeterminateColor = a.getColor(R.styleable.MaterialProgressBar_indeterminateColor, 0);
            indeterminateDrawable.setColorSchemeColors(indeterminateColor);
        }
        indeterminateDrawable.start();
        setIndeterminateDrawable(indeterminateDrawable);
    }
}
