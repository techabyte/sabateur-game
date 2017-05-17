package controller;

import model.PlayerD;
import view.MainView;
public class GameEngine {

	private MainView mainView;
	private static PlayerController players;
	private static int currentPlayerIndex;
	private static int turn;

	//run game
	public static void main(String[] args) {

		new GameEngine();

	}

	public GameEngine() {

	    GameState gameState = new GameState();
	    //gameState
	    
		DeckBuilder deckBuilder = new DeckBuilder();
		deckBuilder.addAllCards();
		deckBuilder.Shuffle();

		players = PlayerController.getInstance();
		currentPlayerIndex = 0;
		mainView = new MainView();
		mainView.launchApp(this);

	}



	public static int getCurrentPlayerIndex() {

		return currentPlayerIndex;

	}
	
	public static void setCurrentPlayerIndex(int index) {
		
		currentPlayerIndex = index;
		
	}


	//set the next player's turn
	public PlayerD nextPlayer() {

		if (currentPlayerIndex == players.getPlayerList().size()-1) {
		    turn++;
			currentPlayerIndex = 0;
			System.out.println("New turn");

		}

		else currentPlayerIndex++;

		return getCurrentPlayer();

	}

	public PlayerD getCurrentPlayer() {

		return players.getPlayerByPosition(currentPlayerIndex);

	}
	public PlayerD getPrevPlay() {

		return players.getPlayerByPosition(currentPlayerIndex);

	}
	
	public static PlayerD getNextPlayer() {

		if (currentPlayerIndex == players.getPlayerList().size()-1) {

			return players.getPlayerByPosition(0);

		}

		else return players.getPlayerByPosition(currentPlayerIndex+1);

	}


}

