package test.demo.ui.textview;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnKeyListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import test.demo.R;

class TodoListItemView extends TextView {

	private Paint marginPaint;
	private Paint linePaint;
	private int paperColor;
	private float margin;

	public TodoListItemView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	private void init() {
		marginPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		marginPaint.setColor(Color.RED);
		linePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		linePaint.setColor(Color.BLUE);
		paperColor = Color.YELLOW;
		margin = Color.CYAN;
	}

	@Override
	public void onDraw(Canvas canvas) {
		canvas.drawColor(paperColor);
		canvas.drawLine(0, 0, getMeasuredHeight(), 0, linePaint);
		canvas.drawLine(0, getMeasuredHeight(), getMeasuredWidth(), getMeasuredHeight(), linePaint);
		canvas.drawLine(margin, 0, margin, getMeasuredHeight(), marginPaint);
		canvas.save();
		canvas.translate(margin, 0);
		super.onDraw(canvas);
		canvas.restore();
	}
}

public class CustomTextView extends Activity {
	@Override
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		setContentView(R.layout.customtextview);
		ListView myListView = (ListView) findViewById(R.id.myListView);
		final EditText myEditText = (EditText) findViewById(R.id.myEditText);
		final ArrayList<String> todoItems = new ArrayList<String>();
		int resID = R.layout.row;
		final ArrayAdapter<String> aa = new ArrayAdapter<String>(this, resID, todoItems);
		myListView.setAdapter(aa);
		myEditText.setOnKeyListener(new OnKeyListener() {
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				if (event.getAction() == KeyEvent.ACTION_DOWN)
					if (keyCode == KeyEvent.KEYCODE_DPAD_CENTER) {
						todoItems.add(0, myEditText.getText().toString());
						myEditText.setText("");
						aa.notifyDataSetChanged();
						return true;
					}
				return false;
			}
		});
	}
}