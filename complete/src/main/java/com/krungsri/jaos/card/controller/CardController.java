package com.krungsri.jaos.card.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import com.krungsri.jaos.card.model.Card;
import com.krungsri.jaos.card.model.CardMessage;

@Controller
public class CardController {
	
	@MessageMapping("/reader")
	@SendTo("/topic/cid")
	public Card card(CardMessage message) throws Exception {
		Thread.sleep(1000); // simulated delay
		return new Card("card mock");
	}

}
