package com.petersoft.advancedswing.searchtextfield;

import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.ImageIcon;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.text.Document;

public class JSearchTextField extends JTextField implements MouseListener, MouseMotionListener {
	Image cross = new ImageIcon(JSearchTextField.class.getResource("cross.png")).getImage();
	int crossHeight = cross.getHeight(null);
	int crossWidth = cross.getWidth(null);

	public JSearchTextField() {
		super();
		initGUI();
	}

	public JSearchTextField(String text) {
		super(text);
		initGUI();
	}

	public JSearchTextField(int columns) {
		super(columns);
		initGUI();
	}

	public JSearchTextField(String text, int columns) {
		super(text, columns);
		initGUI();
	}

	public JSearchTextField(Document doc, String text, int columns) {
		super(doc, text, columns);
		initGUI();
	}

	private void initGUI() {
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
		super.setBorder(new EmptyBorder(0, 2, 0, crossWidth + 1));
	}

	public void paint(Graphics g) {
		super.paint(g);
		if (this.getText().length() > 0) {
			g.drawImage(cross, getWidth() - crossWidth - 1, (getHeight() - crossHeight) / 2, null);
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (e.getX() >= getWidth() - crossWidth) {
			this.setText("");
			for (KeyListener l : this.getKeyListeners()) {
				l.keyPressed(null);
				l.keyReleased(null);
				l.keyTyped(null);
			}
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {

	}

	@Override
	public void mousePressed(MouseEvent e) {

	}

	@Override
	public void mouseReleased(MouseEvent e) {

	}

	@Override
	public void mouseDragged(MouseEvent e) {

	}

	@Override
	public void mouseMoved(MouseEvent e) {
		if (e.getX() >= getWidth() - crossWidth) {
			setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		} else {
			setCursor(Cursor.getPredefinedCursor(Cursor.TEXT_CURSOR));
		}
	}
}
