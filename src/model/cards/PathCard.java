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
	protected boolean isToxic = false;

	public PathCard() {
		
		type = "path";

	}
	
	public boolean[] getExits() {
		
		return exits;
		
	}
	
	//Cycles each of the varibles in the exits array the amount
	//of times the card has been rotated, then returns the result.
	//Does not change the original exits array variable.
	private void rotateExits() {
		
		boolean tmp = exits[3];
		exits[3] = exits[2];
		exits[2] = exits[1];
		exits[1] = exits[0];
		exits[0] = tmp;
		
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
		
		rotateExits();
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
	
	public boolean getIsToxic() {
		
		return isToxic;
		
	}
	
	public void setIsToxic(boolean isToxic) {
		
		this.isToxic = isToxic;
		
	}
	
}
