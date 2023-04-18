public class City {
    int node;
    double x_Coordinates;
    double y_Coordinates;

    public City(int node, double xCoordinates, double yCoordinates) {
        this.node = node;
        this.x_Coordinates = xCoordinates;
        this.y_Coordinates = yCoordinates;
    }

    public int getNode() {
        return node;
    }
    public double getX() {
        return x_Coordinates;
    }

    public double getY() {
        return y_Coordinates;
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
