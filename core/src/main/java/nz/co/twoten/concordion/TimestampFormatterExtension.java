package nz.co.twoten.concordion;

import nz.co.twoten.concordion.footer.TimestampFormattingSpecificationListener;

import org.concordion.api.extension.ConcordionExtender;
import org.concordion.api.extension.ConcordionExtension;

/**
 * Formats the footer of the Concordion output HTML to show the time in hours, minutes and seconds rather than milliseconds.
 * <p>
 * To install the extension:
 * <pre>
 *       System.setProperty("concordion.extensions", "nz.co.twoten.concordion.TimestampFormatterExtension");
 * </pre>
 */
public class TimestampFormatterExtension implements ConcordionExtension {

    @Override
    public void addTo(ConcordionExtender concordionExtender) {
        concordionExtender.withSpecificationProcessingListener(new TimestampFormattingSpecificationListener());
    }
}
