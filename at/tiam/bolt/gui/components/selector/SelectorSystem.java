package at.tiam.bolt.gui.components.selector;

import java.util.ArrayList;

/**
 * Created by quicktime on 5/26/17.
 */
public class SelectorSystem<T extends SelectorButton> {

    public ArrayList<T> buttons = new ArrayList<T>();
    public T selectedButton;

    public void setOnly(T button){

        for(T b : buttons){
            if(b != button){
                b.setSelected(false);
            }
        }

        button.setSelected(true);
        selectedButton = button;
    }

    public T add(T b){
        buttons.add(b);
        return b;
    }
}
