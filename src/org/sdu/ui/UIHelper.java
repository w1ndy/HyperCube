package org.sdu.ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.io.File;
import java.security.InvalidParameterException;
import java.util.concurrent.ConcurrentHashMap;

import javax.imageio.ImageIO;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import org.sdu.util.DebugFramework;

/**
 * UIHelper classes declares a series of constants and manages global
 * resources.
 * 
 * @version 0.1 rev 8006 Dec. 28, 2012.
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
	 * Default avatar box offset in login window.
	 */
	public static final int avatarBoxLoginOffsetX = 10;
	public static final int avatarBoxLoginOffsetY = 68;
	
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
	 * Default TextBox size.
	 */
	public static final int textBoxWidth = 175;
	public static final int textBoxHeight = 25;
	
	/**
	 * Default username TextBox position.
	 */
	public static final int usernameBoxOffsetX = 110;
	public static final int usernameBoxOffsetY = 80;
	
	/**
	 * Default password TextBox position.
	 */
	public static final int passwordBoxOffsetX = 110;
	public static final int passwordBoxOffsetY = 110;
	
	/**
	 * Default register link position.
	 */
	public static final int registerLinkOffsetX = 225;
	public static final int registerLinkOffsetY = 142;
	
	/**
	 * Default register link size
	 */
	public static final int registerLinkWidth = 60;
	public static final int registerLinkHeight = 15;
	
	/**
	 * Default progress bar size
	 */
	public static final int progressBarWidth = 302;
	public static final int progressBarHeight = 5;
	
	/**
	 * Default login prompt position
	 */
	public static final int loginPromptOffsetX = 4;
	public static final int loginPromptOffsetY = 64;
	
	/**
	 * Default login prompt size
	 */
	public static final int loginPromptWidth = 300;
	public static final int loginPromptHeight = 110;
	
	/**
	 * Default progress bar parameters
	 */
	public static final int progressBarFrequency = 25;
	public static final int progressBarRate = 4;
	public static final float progressBarInterval = 100.0f;
	public static final Color progressBarColor = new Color(0x7fc2e2);
	
	/**
	 * Default progress bar position
	 */
	public static final int progressBarLoginOffsetX = 4;
	public static final int progressBarLoginOffsetY = 176;
	
	/**
	 * Default color definition.
	 */
	public static final Color lightColor = new Color(0, 148, 255);
	public static final Color darkColor = new Color(128, 128, 128);
	
	/**
	 * Load image resources.
	 */
	public static boolean loadImage(Element elem, String name, File folder)
	{
		try {
			Image image = ImageIO.read(new File(folder, elem.getTextContent()));
			mapper.put(name, (Object)image);
		} catch(Exception e) {
			debugger.print("Failed to load resource \"" + name + "\": " + e);
			return false;
		}
		return true;
	}
	
	/**
	 * Load font resources.
	 */
	public static boolean loadFont(Element elem, String name, File folder)
	{
		try {
			String fontface, size_str, bold_str, italic_str;
			fontface = elem.getElementsByTagName("fontface").item(0).getTextContent();
			size_str = elem.getElementsByTagName("size").item(0).getTextContent();
			bold_str = elem.getElementsByTagName("bold").item(0).getTextContent();
			italic_str = elem.getElementsByTagName("italic").item(0).getTextContent();
			
			Font font = new Font(fontface,
					(Boolean.valueOf(bold_str).booleanValue() ? Font.BOLD : 0)
						| (Boolean.valueOf(italic_str).booleanValue() ? Font.ITALIC : 0),
					Integer.valueOf(size_str).intValue());
			mapper.put(name, (Object)font);
		} catch(Exception e) {
			debugger.print("Failed to load resource \"" + name + "\": " + e);
			return false;
		}
		return true;
	}
	
	/**
	 * Load string resources.
	 */
	public static boolean loadString(Element elem, String name, File folder)
	{
		try {
			mapper.put(name, (Object)elem.getTextContent());
		} catch(Exception e) {
			debugger.print("Failed to load resource \"" + name + "\": " + e);
			return false;
		}
		return true;
	}
	
	// TODO load color resource here.
	
	/**
	 * Load all resources.
	 * @return
	 */
	public static boolean loadResource(String resourceIndex)
	{
		File fileIndex = new File(resourceIndex);
		if(!fileIndex.exists()) return false;
		File folder = new File("./art");
		if(!folder.exists() || !folder.isDirectory()) return false;
		
		try {
			Document doc = (DocumentBuilderFactory.newInstance()).newDocumentBuilder().parse(fileIndex);
			doc.getDocumentElement().normalize();
			
			Node node;
			String name, type;
			
			node = doc.getElementsByTagName("resource").item(0);
			if(node.getNodeType() != Node.ELEMENT_NODE)
				throw new InvalidParameterException("No resource archive found.");
			NodeList nodeList = ((Element)node).getElementsByTagName("entry");
			
			for(int i = 0; i < nodeList.getLength(); i++) {
				node = nodeList.item(i);
				if(node.getNodeType() == Node.ELEMENT_NODE) {
					Element elem = (Element)node;
					name = elem.getAttribute("name");
					type = elem.getAttribute("type");
					debugger.print("Loading resource \"" + name + "\" of type \"" + type + "\"...");
					
					if(type.equals("image")) {
						if(!loadImage(elem, name, folder)) return false;
					} else if(type.equals("font")) {
						if(!loadFont(elem, name, folder)) return false;
					} else if(type.equals("string")) {
						if(!loadString(elem, name, folder)) return false;
					} else {
						debugger.print("Invalid resource type: " + type);
					}
				}
			}
		} catch(Exception e) {
			debugger.print("Failed to read resource file \"" + resourceIndex + "\": " + e);
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
		Object obj = mapper.put(index, resource);
		if(obj != null) {
			debugger.print("Resource overwritten: " + index);
		}
	}
	
	/**
	 * Get a resource by its index.
	 * return null if resource not found.
	 * 
	 * @param index
	 * @return
	 */
	public static Object getResource(String index)
	{
		Object obj = mapper.get(index);
		if(obj == null) {
			debugger.print("Resource not found: " + index);
		}
		return obj;
	}
}
