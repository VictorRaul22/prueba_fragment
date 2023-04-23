package com.example.prueba_fragment.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

public class StatusUtils {
    private  Activity activity;
    private  String nameTutorial;
    public  StatusUtils(Activity activity, String nameTutorial){
        this.activity=activity;
        this.nameTutorial=nameTutorial;
    }
    public  void storeTutorialStatus( boolean show) {
        SharedPreferences preferences = activity.getSharedPreferences(nameTutorial, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("show", show);
        editor.apply();
    }

    public  boolean getTutorialStatus() {
        SharedPreferences preferences = activity.getSharedPreferences(nameTutorial, Context.MODE_PRIVATE);
        return preferences.getBoolean("show", true);
    }
}
