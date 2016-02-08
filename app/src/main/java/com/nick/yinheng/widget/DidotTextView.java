package com.nick.yinheng.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * @author Nick
 */
public class DidotTextView extends TextView {

    public DidotTextView(Context context) {
        super(context);
        setUseDidot();
    }

    public DidotTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setUseDidot();
    }

    public DidotTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setUseDidot();
    }

    private void setUseDidot() {
        setTypeface(FontsFactory.getDidot(getContext()));
    }
}
