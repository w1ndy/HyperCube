package org.sdu.ui;

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
 * @version 0.1 rev 8003 Dec. 25, 2012.
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
