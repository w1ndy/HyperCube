package org.sdu.ui;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.InputMethodEvent;
import java.awt.event.InputMethodListener;

import javax.swing.JTextField;
import javax.swing.text.Caret;

/**
 * TextBox class implements a TextField with description.
 * 
 * @version 0.1 rev 8005 Jan. 15, 2013.
 * Copyright (c) HyperCube Dev Team.
 */
public class TextBox extends JTextField implements TranslucentComponent
{
	private static final long serialVersionUID = 1L;
	
	private String desc = "", saved = null;
	private RectBorder border;
	private BorderFlashAnimation aniFlash;
	private float globalOpacity = 1.0f;
	private Color colorDesc, colorText;
	
	private InputMethodListener imeListener = new InputMethodListener() {
		@Override
		public void caretPositionChanged(InputMethodEvent arg0) {}

		@Override
		public void inputMethodTextChanged(InputMethodEvent arg0) {
			// Java is incompatible with IME in windows 8.
			TextBox.this.getParent().repaint();
		}
	};
	
	/**
	 * Initialize a TextBox object.
	 */
	public TextBox()
	{
		super();
		colorDesc = Color.GRAY;
		colorText = Color.BLACK;
		setSize(UIHelper.DefaultTextBoxWidth, UIHelper.DefaultTextBoxHeight);
		initialize();
	}
	
	/**
	 * Initialize a TextBox object.
	 */
	public TextBox(Color colorDesc, Color colorText)
	{
		super();
		this.colorDesc = colorDesc;
		this.colorText = colorText;
		setSize(UIHelper.DefaultTextBoxWidth, UIHelper.DefaultTextBoxHeight);
		initialize();
	}
	
	/**
	 * Initialize a TextBox object with specified description.
	 * 
	 * @param desc
	 */
	public TextBox(String desc)
	{
		super(desc);
		this.desc = desc;
		colorDesc = Color.GRAY;
		colorText = Color.BLACK;
		setSize(UIHelper.DefaultTextBoxWidth, UIHelper.DefaultTextBoxHeight);
		initialize();
	}
	
	/**
	 * Initialize a TextBox object with specified description.
	 * 
	 * @param desc
	 */
	public TextBox(String desc, Color colorDesc, Color colorText)
	{
		super(desc);
		this.desc = desc;
		this.colorDesc = colorDesc;
		this.colorText = colorText;
		setSize(UIHelper.DefaultTextBoxWidth, UIHelper.DefaultTextBoxHeight);
		initialize();
	}

	/**
	 * Initialize a TextBox object with specified description.
	 * 
	 * @param desc
	 */
	public TextBox(String desc, int width, int height)
	{
		super(desc);
		this.desc = desc;
		colorDesc = Color.GRAY;
		colorText = Color.BLACK;
		setSize(width, height);
		initialize();
	}
	
	/**
	 * Initialize a TextBox object with specified description.
	 * 
	 * @param desc
	 */
	public TextBox(String desc, int width, int height, Color colorDesc, Color colorText)
	{
		super(desc);
		this.desc = desc;
		this.colorDesc = colorDesc;
		this.colorText = colorText;
		setSize(width, height);
		initialize();
	}
	
	private void initialize()
	{
		border = new RectBorder(UIHelper.darkColor);
		super.setBorder(border);
		super.setFont((Font)UIHelper.getResource("ui.font.text"));
		setText(desc);
		setForeground(colorDesc);
		
		aniFlash = new BorderFlashAnimation(this, border);
		aniFlash.setColor(UIHelper.darkColor);
		
		addInputMethodListener(imeListener);
		addFocusListener(new FocusListener() {
			@Override
			public void focusGained(FocusEvent arg0) {
				if(getText().equals(desc)) {
					setText("");
					setForeground(colorText);
				}
				border.setColor(UIHelper.lightColor);
				aniFlash.setColor(UIHelper.lightColor);
				repaint();
			}

			@Override
			public void focusLost(FocusEvent arg0) {
				if(getText().equals("")) {
					setText(desc);
					setForeground(colorDesc);
				}
				border.setColor(UIHelper.darkColor);
				aniFlash.setColor(UIHelper.darkColor);
				repaint();
			}
		});
	}
	
	/**
	 * Set TextBox description.
	 * 
	 * @param desc
	 */
	public void setDescription(String desc)
	{
		this.desc = desc;
	}
	
	/**
	 * Get TextBox description.
	 * 
	 * @return
	 */
	public String getDescription()
	{
		return this.desc;
	}
	
	@Override
	public void paintComponent(Graphics g)
	{
		((Graphics2D)g).setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, globalOpacity));
		super.paintComponent(g);
	}
	
	/**
	 * On data fails show animation.
	 */
	public void onFailed()
	{
		aniFlash.start();
	}

	@Override
	public void setOpacity(float alpha) {
		globalOpacity = alpha;
		border.setOpacity(alpha);
	}

	@Override
	public float getOpacity() {
		return globalOpacity;
	}
	
	@Override
	public void setEditable(boolean b)
	{
		if(b == false) {
			FontMetrics metrics = this.getFontMetrics((Font)UIHelper.getResource("ui.font.text"));
			if(metrics.stringWidth(getText()) > getWidth()) {
				saved = getText();
				for(int i = 1; i < saved.length(); i++) {
					if(metrics.stringWidth(saved.substring(0, i)) > getWidth() - 20) {
						setText(saved.substring(0, i) + "....");
						break;
					}
				}
			}
		} else {
			if(saved != null) {
				setText(saved);
				saved = null;
			}
		}
		super.setEditable(b);
	}
	
	public String getOriginalText()
	{
		if(saved == null)
			return getText();
		else 
			return saved;
	}
}