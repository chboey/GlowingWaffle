public class City {
    int node;
    double x_Coordinates;
    double y_Coordinates;

    public City(int node, double xCoordinates, double yCoordinates) {
        this.node = node;
        this.x_Coordinates = xCoordinates;
        this.y_Coordinates = yCoordinates;
    }

    @Override
    public String toString() {
        return "City{" +
                "node=" + node +
                ", xCoord=" + x_Coordinates +
                ", yCoord=" + y_Coordinates +
                '}';
    }

}
