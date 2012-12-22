package org.sdu.ui;

import java.awt.Font;
import java.awt.Image;
import java.io.File;
import java.util.concurrent.ConcurrentHashMap;

import javax.imageio.ImageIO;

import org.sdu.util.DebugFramework;

/**
 * UIHelper classes declares a series of constants and manages global
 * resources.
 * 
 * @version 0.1 rev 8001 Dec. 23, 2012.
 * Copyright (c) HyperCube Dev Team.
 */
public class UIHelper
{
	private static DebugFramework debugger = DebugFramework.getFramework();
	private static ConcurrentHashMap<String, Object> mapper = new ConcurrentHashMap<String, Object>();
	
	/**
	 * Specify the size of each square block in frame res.
	 */
	public static final int frameBlockSize = 64;
	
	/**
	 * Specify the title offset.
	 */
	public static final int titleOffsetX = 14;
	public static final int titleOffsetY = 10;
	
	/**
	 * Spacing between title and subtitle.
	 */
	public static final int subtitleSpacing = 5;
	
	/**
	 * Default radius used by Gaussian blur.
	 */
	public static final int defaultBlurRadius = 4;
	
	/**
	 * Default login window size.
	 */
	public static final int loginFrameWidth = 310;
	public static final int loginFrameHeight = 185;
	
	/**
	 * Default avatar box offset.
	 */
	public static final int avatarBoxOffsetX = 10;
	public static final int avatarBoxOffsetY = 68;
	
	/**
	 * Default avatar box size.
	 */
	public static final int avatarBoxWidth = 100;
	public static final int avatarBoxHeight = 100;
	
	/**
	 * Default avatar offset.
	 */
	public static final int avatarOffsetX = 10;
	public static final int avatarOffsetY = 10;
	
	/**
	 * Default avatar status icon offset.
	 */
	public static final int avatarStatusIconOffsetX = avatarOffsetX;
	public static final int avatarStatusIconOffsetY = avatarBoxHeight - avatarOffsetX - 20;
	
	/**
	 * Parameters of fading animation.
	 */
	public static final int normalFadeFrequency = 25;
	public static final int normalFadeRate = 20;
	
	/**
	 * Load all resources.
	 * 
	 * @return
	 */
	public static boolean loadResource()
	{
		Image image;
		Font font;
		try {
			image = ImageIO.read(new File("art/ui/common/frame.png"));
			mapper.put("ui.common.frame", (Object)image);
			
			image = ImageIO.read(new File("art/ui/avatar/frame.png"));
			mapper.put("ui.avatar.frame", (Object)image);
			
			image = ImageIO.read(new File("art/ui/avatar/online.png"));
			mapper.put("ui.avatar.online", (Object)image);
			
			image = ImageIO.read(new File("art/ui/avatar/invisible.png"));
			mapper.put("ui.avatar.invisible", (Object)image);
			
			image = ImageIO.read(new File("art/ui/avatar/none.png"));
			mapper.put("ui.avatar.none", (Object)image);
			
			font = new Font("微软雅黑", Font.BOLD, 28);
			mapper.put("ui.font.title", (Object)font);
			
			font = new Font("微软雅黑", Font.BOLD, 14);
			mapper.put("ui.font.subtitle", (Object)font);
			
			font = new Font("微软雅黑", 0, 10);
			mapper.put("ui.font.text", (Object)font);
			
			mapper.put("ui.string.login.title", (Object)"登录");
			mapper.put("ui.string.login.subtitle", (Object)"HyperCube™");
		} catch(Exception e) {
			debugger.print(e);
			debugger.print("Failed to read resource.");
			return false;
		}
		return true;
	}
	
	/**
	 * Add a resource to table.
	 * 
	 * @param index
	 * @param resource
	 */
	public static void addResource(String index, Object resource)
	{
		mapper.put(index, resource);
	}
	
	/**
	 * Get a resource by its index
	 * 
	 * @param index
	 * @return
	 */
	public static Object getResource(String index)
	{
		return mapper.get(index);
	}
}
