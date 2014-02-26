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
		int error = -1;
		Connection c = SDBH.getConnection();
		String name = Game.getInstance().getName();
		int score = Game.getInstance().getScore();
		int pauses = Game.getInstance().getPauseCount();
		Game.getInstance().getCat();
		int jumps = Cat.getJumps();
		String level = Game.getInstance().getLevel();
		
		PreparedStatement pstm = null;
		String query = "INSERT INTO `score` (`name`, `score`, `jumps`, `pauses`, `level`) values (?, ?, ?, ?, ?);";
		try {
			pstm = c.prepareStatement(query);
			pstm.setString(1, name);
			pstm.setInt(2, score);
			pstm.setInt(3, jumps);
			pstm.setInt(4, pauses);
			pstm.setString(5, level);
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
