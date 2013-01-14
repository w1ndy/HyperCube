package org.sdu.ui;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.LayoutManager2;

/**
 * DashboardLayout class extends FlowLayout manager to LayoutManager2.
 * 
 * @version 0.1 rev 8000 Jan. 10, 2013.
 * Copyright (c) HyperCube Dev Team.
 */
public class DashboardLayout extends FlowLayout implements LayoutManager2
{
	private static final long serialVersionUID = 1L;
	
	private int vgap = 0;
	
	public DashboardLayout()
	{
		super(FlowLayout.CENTER, 0, 5);
		vgap = 5;
	}
	
	public DashboardLayout(int verticalGap)
	{
		super(FlowLayout.CENTER, 0, verticalGap);
		vgap = verticalGap;
	}
	
	@Override
	public void setVgap(int gap)
	{
		vgap = gap;
		super.setVgap(gap);
	}
	
	@Override
	public void addLayoutComponent(Component comp, Object constraints) {}

	@Override
	public float getLayoutAlignmentX(Container target) {
		return 0;
	}

	@Override
	public float getLayoutAlignmentY(Container target) {
		return 0;
	}

	@Override
	public void invalidateLayout(Container target) {
		int totalHeight = vgap;
		for(Component c : target.getComponents()) {
			totalHeight += c.getPreferredSize().height + vgap;
		}
		target.setSize(new Dimension(target.getWidth(), totalHeight));
		layoutContainer(target);
	}

	@Override
	public Dimension maximumLayoutSize(Container target) {
		return null;
	}
}