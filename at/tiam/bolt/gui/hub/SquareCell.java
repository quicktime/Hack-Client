package at.tiam.bolt.gui.hub;

import at.tiam.bolt.Bolt;
import at.tiam.bolt.util.GuiUtils;
import at.tiam.bolt.util.ClientUtils;

/**
 * Created by quicktime on 5/26/17.
 */
public class SquareCell {
    private String title;
    private int colour;
    private String contents;

    private static int[] colours = new int[]{
            0x5FFF0000,
            0x5F00FF00,
            0x5F0000FF,
            0x5F404000,
            0x5F004040,
            0x5F400040,
            0x5F202020,
            0x5F305010
    };

    public SquareCell(String title, int colour, String contents) {
        super();
        this.title = title;
        this.colour = colours[colour];
        this.contents = contents;
    }

    public void renderCell(SquareCell cell, ColorModifier colorModifier, int x, int y, int xPos, int yPos, int w, int h){

        if(mouseOverButton(x, y, xPos, yPos, xPos+w, yPos+h)){
            GuiUtils.drawRect(xPos, yPos, xPos+w, yPos+h, colorModifier.getDarkColourWithAlpha(cell.getColor()));
        }else{
            GuiUtils.drawRect(xPos, yPos, xPos+w, yPos+h, cell.getColor());
        }

        //GuiUtils.drawRect(xPos, yPos, xPos+w, yPos+11, 0x7F2B2B2B);

        Bolt.getBolt().getFontRenderer().drawStringWithShadow(cell.getTitle(), xPos + w/2 - Bolt.getBolt().getFontRenderer().getStringWidth(cell.getTitle())/2, yPos + 2, 0xFFFFFFFF);

        Bolt.getBolt().getFontRenderer().drawSplitString(cell.getContents(), xPos + 3, yPos + 14, w-3, 0xFFFFFFFF);
    }

    public boolean mouseOverButton(int x, int y, int bx, int by, int bx1, int by1){
        return x >= bx && x <= bx1 && y >= by && y <= by1;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getColor() {
        return colour;
    }

    public void setColour(int colour) {
        this.colour = colour;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }
}
