package com.mycompany.guessinggame.database.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.mycompany.guessinggame.data.Winner;
import com.mycompany.guessinggame.database.Database;

/**
 * @author Oleksandr Lysun
 * 
 */
public class WinnerDao {
	// data fields
	private final Database database;
	private final String tableName;
	
	// configure logging
	private static final Logger logger = LogManager.getLogger( WinnerDao.class );
	
	// constructor
	public WinnerDao(Database database, String tableName) {
		this.database = database;
		this.tableName = tableName;
	}
	
	// method creates new table in database
	public void create() throws SQLException {
		String sql = String.format(
				"create table %s(" // 1
						+ "%s %s(%d) %s, " // 2 3 4 5
						+ "%s %s(%d) %s, " // 6 7 8 9
						+ "%s %s(%d) %s, " // 10 11 12 13
						+ "PRIMARY KEY (%s) )", // 14
				tableName, // 1
				Fields.ID.getName(), Fields.ID.getType(), // 2 3
				Fields.ID.getLength(), Fields.ID.getProperties(), // 4 5
				Fields.NAME.getName(), Fields.NAME.getType(), // 6 7 
				Fields.NAME.getLength(), Fields.NAME.getProperties(), // 8 9
				Fields.TRIES.getName(), Fields.TRIES.getType(), // 10 11 
				Fields.TRIES.getLength(), Fields.TRIES.getProperties(), // 12 13
				Fields.ID.getName() );   // 14				

		logger.debug( sql );
						
		Statement statement = null;
		try {
			Connection connection = database.getConnection();
			statement = connection.createStatement();
			statement.executeUpdate( sql );
		} finally {
			close( statement );
		}
	}
	
	// method adds winner to database
	public void add(Winner winner) throws SQLException {
		String sql = String.format(
				"INSERT into `%s`(`%s`, `%s`) values(" // 1 tableName, 2 name, 3 tries
						+ "'%s', " // 4 nameValue
						+ "%d)", // 5 triesValue
				tableName, // 1
				Fields.NAME.getName(), // 2 
				Fields.TRIES.getName(), // 3 
				winner.getName(), // 4
				winner.getNumOfTries() ); // 5
		
		logger.debug( sql );
		
		
		Statement statement = null;
		try {
			Connection connection = database.getConnection();
			statement = connection.createStatement();
			statement.executeUpdate( sql );
		} finally {
			close( statement );
		}
	}	
	
	// method returns list of all winners
	public List<Winner> getWinners() throws SQLException {
		Connection connection;
		Statement statement = null;
		List<Winner> winners = new LinkedList<Winner>();
		
		try {
			connection = database.getConnection();
			statement = connection.createStatement();
			// Execute a statement
			String sql = String.format( "SELECT `%s`, `%s` FROM `%s`", Fields.NAME.getName(), Fields.TRIES.getName(), tableName );
			// logging
			logger.debug( sql );
			
			ResultSet resultSet = statement.executeQuery( sql );

			// get all Winners
			while ( resultSet.next() ) {				
				Winner winner = new Winner();
				winner.setName( resultSet.getString( Fields.NAME.getName() ) );
				winner.setNumOfTries( Integer.valueOf( resultSet.getString( Fields.TRIES.getName() ) ) );
				
				winners.add( winner );
			}
		} finally {
			close( statement );
		}

		return winners;
	}

	// close statement
	private void close(Statement statement) {
		try {
			if (statement != null) {
				statement.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public enum Fields {
		ID( "id", "INT", 5, "NOT NULL AUTO_INCREMENT" ), // 1
		NAME( "name", "VARCHAR", 45, "NOT NULL" ), // 2
		TRIES( "tries", "INT", 2, "NOT NULL" ); // 3
		
		private final String name;
		private final String type;
		private final int length;
		private final String properties;

		Fields(String name, String type, int length, String properties) {
			this.name = name;
			this.type = type;
			this.length = length;
			this.properties = properties;
		}

		public String getName() {
			return name;
		}
		
		public String getType() {
			return type;
		}

		public int getLength() {
			return length;
		}

		public String getProperties() {
			return properties;
		}
	}
}
