package test.demo;

import test.demo.animationframe.Animations;
import test.demo.animationframe.LayoutAnimation;
import test.demo.event.ScrollTest;
import test.demo.graphics.DrawActivity;
import test.demo.graphics.Test;
import test.demo.listactivity.ListActivityDemo;
import test.demo.ui.scrollview.ScrollViewActivity;
import test.demo.ui.textview.TextViewActivity;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class SecondActivity extends Activity {
	private LogMessage message;

	public void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		this.setContentView(R.layout.second);
		EditText text = (EditText) this.findViewById(R.id.editText1);
		message = (LogMessage) this.getIntent().getSerializableExtra("message");
		if (message != null) {
			text.setText(message.getMessage());
			text.setSelection(message.getMessage().length());
		}
		Button button = (Button) this.findViewById(R.id.ok);
		button.setOnClickListener(new OnClickListener() {

			public void onClick(View arg0) {
				if (message != null) {
					EditText text = (EditText) findViewById(R.id.editText1);
					message.setMessage(text.getText().toString());
					Intent intent = new Intent(SecondActivity.this, TestActivity.class);
					intent.putExtra("message", message);
					SecondActivity.this.startActivity(intent);
				}
			}
		});
		button = (Button) this.findViewById(R.id.back);
		button.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				Intent intent = new Intent(SecondActivity.this, LayoutAnimation.class);
				startActivity(intent);
			}
		});
		button = (Button) this.findViewById(R.id.cancel);
		button.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				Intent intent = new Intent(SecondActivity.this, AnimationActivity.class);
				startActivity(intent);
				SecondActivity.this.overridePendingTransition(R.anim.alpha_fade_out, R.anim.alpha_fade_in);
			}
		});
		button = (Button) this.findViewById(R.id.aniamtins);
		button.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				Intent intent = new Intent(SecondActivity.this, Animations.class);
				startActivity(intent);
			}
		});

		button = (Button) this.findViewById(R.id.texView_demo_button);
		button.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				Intent intent = new Intent(SecondActivity.this, TextViewActivity.class);
				startActivity(intent);
			}
		});

		button = (Button) this.findViewById(R.id.graphics);
		button.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				Intent intent = new Intent(SecondActivity.this, Test.class);
				startActivity(intent);
			}
		});
		button = (Button) this.findViewById(R.id.draw);
		button.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				Intent intent = new Intent(SecondActivity.this, DrawActivity.class);
				startActivity(intent);
			}
		});
		
		button = (Button) this.findViewById(R.id.event);
		button.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				Intent intent = new Intent(SecondActivity.this, ScrollTest.class);
				startActivity(intent);
			}
		});
		
		button = (Button) this.findViewById(R.id.scrollViewButton);
		button.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				Intent intent = new Intent(SecondActivity.this, ScrollViewActivity.class);
				startActivity(intent);
			}
		});
		
		button = (Button) this.findViewById(R.id.listActivity);
		button.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				Intent intent = new Intent(SecondActivity.this, ListActivityDemo.class);
				startActivity(intent);
			}
		});
	}
}
