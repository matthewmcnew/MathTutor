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
	private ArrayList<Problem> problems;
	private int count;
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
        String p1 = questions.genProblem();
        Problem p = questions.getProblem();
        text.setText(p1);
        
        score = 0;
        count = 0;
        problems = new ArrayList<Problem>();
        problems.add(p);
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
				   count++;
				   text.setTextColor(Color.BLACK);
				   Problem problem;
				   if(count<problems.size()-1) {
					   problem = problems.get(count);
				   } else {
					   questions.genProblem();
					   problem = questions.getProblem();
					   problems.add(problem);
				   }
				   text.setText(problem.toString());
				   gestures.setGestureVisible(true);
				   //
			   } else if(predictions.get(0).name.equals("right")){
				   if(count>0) {
					   count--;
					   Problem problem = problems.get(count);
					   text.setTextColor(Color.BLACK);
					   text.setText(problem.toString());
				   }
			   } else {
		     int result = Integer.valueOf(predictions.get(0).name);

		     if(problems.get(count).getSol() == result) {
		    	 text.setTextColor(Color.GREEN);
		    	 
		    	 Problem problem = problems.get(count);
		    	 problem.setSolved(true);
		    	 problems.set(count, problem);
		    	 
		    	 text.setText(problem.toString());
		    	 scoreView.setText("Score: " + ++score);
		    	 //	 text.setTextColor(Color.BLACK);
		    	 Toast.makeText(this, "Good Job Sport", Toast.LENGTH_LONG).show();
		    	 
		//    	 gestures.setGestureVisible(false);

//		    	 text.setText(questions.genProblem());
		     } else {
		    	 String problem = problems.get(count) + " = " + result;
		    	 
		    	 text.setText(problem);
		    	 text.setTextColor(Color.RED);
		    	 
		    	 Toast.makeText(this, "Try Again. ", Toast.LENGTH_LONG).show();

		    	 //			 text.setTextColor(Color.BLACK);
		    	 //	    	 text.setText(questions.genProblem());

		     }  

			   }

		   } 

		
	}
}
