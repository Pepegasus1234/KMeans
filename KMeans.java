import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
public class KMeans {
    static final Double PRECISION = 0.0;

    static LinkedList<HashMap<String, Double>> kmeanspp(DataSet data, int K) {
        LinkedList<HashMap<String, Double>> centroids = new LinkedList<>();
        centroids.add(data.randomFromDataSet());
        for (int i = 1; i < K; i++) {
            centroids.add(data.calculateWeighedCentroid());
        }
        return centroids;
    }

    static LinkedList<HashMap<String, Double>> kmeans(DataSet data, int K) {
        LinkedList<HashMap<String, Double>> centroids = kmeanspp(data, K);

        // initialize Sum of Squared Errors to max, we'll lower it at each iteration
        Double SSE = Double.MAX_VALUE;

        while (true) {

            // assign observations to centroids

            var records = data.getRecords();

            for (var record : records) {
                Double minDist = Double.MAX_VALUE;
                for (int i = 0; i < centroids.size(); i++) {
                    Double dist = DataSet.euclidianDistance(centroids.get(i), record.getRecord());
                    if (dist < minDist) {
                        minDist = dist;
                        record.setClusterNo(i);
                    }
                }
            }


            centroids = data.recomputeCentroids(K);

            Double newSSE = data.calculateTotalSSE(centroids);
            if (SSE - newSSE <= PRECISION) {
                return centroids;
            }
            SSE = newSSE;
        }
    }

    public static void main(String[] args) {
        try {

            DataSet data = new DataSet("files/sample.csv");
            LinkedList<HashMap<String, Double>> centroids = kmeans(data, 2);
            data.createCsvOutput("files/sampleClustered.csv");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
