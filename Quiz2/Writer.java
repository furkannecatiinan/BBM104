import java.io.PrintWriter;

public class Writer {
    public static void writeDecorationDetails(PrintWriter output, Classroom classroom, Decoration wallDecoration, Decoration floorDecoration, double classroomCost) {
        double wallSurfaceArea = PriceCalculator.calculateWallSurfaceArea(classroom);
        double floorArea = PriceCalculator.calculateFloorArea(classroom);
        int tilesUsed = PriceCalculator.calculateTilesUsed(classroom, floorDecoration);
        int tilesSize = (int) floorDecoration.getArea();

        if (wallDecoration.getType().equals("Tile")) {
            output.println("Classroom " + classroom.getName() + " used " + (int)Math.ceil(wallSurfaceArea/tilesSize) + " Tiles for walls and used " + tilesUsed + " Tiles for flooring, these costed " + (int)classroomCost + "TL.");
        } else {
            output.println("Classroom " + classroom.getName() + " used " + (int) Math.ceil(wallSurfaceArea) + "m2 of " + wallDecoration.getType() + " for walls and used " + (int) Math.ceil(floorArea/tilesSize) + " Tiles for flooring, these costed " + (int)classroomCost + "TL.");
        }
    }
}
