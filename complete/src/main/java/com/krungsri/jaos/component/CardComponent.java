package com.krungsri.jaos.component;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.smartcardio.CardException;
import javax.smartcardio.CardTerminal;
import javax.smartcardio.CardTerminals;
import javax.smartcardio.TerminalFactory;
import javax.smartcardio.CardTerminals;

import org.springframework.stereotype.Component;

@Component
public class CardComponent {
	
	TerminalFactory context;
	private CardTerminals terminals;
	
	@PostConstruct
    private void init() {
        System.out.println((">> CardComponent initialization logic ..."));
        TerminalFactory terminalFactory = TerminalFactory.getDefault();
		terminals = terminalFactory.terminals();
		
		try {
			List<CardTerminal> terminalList = terminals.list();
			if(terminalList.size() > 0) {
				
			}
		} catch (CardException e) {
			System.out.println((">> CardComponent CardTerminal fail ..."));
			e.printStackTrace();
		}
    }
	
	@PreDestroy
    public void destroy() {
        System.out.println(">> Callback triggered - @PreDestroy.");
        context = null;
		System.gc(); // not entirely sure if this will immediately dispose() the TerminalFactory
    }

}
