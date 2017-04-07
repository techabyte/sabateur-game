package controller;

import model.Board;
import model.Deck;
import model.Player;
import view.MainView;

public class GameEngine {
	
	private MainView mainView;
	private Board board;
	private PlayerController players;
	private Deck deck;
	private int currentPlayerIndex;
	
	public static void main(String[] args) {
		
		new GameEngine();

	}
	
	public GameEngine() {
		
		//setup board
		board = Board.getInstance();
		board.initBoard();
		board.setGameBoard(6, 3, 5);
		
		players = PlayerController.getInstance();
		deck = Deck.getInstance();
		currentPlayerIndex = 0;
		mainView = new MainView();
		mainView.launchApp(this);
		
	}
	
	public Board getBoard() {
		
		return board;
		
	}
	
	public PlayerController getPlayers() {
		
		return players;
		
	}
	
	public Deck getDeck() {
		
		return deck;
		
	}
	
	public int getCurrentPlayerIndex() {
		
		return currentPlayerIndex;
		
	}
	
	public Player nextPlayer() {
		
		if (currentPlayerIndex == players.getPlayerList().size()-1) {
			
			currentPlayerIndex = 0;
			
		}
		
		else currentPlayerIndex++;
		
		return getCurrentPlayer();
		
	}
	
	public Player getCurrentPlayer() {
		
		return players.getPlayerByPosition(currentPlayerIndex);
		
	}

}

