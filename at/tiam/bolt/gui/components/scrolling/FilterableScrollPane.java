package at.tiam.bolt.gui.components.scrolling;

import at.tiam.bolt.gui.AbstractComponent;
import at.tiam.bolt.gui.components.selector.SelectorButton;

import java.util.List;
import java.util.ArrayList;

/**
 * Created by quicktime on 5/26/17.
 */
public class FilterableScrollPane extends ScrollPane {

    protected List<SelectorButton> componentsPool = new ArrayList<SelectorButton>();

    public FilterableScrollPane(int height) {
        super(height);
    }

    public void addFilterableComponent(SelectorButton c) {
        this.componentsPool.add(c);
        super.addComponent(c);
    }

    public void applyFilter(String filter){

        filter = filter.toLowerCase();

        components.clear();

        if(filter.length() > 0){

            for(SelectorButton selectorButton : componentsPool){
                if(selectorButton.getStaticText().toLowerCase().contains(filter)){
                    this.addComponent(selectorButton);
                }
            }

        }else{

            for(AbstractComponent comp : componentsPool){
                this.addComponentRaw(comp);
            }

            this.updateSize();
        }
    }
}
