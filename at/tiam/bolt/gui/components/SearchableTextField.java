package at.tiam.bolt.gui.components;

import at.tiam.bolt.gui.components.scrolling.FilterableScrollPane;

/**
 * Created by quicktime on 5/26/17.
 */
public class SearchableTextField extends TextField {

    private FilterableScrollPane scrollPane;

    public SearchableTextField(String placeholder, FilterableScrollPane scrollPane) {
        super(placeholder);
        this.scrollPane = scrollPane;
    }

    @Override
    protected void removeLastCharacter() {
        super.removeLastCharacter();
        applyFilter();
    }

    @Override
    protected void addCharacter(char c) {
        super.addCharacter(c);
        applyFilter();
    }

    @Override
    protected void addText(String string) {
        super.addText(string);
        applyFilter();
    }

    private void applyFilter() { scrollPane.applyFilter(contents); }
}
