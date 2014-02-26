package net.onthetrain.game;

public class Obstacle {
	protected int height;
	protected final int width = 20;
	protected int position;
	
	public Obstacle(int height, int position) {
		this.height = 12;
		this.position = position;
	}
	
	public void updatePosition() {
		position -= 1;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}

	public int getWidth() {
		return width;
	}
}
