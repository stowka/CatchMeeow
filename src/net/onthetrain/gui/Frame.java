package net.onthetrain.gui;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import net.onthetrain.db.Save;
import net.onthetrain.game.Game;

public class Frame extends JFrame implements Runnable, ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final int WIDTH = 800;
	private static final int HEIGHT = 200;

	private Panel panel = null;
	
	private JMenuBar menuBar;
	private JMenu file, help;
	private JMenuItem close, save, credits;

	public Frame() {
		this.panel = new Panel();
		
		this.menuBar = new JMenuBar();
		this.file = new JMenu("File");
		this.help = new JMenu("Help");
		
		this.close = new JMenuItem("Close");
		this.close.addActionListener(this);
		this.save = new JMenuItem("Save");
		this.save.addActionListener(this);
		this.credits = new JMenuItem("Credits");
		this.credits.addActionListener(this);
		
		this.file.add(save);
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

	@Override
	public void run() {
		while (true) {
			while (Game.getInstance().getCat().isFlying()) {
				panel.repaint();
			}
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		if (source == close) {
			close();
		} else if (source == save) {
			new Thread(new Save(Game.getInstance().getScore(), Game.getInstance().getName())).start();
		} else if (source == credits) {
			credits();
		}
	}

	private void close() {
		String[] options = {"Yes, but I'll play again very soon!", "No, I just misclicked!"};
		int n = JOptionPane.showOptionDialog(null, "Are you sure?", "Exit",
				JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null,
				options, options[0]);
		if (0 == n)
			System.exit(0);
	}

	private void credits() {
		// TODO Auto-generated method stub
		
	}
}
