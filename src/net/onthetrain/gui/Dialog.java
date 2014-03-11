package net.onthetrain.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import net.onthetrain.game.Game;

public class Dialog extends JDialog {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JLabel nameLabel, speedLabel, icon;
	private JComboBox speed;
	private JTextField name;

	public Dialog(JFrame parent, String title, boolean modal) {
		super(parent, title, modal);
		this.setSize(600, 170);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		this.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		this.initComponent();
		this.setVisible(true);
	}

	private void initComponent() {
		// Icon
		icon = new JLabel(new ImageIcon("resources/img/settings.png"));
		System.out.println(getClass().getResource("resources/img/settings.png"));
		JPanel panIcon = new JPanel();
		panIcon.setBackground(Color.WHITE);
		panIcon.setPreferredSize(new Dimension(110, 100));
		panIcon.setLayout(new BorderLayout());
		panIcon.add(icon);

		// Name
		JPanel panName = new JPanel();
		panName.setBackground(Color.WHITE);
		panName.setPreferredSize(new Dimension(220, 100));
		name = new JTextField();
		name.setPreferredSize(new Dimension(100, 25));
		panName.setBorder(BorderFactory.createTitledBorder("Name"));
		nameLabel = new JLabel("What's your name?");
		panName.add(nameLabel);
		panName.add(name);

		// Speed
		JPanel panSpeed = new JPanel();
		panSpeed.setBackground(Color.WHITE);
		panSpeed.setPreferredSize(new Dimension(220, 100));
		panSpeed.setBorder(BorderFactory
				.createTitledBorder("Speed"));
		speed = new JComboBox();
		speed.addItem("Slow");
		speed.addItem("Medium");
		speed.addItem("Fast");
		speed.setSelectedIndex(1);
		speedLabel = new JLabel("How fast do you want to play?");
		panSpeed.add(speedLabel);
		panSpeed.add(speed);

		JPanel content = new JPanel();
		content.setBackground(Color.WHITE);
		content.add(panIcon);
		content.add(panName);
		content.add(panSpeed);

		JPanel control = new JPanel();
		JButton okBouton = new JButton("Play!");

		okBouton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (!name.getText().equals("")) {
					Game.getInstance().setName(name.getText());
					Frame.getInstance().setTitle("Catch Meeow - " + name.getText() + " [" + speed.getSelectedItem() + "]");
					Game.getInstance().setLevel(speed.getSelectedItem().toString());
					long speedLong = speed.getSelectedItem().equals("Slow") ? 5 : speed.getSelectedItem().equals("Medium") ? 3 : 2;
					Game.getInstance().setSpeed(speedLong);
					setVisible(false);
				} else {
					name.grabFocus();
				}
			}
		});

		JButton cancelBouton = new JButton("Cancel");
		cancelBouton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				setVisible(false);
			}
		});

		control.add(okBouton);
		control.add(cancelBouton);
 
		this.getContentPane().add(panIcon, BorderLayout.WEST);
		this.getContentPane().add(content, BorderLayout.CENTER);
		this.getContentPane().add(control, BorderLayout.SOUTH);
	}
}