package org.sdu.ui;

import java.awt.image.BufferedImage;

/**
 * GaussianBlur class implements the Gaussian Blur effect.
 * 
 * @version 0.1 rev 8000 Dec. 21, 2012.
 * Copyright (c) HyperCube Dev Team.
 */
public class GaussianBlur
{
	private double[] matrix;
	private int radius;
	
	/**
	 * Initialize 1D kernel of Gaussian Blur with radius of [radius] and
	 * standard deviation of [deviation].
	 * 
	 * @param radius
	 * @param deviation
	 */
	public GaussianBlur(int radius, double deviation)
	{
		matrix = new double[2 * radius + 1];
		int i;
		this.radius = radius;
		double p = -1.0f / (double)(2 * deviation * deviation);
		double f = 1.0f / Math.sqrt(2.0f * Math.PI * deviation * deviation);
		for(i = -radius; i <= radius; i++) {
			matrix[i + radius] = f * Math.pow(Math.E, (i * i) * p);
		}
	}
	
	/**
	 * Blur the image with generated kernel.
	 * 
	 * @param image
	 */
	public void blur(BufferedImage image)
	{
		BufferedImage buf = new BufferedImage(image.getWidth(), image.getHeight(), image.getType());
		int i, j, k, rgb;
		float av, rv, gv, bv;
		
		// Calculate vertically.
		for(i = 0; i < image.getWidth(); i++) {
			for(j = 0; j < image.getHeight(); j++) {
				av = rv = gv = bv = 0.0f;
				for(k = -radius; k <= radius; k++) {
					if(i + k < 0 || i + k >= image.getWidth())
						rgb = 0;
					else
						rgb = image.getRGB(i + k, j);
					av += (double)((rgb >> 24) & 0xff) * matrix[k + radius];
					rv += (double)((rgb >> 16) & 0xff) * matrix[k + radius];
					gv += (double)((rgb >> 8) & 0xff) * matrix[k + radius];
					bv += (double)(rgb & 0xff) * matrix[k + radius];
				}
				buf.setRGB(i, j, (((int)av << 24) & 0xff000000) + (((int)rv << 16) & 0x00ff0000)
						+ (((int)gv << 8) & 0x0000ff00) + (((int)bv) & 0x000000ff));
			}
		}
		
		// Calculate horizontally.
		for(i = 0; i < image.getWidth(); i++) {
			for(j = 0; j < image.getHeight(); j++) {
				av = rv = gv = bv = 0.0f;
				for(k = -radius; k <= radius; k++) {
					if(j + k < 0 || j + k >= image.getHeight())
						rgb = 0;
					else
						rgb = buf.getRGB(i, j + k);
					av += (double)((rgb >> 24) & 0xff) * matrix[k + radius];
					rv += (double)((rgb >> 16) & 0xff) * matrix[k + radius];
					gv += (double)((rgb >> 8) & 0xff) * matrix[k + radius];
					bv += (double)(rgb & 0xff) * matrix[k + radius];
				}
				image.setRGB(i, j, (((int)av << 24) & 0xff000000) + (((int)rv << 16) & 0x00ff0000)
						+ (((int)gv << 8) & 0x0000ff00) + (((int)bv) & 0x000000ff));
			}
		}
	}
	
	private static GaussianBlur r4blur = null;
	
	/**
	 * Get Gaussian Blur in radius 4.
	 * 
	 * @return Gaussian Blur handle
	 */
	public static GaussianBlur getFourRadiusBlur()
	{
		if(r4blur == null) {
			r4blur = new GaussianBlur(4, 1.3);
		}
		return r4blur;
	}
}