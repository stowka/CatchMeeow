package net.onthetrain.game;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import net.onthetrain.gui.Frame;

public class Game implements Runnable {
	private int height, width;
	private List<Obstacle> obstacles;
	private Cat cat;

	private String name;

	private long time;
	private int score, n;
	
	private boolean increasement; 

	private static final int WIDTH = 600;
	private static final int HEIGHT = 200;
	
	private static final long SPEED = 5;

	public Game(String name) {
		this.height = HEIGHT;
		this.width = WIDTH;
		this.cat = new Cat(95);
		this.obstacles = new ArrayList<Obstacle>();
		this.name = name;
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

		score = (int) (System.currentTimeMillis() - time) / 1000;

		//new Thread(new Save(score, name)).start();

		Object[] options = { "Yes", "No" };
		int n = JOptionPane.showOptionDialog(null, "Ouch!\n" + score
				+ " seconds\n" + "Try again?", "A silly question",
				JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null,
				options, options[0]);
		if (1 == n)
			System.exit(0);
		else {
			this.cat = new Cat(88);
			this.obstacles = new ArrayList<Obstacle>();
		}
	}

	@Override
	public void run() {
		n = 0;
		increasement = true;
		while (true) {
			time = System.currentTimeMillis();
			while (cat.isFlying()) {
				if (increasement && ++n % ((int) (Math.random() * 50 + 80)) == 0) {
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
					Thread.sleep(SPEED);
				} catch (InterruptedException e) {
					e.printStackTrace();
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
		String name = (String) JOptionPane.showInputDialog(null,
				"What's your name?\n", "Catch Meeow",
				JOptionPane.PLAIN_MESSAGE, null, null, "");

		if (null == name) {
			System.exit(0);
		} else {
			Game game = new Game(name);
			Frame frame = new Frame(game, name);
			new Thread(frame).start();
			new Thread(game).start();
		}
	}
}
