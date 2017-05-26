package at.tiam.bolt.module.value;

import at.tiam.bolt.gui.AbstractComponent;
import at.tiam.bolt.gui.IRectangle;
import at.tiam.bolt.gui.components.Slider;

/**
 * Created by quicktime on 5/26/17.
 */
public class SliderValue extends AbstractValue<Double> {

    private double lowerBound;
    private double upperBound;
    private boolean rounded;

    public SliderValue(String name, String saveName, ValueRegistry valueRegistry, double defaultValue, double lowerBound, double upperBound, boolean rounded) {
        super(name, saveName, valueRegistry, defaultValue);
        this.lowerBound = lowerBound;
        this.upperBound = upperBound;
        this.rounded = rounded;
    }

    public double getLowerBound() { return lowerBound; }
    public void setLowerBound(double lowerBound) { this.lowerBound = lowerBound; }
    public double getUpperBound() { return upperBound; }
    public void setUpperBound(double upperBound) { this.upperBound = upperBound; }
    public boolean isRounded() { return rounded; }
    public void setRounded(boolean rounded) { this.rounded = rounded; }

    public void setValue(double value) {
        if (rounded) {
            value = (int)Math.round(value);
        }
        if (value > upperBound) {
            value = upperBound;
        }
        if (value < lowerBound) {
            value = lowerBound;
        }

        super.setValue(value);
    }

    @Override
    public AbstractComponent getComponent(IRectangle panel) { return new Slider(this); }
}
