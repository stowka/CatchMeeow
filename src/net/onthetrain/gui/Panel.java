package net.onthetrain.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import net.onthetrain.game.Bonus;
import net.onthetrain.game.Cat;
import net.onthetrain.game.Game;
import net.onthetrain.game.Obstacle;

public class Panel extends JPanel implements KeyListener, MouseListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Font font, title = null;
	private Image cat = null, /*cat2 = null, */prey = null, bonus = null;

	public Panel() {
		try {
			this.cat = ImageIO.read(new File("resources/img/miaouss.png"));
//			this.cat2 = ImageIO.read(new File("resources/img/miaouss2.png"));
			this.prey = ImageIO.read(new File("resources/img/magicarpe.png"));
			this.bonus = ImageIO.read(new File("resources/img/pokeball.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		try {
			this.font = Font.createFont(Font.TRUETYPE_FONT,
					new FileInputStream("resources/fonts/AngryBirds-Regular.ttf"));
			this.title = Font.createFont(Font.TRUETYPE_FONT,
					new FileInputStream("resources/fonts/Pokemon-Solid.ttf"));
			this.font = this.font.deriveFont(18F);
			this.title = this.font.deriveFont(18F);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (FontFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;

		RenderingHints rh = g2d.getRenderingHints();
		rh.put(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setRenderingHints(rh);

		// Sky
		g2d.setColor(new Color(73, 159, 255));
		g2d.fillRect(0, 0, getWidth(), getHeight() - 50);

		// Sun
		g2d.setColor(new Color(255, 181, 2));
		g2d.fillRect(0, 0, getWidth(), 1);

		// Ground
		g2d.setColor(new Color(0, 150, 85));
		g2d.fillRect(0, getHeight() - 50, getWidth(), getHeight());

		// Time
		g2d.setColor(Color.WHITE);
		g2d.setFont(font);
		String str = Game.getInstance().getScore() > 1 ? " feet" : " foot";
		g2d.drawString(Game.getInstance().getScore() + str, 20,
				getHeight() - 20);

		// Pause
		if (Game.getInstance().isPaused()) {
			g2d.drawString("[PAUSE]", 20, 20);
		}

		// Jumps
		if (Cat.getJumps() >= 1) {
			str = Cat.getJumps() > 1 ? " jumps" : " jump";
			g2d.drawString(Cat.getJumps() + str, 170, getHeight() - 20);
		}

		// Bonus
		if (Game.getInstance().getCaughtBonusCount() >= 1) {
			str = Game.getInstance().getCaughtBonusCount() > 1 ? " bonus'"
					: " bonus";
			g2d.drawString(Game.getInstance().getCaughtBonusCount() + str, 320,
					getHeight() - 20);
		}

		// Pauses
		if (Game.getInstance().getPauseCount() >= 1) {
			str = Game.getInstance().getPauseCount() > 1 ? " pauses" : " pause";
			g2d.drawString(Game.getInstance().getPauseCount() + str, 470,
					getHeight() - 20);
		}

		// Title
		g2d.setFont(title);
		g2d.drawString("Catch Meeow", getWidth() - 150, getHeight() - 20);

		// Character
		g2d.drawImage(cat, 80, getHeight()
				- Game.getInstance().getCat().getHeight(), 47, 47, this);

		// Prey
		g2d.drawImage(prey, getWidth() - 80 - (Game.getInstance().getN() / 3),
				70, 40, 40, this);

		// Obstacles
		g2d.setColor(new Color(210, 63, 43));
		for (Obstacle o : Game.getInstance().getObstacles()) {
			g2d.fillOval(o.getPosition() - o.getWidth(),
					getHeight() - (o.getHeight() + 50), o.getWidth(),
					o.getHeight());
		}

		// Bonus
		if (Game.getInstance().getBonus() != null) {
			Game.getInstance().getBonus();
			g2d.drawImage(bonus, Game.getInstance().getBonus().getPosition(),
					Bonus.getHeight(), 20, 20, this);
		}

	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (!Game.getInstance().isPaused()
				&& (e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_SPACE)) {
			Game.getInstance().getCat().jump();
		}

		if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			Game.getInstance().setPaused(!Game.getInstance().isPaused());
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if (!Game.getInstance().isPaused())
			Game.getInstance().getCat().jump();
	}

	@Override
	public void keyReleased(KeyEvent e) {
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void mouseClicked(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}
}
