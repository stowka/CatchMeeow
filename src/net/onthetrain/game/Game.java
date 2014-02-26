package net.onthetrain.game;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import net.onthetrain.db.Save;
import net.onthetrain.gui.Dialog;
import net.onthetrain.gui.Frame;

public class Game implements Runnable {
	private int height, width;
	private List<Obstacle> obstacles;
	private Cat cat;
	private Dialog settings;
	
	private String name, level;
	
	private long time;
	private int score, n, pauseCount;

	private boolean increasement;

	private static final int WIDTH = 800;
	private static final int HEIGHT = 200;

	private long speed;
	
	private boolean paused;
	
	private static Game instance = null;

	private Game() {
		this.height = HEIGHT;
		this.width = WIDTH;
		this.cat = new Cat(95);
		this.obstacles = new ArrayList<Obstacle>();
		this.name = "";
		this.speed = 3;
		this.pauseCount = 0;
	}
	
	public void showSettings() {
		if (settings == null)
			settings = new Dialog(null, "Catch Meeow", true);
		else
			settings.setVisible(true);
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	
	public static Game getInstance() {
		if (Game.instance == null)
			instance = new Game();
		return instance;
	}

	public long getSpeed() {
		return speed;
	}

	public void setSpeed(long speed) {
		this.speed = speed;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public void setBird(Cat bird) {
		this.cat = bird;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public List<Obstacle> getObstacles() {
		return obstacles;
	}

	public void setObstacles(List<Obstacle> obstacles) {
		this.obstacles = obstacles;
	}

	public void addObstacle(Obstacle obstacle) {
		this.obstacles.add(obstacle);
	}

	public void updateObstaclePositions() {
		for (int i = 0; i < obstacles.size(); i += 1) {
			obstacles.get(i).updatePosition();
			if (obstacles.get(i).getPosition() <= 0)
				obstacles.remove(i);
		}
	}

	private boolean checkObstacle() {
		for (Obstacle o : obstacles) {
			if (o.getPosition() - o.getWidth() < 120 && o.getPosition() > 85
					&& o.getHeight() + 90 >= cat.getHeight()) {
				return false;
			}
		}
		return true;
	}

	public void lose(long time, String reason) {
		cat.setFlying(false);
		new Thread(new Save()).start();
		Object[] options = { "Yes", "No" };
		int n = JOptionPane.showOptionDialog(null, "Ouch!\n" + score
				+ " seconds\n" + "Try again?", "A silly question",
				JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null,
				options, options[0]);
		if (1 == n)
			System.exit(0);
		else {
			Game.getInstance().setCat(new Cat(95));
			Game.getInstance().setObstacles(new ArrayList<Obstacle>());
		}
	}

	@Override
	public void run() {
		n = 0;
		increasement = true;
		while (true) {
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			while (cat.isFlying()) {
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				while (!isPaused()) {
					if (System.currentTimeMillis() - time > 1000) {
						score += 1;
						time = System.currentTimeMillis();
					}
					
					if (increasement
							&& ++n % ((int) (Math.random() * 50 + 80)) == 0) {
						Obstacle o = new ObstacleDown(height, width);
						addObstacle(o);
						increasement = false;
					}
	
					if (!increasement)
						n -= 1;
	
					if (0 == n)
						increasement = true;
	
					updateObstaclePositions();
	
					if (!checkObstacle())
						lose(time, "obstacle");
	
					if (n % 2 == 0 || n % 3 == 0)
						cat.fall();
	
					if (cat.getHeight() >= height - 20) {
						lose(time, "sky");
					}
	
					try {
						Thread.sleep(speed);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}

	}

	public Cat getCat() {
		return cat;
	}

	public void setCat(Cat cat) {
		this.cat = cat;
	}

	public int getN() {
		return n;
	}

	public void setN(int n) {
		this.n = n;
	}

	public static void main(String[] args) {
		Game.getInstance().showSettings();
		new Thread(Frame.getInstance()).start();
		new Thread(Game.getInstance()).start();
	}

	public boolean isPaused() {
		return paused;
	}
	
	public void setPaused(boolean paused) {
		this.paused = paused;
		if (!paused) {
			Game.getInstance().setTime(System.currentTimeMillis());
		} else {
			pauseCount += 1;
		}
	}

	public int getPauseCount() {
		return pauseCount;
	}

	public void setPauseCount(int pauseCount) {
		this.pauseCount = pauseCount;
	}
}
