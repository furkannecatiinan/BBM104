/**
 * Represents different types of blocks that can be used in the game. Each block type has
 * specific properties such as image paths, value, and weight which are essential for the game mechanics.
 */
public class Blocks {
    public static final String SOIL_IMAGE_PATH = "file:src/assets/underground/soil_01.png";
    public static final String OBSTACLE_IMAGE_PATH = "file:src/assets/underground/obstacle_01.png";
    public static final String GOLD_IMAGE_PATH = "file:src/assets/underground/valuable_goldium.png";
    public static final String DIAMOND_IMAGE_PATH = "file:src/assets/underground/valuable_diamond.png";
    public static final String LAVA_IMAGE_PATH = "file:src/assets/underground/lava_01.png";
    public static final String FLOOR_IMAGE_PATH = "file:src/assets/underground/floor_01.png";
    public static final String TOPSOIL_IMAGE_PATH = "file:src/assets/underground/top_01.png";
    public static final String AMAZONITE_IMAGE_PATH = "file:src/assets/underground/valuable_amazonite.png";
    public static final String BRONZIUM_IMAGE_PATH = "file:src/assets/underground/valuable_bronzium.png";
    public static final String EINSTEINIUM_IMAGE_PATH = "file:src/assets/underground/valuable_einsteinium.png";
    public static final String EMERALD_IMAGE_PATH = "file:src/assets/underground/valuable_emerald.png";
    public static final String IRONIUM_IMAGE_PATH = "file:src/assets/underground/valuable_ironium.png";
    public static final String PLATINIUM_IMAGE_PATH = "file:src/assets/underground/valuable_platinum.png";
    public static final String RUBY_IMAGE_PATH = "file:src/assets/underground/valuable_ruby.png";
    public static final String SILVERIUM_IMAGE_PATH = "file:src/assets/underground/valuable_silverium.png";
    public static final String EMPTY_IMAGE_PATH = "file:src/assets/underground/empty_15.png";
    public static final String SKY_IMAGE_PATH = "file:src/assets/underground/empty_15.png";

    /**
     * Enumeration of all possible block types used in the game.
     * Each type has a specific value and weight.
     */
    public enum BlockType {
        SOIL(0, 0),
        OBSTACLE(-1, 0),
        EMPTY(0, 0),
        GOLD(250, 20),
        DIAMOND(100000, 100),
        LAVA(-1, 0),
        FLOOR(0, 0),
        TOPSOIL(0, 0),
        AMAZONITE(500000, 120),
        BRONZIUM(60, 10),
        EINSTEINIUM(2000, 40),
        EMERALD(5000, 60),
        IRONIUM(30, 10),
        PLATINIUM(750, 30),
        RUBY(20000, 80),
        SILVERIUM(100, 10),
        SKY(0, 0);

        private final int value;
        private final int weight;


        BlockType(int value, int weight) {
            this.value = value;
            this.weight = weight;
        }

        public int getValue() {
            return value;
        }

        public int getWeight() {
            return weight;
        }
    }

}
