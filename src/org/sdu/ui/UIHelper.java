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
 * @version 0.1 rev 8000 Dec. 22, 2012.
 * Copyright (c) HyperCube Dev Team.
 */
public class UIHelper
{
	private static DebugFramework debugger = DebugFramework.getFramework();
	private static ConcurrentHashMap<String, Object> mapper = new ConcurrentHashMap<String, Object>();
	
	/**
	 * Specify the size of each square block in frame res.
	 */
	public static int frameBlockSize = 64;
	
	/**
	 * Specify the title offset.
	 */
	public static int titleOffsetX = 14;
	public static int titleOffsetY = 10;
	
	/**
	 * Spacing between title and subtitle.
	 */
	public static int subtitleSpacing = 5;
	
	/**
	 * Default radius used by Gaussian blur.
	 */
	public static int defaultBlurRadius = 4;
	
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
