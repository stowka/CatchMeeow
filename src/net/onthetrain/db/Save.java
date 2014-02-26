package net.onthetrain.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Save implements Runnable {

	private int score = 0;
	private String name = null;
	
	public Save(int score, String name) {
		this.score = score;
		this.name = name;
	}
	
	@Override
	public void run() {
		int error = -1;
		Connection c = SDBH.getConnection();
		PreparedStatement pstm = null;
		String query = "INSERT INTO `score` (`name`, `time`) values (?, ?);";
		try {
			pstm = c.prepareStatement(query);
			pstm.setString(1, name);
			pstm.setInt(2, score);
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
