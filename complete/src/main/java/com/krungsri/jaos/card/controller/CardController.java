package com.krungsri.jaos.card.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import com.krungsri.jaos.card.model.CardModel;
import com.krungsri.jaos.card.model.CardMessage;
import com.krungsri.jaos.component.CardComponent;

@Controller
public class CardController {
	
	@Autowired
	CardComponent cardComponent ;
	
	@MessageMapping("/reader")
	@SendTo("/card/cid")
	public CardModel card(CardMessage message) throws Exception {
		Thread.sleep(1000); // simulated delay
		if(message.getCommand().equalsIgnoreCase("card status")) {
			return new CardModel("00","success","isCardPresent:"+this.cardComponent.isCardPresent());
		}else if(message.getCommand().equalsIgnoreCase("card read")) {
			return this.cardComponent.transmit();
		}else {
			return new CardModel("99","fail","command not found");
		}
		
	}

}
