package model.cards;

public abstract class Card {
	private int quantity;
	

	public Card(){
		
	}
	
	public void changeQuantity(int amount){
		quantity = quantity + amount;
	}
	
	public int getQuantity(){
		return quantity;
	}
	
}
