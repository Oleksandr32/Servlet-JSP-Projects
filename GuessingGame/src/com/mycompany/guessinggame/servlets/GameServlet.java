package com.mycompany.guessinggame.servlets;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import java.sql.SQLException;

import java.util.Collections;
import java.util.List;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.mycompany.guessinggame.data.Winner;
import com.mycompany.guessinggame.database.Database;
import com.mycompany.guessinggame.database.dao.WinnerDao;

/**
 * Servlet implementation class gameServlet
 * @author Oleksandr Lysun
 */
@WebServlet( "/winners" )
public class GameServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	// date fields
	private static final String PARAM_NAME = "name";
	private static final String PARAM_TRIES = "numOfTries";
	private static final String PAGE_WINNERS = "winners.jsp";

	private Properties dbProperties;
	private Database database;
	private WinnerDao winnerDao;
	
	private List<Winner> winners;
	
	// configure logging
	private static final Logger logger = LogManager.getLogger( GameServlet.class );
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GameServlet() {
        super();
        
        dbProperties = null;
        database = null;
        winnerDao = null;
        winners = null;
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		logger.info( "GameServlet\nRequest method: get" );
		
		boolean result = prepare();
		if ( result ) {
			logger.debug( "Successful preparation." );
			
			getWinners();
			if (winners != null) { 
				request.setAttribute( "winners", winners ); 
				logger.debug( "Set Attribute: 'winners'" ); 
			}
			
			request.getRequestDispatcher( PAGE_WINNERS ).forward( request, response );
			logger.debug( "PAGE_WINNERS: RequestDispatcher.forward(...) to " + PAGE_WINNERS );
			return;
		}
		
		logger.debug( "Preparation failed." );	
		
		response.sendError( HttpServletResponse.SC_NO_CONTENT, "Preparation failed." ); 
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		logger.info( "Processing the sent form. Request method: post." );
		
		String name = request.getParameter( PARAM_NAME );
		Integer numOfTries = Integer.valueOf( request.getParameter( PARAM_TRIES ) );
		
		logger.debug("name = " + name + "\nnumber of tries = " + numOfTries);
		
		boolean result = prepare();
		if ( result ) {
			logger.debug( "Successful preparation." );
			
			try {
				result = Database.tableExists( Database.TABLE_NAME );
					
				if ( !result )
					winnerDao.create();		
				
				result = addWinner( name, numOfTries );
				
				if ( result ) {
					logger.debug( "The winner has been successfully added." );
					
					getWinners();
					if (winners != null) { 
						request.setAttribute( "winners", winners ); 
						logger.debug( "Set Attribute: 'winners'" ); 
					}
					
					request.setAttribute( "infoMessage", "You have been added to the list!" );
					
					request.getRequestDispatcher( PAGE_WINNERS ).forward( request, response );
					logger.debug( "PAGE_WINNERS: RequestDispatcher.forward(...) to " + PAGE_WINNERS );
					return;					
					
				} else 
					logger.debug( "The winner has not been added." );	
				
			} catch (SQLException e) {
				logger.error( e.getMessage() );
				response.sendError( HttpServletResponse.SC_NO_CONTENT, "Preparation failed." );
				return;
			}			
		} 
		
		logger.debug( "Preparation failed." );	
		
		response.sendError( HttpServletResponse.SC_NO_CONTENT, "Preparation failed." ); 
	}
	
	// method returns list of all winners
	private void getWinners() {
		logger.debug( "Get list of all winners." );
		
		try { 
			winners = winnerDao.getWinners(); 
		} catch (SQLException e) {
			logger.error( e.getMessage() );
		}
		
		logger.debug( "sort list of winners by number of tries" );
		// sort list of winners by tries
		Collections.sort( winners, (w1, w2) -> w1.getNumOfTries().compareTo( w2.getNumOfTries() ) );
	}
	
	// method adds new winner
	private boolean addWinner(String name, Integer numOfTries) {
		logger.debug( "Adding a winner." );
		
		Winner winner = new Winner( name, numOfTries );		
		try {
			winnerDao.add( winner );
		} catch (SQLException e) {
			logger.error( e.getMessage() );
			
			return false;
		}
		
		return true;
	}
	
	// initialize all required fields
	private boolean prepare() {
		logger.debug( "Initialize all required fields." );
		
		if ( dbProperties == null ) {
			// test database
			File dbPropertiesFile = new File( Database.DB_PROPERTIES_FILENAME );
			if (!dbPropertiesFile.exists()) {
				logger.error( "Data Base property file not found." + dbPropertiesFile.getAbsolutePath() );
				return false;
			}		
			
			dbProperties = new Properties();
			try {
				dbProperties.load( new FileReader( dbPropertiesFile ) );
			} catch (IOException e) {
				logger.error( e.getMessage() );
				return false;
			}
		}
		
		if ( database == null ) {
			try {
				database = new Database( dbProperties );
			} catch (IOException e) {
				logger.error( e.getMessage() );
				return false;
			} catch (SQLException e) {
				logger.error( e.getMessage() );
				return false;
			}
		}
		
		if ( winnerDao == null ) 
			winnerDao = new WinnerDao( database, Database.TABLE_NAME );
		
		return true;
	}

}
