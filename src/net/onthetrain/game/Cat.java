package net.onthetrain.game;

public class Cat {
	private int height;
	private boolean flying;
	private static int jumps = 0;
	
	public Cat(int height) {
		this.height = height;
		this.flying = true;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}
	
	public void fall() {
		if (this.height > 95)
			this.height -= 1;
	}
	
	public void jump() {
		jumps += 1;
		if (this.height > 88 || this.height < 200)
			this.height += 70;
	}

	public boolean isFlying() {
		return flying;
	}

	public void setFlying(boolean flying) {
		this.flying = flying;
	}

	public static int getJumps() {
		return jumps;
	}

	public void resetJumps() {
		jumps = 0;
	}
}
