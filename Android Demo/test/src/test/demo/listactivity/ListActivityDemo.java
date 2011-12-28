package test.demo.listactivity;

import test.demo.R;
import test.demo.SecondActivity;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class ListActivityDemo extends Activity {
	public void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		this.setContentView(R.layout.listactivitydemo);
		Button button = (Button) this.findViewById(R.id.list1);
		button.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				Intent intent = new Intent(ListActivityDemo.this, List1.class);
				startActivity(intent);
			}
		});
		
		button = (Button) this.findViewById(R.id.list2);
		button.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				Intent intent = new Intent(ListActivityDemo.this, List2.class);
				startActivity(intent);
			}
		});
		
		button = (Button) this.findViewById(R.id.dynamic);
		button.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				Intent intent = new Intent(ListActivityDemo.this, DynamicDemo.class);
				startActivity(intent);
			}
		});
		
		button = (Button) this.findViewById(R.id.multi);
		button.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				Intent intent = new Intent(ListActivityDemo.this, List5.class);
				startActivity(intent);
			}
		});
	}
}
