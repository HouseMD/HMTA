package com.house.infinitybot.rooms.identifiers;

public class MazeIdentifier extends Identifier {
	
	private int width, height;

	public MazeIdentifier(int xOffset, int yOffset, int zOffset, int width, int height) {
		super(xOffset, yOffset, zOffset);
		this.width = width;
		this.height = height;
	}
	
	public int getWidth() {
		return width;
	}
	public int getHeigth() {
		return height;
	}

}
