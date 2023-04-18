public class NN_3OPT {
    private double[][] distances;
    private int[] bestRoute;
    private int bestDistance;

    public NN_3OPT(double[][] distances) {
        this.distances = distances;
    }

    public void run() {
        int n = distances.length;
        boolean[] visited = new boolean[n];
        int[] route = new int[n+1];
        int startNode = (int) (Math.random() * n); // generate random starting node
        route[0] = startNode;
        visited[startNode] = true;
        for (int i = 1; i < n; i++) {
            int current = route[i-1];
            int next = -1;
            int minDist = Integer.MAX_VALUE;
            for (int j = 0; j < n; j++) {
                if (!visited[j] && distances[current][j] < minDist) {
                    next = j;
                    minDist = (int) distances[current][j];
                }
            }
            route[i] = next;
            visited[next] = true;
        }

        route[n] = startNode;
        int distance = calculateDistance(route);
        while (true) {
            boolean improvement = false;
            for (int i = 0; i < n-2; i++) {
                for (int j = i+1; j < n-1; j++) {
                    for (int k = j+1; k < n; k++) {
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
