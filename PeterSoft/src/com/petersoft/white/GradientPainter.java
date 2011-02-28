package com.petersoft.white;

import java.awt.Color;
import java.awt.Component;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;

import sun.swing.CachedPainter;

public class GradientPainter extends CachedPainter {
	/**
	 * Instance used for painting. This is the only instance that is ever
	 * created.
	 */
	public static final GradientPainter INSTANCE = new GradientPainter(8);

	// Size of images to create. For vertical gradients this is the width,
	// otherwise it's the height.
	private static final int IMAGE_SIZE = 64;

	/**
	 * This is the actual width we're painting in, or last painted to.
	 */
	private int w;
	/**
	 * This is the actual height we're painting in, or last painted to
	 */
	private int h;

	GradientPainter(int count) {
		super(count);
	}

	public void paint(Component c, Graphics2D g, java.util.List gradient, int x, int y, int w, int h, boolean isVertical) {
		int imageWidth;
		int imageHeight;
		if (isVertical) {
			imageWidth = IMAGE_SIZE;
			imageHeight = h;
		} else {
			imageWidth = w;
			imageHeight = IMAGE_SIZE;
		}
		synchronized (c.getTreeLock()) {
			this.w = w;
			this.h = h;
			paint(c, g, x, y, imageWidth, imageHeight, gradient, isVertical);
		}
	}

	protected void paintToImage(Component c, Image image, Graphics g, int w, int h, Object[] args) {
		Graphics2D g2 = (Graphics2D) g;
		java.util.List gradient = (java.util.List) args[0];
		boolean isVertical = ((Boolean) args[1]).booleanValue();
		// Render to the VolatileImage
		if (isVertical) {
			drawVerticalGradient(g2, ((Number) gradient.get(0)).floatValue(), ((Number) gradient.get(1)).floatValue(), (Color) gradient.get(2), (Color) gradient.get(3), (Color) gradient.get(4), w, h);
		} else {
			drawHorizontalGradient(g2, ((Number) gradient.get(0)).floatValue(), ((Number) gradient.get(1)).floatValue(), (Color) gradient.get(2), (Color) gradient.get(3), (Color) gradient.get(4), w,
					h);
		}
	}

	protected void paintImage(Component c, Graphics g, int x, int y, int imageW, int imageH, Image image, Object[] args) {
		boolean isVertical = ((Boolean) args[1]).booleanValue();
		// Render to the screen
		g.translate(x, y);
		if (isVertical) {
			for (int counter = 0; counter < w; counter += IMAGE_SIZE) {
				int tileSize = Math.min(IMAGE_SIZE, w - counter);
				g.drawImage(image, counter, 0, counter + tileSize, h, 0, 0, tileSize, h, null);
			}
		} else {
			for (int counter = 0; counter < h; counter += IMAGE_SIZE) {
				int tileSize = Math.min(IMAGE_SIZE, h - counter);
				g.drawImage(image, 0, counter, w, counter + tileSize, 0, 0, w, tileSize, null);
			}
		}
		g.translate(-x, -y);
	}

	private void drawVerticalGradient(Graphics2D g, float ratio1, float ratio2, Color c1, Color c2, Color c3, int w, int h) {
		int mid = (int) (ratio1 * h);
		int mid2 = (int) (ratio2 * h);
		if (mid > 0) {
			g.setPaint(getGradient((float) 0, (float) 0, c1, (float) 0, (float) mid, c2));
			g.fillRect(0, 0, w, mid);
		}
		if (mid2 > 0) {
			g.setColor(c2);
			g.fillRect(0, mid, w, mid2);
		}
		if (mid > 0) {
			g.setPaint(getGradient((float) 0, (float) mid + mid2, c2, (float) 0, (float) mid * 2 + mid2, c1));
			g.fillRect(0, mid + mid2, w, mid);
		}
		if (h - mid * 2 - mid2 > 0) {
			g.setPaint(getGradient((float) 0, (float) mid * 2 + mid2, c1, (float) 0, (float) h, c3));
			g.fillRect(0, mid * 2 + mid2, w, h - mid * 2 - mid2);
		}
	}

	private void drawHorizontalGradient(Graphics2D g, float ratio1, float ratio2, Color c1, Color c2, Color c3, int w, int h) {
		int mid = (int) (ratio1 * w);
		int mid2 = (int) (ratio2 * w);
		if (mid > 0) {
			g.setPaint(getGradient((float) 0, (float) 0, c1, (float) mid, (float) 0, c2));
			g.fillRect(0, 0, mid, h);
		}
		if (mid2 > 0) {
			g.setColor(c2);
			g.fillRect(mid, 0, mid2, h);
		}
		if (mid > 0) {
			g.setPaint(getGradient((float) mid + mid2, (float) 0, c2, (float) mid * 2 + mid2, (float) 0, c1));
			g.fillRect(mid + mid2, 0, mid, h);
		}
		if (w - mid * 2 - mid2 > 0) {
			g.setPaint(getGradient((float) mid * 2 + mid2, (float) 0, c1, w, (float) 0, c3));
			g.fillRect(mid * 2 + mid2, 0, w - mid * 2 - mid2, h);
		}
	}

	private GradientPaint getGradient(float x1, float y1, Color c1, float x2, float y2, Color c2) {
		return new GradientPaint(x1, y1, c1, x2, y2, c2, true);
	}
}
