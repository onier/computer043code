package test.demo.graphics;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Picture;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

public class Test extends GraphicsActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(new SampleView(this));
	}

	private static class SampleView extends View {
		private Paint[] mPaints;
		private Paint mFramePaint;
		private boolean[] mUseCenters;
		private RectF[] mOvals;
		private RectF mBigOval;
		private float mStart;
		private float mSweep;
		private int mBigIndex;

		private static final float SWEEP_INC = 2;
		private static final float START_INC = 15;

		public SampleView(Context context) {
			super(context);

			mPaints = new Paint[4];
			mUseCenters = new boolean[4];
			mOvals = new RectF[4];

			mPaints[0] = new Paint();
			mPaints[0].setAntiAlias(true);
			mPaints[0].setStyle(Paint.Style.FILL);
			mPaints[0].setColor(0x88FF0000);
			mUseCenters[0] = false;

			mPaints[1] = new Paint(mPaints[0]);
			mPaints[1].setColor(0x8800FF00);
			mUseCenters[1] = true;

			mPaints[2] = new Paint(mPaints[0]);
			mPaints[2].setStyle(Paint.Style.STROKE);
			mPaints[2].setStrokeWidth(4);
			mPaints[2].setColor(0x880000FF);
			mUseCenters[2] = false;

			mPaints[3] = new Paint(mPaints[2]);
			mPaints[3].setColor(0x88888888);
			mUseCenters[3] = true;

			mBigOval = new RectF(40, 10, 280, 250);

			mOvals[0] = new RectF(10, 270, 70, 330);
			mOvals[1] = new RectF(90, 270, 150, 330);
			mOvals[2] = new RectF(170, 270, 230, 330);
			mOvals[3] = new RectF(250, 270, 310, 330);

			mFramePaint = new Paint();
			mFramePaint.setAntiAlias(true);
			mFramePaint.setStyle(Paint.Style.STROKE);
			mFramePaint.setStrokeWidth(0);
		}

		private void drawArcs(Canvas canvas, RectF oval, boolean useCenter, Paint paint) {
			canvas.drawRect(oval, mFramePaint);
			canvas.drawArc(oval, mStart, mSweep, useCenter, paint);
		}

		@Override
		protected void onDraw(Canvas canvas) {
			canvas.drawColor(Color.WHITE);
			drawArcs(canvas, mBigOval, mUseCenters[mBigIndex], mPaints[mBigIndex]);

			for (int i = 0; i < 4; i++) {
				drawArcs(canvas, mOvals[i], mUseCenters[i], mPaints[i]);
			}

			mSweep += SWEEP_INC;
			if (mSweep > 360) {
				mSweep -= 360;
				mStart += START_INC;
				if (mStart >= 360) {
					mStart -= 360;
				}
				mBigIndex = (mBigIndex + 1) % mOvals.length;
			}
			invalidate();
		}
	}
}

class GraphicsActivity extends Activity {
	// set to true to test Picture
	private static final boolean TEST_PICTURE = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public void setContentView(View view) {
		if (TEST_PICTURE) {
			ViewGroup vg = new PictureLayout(this);
			vg.addView(view);
			view = vg;
		}

		super.setContentView(view);
	}
}

class PictureLayout extends ViewGroup {
	private final Picture mPicture = new Picture();

	public PictureLayout(Context context) {
		super(context);
	}

	public PictureLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	public void addView(View child) {
		if (getChildCount() > 1) {
			throw new IllegalStateException("PictureLayout can host only one direct child");
		}

		super.addView(child);
	}

	@Override
	public void addView(View child, int index) {
		if (getChildCount() > 1) {
			throw new IllegalStateException("PictureLayout can host only one direct child");
		}

		super.addView(child, index);
	}

	@Override
	public void addView(View child, LayoutParams params) {
		if (getChildCount() > 1) {
			throw new IllegalStateException("PictureLayout can host only one direct child");
		}

		super.addView(child, params);
	}

	@Override
	public void addView(View child, int index, LayoutParams params) {
		if (getChildCount() > 1) {
			throw new IllegalStateException("PictureLayout can host only one direct child");
		}

		super.addView(child, index, params);
	}

	@Override
	protected LayoutParams generateDefaultLayoutParams() {
		return new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		final int count = getChildCount();

		int maxHeight = 0;
		int maxWidth = 0;

		for (int i = 0; i < count; i++) {
			final View child = getChildAt(i);
			if (child.getVisibility() != GONE) {
				measureChild(child, widthMeasureSpec, heightMeasureSpec);
			}
		}

		maxWidth += getPaddingLeft() + getPaddingRight();
		maxHeight += getPaddingTop() + getPaddingBottom();

		Drawable drawable = getBackground();
		if (drawable != null) {
			maxHeight = Math.max(maxHeight, drawable.getMinimumHeight());
			maxWidth = Math.max(maxWidth, drawable.getMinimumWidth());
		}

		setMeasuredDimension(resolveSize(maxWidth, widthMeasureSpec), resolveSize(maxHeight, heightMeasureSpec));
	}

	private void drawPict(Canvas canvas, int x, int y, int w, int h, float sx, float sy) {
		canvas.save();
		canvas.translate(x, y);
		canvas.clipRect(0, 0, w, h);
		canvas.scale(0.5f, 0.5f);
		canvas.scale(sx, sy, w, h);
		canvas.drawPicture(mPicture);
		canvas.restore();
	}

	@Override
	protected void dispatchDraw(Canvas canvas) {
		super.dispatchDraw(mPicture.beginRecording(getWidth(), getHeight()));
		mPicture.endRecording();

		int x = getWidth() / 2;
		int y = getHeight() / 2;

		if (false) {
			canvas.drawPicture(mPicture);
		} else {
			drawPict(canvas, 0, 0, x, y, 1, 1);
			drawPict(canvas, x, 0, x, y, -1, 1);
			drawPict(canvas, 0, y, x, y, 1, -1);
			drawPict(canvas, x, y, x, y, -1, -1);
		}
	}

	@Override
	public ViewParent invalidateChildInParent(int[] location, Rect dirty) {
		location[0] = getLeft();
		location[1] = getTop();
		dirty.set(0, 0, getWidth(), getHeight());
		return getParent();
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		final int count = super.getChildCount();

		for (int i = 0; i < count; i++) {
			final View child = getChildAt(i);
			if (child.getVisibility() != GONE) {
				final int childLeft = getPaddingLeft();
				final int childTop = getPaddingTop();
				child.layout(childLeft, childTop, childLeft + child.getMeasuredWidth(), childTop + child.getMeasuredHeight());

			}
		}
	}
}
