/**
 * TileInfo class
 * This class is used to store the information of a tile
 * It contains the type of the tile, the value of the tile, and the weight of the tile
 * The weight of the tile is used to calculate the probability of the tile to be generated
 * The higher the weight, the higher the probability of the tile to be generated
 */
public class TileInfo {
    private Blocks.BlockType type;
    private int value;
    private int weight;

    /**
     * Constructs a TileInfo object with the specified type, value, and weight.
     *
     * @param type the type of the tile
     * @param value the value of the tile
     * @param weight the weight of the tile
     */
    public TileInfo(Blocks.BlockType type, int value, int weight) {
        this.type = type;
        this.value = value;
        this.weight = weight;
    }

    public Blocks.BlockType getType() {
        return type;
    }

    public void setType(Blocks.BlockType type) {
        this.type = type;
    }

    public int getValue() {
        return value;
    }

    public int getWeight() {
        return weight;
    }

}
