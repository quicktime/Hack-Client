package at.tiam.bolt.gui.map;

/**
 * Created by quicktime on 5/26/17.
 */
public class ChunkData {

    private int x, z;

    public ChunkData(int x, int z) {
        this.x = x;
        this.z = z;
    }

    public boolean containsPoint(int xPos, int zPos) {
        return xPos > x && xPos <= x + 16 && zPos > z && zPos <= z + 16;
    }

    public int getX() {
        return x;
    }

    public int getZ() {
        return z;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setZ(int z) {
        this.z = z;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ChunkData) {
            ChunkData chunkData = (ChunkData)obj;
            return chunkData.getX() == this.x && chunkData.getZ() == this.z;
        } else {
            return false;
        }
    }
}
