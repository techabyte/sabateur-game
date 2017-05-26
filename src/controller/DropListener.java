package controller;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.TransferMode;
import javafx.stage.Stage;
import model.Board;
import model.PlayerD;
import model.cards.PathCard;
import model.cards.PersonalCard;
import model.cards.PowerToolDecorator;
import model.cards.SuperPowerToolDecorator;
import view.GameView;
import view.PlayAgainView;
import model.cards.ActionCard;
import model.cards.Card;

//allows for one component to be dropped on another
public class DropListener {

	private ActionCardValidator validator;
	private GameView gameView;
	AvatarMaker avatarMaker = new AvatarMaker();
	
	public DropListener(GameView gameView) {
		
		this.gameView = gameView;
		
	}

	public void dragOver(DragEvent event, ImageView target) {

		if (event.getGestureSource() != target) {

			event.acceptTransferModes(TransferMode.COPY_OR_MOVE);

		}

		event.consume();

	}
	
	public void dragOver(DragEvent event, Label target) {

		if (event.getGestureSource() != target) {

			event.acceptTransferModes(TransferMode.COPY_OR_MOVE);

		}

		event.consume();

	}

	/*
	 * when a card is dropped on the board, it goes through a validation process, and if it is
	 * valid, the board is updated to reflect the move
	 */
	public boolean drop(Stage stage, DragEvent event, PlayerD currentPlayer, int draggedCardIndex, 
			ImageView target, ImageView[][] imageViews, int row, int col) {
		
		validator = new ActionCardValidator();
		
		//for debugging
		//System.out.println(currentPlayer.getHand().getCards().get(draggedCardIndex).getType());
		if(validator.checkMove(currentPlayer.getHand().getCards().get(draggedCardIndex), row, col)) {

			if (event.getGestureSource() != target) {

				Card card;
				boolean minersWin = false;

				//if the dragged card is a path card, get the correct rotation and image
				if (currentPlayer.getHand().getCards().get(draggedCardIndex).getType() == "path") {
					
					if (currentPlayer.hasPowerTool() && !currentPlayer.hasSuperPowerTool()) {
						card = new PowerToolDecorator(currentPlayer.getHand().getCards().get(draggedCardIndex)).doAction(row, col);
						currentPlayer.removePowerTool();
						gameView.removeSpecialAvatar();
						playCard(card, target, currentPlayer, draggedCardIndex);
						minersWin = validator.checkMinersWin(row, col);
					}
					
					else if(currentPlayer.hasSuperPowerTool()) {
						
						card = new SuperPowerToolDecorator(new PowerToolDecorator(
								currentPlayer.getHand().getCards().get(draggedCardIndex))).doAction(row, col);
						currentPlayer.removeSuperPowerTool();
						gameView.removeSpecialAvatar();
						playCard(card, imageViews[row][col], currentPlayer, draggedCardIndex);
						if (validator.checkSuperPowerMove(card, row, col+1)) {
							playCard(card, imageViews[row][col+1], currentPlayer, draggedCardIndex);
							minersWin = validator.checkMinersWin(row, col+1);
						}
						if (validator.checkSuperPowerMove(card, row, col-1)) {
							playCard(card, imageViews[row][col-1], currentPlayer, draggedCardIndex);
							minersWin = validator.checkMinersWin(row, col-1);
						}
						if (validator.checkSuperPowerMove(card, row+1, col)) {
							playCard(card, imageViews[row+1][col], currentPlayer, draggedCardIndex);
							minersWin = validator.checkMinersWin(row+1, col);
						}
						if (validator.checkSuperPowerMove(card, row-1, col)) {
							playCard(card, imageViews[row-1][col], currentPlayer, draggedCardIndex);
							minersWin = validator.checkMinersWin(row-1, col);
						}
						
					}
					
					else {
						card = currentPlayer.getHand().getCards().get(draggedCardIndex).doAction(row, col);
						playCard(card, target, currentPlayer, draggedCardIndex);
						minersWin = validator.checkMinersWin(row, col);
						
					}
					
					
					if(minersWin) {
						
						DistributeGold.currentPlayer(currentPlayer);
						DistributeGold.miners();
						DistributeGold.heistedMiners();
						DistributeGold.exposedSabateurs();
						new PlayAgainView(stage).displayView("miners");
						
					}
					
					else if(validator.checkSabateursWin()) {
						
						DistributeGold.sabateurs();
						DistributeGold.heistedMiners();
						DistributeGold.exposedSabateurs();
						new PlayAgainView(stage).displayView("sabateurs");
						
					}
									
					return true;

				}

				else if (currentPlayer.getHand().getCards().get(draggedCardIndex).getType() == "action") {
					
					card = (ActionCard) currentPlayer.getHand().getCards().get(draggedCardIndex);
					Card boardCard = Board.getInstance().getCard(row, col);
					String imageName;
					if(((ActionCard) card).getEffect() == "enable") {
						imageName = "/resources/images/cards/" + boardCard.getName() 
								+ "-rotate" + ((PathCard)boardCard).getRotation() + ".png";
						((PathCard) boardCard).setIsToxic(false);
					}
					else {
						imageName = "/resources/images/cards/" + card.getName() + ".png";
						((PathCard) boardCard).setIsToxic(true);
					}
					Image image = new Image(getClass().getResourceAsStream(imageName));
					target.setImage(image);
					currentPlayer.getHand().discardCard(draggedCardIndex);
					currentPlayer.drawCard();
					
					if(validator.checkSabateursWin()) {
						
						DistributeGold.sabateurs();
						DistributeGold.heistedMiners();
						DistributeGold.exposedSabateurs();
						new PlayAgainView(stage).displayView("sabateurs");
						
					}
					
					return true;

				}

			}

		}
				
		return false;

	}
	
	/*
	 * when a card is dropped on the discard icon, it is removed from the player's hand
	 */
	public boolean drop(Stage stage, DragEvent event, PlayerD currentPlayer, int draggedCardIndex, ImageView target) {
		
		validator = new ActionCardValidator();
		currentPlayer.getHand().discardCard(draggedCardIndex);
		currentPlayer.drawCard();
		
		if(validator.checkSabateursWin()) {
			
			DistributeGold.sabateurs();
			DistributeGold.heistedMiners();
			DistributeGold.exposedSabateurs();
			new PlayAgainView(stage).displayView("sabateurs");
			
		}
		
		return true;

	}
	
	// when a card is dropped on a player, if it is a personal card and a legal move,
	// discard card, draw new card, check if sabateurs won and return true
	public boolean drop(Stage stage, DragEvent event, PlayerD currentPlayer, PlayerD targetPlayer, int draggedCardIndex, Label target) {
		
		validator = new ActionCardValidator();
		PersonalCardValidator personalCardValidator = new PersonalCardValidator();
		
		if(!(currentPlayer.getHand().getCards().get(draggedCardIndex).getType() == "personal")) {
			
			return false;
			
		}
		
		PersonalCard card = (PersonalCard)currentPlayer.getHand().getCards().get(draggedCardIndex);
		if(personalCardValidator.checkMove(card, currentPlayer, targetPlayer)) {
			
			if (card.getName() == "power tool") {
				
				if (targetPlayer.hasPowerTool()) {

					String superPowerTool;
					superPowerTool = avatarMaker.addPower();
					gameView.setAvatarSpecial(targetPlayer, superPowerTool);
					
				}
				
				else {
					
					if (!targetPlayer.hasSuperPowerTool()) {

				        String powerTool;
				        powerTool = avatarMaker.addSuperPower();
						gameView.setAvatarSpecial(targetPlayer, powerTool );

						
					}
					
				}
				
			}
			
			card.doAction(currentPlayer, targetPlayer);
			currentPlayer.getHand().discardCard(draggedCardIndex);
			currentPlayer.drawCard();
			
			if(validator.checkSabateursWin()) {
				
				DistributeGold.sabateurs();
				DistributeGold.heistedMiners();
				DistributeGold.exposedSabateurs();
				new PlayAgainView(stage).displayView("sabateurs");
				
			}
			
			return true;
			
		}
		
		return false;

	}
	
	public void playCard(Card card, ImageView target, PlayerD currentPlayer, int draggedCardIndex) {
		
		String imageName = "/resources/images/cards/" + card.getName() + "-rotate" + ((PathCard) card).getRotation() + ".png";
		Image image = new Image(getClass().getResourceAsStream(imageName));
		target.setImage(image);
		currentPlayer.getHand().discardCard(draggedCardIndex);
		currentPlayer.drawCard();
		
	}

}