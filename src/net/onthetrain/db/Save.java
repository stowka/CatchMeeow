package net.onthetrain.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import net.onthetrain.game.Cat;
import net.onthetrain.game.Game;

public class Save implements Runnable {
	
	public Save() {
	}
	
	@Override
	public void run() {
		int score = Game.getInstance().getScore();
		if (score >= 10) {
			int error = -1;
			Connection c = SDBH.getConnection();
			String name = Game.getInstance().getName();
			int pauses = Game.getInstance().getPauseCount();
			int jumps = Cat.getJumps();
			int bonus = Game.getInstance().getBonusCount();
			int caughtBonus = Game.getInstance().getCaughtBonusCount();
			String level = Game.getInstance().getLevel();
			
			PreparedStatement pstm = null;
			String query = "INSERT INTO `score` (`name`, `score`, `jumps`, `bonus`, `caughtBonus`, `pauses`, `level`) values (?, ?, ?, ?, ?, ?, ?);";
			try {
				pstm = c.prepareStatement(query);
				pstm.setString(1, name);
				pstm.setInt(2, score);
				pstm.setInt(3, jumps);
				pstm.setInt(4, bonus);
				pstm.setInt(5, caughtBonus);
				pstm.setInt(6, pauses);
				pstm.setString(7, level);
				error = pstm.executeUpdate();
				if (1 != error) {
					System.err.println("Error while saving score");
				}
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				try {
					pstm.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}

}
