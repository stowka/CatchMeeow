package net.onthetrain.game;

public class Bonus {
	private static final int life = 4;
	private static final int height = 12;
	
	private int position;
	
	public Bonus(int position) {
		this.position = position;
	}

	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}

	public static int getLife() {
		return life;
	}
	
	public static int getHeight() {
		return height;
	}

	public void updatePosition() {
		position -= 1; 
	}
}
