package com.example.prueba_fragment.utils;

import android.app.Activity;
import android.widget.Toast;


import com.example.prueba_fragment.R;
import com.getkeepsafe.taptargetview.TapTarget;
import com.getkeepsafe.taptargetview.TapTargetSequence;

import java.lang.annotation.Target;
import java.util.List;


public class CustomTapTargetSequence  {
    private  TapTargetSequence sequence;
    private StatusUtils status;
    public  CustomTapTargetSequence(Activity activity, String nameTutorial,TapTarget[] targetsCus ){
         status=new StatusUtils(activity,nameTutorial);
        for (TapTarget target : targetsCus) {
            target.cancelable(true)
                    .tintTarget(false)
                    .dimColor(R.color.black);
        }
         sequence = new TapTargetSequence(activity)
                .targets(targetsCus)
                .listener(new TapTargetSequence.Listener() {
                    @Override
                    public void onSequenceFinish() {
                        Toast.makeText(activity, "Tutorial finalizado", Toast.LENGTH_LONG).show();
                        status.storeTutorialStatus( false);
                    }

                    @Override
                    public void onSequenceStep(TapTarget lastTarget, boolean targetClicked) {

                    }

                    @Override
                    public void onSequenceCanceled(TapTarget lastTarget) {
                        // Acción a realizar cuando se cancela la secuencia
                    }
                });
    }
    public void startSequence(){
        if(status.getTutorialStatus()) {
            sequence.start();
        }
    }
    public  void repeat(){
        status.storeTutorialStatus(true);
        startSequence();
    }

}
