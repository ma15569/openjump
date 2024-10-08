package org.openjump.core.ui.plugin.tools.aggregate;

/**
 * A wrapper class including :
 * <ul>
 *     <li>name of the input attribute</li>
 *     <li>aggregator to be used to aggregate values</li>
 *     <li>name of the output attribute</li>
 * </ul>
 */
public class AttributeAggregator {

    private final String inputName;
    private final Aggregator<?> aggregator;
    private final String outputName;

    public AttributeAggregator(String inputName, Aggregator<?> aggregator, String outputName) {
        this.inputName = inputName;
        this.aggregator = aggregator;
        this.outputName = outputName;
    }

    String getInputName() {
        return inputName;
    }

    public Aggregator<?> getAggregator() {
        return aggregator;
    }

    String getOutputName() {
        return outputName;
    }
}
