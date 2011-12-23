package test.demo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class TestActivity extends Activity {
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.first);
		final EditText edit = (EditText) this.findViewById(R.id.inputEditText);
		Button button = (Button) this.findViewById(R.id.ok);
		button.setOnClickListener(new OnClickListener() {

			public void onClick(View arg0) {
				Intent intent = new Intent(TestActivity.this, SecondActivity.class);
				intent.putExtra("message", new LogMessage(edit.getText().toString()));
				TestActivity.this.startActivity(intent);
//				TestActivity.this.overridePendingTransition(R.anim.second_activity_enter, R.anim.second_activity_out);
			}
		});
		LogMessage message = (LogMessage) this.getIntent().getSerializableExtra("message");
		if (message != null) {
			edit.setText(message.getMessage());
			edit.setSelection(message.getMessage().length());
		}
	}
}