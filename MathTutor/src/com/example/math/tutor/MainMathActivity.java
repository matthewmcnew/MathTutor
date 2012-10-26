package com.example.math.tutor;

import java.util.ArrayList;

import android.app.Activity;
import android.gesture.Gesture;
import android.gesture.GestureLibraries;
import android.gesture.GestureLibrary;
import android.gesture.GestureOverlayView;
import android.gesture.GestureOverlayView.OnGesturePerformedListener;
import android.gesture.Prediction;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.widget.TextView;
import android.widget.Toast;

public class MainMathActivity extends Activity implements OnGesturePerformedListener {
	
    private GestureLibrary mLibrary;
	private QuestionGen questions;
	private TextView text;
	private TextView scoreView;
    
	private int score;
	private GestureOverlayView gestures;

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
        scoreView = (TextView) findViewById(R.id.score);
        gestures = (GestureOverlayView) findViewById(R.id.gestures);
   
        gestures.addOnGesturePerformedListener(this);
        text.setText(questions.genProblem());
        
        score = 0;
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
			   
			   if(predictions.get(0).name.equals("left")) {
			    	 text.setTextColor(Color.BLACK);
			    	 text.setText(questions.genProblem());
			    	 gestures.setGestureVisible(true);
			   } else {
		     int result = Integer.valueOf(predictions.get(0).name);

		     if(questions.getAnswer() == result) {
		    	 text.setTextColor(Color.GREEN);

		    	 text.setText(questions.problem + " = " + result);
		    	 scoreView.setText("Score: " + ++score);
		    	 //	 text.setTextColor(Color.BLACK);
		    	 Toast.makeText(this, "Good Job Sport", Toast.LENGTH_LONG).show();
		    	 
		    	 gestures.setGestureVisible(false);

//		    	 text.setText(questions.genProblem());
		     } else
		     {
		    	 text.setText(questions.problem + " = " + result);
		    	 text.setTextColor(Color.RED);
		    	 //
		    	 Toast.makeText(this, "Try Again. ", Toast.LENGTH_LONG).show();
		    	 		    	 
	//			 text.setTextColor(Color.BLACK);
	//	    	 text.setText(questions.genProblem());

		     }  
		     
			   }
		
		   } 
			   
		
	}
}
