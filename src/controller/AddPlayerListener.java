package controller;

import javafx.stage.Stage;
import model.Player;
import view.AddPlayerView;
import model.totalPlayers;
import model.Player;

import java.util.ArrayList;

public class AddPlayerListener {

	public void playerCount(int numPlayers, Stage stage) {
		totalPlayers totalPlayers = new totalPlayers(numPlayers);
		totalPlayers.setNumPlayers(numPlayers);
		System.out.println(totalPlayers.getNumPlayers());
		changeScene(totalPlayers.getNumPlayers(), stage);
	}

	public void createPlayer(String[] players) {
		ArrayList<Player> playArr = new ArrayList<>();
		for(String pNames: players) {
			//System.out.println(pNames);
			Player player = new Player(pNames);
			playArr.add(player);
			System.out.println(player.getName());
		}
		String aname = "test";

		System.out.println(playArr.size());
		for(Player pN : playArr) {
			System.out.println(pN.getName());
		}
		System.out.println(playArr.get(1).getName());


	}
	
	public void changeScene(int numPlayers, Stage stage) {
		
		AddPlayerView addPlayerView = new AddPlayerView(stage);
		addPlayerView.displayView(numPlayers);
	}

}
