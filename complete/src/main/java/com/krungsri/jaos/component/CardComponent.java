package com.krungsri.jaos.component;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.smartcardio.CardException;
import javax.smartcardio.CardTerminal;
import javax.smartcardio.CardTerminals;
import javax.smartcardio.TerminalFactory;
import javax.smartcardio.Card;
import javax.smartcardio.CardChannel;
import org.springframework.stereotype.Component;
import javax.smartcardio.CommandAPDU;
import javax.smartcardio.ResponseAPDU;

import com.krungsri.jaos.card.model.CardModel;

@Component
public class CardComponent {

	private TerminalFactory context;
	private CardTerminals terminals;
	private CardTerminal terminal;
	private boolean isCardPresent;
	private Card card;
	private CardChannel channel;

	byte[] cmd1 = { (byte) 0x00, (byte) 0xA4, (byte) 0x04, (byte) 0x00, (byte) 0x08, (byte) 0xA0, (byte) 0x00,
			(byte) 0x00, (byte) 0x00, (byte) 0x54, (byte) 0x48, (byte) 0x00, (byte) 0x01 };

	@PostConstruct
	private void init() {
		System.out.println((">> CardComponent initialization"));
		this.context = TerminalFactory.getDefault();
		this.terminals = this.context.terminals();

		try {
			List<CardTerminal> terminalList = terminals.list();
			if (terminalList.size() > 0) {
				this.terminal = terminalList.get(0);
				String name = this.terminal.getName();
				System.out.println(">> terminal name : " + name);
				this.isCardPresent = terminal.isCardPresent();
				System.out.println(">> isCardPresent : " + isCardPresent);
				// Establish a connection with the card using
				// "T=0", "T=1", "T=CL" or "*"
				this.card = this.terminal.connect("*");
				System.out.println(">> connect * " + card.getProtocol());
				// Get ATR
				byte[] atr = card.getATR().getBytes();
				System.out.println(">> ATR: " + toString(atr));

				// Select Card Manager
				// - Establish channel to exchange APDU
				// - Send SELECT Command APDU
				// - Show Response APDU
				this.channel = card.getBasicChannel();
			} else {
				System.out.println(">> terminal not found");
			}
		} catch (CardException e) {
			System.out.println((">> CardComponent CardTerminal fail ..."));
			e.printStackTrace();
		}

	}

	public CardModel transmit() {
		if (isCardPresent()) {
			ResponseAPDU r;
			try {
				System.out.println(">>" + toString(cmd1));
				r = channel.transmit(new CommandAPDU(cmd1));
				System.out.println(">>: " + toString(r.getBytes()));
				return new com.krungsri.jaos.card.model.CardModel("00", "success", toString(r.getBytes()));
			} catch (CardException e) {
				return new com.krungsri.jaos.card.model.CardModel("99", "fail", "transmit apdu fail");
			}
		} else {
			return new com.krungsri.jaos.card.model.CardModel("99", "fail", "please insert card");
		}
	}

	public boolean isCardPresent() {
		return isCardPresent;
	}

	public void setCardPresent(boolean isCardPresent) {
		this.isCardPresent = isCardPresent;
	}

	public static String toString(byte[] bytes) {
		StringBuilder sbTmp = new StringBuilder();
		for (byte b : bytes) {
			sbTmp.append(String.format("%X", b));
		}
		return sbTmp.toString();
	}

	@PreDestroy
	public void destroy() {
		System.out.println(">> Callback triggered - @PreDestroy.");
		context = null;
		System.gc(); // not entirely sure if this will immediately dispose() the TerminalFactory
	}

}
