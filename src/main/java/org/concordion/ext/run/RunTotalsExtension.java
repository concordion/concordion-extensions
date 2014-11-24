package org.concordion.ext.run;


import org.concordion.api.Element;
import org.concordion.api.extension.ConcordionExtender;
import org.concordion.api.extension.ConcordionExtension;
import org.concordion.api.listener.RunEvent;
import org.concordion.api.listener.RunFailureEvent;
import org.concordion.api.listener.RunIgnoreEvent;
import org.concordion.api.listener.RunListener;
import org.concordion.api.listener.RunSuccessEvent;
import org.concordion.api.listener.ThrowableCaughtEvent;

public class RunTotalsExtension implements ConcordionExtension, RunListener {

	@Override
	public void addTo(ConcordionExtender concordionExtender) {
		concordionExtender.withRunListener(this);
	}

	@Override
	public void throwableCaught(ThrowableCaughtEvent event) {
		writeText(event.getElement(), "threw exception");
	}

	
	
	@Override
	public void successReported(RunSuccessEvent event) {
		writeText(event);
	}


	@Override
	public void failureReported(RunFailureEvent event) {
		writeText(event);
	}

	@Override
	public void ignoredReported(RunIgnoreEvent event) {
		writeText( event);
	}

	private void writeText(RunEvent event) {
		writeText(event.getElement(), event.getResultSummary().printCountsToString(event.getResultSummary()));
	}
	
	private void writeText(Element element, String text) {
		Element sister = new Element("counts");
		sister.appendText(" (" + text + ")");
		element.appendSister(sister);
	}

}
