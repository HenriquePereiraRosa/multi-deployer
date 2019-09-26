package br.com.insper.event;

import java.util.ArrayList;
import java.util.List;

public class Trigger {
	    private List<TriggerInterface> listeners = new ArrayList<>();

	    public void addListener(TriggerInterface toAdd) {
	        listeners.add(toAdd);
	    }

	    public void triggerEvent(String url) {

	        // Notify everybody that may be interested.
	        for (TriggerInterface hl : listeners)
	            hl.handleEvent(url);
	    }

}
