package com.petersoft.white;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JSlider;
import javax.swing.UIManager;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicSliderUI;
import javax.swing.plaf.metal.MetalLookAndFeel;

public class SliderUI extends BasicSliderUI {
	Image horizThumbIcon = new ImageIcon(ScrollBarUI.class.getResource("images/PSlider/horizontalThumb.png")).getImage();
	Image vertThumbIcon = new ImageIcon(ScrollBarUI.class.getResource("images/PSlider/verticalThumb.png")).getImage();
	protected static int tickLength;
	protected static int trackWidth;
	protected final int TICK_BUFFER = 4;

	public SliderUI(JSlider b) {
		super(b);
	}

	public static ComponentUI createUI(JComponent b) {
		return new SliderUI((JSlider) b);
	}

	public void installUI(JComponent c) {
		super.installUI(c);
		c.setBackground(Color.white);
		trackWidth = ((Integer) UIManager.get("Slider.trackWidth")).intValue();
		tickLength = ((Integer) UIManager.get("Slider.majorTickLength")).intValue();
	}

	public int getTickLength() {
		return slider.getOrientation() == JSlider.HORIZONTAL ? tickLength + TICK_BUFFER + 1 : tickLength + TICK_BUFFER + 3;
	}

	protected int getTrackWidth() {
		// This strange calculation is here to keep the
		// track in proportion to the thumb.
		final double kIdealTrackWidth = 7.0;
		final double kIdealThumbHeight = 16.0;
		final double kWidthScalar = kIdealTrackWidth / kIdealThumbHeight;

		if (slider.getOrientation() == JSlider.HORIZONTAL) {
			return (int) (kWidthScalar * thumbRect.height);
		} else {
			return (int) (kWidthScalar * thumbRect.width);
		}
	}

	protected int getTrackLength() {
		if (slider.getOrientation() == JSlider.HORIZONTAL) {
			return trackRect.width;
		}
		return trackRect.height;
	}

	protected int getThumbOverhang() {
		return (int) (getThumbSize().getHeight() - getTrackWidth()) / 2;
	}

	public void paintThumb(Graphics g) {
		if (slider.getOrientation() == JSlider.HORIZONTAL) {
			g.drawImage(horizThumbIcon, thumbRect.x, thumbRect.y, null);
		} else {
			g.drawImage(vertThumbIcon, thumbRect.x, thumbRect.y, null);
		}
	}

	public void paintTrack(Graphics g) {
		// Color trackColor = !slider.isEnabled() ?
		// MetalLookAndFeel.getControlShadow() : slider.getForeground();

		boolean leftToRight = slider.getComponentOrientation().isLeftToRight();

		g.translate(trackRect.x, trackRect.y);

		int trackLeft = 0;
		int trackTop = 0;
		int trackRight = 0;
		int trackBottom = 0;

		// Draw the track
		if (slider.getOrientation() == JSlider.HORIZONTAL) {
			trackBottom = (trackRect.height - 1) - getThumbOverhang();
			trackTop = trackBottom - (getTrackWidth() - 1);
			trackRight = trackRect.width - 1;
		} else {
			if (leftToRight) {
				trackLeft = (trackRect.width - getThumbOverhang()) - getTrackWidth();
				trackRight = (trackRect.width - getThumbOverhang()) - 1;
			} else {
				trackLeft = getThumbOverhang();
				trackRight = getThumbOverhang() + getTrackWidth() - 1;
			}
			trackBottom = trackRect.height - 1;
		}

		if (slider.isEnabled()) {
			g.setColor(MetalLookAndFeel.getControlDarkShadow());
			g.drawRect(trackLeft, trackTop, (trackRight - trackLeft) - 1, (trackBottom - trackTop) - 1);

			g.setColor(MetalLookAndFeel.getControlHighlight());
			g.drawLine(trackLeft + 1, trackBottom, trackRight, trackBottom);
			g.drawLine(trackRight, trackTop + 1, trackRight, trackBottom);

			g.setColor(MetalLookAndFeel.getControlShadow());
			g.drawLine(trackLeft + 1, trackTop + 1, trackRight - 2, trackTop + 1);
			g.drawLine(trackLeft + 1, trackTop + 1, trackLeft + 1, trackBottom - 2);
		} else {
			g.setColor(MetalLookAndFeel.getControlShadow());
			g.drawRect(trackLeft, trackTop, (trackRight - trackLeft) - 1, (trackBottom - trackTop) - 1);
		}

		// Draw the fill
		int middleOfThumb = 0;
		int fillTop = 0;
		int fillLeft = 0;
		int fillBottom = 0;
		int fillRight = 0;

		if (slider.getOrientation() == JSlider.HORIZONTAL) {
			middleOfThumb = thumbRect.x + (thumbRect.width / 2);
			middleOfThumb -= trackRect.x; // To compensate for the
			// g.translate()
			fillTop = !slider.isEnabled() ? trackTop : trackTop + 1;
			fillBottom = !slider.isEnabled() ? trackBottom - 1 : trackBottom - 2;

			if (!drawInverted()) {
				fillLeft = !slider.isEnabled() ? trackLeft : trackLeft + 1;
				fillRight = middleOfThumb;
			} else {
				fillLeft = middleOfThumb;
				fillRight = !slider.isEnabled() ? trackRight - 1 : trackRight - 2;
			}
		} else {
			middleOfThumb = thumbRect.y + (thumbRect.height / 2);
			middleOfThumb -= trackRect.y; // To compensate for the
			// g.translate()
			fillLeft = !slider.isEnabled() ? trackLeft : trackLeft + 1;
			fillRight = !slider.isEnabled() ? trackRight - 1 : trackRight - 2;

			if (!drawInverted()) {
				fillTop = middleOfThumb;
				fillBottom = !slider.isEnabled() ? trackBottom - 1 : trackBottom - 2;
			} else {
				fillTop = !slider.isEnabled() ? trackTop : trackTop + 1;
				fillBottom = middleOfThumb;
			}
		}

		if (slider.isEnabled()) {
			g.setColor(slider.getBackground());
			g.drawLine(fillLeft, fillTop, fillRight, fillTop);
			g.drawLine(fillLeft, fillTop, fillLeft, fillBottom);

			g.setColor(Color.lightGray);
			g.fillRect(fillLeft + 1, fillTop + 1, fillRight - fillLeft, fillBottom - fillTop);
		} else {
			g.setColor(Color.gray);
			g.fillRect(fillLeft, fillTop, fillRight - fillLeft, trackBottom - trackTop);
		}

		g.translate(-trackRect.x, -trackRect.y);
	}

}
