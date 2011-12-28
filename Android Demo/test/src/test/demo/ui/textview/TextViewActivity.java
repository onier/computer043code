package test.demo.ui.textview;

import test.demo.R;
import test.demo.SecondActivity;
import test.demo.animationframe.Animations;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class TextViewActivity extends Activity {
	public void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		this.setContentView(R.layout.textview_demo);
		Button button = (Button) this.findViewById(R.id.en4you);
		button.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				Intent intent = new Intent(TextViewActivity.this, EU4You.class);
				startActivity(intent);
			}
		});

		button = (Button) this.findViewById(R.id.AutoComplete);
		button.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				Intent intent = new Intent(TextViewActivity.this, AutoCompleteText.class);
				startActivity(intent);
				TextViewActivity.this.overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
			}
		});

		button = (Button) this.findViewById(R.id.AutoCompleteSize);
		button.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				Intent intent = new Intent(TextViewActivity.this, AutoCompleteSize.class);
				startActivity(intent);
				TextViewActivity.this.overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
			}
		});

		button = (Button) this.findViewById(R.id.CustomTextView);
		button.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				Intent intent = new Intent(TextViewActivity.this, CustomTextView.class);
				startActivity(intent);
				TextViewActivity.this.overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
			}
		});
	}
}
