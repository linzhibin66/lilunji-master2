package com.dgcheshang.cheji.Tools;

import android.speech.tts.TextToSpeech;

import java.util.concurrent.LinkedBlockingDeque;

/**
 *语音播报
 */

public class Speaking {
    private static LinkedBlockingDeque<String> lbd=new LinkedBlockingDeque<>();
    public static boolean kg=false;
    /*public void Speaking(String s){
        try {
            lbd.put(s);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }*/

    public static void in(String s){
        try {
            lbd.put(s);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void getInstance(){
        new Speakout();
        while (true) {
            if (kg) {
                kg=false;
                try {
                    String text = lbd.take();
                    if(Speakout.tts!=null){
                        if(!Speakout.tts.isSpeaking()) {
                            Speakout.tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);
                            kg = true;
                        }else{
                            while(true){
                                Thread.sleep(100);
                                if(!Speakout.tts.isSpeaking()){
                                    Speakout.tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);
                                    kg = true;
                                    break;
                                }
                            }
                        }
                    }else{
                        new Speakout();
                    }
                } catch (Exception e) {
                }

            }else{
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
