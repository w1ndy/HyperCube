package org.sdu.ui;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.Stroke;

import javax.swing.border.Border;

/**
 * RectBorder class implements a rectangular border with specified color.
 *
 * @version 0.1 rev 8001 Dec. 26, 2012.
 * Copyright (c) HyperCube Dev Team.
 */
public class RectBorder implements Border
{
	private Color colorBorder;
	private Stroke stroke;
	
	/**
	 * Initialize RectBorder object.
	 */
	public RectBorder()
	{
		super();
		colorBorder = Color.BLACK;
		stroke = new BasicStroke(4);
	}
	
	/**
	 * Initialize RectBorder object with specified color.
	 */
	public RectBorder(Color color)
	{
		super();
		colorBorder = color;
		stroke = new BasicStroke(4);
	}
	
	@Override
	public Insets getBorderInsets(Component arg0) {
		return new Insets(3, 5, 3, 5);
	}

	@Override
	public boolean isBorderOpaque() {
		return false;
	}

	@Override
	public void paintBorder(Component c, Graphics g, int x, int y,
			int w, int h) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.setStroke(stroke);
		g2d.setColor(colorBorder);
		g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER));
		g2d.drawRect(x, y, w, h);
	}

	/**
	 * @return the colorBorder
	 */
	public Color getColor() {
		return colorBorder;
	}

	/**
	 * @param colorBorder the colorBorder to set
	 */
	public void setColor(Color colorBorder) {
		this.colorBorder = colorBorder;
	}
}