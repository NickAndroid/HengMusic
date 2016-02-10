package com.nick.yinheng.widget;

import android.content.Context;
import android.graphics.Typeface;

/**
 * @author Nick
 */
public class FontsFactory {

    private static Typeface mTypefaceRoboto;
    private static Typeface mTypefaceHero;
    private static Typeface mTypefaceDidot;

    public static void createRoboto(Context context) {
        mTypefaceRoboto = Typeface.createFromAsset(context.getAssets(),
                "fonts/Roboto-Light.ttf");
    }

    public static void createHero(Context context) {
        mTypefaceHero = Typeface.createFromAsset(context.getAssets(),
                "fonts/hero.ttf");
    }

    public static void createDidot(Context context) {
        mTypefaceDidot = Typeface.createFromAsset(context.getAssets(),
                "fonts/didot.ttf");
    }

    public static Typeface getRoboto(Context context) {
        if (mTypefaceRoboto == null) {
            createRoboto(context);
        }
        return mTypefaceRoboto;
    }

    public static Typeface getHero(Context context) {
        if (mTypefaceHero == null) {
            createHero(context);
        }
        return mTypefaceHero;
    }

    public static Typeface getDidot(Context context) {
        if (mTypefaceDidot == null) {
            createDidot(context);
        }
        return mTypefaceDidot;
    }

}
