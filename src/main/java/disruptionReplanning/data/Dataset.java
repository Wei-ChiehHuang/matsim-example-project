package disruptionReplanning.data;

import java.util.HashMap;
import java.util.Map;

public class Dataset {



    static Map<Integer, Trips> tripMap = new HashMap<>();

    public static Map<Integer, Trips> getTripMap() {
        return tripMap;
    }

    public static void setTripMap(Map<Integer, Trips> tripMap) {
        Dataset.tripMap = tripMap;
    }

    public static Map<Integer, PublicTransportLines> getLineMap() {
        return lineMap;
    }

    public static void setLineMap(Map<Integer, PublicTransportLines> lineMap) {
        Dataset.lineMap = lineMap;
    }

    public static Map<Integer, PublicTransportLines> lineMap = new HashMap<>();

}
