package disruptionReplanning.identifyAffectedPassengers;

import disruptionReplanning.Util;
import disruptionReplanning.createMatsimPlans.Trips2Plans;
import disruptionReplanning.data.Dataset;
import disruptionReplanning.data.PublicTransportLines;
import disruptionReplanning.data.Trips;
import org.apache.log4j.Logger;
import org.matsim.api.core.v01.Scenario;
import org.matsim.api.core.v01.network.Network;
import org.matsim.core.api.experimental.events.EventsManager;
import org.matsim.core.events.EventsReaderXMLv1;
import org.matsim.core.events.EventsUtils;
import org.matsim.core.events.handler.EventHandler;
import org.matsim.core.network.NetworkUtils;
import org.matsim.core.network.io.MatsimNetworkReader;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class IdentifyAffectedCommuters {

    private static final Logger logger = Logger.getLogger(IdentifyAffectedCommuters.class);

    private static final String LinesListPath = "Z:/indiv/wei/Master Thesis/Thesis/ScenarioAnalysis/network/PublicTransportLines.csv";
    private static final String TripsListPath = "Z:/indiv/wei/Master Thesis/Thesis/ScenarioAnalysis/mucSP/trips.csv";

    public static Map<Integer, PublicTransportLines> lineMap = new HashMap<>();

    public static void main(String[] args) throws IOException {

        Trips2Plans trips2plans = new Trips2Plans();
        trips2plans.readTrips(TripsListPath);

        readPTLineList(LinesListPath);
        Dataset.setLineMap(lineMap);

        EventsManager events = EventsUtils.createEventsManager();
        Network network = NetworkUtils.createNetwork();
        MatsimNetworkReader networkReader = new MatsimNetworkReader(network);
        networkReader.readFile("Z:/indiv/wei/Master Thesis/Thesis/ScenarioAnalysis/mucSP/initialAssignment/output_network.xml.gz");

        AffectedCommutersEventHandler disruptionHandler = new AffectedCommutersEventHandler();
        events.addHandler(disruptionHandler);

        EventsReaderXMLv1 eventsReader = new EventsReaderXMLv1(events);
        eventsReader.readFile("Z:/indiv/wei/Master Thesis/Thesis/ScenarioAnalysis/mucSP/initialAssignment/output_events.xml.gz");

        Map<Integer, Trips> temp = Dataset.getTripMap();
        for (int t = 0; t < temp.size(); t++) {
            if(temp.get(t).isInterrupted()){
                System.out.println(temp.get(t).getTripId());
                System.out.println(temp.get(t).getInterruptedVehicle());
                System.out.println(temp.get(t).getInterruptionType());
            }
        }
        System.out.println("check");
        //output a new trip table
        //output new plans

    }

    public static void readPTLineList(String LinesListPath) throws IOException {

        logger.info("Start reading pt line lists.");

        BufferedReader reader = new BufferedReader(new FileReader(LinesListPath));

        String[] header = reader.readLine().split(",");

        int indexSystem = Util.findPositionInArray("System", header);
        int indexRoute = Util.findPositionInArray("Route", header);

        int indexLine = Util.findPositionInArray("Line", header);
        int indexDescription = Util.findPositionInArray("Description", header);
        int indexInterrupted = Util.findPositionInArray("Interrupted", header);

        int id = 0;

        String line;
        while ((line = reader.readLine()) != null) {

            String[] splitLine = line.split(",");

            String system = splitLine[indexSystem];
            String route = splitLine[indexRoute];
            int lineId = Integer.parseInt(splitLine[indexLine]);
            String description = splitLine[indexDescription];
            int interrupted = Integer.parseInt(splitLine[indexInterrupted]);

            PublicTransportLines ptLines = new PublicTransportLines(system, route, lineId, description, interrupted);

            lineMap.put(id, ptLines);
            id++;
        }
        logger.info("Read " + id + " public transport lines");
    }


}
