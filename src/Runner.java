import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Runner {

    public static void main(String[] args) throws IOException {
        String fileName = "TSP_107.txt";

        City[] cities = new City[107];
        int index = 0;

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] Data = line.split("\\s+");
                int node = Integer.parseInt(Data[0]);
                double x_Coordinates = Double.parseDouble(Data[1]);
                double y_Coordinates = Double.parseDouble(Data[2]);

                cities[index] = new City(node, x_Coordinates, y_Coordinates);
                index++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        Driver.CalculateED(cities);
    }
}