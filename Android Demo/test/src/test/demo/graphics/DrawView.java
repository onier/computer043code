package test.demo.graphics;

import java.io.InputStream;

import test.demo.R;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Paint.Cap;
import android.graphics.Shader;
import android.graphics.drawable.ShapeDrawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class DrawView extends View {
	private Bitmap bitmap;
	private Canvas imageCanvas;
	private Point2D startPoint;
	private Point2D endPoint;

	public DrawView(Context context) {
		super(context);
	}

	public DrawView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public DrawView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);

	}

	public boolean onTouchEvent(MotionEvent event) {
		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			this.onDown(event);
		}
		if (event.getAction() == MotionEvent.ACTION_MOVE) {
			onMove(event);
		}
		if (event.getAction() == MotionEvent.ACTION_UP) {
			onUp(event);
		}
		return true;
	}

	public void onUp(MotionEvent event) {
		System.out.println("onUp");
		endPoint = new Point2D(event.getX(), event.getY());
		this.invalidate();
	}

	public void onMove(MotionEvent event) {
		System.out.println("onMove");
		endPoint = new Point2D(event.getX(), event.getY());
		this.invalidate();
	}

	public void onDown(MotionEvent event) {
		System.out.println("onDown");
		startPoint = new Point2D(event.getX(), event.getY());
		// imageCanvas.drawLine(startPoint.x, startPoint.y, endPoint.x,
		// endPoint.y, null);
		// startPoint = null;
		// endPoint = null;
		this.invalidate();
	}

	public void onDraw(Canvas canvas) {
		canvas.drawColor(Color.WHITE);
		Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
		paint.setColor(Color.RED);
		paint.setAlpha(125);
		paint.setStrokeWidth(4f);
		paint.setStrokeCap(Cap.SQUARE);
		paint.setFakeBoldText(true);
		paint.setFlags(DRAWING_CACHE_QUALITY_AUTO);
		paint.setShader(new Shader());
		Bitmap image = getBitmap();
		Matrix matrix = new Matrix();
		matrix.postScale(-2, 1); // Mirror
		matrix.postTranslate(250, 180);
		// matrix.postTranslate(image.getWidth(), 0);
		canvas.drawBitmap(getBitmap(), matrix, new Paint());
		canvas.drawBitmap(getBitmap(), 0, 0, new Paint());
		// if (startPoint != null && endPoint != null) {
		// canvas.drawLine(startPoint.x, startPoint.y, endPoint.x, endPoint.y,
		// paint);
		// }
		// canvas.drawLines(new float[] { 0, 0, 100f, 100f }, new Paint());
		// RectF oval = new RectF(10, 10, 30, 40);
		// canvas.drawArc(oval, 0, 360, true, paint);
	}

	public Bitmap getBitmap() {
		if (bitmap == null) {
			// bitmap = Bitmap.createBitmap(this.getWidth(), this.getHeight(),
			// Config.ARGB_4444);
			// imageCanvas = new Canvas(bitmap);
			// onDraw(imageCanvas);
			// imageCanvas.drawColor(Color.WHITE);
			// Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
			// paint.setColor(Color.RED);
			// paint.setAlpha(125);
			// paint.setStrokeWidth(4f);
			// paint.setStrokeCap(Cap.SQUARE);
			// // paint.setFakeBoldText(true);
			// // paint.setFlags(DRAWING_CACHE_QUALITY_AUTO);
			// paint.setShader(new Shader());
			// // canvas.drawBitmap(getBitmap(), 0, 0, new Paint());
			// if (startPoint != null && endPoint != null) {
			// imageCanvas.drawLine(startPoint.x, startPoint.y, endPoint.x,
			// endPoint.y, paint);
			// }
			// imageCanvas.drawLines(new float[] { 0, 0, 100f, 100f }, new
			// Paint());
			// RectF oval = new RectF(10, 10, 30, 40);
			// imageCanvas.drawArc(oval, 0, 360, true, paint);

			InputStream inputStream = this.getContext().getResources().openRawResource(R.drawable.ic_launcher);
			bitmap = BitmapFactory.decodeStream(inputStream);
		}
		return bitmap;
	}

	class Point2D {
		float x;
		float y;

		public Point2D() {

		}

		public Point2D(float x, float y) {
			this.x = x;
			this.y = y;
		}
	}
}
