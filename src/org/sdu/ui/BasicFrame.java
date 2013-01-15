package org.sdu.ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * Basic class implements the basic UI.
 * 
 * @version 0.1 rev 8007 Jan. 15, 2013.
 * Copyright (c) HyperCube Dev Team.
 */
public class BasicFrame extends JFrame
{
	private static final long serialVersionUID = 1L;
	
	private Point mouseCoord = null;
	private Rectangle rectClient = null;
	
	private String title = null;
	private String subtitle = null;
	
	private int minFrameWidth;
	private int minFrameHeight;
	
	protected int titleOffsetX;
	protected int titleOffsetY;
	protected int subtitleOffsetX;
	protected int subtitleOffsetY;

	private Image imageFrame;

	private BufferedImage imageTitle;
	
	/**
	 * Construct a basic frame.
	 */
	public BasicFrame()
	{
		super();
		initializeBasicFrame();
	}
	
	/**
	 * Construct a basic frame with title.
	 * 
	 * @param title
	 * @param subtitle
	 */
	public BasicFrame(String title, String subtitle)
	{
		super(title);
		this.title = title;
		this.subtitle = subtitle;
		initializeBasicFrame();
	}
	
	/**
	 * Add limit for minimum frame size.
	 */
	@Override
	public void setSize(int width, int height)
	{
		if(width < minFrameWidth) width = minFrameWidth;
		if(height < minFrameHeight) height = minFrameHeight;
		super.setSize(width, height);
	}
	
	/**
	 * Set title location.
	 */
	public void setTitleLocation(int x, int y)
	{
		titleOffsetX = x;
		titleOffsetY = y;
	}
	
	/**
	 * Set subtitle location.
	 */
	public void setSubtitleLocation(int x, int y)
	{
		subtitleOffsetX = x;
		subtitleOffsetY = y;
	}
	
	/**
	 * Set title of the frame.
	 */
	@Override
	public void setTitle(String title)
	{
		this.title = title;
		generateResources();
		setMeasurement();
		super.setTitle(title);
		repaint();
	}
	
	/**
	 * Set subtitle of the frame.
	 */
	public void setSubtitle(String subtitle)
	{
		this.subtitle = subtitle;
		setMeasurement();
		repaint();
	}
	
	/**
	 * Make the frame movable.
	 */
	private void makeMovable()
	{
		addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent arg0) {}
			
			@Override
			public void mouseEntered(MouseEvent arg0) {}
			
			@Override
			public void mouseExited(MouseEvent arg0) {}
			
			@Override
			public void mousePressed(MouseEvent arg0) {
				if(rectClient != null && rectClient.contains(arg0.getPoint()))
					return ;
				mouseCoord = arg0.getPoint();
			}
			
			@Override
			public void mouseReleased(MouseEvent arg0) {
				mouseCoord = null;
			}
		});
		
		addMouseMotionListener(new MouseMotionListener() {
			@Override
			public void mouseDragged(MouseEvent arg0) {
				Point p = arg0.getLocationOnScreen();
				if(mouseCoord != null)
					setLocation(p.x - mouseCoord.x, p.y - mouseCoord.y);
			}

			@Override
			public void mouseMoved(MouseEvent arg0) {}
		});
	}
	
	/**
	 * Set client area.
	 */
	public void setClientRect(Rectangle r)
	{
		rectClient = r;
	}

	/**
	 * Set client area.
	 */
	public void setClientRect(int x, int y, int w, int h)
	{
		rectClient = new Rectangle(x, y, w, h);
	}
	
	/**
	 * Set measurement of frame components.
	 */
	private void setMeasurement()
	{
		FontMetrics fm = getFontMetrics((Font)UIHelper.getResource("ui.font.subtitle"));
		
		// Get subtitle's width
		int stwidth;
		if(subtitle != null)
			stwidth = fm.stringWidth(subtitle);
		else
			stwidth = 0;
		
		// Set default title offset
		titleOffsetX = UIHelper.titleOffsetX;
		titleOffsetY = UIHelper.titleOffsetY;
		
		// Set default subtitle offset
		if(imageTitle != null)
			subtitleOffsetX = titleOffsetX + imageTitle.getWidth() + UIHelper.subtitleSpacing;
		else
			subtitleOffsetX = titleOffsetX;
		subtitleOffsetY = titleOffsetY + fm.getMaxAscent() + UIHelper.defaultBlurRadius * 2;
		
		// Set minimum frame size
		minFrameWidth = subtitleOffsetX + stwidth + titleOffsetX + UIHelper.defaultBlurRadius;
		minFrameWidth = (minFrameWidth < UIHelper.frameBlockSize * 2) ? UIHelper.frameBlockSize * 2 : minFrameWidth;
		minFrameHeight = UIHelper.frameBlockSize * 2;
		
		int w = (getWidth() < minFrameWidth) ? minFrameWidth : getWidth();
		int h = (getHeight() < minFrameHeight) ? minFrameHeight : getHeight();
		super.setSize(w, h);
	}
	
	/**
	 * Generate resources.
	 */
	private void generateResources()
	{
		imageFrame = (Image)UIHelper.getResource("ui.common.frame");
		
		if(title != null) {
			Font font = (Font)UIHelper.getResource("ui.font.title");
			imageTitle = GaussianBlur.getDefaultBlur().generateShadowText(
					title, 
					font,
					this.getFontMetrics(font),
					Color.BLACK,
					Color.WHITE);
		}
	}
	
	/**
	 * Draw basic frame.
	 * @param background 
	 */
	private void drawBasicFrame(Graphics2D g, Color background)
	{
		int width = BasicFrame.this.getWidth();
		int height = BasicFrame.this.getHeight();
		int lx = UIHelper.frameBlockSize;
		int rx = width - UIHelper.frameBlockSize;
		int uy = UIHelper.frameBlockSize;
		int by = height - UIHelper.frameBlockSize;
		
		g.setColor(new Color(0, 0, 0, 0));
		g.clearRect(0, 0, width, height);
		
		// Draw background.
		g.setColor(background);
		g.fillRect(lx, uy, rx - lx, by - uy);
		
		// Draw left upper corner.
		g.drawImage(imageFrame, 0, 0, UIHelper.frameBlockSize, UIHelper.frameBlockSize,
				0, 0, UIHelper.frameBlockSize, UIHelper.frameBlockSize, this);
		
		// Draw right upper corner.
		g.drawImage(imageFrame,
				rx, 0, 
				width, UIHelper.frameBlockSize,
				UIHelper.frameBlockSize * 2, 0, 
				UIHelper.frameBlockSize * 3, UIHelper.frameBlockSize, this);
		
		// Draw lower left corner.
		g.drawImage(imageFrame,
				0, by,
				UIHelper.frameBlockSize, height,
				0, UIHelper.frameBlockSize * 2,
				UIHelper.frameBlockSize, UIHelper.frameBlockSize * 3, this);
		
		// Draw lower right corner.
		g.drawImage(imageFrame,
				rx, by,
				width, height,
				UIHelper.frameBlockSize * 2, UIHelper.frameBlockSize * 2,
				UIHelper.frameBlockSize * 3, UIHelper.frameBlockSize * 3, this);
		
		// Draw upper and lower strips.
		g.drawImage(imageFrame, 
				lx, 0, 
				rx, UIHelper.frameBlockSize,
				UIHelper.frameBlockSize, 0, 
				UIHelper.frameBlockSize * 2, UIHelper.frameBlockSize, this);
		g.drawImage(imageFrame,
				lx, by,
				rx, height,
				UIHelper.frameBlockSize, UIHelper.frameBlockSize * 2,
				UIHelper.frameBlockSize * 2, UIHelper.frameBlockSize * 3, this);
		
		// Draw left and right strips.
		g.drawImage(imageFrame,
				0, uy,
				UIHelper.frameBlockSize, by,
				0, UIHelper.frameBlockSize,
				UIHelper.frameBlockSize, UIHelper.frameBlockSize * 2, this);
		g.drawImage(imageFrame,
				rx, uy,
				width, by,
				UIHelper.frameBlockSize * 2, UIHelper.frameBlockSize,
				UIHelper.frameBlockSize * 3, UIHelper.frameBlockSize * 2, this);
	}
	
	/**
	 * Initialize the frame.
	 */
	private void initializeBasicFrame()
	{
		generateResources();
		setMeasurement();
		makeMovable();
		
		setUndecorated(true);
		setBackground(new Color(0, 0, 0, 0));

		setContentPane(new JPanel() {
			private static final long serialVersionUID = 1L;
			private Color bk = new Color(((BufferedImage)imageFrame).getRGB(UIHelper.frameBlockSize, UIHelper.frameBlockSize));
			
			@Override
			public void paintComponent(Graphics g)
			{
				Graphics2D g2d = (Graphics2D)g;
				drawBasicFrame(g2d, bk);
				if(imageTitle != null) {
					g2d.drawImage(imageTitle, titleOffsetX, titleOffsetY, this);
				}
				if(subtitle != null) {
					g2d.setFont((Font)UIHelper.getResource("ui.font.subtitle"));
					g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
					g2d.drawString(subtitle, subtitleOffsetX, subtitleOffsetY);
				}
			}
		});
		
		setLayout(null);
	}
}
