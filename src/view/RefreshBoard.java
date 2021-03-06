package view;

import controller.PlayGameListener;
import javafx.scene.image.ImageView;
import model.Board;
import model.cards.Card;
import model.cards.PathCard;

public class RefreshBoard {

	public RefreshBoard() {

	}

	ImageView[][] imageView;
	// GameView.getImageView();

	public void refreshView() {
	    //refresh hand images
	    PlayGameListener.getGameView().refreshHand();

	    
	    //refresh board images
		for (int i = 0; i < Board.getInstance().getRows(); i++) {
			for (int j = 0; j < Board.getInstance().getCols(); j++) {
				Card card = Board.getInstance().getCard(i, j);
				if (card.getType() != null && card.getType() != "board") {
    					String imageName;
    					if (card.getType() == "path" && card.getName() != "start") {

    		                   if(((PathCard) Board.getInstance().getCard(i, j)).getIsToxic()){
                                   imageName = "/resources/images/cards/Toxic Card.png";
    		                   }
    		                   else if(((PathCard) Board.getInstance().getCard(i, j)).getIsInfested()){
    		                       imageName = "/resources/images/cards/Rat.png";
    		                   }
    		                   else if(((PathCard) Board.getInstance().getCard(i, j)).getIsBlocked()){
    		                       imageName = "/resources/images/cards/Road Block.png";
    		                   }
    		                   else{
    		                       imageName = "/resources/images/cards/" + card.getName() + "-rotate"
                                           + ((PathCard) card).getRotation() + ".png";
    		                   }
    					} else {
    						imageName = "/resources/images/cards/" + card.getName() + ".png";
    					}
    					PlayGameListener.getGameView().setImage(j, i, imageName);
				}
			}
		}
	}

}
