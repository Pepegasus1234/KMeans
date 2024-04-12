import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import static org.junit.jupiter.api.Assertions.*;

class KMeansTest {
    @org.junit.jupiter.api.Test
    void CheckCentroidCorrectness() {
        DataSet data;
        LinkedList<HashMap<String, Double>> centroids;
        try {
            data = new DataSet("files/sample.csv");
            centroids = KMeans.kmeans(data, 2);
            for (var record : data.getRecords()) {
                if (record.clusterNo == 0) {
                    assertTrue(DataSet.euclidianDistance(centroids.get(0), record.getRecord())<DataSet.euclidianDistance(centroids.get(1), record.getRecord()));
                }
                else if (record.clusterNo == 1){
                    assertTrue(DataSet.euclidianDistance(centroids.get(1), record.getRecord())<DataSet.euclidianDistance(centroids.get(0), record.getRecord()));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


