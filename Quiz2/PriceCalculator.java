public class PriceCalculator {

    public static double calculateTotalDecorationCost(Classroom classroom, Decoration floorDecoration, Decoration wallDecoration) {
        double totalCost = 0.0;
        double floorArea = calculateFloorArea(classroom);


        if (floorDecoration != null) {
            double floorCost = calculateDecorationCost(floorArea, floorDecoration);
            totalCost += floorCost;
        }

        double wallSurfaceArea = calculateWallSurfaceArea(classroom);

        if (wallDecoration != null) {
            double wallCost = calculateDecorationCost(wallSurfaceArea, wallDecoration);
            totalCost += wallCost;
        }

        return Math.ceil(totalCost);
    }

    public static double calculateDecorationCost(double area, Decoration decoration) {
        switch (decoration.getType()) {
            case "Tile":
                int tilesUsed = (int) Math.ceil(area / decoration.getArea());
                return tilesUsed * decoration.getPrice();
            case "Paint":
            case "Wallpaper":
                return area * decoration.getPrice();
            default:
                return 0;
        }
    }


    public static double calculateFloorArea(Classroom classroom) {
        switch (classroom.getShape()) {
            case "Circle":

                double radius = classroom.getWidth()/2;
                return Math.PI * Math.pow(radius, 2);
            case "Rectangle":
                return classroom.getWidth() * classroom.getLength();
            default:
                return 0;
        }
    }




    public static double calculateWallSurfaceArea(Classroom classroom) {
        switch (classroom.getShape()) {
            case "Circle":

                double radius = classroom.getWidth()/2;
                double perimeterCircle =(Math.PI * 2 * radius * classroom.getHeight());

                return perimeterCircle;
            case "Rectangle":

                double perimeterRectangle = 2 * (classroom.getHeight() * classroom.getWidth() + classroom.getHeight() * classroom.getLength());


                return perimeterRectangle;
            default:
                return 0;
        }


}
    static Decoration findDecorationByName(String name) {
        if (name == null || name.isEmpty()) return null;

        for (Decoration decoration : Reader.decorations) {
            if (decoration != null && name.equals(decoration.getName())) {
                return decoration;
            }
        }
        return null;
    }
    public static int calculateTilesUsed(Classroom classroom, Decoration floorDecoration) {
        return (int) Math.ceil(calculateFloorArea(classroom) / floorDecoration.getArea());
    }
}


