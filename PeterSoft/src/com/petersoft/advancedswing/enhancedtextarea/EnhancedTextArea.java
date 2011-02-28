package com.petersoft.advancedswing.enhancedtextarea;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;
import javax.swing.WindowConstants;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter.DefaultHighlightPainter;
import javax.swing.text.Element;
import javax.swing.text.Highlighter;

import com.petersoft.advancedswing.searchtextfield.JSearchTextField;

/**
 * This code was edited or generated using CloudGarden's Jigloo SWT/Swing GUI
 * Builder, which is free for non-commercial use. If Jigloo is being used
 * commercially (ie, by a corporation, company or business for any purpose
 * whatever) then you should purchase a license for each developer using Jigloo.
 * Please visit www.cloudgarden.com for details. Use of Jigloo implies
 * acceptance of these licensing terms. A COMMERCIAL LICENSE HAS NOT BEEN
 * PURCHASED FOR THIS MACHINE, SO JIGLOO OR THIS CODE CANNOT BE USED LEGALLY FOR
 * ANY CORPORATE OR COMMERCIAL PURPOSE.
 */
public class EnhancedTextArea extends javax.swing.JPanel implements LogFileTailerListener, DocumentListener {
	private JToolBar jToolBar1;
	private JScrollPane jScrollPane1;
	private JLabel jSearchLabel;
	private JPanel jStatusPanel;
	private JSearchTextField jSearchTextField;
	private JLabel jStatusLabel;
	private JButton jFontBiggerButton;
	private JButton jFontSmallerButton;
	private JToggleButton jLineWrapButton;
	private JTextArea jTextArea1;
	private JTextArea lines;

	/**
	 * Auto-generated main method to display this JPanel inside a new JFrame.
	 */
	public static void main(String[] args) {
		JFrame frame = new JFrame();
		frame.getContentPane().add(new EnhancedTextArea());
		frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
	}

	public EnhancedTextArea() {
		super();
		initGUI();
	}

	private void initGUI() {
		try {
			BorderLayout thisLayout = new BorderLayout();
			this.setLayout(thisLayout);
			this.setPreferredSize(new java.awt.Dimension(725, 290));
			{
				jToolBar1 = new JToolBar();
				this.add(jToolBar1, BorderLayout.NORTH);
				{
					jLineWrapButton = new JToggleButton();
					jToolBar1.add(jLineWrapButton);
					jLineWrapButton.setText("Wrap");
					jLineWrapButton.setIcon(new ImageIcon(getClass().getClassLoader().getResource("com/petersoft/advancedswing/enhancedtextarea/linewrap.png")));
					jLineWrapButton.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent evt) {
							jLineWrapButtonActionPerformed(evt);
						}
					});
				}
				{
					jFontBiggerButton = new JButton();
					jToolBar1.add(jFontBiggerButton);
					jFontBiggerButton.setIcon(new ImageIcon(getClass().getClassLoader().getResource("com/petersoft/advancedswing/enhancedtextarea/font_add.png")));
					jFontBiggerButton.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent evt) {
							jFontBiggerButtonActionPerformed(evt);
						}
					});
				}
				{
					jFontSmallerButton = new JButton();
					jToolBar1.add(jFontSmallerButton);
					jFontSmallerButton.setIcon(new ImageIcon(getClass().getClassLoader().getResource("com/petersoft/advancedswing/enhancedtextarea/font_delete.png")));
					jFontSmallerButton.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent evt) {
							jFontSmallerButtonActionPerformed(evt);
						}
					});
				}
				{
					jSearchTextField = new JSearchTextField();
					jToolBar1.add(jSearchTextField);
					jSearchTextField.setMaximumSize(new java.awt.Dimension(100, 22));
					jSearchTextField.setPreferredSize(new java.awt.Dimension(100, 22));
					jSearchTextField.addKeyListener(new KeyAdapter() {
						public void keyReleased(KeyEvent evt) {
							jSearchTextFieldKeyReleased(evt);
						}
					});

				}
			}
			{
				jScrollPane1 = new JScrollPane();
				this.add(jScrollPane1, BorderLayout.CENTER);
				{
					jTextArea1 = new JTextArea();
					jTextArea1.getDocument().addDocumentListener(this);
					lines = new JTextArea("1");
					lines.setBackground(new Color(200, 230, 245));
					lines.setEditable(false);
					jScrollPane1.setRowHeaderView(lines);

					jTextArea1.getDocument().addDocumentListener(new DocumentListener() {
						public String getText() {
							int caretPosition = jTextArea1.getDocument().getLength();
							Element root = jTextArea1.getDocument().getDefaultRootElement();
							String text = "1" + System.getProperty("line.separator");
							for (int i = 2; i < root.getElementIndex(caretPosition) + 2; i++) {
								text += i + System.getProperty("line.separator");
							}
							return text;
						}

						@Override
						public void changedUpdate(DocumentEvent de) {
							lines.setText(getText());
						}

						@Override
						public void insertUpdate(DocumentEvent de) {
							lines.setText(getText());
						}

						@Override
						public void removeUpdate(DocumentEvent de) {
							lines.setText(getText());
						}

					});
					jScrollPane1.setViewportView(jTextArea1);
				}
			}
			{
				jStatusPanel = new JPanel();
				FlowLayout jStatusPanelLayout = new FlowLayout();
				jStatusPanelLayout.setAlignment(FlowLayout.LEFT);
				jStatusPanel.setLayout(jStatusPanelLayout);
				this.add(jStatusPanel, BorderLayout.SOUTH);
				{
					jStatusLabel = new JLabel();
					jStatusPanel.add(jStatusLabel);
				}
				{
					jSearchLabel = new JLabel();
					jStatusPanel.add(jSearchLabel);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public JTextArea getTextArea() {
		return jTextArea1;
	}

	public void setText(String text) {
		jTextArea1.setText(text);
	}

	private void updateStatus() {
		jStatusLabel.setText("Line:" + jTextArea1.getText().split("\n").length + ", Char:" + jTextArea1.getText().length());
	}

	private void jLineWrapButtonActionPerformed(ActionEvent evt) {
		jTextArea1.setLineWrap(jLineWrapButton.isSelected());
	}

	private void jFontSmallerButtonActionPerformed(ActionEvent evt) {
		Font f = jTextArea1.getFont();
		Font newFont = new Font(f.getFontName(), f.getStyle(), f.getSize() - 1);
		jTextArea1.setFont(newFont);
		lines.setFont(newFont);
	}

	private void jFontBiggerButtonActionPerformed(ActionEvent evt) {
		Font f = jTextArea1.getFont();
		Font newFont = new Font(f.getFontName(), f.getStyle(), f.getSize() + 1);
		jTextArea1.setFont(newFont);
		lines.setFont(newFont);
	}

	public void addTrailListener(File file) {
		LogFileTailer tailer = new LogFileTailer(file, 1000, false);
		tailer.addLogFileTailerListener(this);
		tailer.start();
	}

	@Override
	public void newLogFileLine(String line) {
		jTextArea1.append(line + System.getProperty("line.separator"));
		jTextArea1.setCaretPosition(jTextArea1.getDocument().getLength());
		updateStatus();
	}

	private void jSearchTextFieldKeyReleased(KeyEvent evt) {
		String text = jTextArea1.getText().toLowerCase();
		String searchPattern = jSearchTextField.getText().toLowerCase();

		if (evt.getKeyCode() == 10) {
			int caretPosition = jTextArea1.getCaretPosition();
			boolean found = false;
			for (int j = caretPosition + 1; j < text.length() - searchPattern.length(); j += 1) {
				if (searchPattern.equals(text.substring(j, j + searchPattern.length()))) {
					jTextArea1.setCaretPosition(j);
					found = true;
					break;
				}
			}
			if (!found) {
				for (int j = 0; j < caretPosition; j++) {
					if (searchPattern.equals(text.substring(j, j + searchPattern.length()))) {
						jTextArea1.setCaretPosition(j);
						break;
					}
				}
			}
		}
		
		if (searchPattern.length() > 0) {
			Highlighter h = jTextArea1.getHighlighter();
			DefaultHighlightPainter painter = new DefaultHighlightPainter(Color.YELLOW);
			DefaultHighlightPainter painter2 = new DefaultHighlightPainter(Color.RED);
			h.removeAllHighlights();

			int count = 0;
			boolean isCurrent = false;
			for (int j = 0; j < text.length(); j += 1) {
				if (j < text.length() - searchPattern.length() && searchPattern.equals(text.substring(j, j + searchPattern.length()))) {
					count++;
					try {
						if (j >= jTextArea1.getCaretPosition() && isCurrent == false) {
							h.addHighlight(j, j + searchPattern.length(), painter2);
							isCurrent = true;
						} else {
							h.addHighlight(j, j + searchPattern.length(), painter);
						}
					} catch (BadLocationException ble) {
					}
				}
			}
			jSearchLabel.setText("Match:" + count);
		} else {
			jSearchLabel.setText("");
			Highlighter h = jTextArea1.getHighlighter();
			h.removeAllHighlights();
		}
	}

	@Override
	public void insertUpdate(DocumentEvent e) {
		updateStatus();
	}

	@Override
	public void removeUpdate(DocumentEvent e) {
		updateStatus();
	}

	@Override
	public void changedUpdate(DocumentEvent e) {
		updateStatus();
	}

}
