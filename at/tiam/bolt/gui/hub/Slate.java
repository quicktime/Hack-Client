package at.tiam.bolt.gui.hub;

import java.io.IOException;

/**
 * Created by quicktime on 5/25/17.
 */
public class Slate {

    private SquareCell[] cells;
    private String name;
    protected GuiHub guiHub;

    public Slate(String name, SquareCell[] cells, GuiHub guiHub) {
        super();
        this.cells = cells;
        this.name = name;
        this.guiHub = guiHub;
    }

    public void renderSlate(int x, int y) {
        int alignXMiddle = guiHub.width / 2 - (5 * guiHub.cellSize) / 2;
        int lineNum = cells.length / 5;
        int alignYMiddle = guiHub.height / 2 - ((lineNum * 2) * guiHub.cellSize) / 2;

        int num = 0;

        int w = guiHub.cellSize - guiHub.cellPadding;
        int h = guiHub.cellSize - guiHub.cellPadding;

        for (SquareCell squareCell : cells) {
            int column = num % 5;
            int row = num / 5;

            int xPos = column * guiHub.cellSize + alignXMiddle + guiHub.cellPadding;
            int yPos = alignYMiddle + row * guiHub.cellSize + guiHub.cellPadding;

            squareCell.renderCell(squareCell, guiHub.colorModifier, x, y, xPos, yPos, w, h);

            num++;
        }
    }

    public void mouseClicked(int x, int y) {

    }

    public void keyTyped(char c, int key) throws IOException {

    }

    public void init() {

    }

    public void close() {

    }

    public SquareCell[] getCells() { return cells; }
    public void setCells(SquareCell[] cells) { this.cells = cells; }
    public String getName() { return name; }

    public void setName(String name) {
        this.name = name;
    }
    public void mouseReleased(int x, int y, int state) { }
}
