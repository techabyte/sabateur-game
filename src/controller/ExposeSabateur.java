package controller;

import java.util.Iterator;

import model.PlayerD;

public class ExposeSabateur {
	
	public void playCard(PlayerD currentPlayer, PlayerD targetPlayer) {
		
		if (targetPlayer.getType() == "sabateur") {
		
			if (targetPlayer.getExposedBy() != null) {
			
				targetPlayer.getExposedBy().removeExposedSabateur(targetPlayer);
				removeExposedSabRecursive(targetPlayer, currentPlayer);
				currentPlayer.exposeSabateur(targetPlayer);
				targetPlayer.setExposedBy(currentPlayer);
			
			}
			else {
			
				removeExposedSabRecursive(targetPlayer, currentPlayer);
				currentPlayer.exposeSabateur(targetPlayer);
				targetPlayer.setExposedBy(currentPlayer);
				
			}
			
		}
		
	}
	
	public void removeExposedSabRecursive(PlayerD topPlayer, PlayerD removePlayer) {
		
		for(Iterator<PlayerD> iterator = topPlayer.getExposedSabateurs().iterator(); iterator.hasNext(); ) {
			
			PlayerD p = iterator.next();
			
			if(p.equals(removePlayer)) {
				
				iterator.remove();
				removeExposedSabRecursive(topPlayer, removePlayer);
				
			}
			
			else removeExposedSabRecursive(p, removePlayer);
			
		}
		
	}

}
