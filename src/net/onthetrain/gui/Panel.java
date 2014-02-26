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

import net.onthetrain.game.Cat;
import net.onthetrain.game.Game;
import net.onthetrain.game.Obstacle;

public class Panel extends JPanel implements KeyListener, MouseListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Game game = null;
	private Font font = null;
	private Image cat = null, prey = null;

	public Panel(Game game) {
		this.game = game;
		try {
			this.cat = ImageIO.read(new File("assets/img/miaouss.png"));
			this.prey = ImageIO.read(new File("assets/img/magicarpe.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		try {
			this.font = Font.createFont(Font.TRUETYPE_FONT, new FileInputStream("assets/fonts/SourceSansPro-Regular.ttf"));
			this.font = this.font.deriveFont(18F);
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

		// Ground
		g2d.setColor(new Color(0, 150, 85));
		g2d.fillRect(0, getHeight() - 50, getWidth(), getHeight());

		// Time
		g2d.setColor(Color.WHITE);
		g2d.setFont(font);
		g2d.drawString(
				(int) ((System.currentTimeMillis() - game.getTime()) / 1000)
						+ " seconds", 20, getHeight() - 20);

		// Jumps
		g2d.drawString(Cat.getJumps() + " jumps", 20 + (getWidth() / 2),
				getHeight() - 20);

		// Character
		g2d.drawImage(cat, 80, getHeight() - game.getCat().getHeight(), 47, 47,
				this);

		// Prey
		g2d.drawImage(prey, getWidth() - 80 - (game.getN() / 3), 88, 40, 40,
				this);

		// Obstacles

		g2d.setColor(new Color(210, 63, 43));
		for (Obstacle o : game.getObstacles()) {
			g2d.fillRect(o.getPosition() - o.getWidth(),
					getHeight() - (o.getHeight() + 50), o.getWidth(),
					o.getHeight());
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == 49) {
			game.getCat().jump();
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		game.getCat().jump();
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
