package test.demo.ui.scrollview;

import test.demo.R;
import test.demo.SecondActivity;
import test.demo.event.ScrollTest;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class ScrollViewActivity extends Activity {
	public void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		this.setContentView(R.layout.scrollview);

		Button button = (Button) this.findViewById(R.id.scrollview_button1);
		button.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				Intent intent = new Intent(ScrollViewActivity.this, ScrollView1.class);
				startActivity(intent);
			}
		});
	}
}
