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
	private ArrayList<String> problems;
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
        text.setText(p1);
        
        score = 0;
        count = 0;
        problems = new ArrayList<String>();
        problems.add(p1);
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
				   String problem;
				   if(count<problems.size()-1) {
					   problem = problems.get(count);
				   } else {
					   problem = questions.genProblem();
					   problems.add(problem);
				   }
				   text.setText(problem);
				   gestures.setGestureVisible(true);
				   //
			   } else if(predictions.get(0).name.equals("right")){
				   if(count>0) {
					   count--;
					   String problem = problems.get(count);
					   text.setTextColor(Color.BLACK);
					   text.setText(problem);
				   }
			   } else {
		     int result = Integer.valueOf(predictions.get(0).name);

		     if(questions.getAnswer() == result) {
		    	 text.setTextColor(Color.GREEN);
		    	 
		    	 String problem = questions.problem + " = " + result;
		    	 problems.set(count, problem);
		    	 
		    	 text.setText(problem);
		    	 scoreView.setText("Score: " + ++score);
		    	 //	 text.setTextColor(Color.BLACK);
		    	 Toast.makeText(this, "Good Job Sport", Toast.LENGTH_LONG).show();
		    	 
		    	 gestures.setGestureVisible(false);

//		    	 text.setText(questions.genProblem());
		     } else
		     {
		    	 String problem = questions.problem + " = " + result;
		    	 problems.set(count, problem);
		    	 
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
