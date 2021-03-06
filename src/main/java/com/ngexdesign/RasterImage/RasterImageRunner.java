package com.ngexdesign.RasterImage;

import javax.swing.*;

import java.io.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.*;

import javax.imageio.*;

import com.ngexdesign.RasterImage.RasterImage.SwipeType;

@SuppressWarnings({ "serial", "unused" })
public class RasterImageRunner extends JPanel
{
	private static int width = 472;
	private static int height = 408;

	private Timer timer;
	private int boundaryX = 1;
	private BufferedImage image;
	private RasterImage rasterImage;

	private int direction = 1;
	private int speed = 5;
	private double angle = 0.0;

	public RasterImageRunner ()
	{
		rasterImage = new RasterImage(width, height);
		timer = new Timer(20, new RasterTimer(SwipeType.SWEEP));
		timer.start();
	}

	public void paintComponent(Graphics g) 
	{
		g.drawImage(image, 0, 0, null);
		repaint();
	}

	public static void main(String [] args)
	{
		JFrame f = new JFrame("Window");
		f.getContentPane().add(new RasterImageRunner());
		f.setSize(width, height);
		f.setVisible(true);
	}
	
	private class RasterTimer implements ActionListener 
	{
		private SwipeType type;
		
		public RasterTimer(SwipeType type)
		{
			this.type = type;
		}
		
		public void actionPerformed(ActionEvent e) 
		{
			if(type == SwipeType.HORIZONTAL || type == SwipeType.VERTICAL)
			{
				if (boundaryX <= 0)
				{
					boundaryX = 1;
					direction = 1;
				}
				else if (boundaryX >= width)
				{
					boundaryX = width - 1;
					direction = -1;
				}
				image = rasterImage.generateMagicImage(SwipeType.HORIZONTAL, boundaryX);
				boundaryX += direction * speed;
			}
			else if (type == SwipeType.SWEEP)
			{
				if (angle <= 0)
				{
					angle = 0.0;
					direction = 1;
				}
				else if (angle > Math.PI)
				{
					angle = Math.PI;
					direction = -1;
				}
				image = rasterImage.generateMagicImage(SwipeType.SWEEP, angle);
				angle += direction * speed * Math.PI/180;
			}
		}
	}
}