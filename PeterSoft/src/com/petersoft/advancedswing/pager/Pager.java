package com.petersoft.advancedswing.pager;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.EventListenerList;

/**
* This code was edited or generated using CloudGarden's Jigloo
* SWT/Swing GUI Builder, which is free for non-commercial
* use. If Jigloo is being used commercially (ie, by a corporation,
* company or business for any purpose whatever) then you
* should purchase a license for each developer using Jigloo.
* Please visit www.cloudgarden.com for details.
* Use of Jigloo implies acceptance of these licensing terms.
* A COMMERCIAL LICENSE HAS NOT BEEN PURCHASED FOR
* THIS MACHINE, SO JIGLOO OR THIS CODE CANNOT BE USED
* LEGALLY FOR ANY CORPORATE OR COMMERCIAL PURPOSE.
*/
public class Pager extends JPanel {
	private JButton jFirstPageButton;
	private JButton jNextPageButton;
	private JButton jLastPageButton;
	private JTextField jPageNoTextField;
	private JButton jPreviousPageButton;
	protected EventListenerList listenerList = new EventListenerList();
	protected EventListenerList textFieldlistenerList = new EventListenerList();

	public Pager() {
		initGUI();
	}

	private void initGUI() {
		try {
			{
				jFirstPageButton = new JButton();
				this.add(jFirstPageButton);
				jFirstPageButton.setText("|<");
				jFirstPageButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent evt) {
						jFirstPageButtonActionPerformed(evt);
					}
				});
			}
			{
				jPreviousPageButton = new JButton();
				this.add(jPreviousPageButton);
				jPreviousPageButton.setText("<");
				jPreviousPageButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent evt) {
						jPreviousPageButtonActionPerformed(evt);
					}
				});
			}
			{
				jPageNoTextField = new JTextField("1");
				this.add(jPageNoTextField);
				jPageNoTextField.setPreferredSize(new java.awt.Dimension(65, 22));
				jPageNoTextField.addKeyListener(new KeyAdapter() {
					public void keyReleased(KeyEvent evt) {
						jPageNoTextFieldKeyReleased(evt);
					}
				});
			}
			{
				jNextPageButton = new JButton();
				this.add(jNextPageButton);
				jNextPageButton.setText(">");
				jNextPageButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent evt) {
						jNextPageButtonActionPerformed(evt);
					}
				});
			}
			{
				jLastPageButton = new JButton();
				this.add(jLastPageButton);
				jLastPageButton.setText(">|");
				jLastPageButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent evt) {
						jLastPageButtonActionPerformed(evt);
					}
				});
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void addPagerEventListener(PagerEventListener listener) {
		listenerList.add(PagerEventListener.class, listener);
	}

	public void removePagerEventListener(PagerEventListener listener) {
		listenerList.remove(PagerEventListener.class, listener);
	}

	public void addPagerTextFieldEventListener(PagerTextFieldEventListener listener) {
		textFieldlistenerList.add(PagerTextFieldEventListener.class, listener);
	}

	public void removePagerTextFieldEventListener(PagerTextFieldEventListener listener) {
		textFieldlistenerList.remove(PagerTextFieldEventListener.class, listener);
	}

	void fireMyEvent(PagerEvent evt) {
		Object[] listeners = listenerList.getListenerList();

		for (int i = 0; i < listeners.length; i += 2) {
			if (listeners[i] == PagerEventListener.class) {
				((PagerEventListener) listeners[i + 1]).clicked(evt);
			}
		}
	}

	void fireTextFieldEvent(PagerTextFieldEvent evt) {
		Object[] listeners = textFieldlistenerList.getListenerList();

		for (int i = 0; i < listeners.length; i += 2) {
			if (listeners[i] == PagerTextFieldEventListener.class) {
				((PagerTextFieldEventListener) listeners[i + 1]).KeyReleased(evt);
			}
		}
	}

	private void jNextPageButtonActionPerformed(ActionEvent evt) {
		PagerEvent event = new PagerEvent(this);
		event.setType(2);
		fireMyEvent(event);
	}

	private void jPreviousPageButtonActionPerformed(ActionEvent evt) {
		PagerEvent event = new PagerEvent(this);
		event.setType(1);
		fireMyEvent(event);
	}

	private void jFirstPageButtonActionPerformed(ActionEvent evt) {
		PagerEvent event = new PagerEvent(this);
		event.setType(0);
		fireMyEvent(event);
	}

	private void jLastPageButtonActionPerformed(ActionEvent evt) {
		PagerEvent event = new PagerEvent(this);
		event.setType(3);
		fireMyEvent(event);
	}

	public void setPageNo(int pageNo) {
		jPageNoTextField.setText(String.valueOf(pageNo));
	}

	private void jPageNoTextFieldKeyReleased(KeyEvent evt) {
		PagerTextFieldEvent event = new PagerTextFieldEvent(this);
		event.setValue(jPageNoTextField.getText());
		fireTextFieldEvent(event);
	}
}
