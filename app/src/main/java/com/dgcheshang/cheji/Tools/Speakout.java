package com.dgcheshang.cheji.Tools;

import android.speech.tts.TextToSpeech;
import android.support.v4.BuildConfig;
import android.util.Log;

import com.dgcheshang.cheji.CjApplication;

import java.util.Locale;

/**
 * Created by Administrator on 2017/8/16.
 */

public class Speakout {
    //private static String text;
    //public Speakout(String s){text=s;}
    public static TextToSpeech tts=null;

    TextToSpeech speech = new TextToSpeech(CjApplication.getInstance(), new TextToSpeech.OnInitListener() {
        @Override
        public void onInit(int status) {
            if (status == TextToSpeech.SUCCESS) {
                int result = speech.setLanguage(Locale.CHINESE);
                if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                    if (BuildConfig.DEBUG) Log.d("TAG", "朗读出现错误...");
                } else {
                    tts=speech;
                    Speaking.kg=true;
                    //speech.speak(text, TextToSpeech.QUEUE_FLUSH, null);
                }
                //Speaking.kg=true;
            }
        }
    });
}
