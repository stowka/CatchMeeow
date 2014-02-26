package net.onthetrain.gui;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import net.onthetrain.game.Game;

public class Frame extends JFrame implements Runnable, ActionListener, WindowListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final int WIDTH = 800;
	private static final int HEIGHT = 200;

	private Panel panel = null;

	private JMenuBar menuBar;
	private JMenu file, help;
	private JMenuItem settings, close, credits;
	
	private static Frame instance = null;

	private Frame() {
		this.panel = new Panel();

		this.menuBar = new JMenuBar();
		this.file = new JMenu("File");
		this.help = new JMenu("Help");

		this.settings = new JMenuItem("Settings");
		this.settings.addActionListener(this);
		this.close = new JMenuItem("Close");
		this.close.addActionListener(this);
		this.credits = new JMenuItem("Credits");
		this.credits.addActionListener(this);

		this.file.add(settings);
		this.file.add(close);
		this.help.add(credits);

		this.menuBar.add(file);
		this.menuBar.add(help);

		this.setTitle("Catch Meeow - " + Game.getInstance().getName());

		this.setIconImage(Toolkit.getDefaultToolkit().getImage(
				"assets/img/miaouss.png"));

		this.setResizable(false);
		this.setSize(new Dimension(WIDTH, HEIGHT));
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.addKeyListener(panel);
		this.addMouseListener(panel);

		this.setContentPane(panel);
		this.setJMenuBar(menuBar);

		this.setVisible(true);
	}
	
	public static Frame getInstance() {
		if (instance == null)
			instance = new Frame();
		return instance;
	}

	@Override
	public void run() {
		while (true) {
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			Game.getInstance().setTime(System.currentTimeMillis());
			Game.getInstance().getCat().resetJumps();
			Game.getInstance().resetScore();
			Game.getInstance().setPauseCount(0);
			while (Game.getInstance().getCat().isFlying())
				panel.repaint();
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		if (source == settings) {
			Game.getInstance().showSettings();
		} else if (source == close) {
			close();
		} else if (source == credits) {
			credits();
		}
	}

	private void close() {
		String[] options = { "Yes, but I'll play again very soon!",
				"No, I just misclicked!" };
		int n = JOptionPane.showOptionDialog(null, "Are you sure?", "Exit",
				JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null,
				options, options[0]);
		if (0 == n)
			System.exit(0);
	}

	private void credits() {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowActivated(WindowEvent arg0) {}

	@Override
	public void windowClosed(WindowEvent arg0) {}

	@Override
	public void windowClosing(WindowEvent arg0) {
		close();
	}

	@Override
	public void windowDeactivated(WindowEvent arg0) {
		Game.getInstance().setPaused(true);
	}

	@Override
	public void windowDeiconified(WindowEvent arg0) {}

	@Override
	public void windowIconified(WindowEvent arg0) {
		Game.getInstance().setPaused(true);
	}

	@Override
	public void windowOpened(WindowEvent arg0) {}
}
