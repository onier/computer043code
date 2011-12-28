package test.demo.graphics;

import test.demo.R;
import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ScrollView;
import android.widget.TextView;

public class DrawActivity extends Activity {

	public void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		this.setContentView(R.layout.drawview);
		this.setTitle("Draw Demo");
		DrawView view = (DrawView) this.findViewById(R.id.drawArae);
		final TextView textView = (TextView) this.findViewById(R.id.infotext);
		// view.setOnTouchListener(new OnTouchListener() {
		//
		// public boolean onTouch(View v, MotionEvent event) {
		// textView.setText(event.getX() + "  " + event.getY());
		// return false;
		// }
		// });
		
	}
}
