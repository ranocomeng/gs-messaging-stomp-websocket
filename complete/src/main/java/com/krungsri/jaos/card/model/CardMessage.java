package com.krungsri.jaos.card.model;

public class CardMessage {
	
	public CardMessage() {
	}
	
	
	public CardMessage(String command) {
		super();
		this.command = command;
	}

	String command ;

	public String getCommand() {
		return command;
	}

	public void setCommand(String command) {
		this.command = command;
	}
	
	

}
