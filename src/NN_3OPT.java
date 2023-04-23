public class NN_3OPT {
    private double[][] distances;
    private int[] bestRoute;
    private int bestDistance;

    public NN_3OPT(double[][] distances) {
        this.distances = distances;
    }

    public void run() {
        boolean[] visited = new boolean[distances.length];
        int[] route = new int[distances.length+1];

        int start_Node = (int) (Math.random() * distances.length); // generate random starting node
        route[0] = start_Node;
        visited[start_Node] = true;

        for (int i = 1; i < distances.length; i++) {
            int current_Node = route[i-1];
            int next = -1;
            int minDist = Integer.MAX_VALUE;
            for (int j = 0; j < distances.length; j++) {
                if (!visited[j] && distances[current_Node][j] < minDist) {
                    next = j;
                    minDist = (int) distances[current_Node][j];
                }
            }

            route[i] = next;
            visited[next] = true;
        }

        route[distances.length] = start_Node;
        int distance = calculateDistance(route);
        while (true) {
            boolean improvement = false;
            for (int i = 0; i < distances.length-2; i++) {
                for (int j = i+1; j < distances.length-1; j++) {
                    for (int k = j+1; k < distances.length; k++) {
                        int[] newRoute = threeOptSwap(route, i, j, k);
                        int newDistance = calculateDistance(newRoute);
                        if (newDistance < distance) {
                            route = newRoute;
                            distance = newDistance;
                            improvement = true;
                        }
                    }
                }
            }
            if (!improvement) {
                break;
            }
        }
        bestRoute = route;
        bestDistance = distance;
    }


    private int[] threeOptSwap(int[] route, int i, int j, int k) {
        int n = route.length;
        int[] newRoute = new int[n];
        if (i + 1 >= 0) System.arraycopy(route, 0, newRoute, 0, i + 1);
        int r = i+1;
        for (int x = k; x >= j+1; x--) {
            newRoute[r++] = route[x];
        }
        for (int x = j; x >= i+1; x--) {
            newRoute[r++] = route[x];
        }
        for (int x = k+1; x < n; x++) {
            newRoute[r++] = route[x];
        }
        return newRoute;
    }

    private int calculateDistance(int[] route) {
        int distance = 0;
        for (int i = 0; i < route.length - 1; i++) {
            distance += distances[route[i]][route[i + 1]];
        }
        return distance;
    }

    public int[] getBestRoute() {
        return bestRoute;
    }

    public int getBestDistance() {
        return bestDistance;
    }
}
