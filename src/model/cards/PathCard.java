package model.cards;

import model.Board;

public abstract class PathCard implements Card {
	
	protected String type;
	protected String name;
	protected int x;
	protected int y;
	protected boolean[] exits = new boolean [4];
	protected int rotation = 0;
	protected Boolean isDisabled = false;

	public PathCard() {
		
		type = "path";

	}
	
	public boolean[] getExits() {
		
		return rotateExits();
		
	}
	
	//Cycles each of the varibles in the exits array the amount
	//of times the card has been rotated, then returns the result.
	//Does not change the original exits array variable.
	private boolean[] rotateExits() {
		
		if(rotation == 1) return exits;
		
		else {
			
			boolean[] exitsToReturn = exits;
			
			for(int i = 0; i<rotation; i++) {
				
				Boolean firstValue = exitsToReturn[0];
				
				for(int j=0;j<exits.length-1;j++) { 
					
					exitsToReturn[j] = exitsToReturn[j+1];
					
				}
				
				exitsToReturn[3] = firstValue;
				
			}	
			
			return exitsToReturn;
		}
		
	}

	//takes two ints, x and y coordinate.
	public void setPosition(int x, int y) {
		
		this.x = x;
		this.y = y;
		
	}
	//returns array of ints, x and y coordinate
	public int[] getPosition() {
		
		int [] position = {x,y};
		return position;
		
	}
	
	public int getRotation() {
		
		return rotation;
		
	}
	
	//changes rotation one tick. 
	//rotate card by clicking on it in the player's hand
	public void changeRotation() {
		
		if(rotation == 3) rotation = 0;
		else rotation ++;
		
	}
	
	public Boolean disabled() {
		
		return isDisabled;
		
	}

	@Override
	public String getType() {
		
		return type;
		
	}
	
	@Override
	public String getName() {
		
		return name;
		
	}
	
	public Card doAction(int row, int col) {
		
		Card card = doAction();
		Board.getInstance().playCard(row, col, card);
		return card;
		
	}
	
}
