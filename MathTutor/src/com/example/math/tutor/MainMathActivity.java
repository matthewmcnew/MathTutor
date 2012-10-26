package com.example.math.tutor;

import java.util.ArrayList;

import android.app.Activity;
import android.gesture.Gesture;
import android.gesture.GestureLibraries;
import android.gesture.GestureLibrary;
import android.gesture.GestureOverlayView;
import android.gesture.GestureOverlayView.OnGesturePerformedListener;
import android.gesture.Prediction;
import android.os.Bundle;
import android.view.Menu;
import android.widget.TextView;
import android.widget.Toast;

public class MainMathActivity extends Activity implements OnGesturePerformedListener {
	
    private GestureLibrary mLibrary;
	private QuestionGen questions;
	private TextView text;
    

	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_math);
        
        mLibrary = GestureLibraries.fromRawResource(this, R.raw.gestures);
        if (!mLibrary.load()) {
          finish();
        }   
        questions = new QuestionGen(); 
        
        text = (TextView) findViewById(R.id.textView1);
        GestureOverlayView gestures = (GestureOverlayView) findViewById(R.id.gestures);
   
        gestures.addOnGesturePerformedListener(this);
        text.setText(questions.genProblem());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main_math, menu);
        return true;
    }

	@Override
	public void onGesturePerformed(GestureOverlayView overlay, Gesture gesture) {
		   ArrayList<Prediction> predictions = mLibrary.recognize(gesture);

		   if (predictions.size() > 0 && predictions.get(0).score > 1.0) {
		     int result = Integer.valueOf(predictions.get(0).name);

		     if(questions.getAnswer() == result) {
		    	 Toast.makeText(this, "Good Job Sport", Toast.LENGTH_LONG).show();
		    	 text.setText(questions.genProblem());
		     } else
		     {
		    	 Toast.makeText(this, "Try Again. ", Toast.LENGTH_LONG).show(); 
		     }  
		
		   } 
			   
		
	}
}
