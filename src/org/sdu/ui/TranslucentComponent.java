package org.sdu.ui;

/**
 * TranslucentComponent interface defines a opacity-modifiable component.
 * 
 * @version 0.1 rev 8000 Dec. 27, 2012.
 * Copyright (c) HyperCube Dev Team.
 */
public interface TranslucentComponent
{
	/**
	 * Set opacity of the component.
	 * 
	 * @param alpha
	 */
	public void setOpacity(float alpha);
	
	/**
	 * Get opacity of the component.
	 * 
	 * @return
	 */
	public float getOpacity();
}