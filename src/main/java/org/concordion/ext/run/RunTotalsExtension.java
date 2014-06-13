package org.concordion.ext.run;


import org.concordion.api.extension.ConcordionExtender;
import org.concordion.api.extension.ConcordionExtension;
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
		event.getElement().appendText(" (threw exception)");
	}

	@Override
	public void successReported(RunSuccessEvent event) {		
		event.getElement().appendText(" (" + event.getResultSummary().printCountsToString(event.getResultSummary()) + ")");
	}

	@Override
	public void failureReported(RunFailureEvent event) {
		event.getElement().appendText(" (" + event.getResultSummary().printCountsToString(event.getResultSummary()) + ")");
	}

	@Override
	public void ignoredReported(RunIgnoreEvent event) {
		event.getElement().appendText(" (" + event.getResultSummary().printCountsToString(event.getResultSummary()) + ")");
	}

}
