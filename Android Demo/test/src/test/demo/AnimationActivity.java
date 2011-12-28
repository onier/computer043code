package test.demo;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

public class AnimationActivity extends Activity implements View.OnClickListener {

	View viewToAnimate;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.animation);
		
		Button button = (Button) findViewById(R.id.animationbutton);
		button.setOnClickListener(this);

		viewToAnimate = findViewById(R.id.theView);
	}

	public void onClick(View v) {
		if (viewToAnimate.getVisibility() == View.VISIBLE) {
			Animation out = AnimationUtils.makeOutAnimation(this, true);
			viewToAnimate.startAnimation(out);
			viewToAnimate.setVisibility(View.INVISIBLE);
		} else {
			Animation in = AnimationUtils.loadAnimation(this, android.R.anim.fade_in);
			viewToAnimate.startAnimation(in);
			viewToAnimate.setVisibility(View.VISIBLE);
		}
	}
}