package com.mowitnow.mower.entities;

public class Lawn {
	private final int width;
	private final int height;

	public Lawn(int width, int height) {
		this.width = width;
		this.height = height;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}
}