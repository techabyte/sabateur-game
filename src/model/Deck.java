package model;
import java.util.Collections;
import java.util.Iterator;
import java.util.Stack;

import model.cards.*;

public class Deck {
	private static Deck ourInstance = new Deck();

	public static Deck getInstance() {
		return ourInstance;
	}


	private Stack<Card> deck = new Stack<Card>();

	public Deck(){

		//add the following cards to the deck
		//where the passed int is the number of cards
		addLPathCards(4);
		addXPathCards(4);
		addStraightPathCards(4);
		addBlockPathCards(4);
		addTPathCards(4);


	}
	public void shuffle(){
		//shuffle all cards in deck
		Collections.shuffle(deck);
	}

	public Card draw(){
		//drawn next card from deck
		//return null if no cards in deck
		//if(this.cards.length > 0){
		if(deck.isEmpty() == false){
			return deck.pop();
		}
		return null;
	}
	//print card name to console, for debugging:
	public void printCards(){
		System.out.println("cards2 stack printout: ");
		Iterator<Card> deckIteration = deck.iterator();
		while (deckIteration.hasNext()){
			System.out.println(deckIteration.next());
		}
	}


	private void addLPathCards(int totalCards){
		System.out.println("addingLPC...");
		for(int i = 0; i<totalCards; i++){
			System.out.println("LPC added");
			LPathCard lPCard = new LPathCard(0);
			deck.push(lPCard);
		}
	}
	private void addXPathCards(int totalCards){
		for(int i = 0; i<totalCards; i++){
			XPathCard xCard = new XPathCard(0);
			deck.push(xCard);
		}
	}
	private void addBlockPathCards(int totalCards){
		for(int i = 0; i<totalCards; i++){
			BlockPathCard blockCard = new BlockPathCard(0);
			deck.push(blockCard);
		}
	}
	private void addStraightPathCards(int totalCards){
		for(int i = 0; i<totalCards; i++){
			StraightPathCard straightCard = new StraightPathCard(0);
			deck.push(straightCard);
		}
	}
	private void addTPathCards(int totalCards){
		System.out.println("addingTPC...");
		for(int i = 0; i<totalCards; i++){
			System.out.println("TPC added");
			TPathCard tPathCard = new TPathCard(0);
			deck.push(tPathCard);
		}
	}

}
