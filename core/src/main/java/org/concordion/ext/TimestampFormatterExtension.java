package org.concordion.ext;


import org.concordion.api.extension.ConcordionExtender;
import org.concordion.api.extension.ConcordionExtension;
import org.concordion.ext.footer.TimestampFormattingSpecificationListener;

/**
 * Formats the footer of the Concordion output HTML to show the time in hours, minutes and seconds rather than milliseconds.
 * <p>
 * To install the extension:
 * <pre>
 *       System.setProperty("concordion.extensions", "org.concordion.ext.TimestampFormatterExtension");
 * </pre>
 */
public class TimestampFormatterExtension implements ConcordionExtension {

    @Override
    public void addTo(ConcordionExtender concordionExtender) {
        concordionExtender.withSpecificationProcessingListener(new TimestampFormattingSpecificationListener());
    }
}
