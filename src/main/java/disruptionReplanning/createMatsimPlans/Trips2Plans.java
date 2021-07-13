package disruptionReplanning.createMatsimPlans;

import disruptionReplanning.Util;
import disruptionReplanning.data.MitoMode;
import disruptionReplanning.data.MitoPurpose;
import disruptionReplanning.data.Trips;
import disruptionReplanning.data.Dataset;
import org.apache.log4j.Logger;
import org.matsim.api.core.v01.Coord;
import org.matsim.api.core.v01.Id;
import org.matsim.api.core.v01.Scenario;
import org.matsim.api.core.v01.population.*;
import org.matsim.core.config.Config;
import org.matsim.core.config.ConfigUtils;
import org.matsim.core.scenario.ScenarioUtils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Trips2Plans {

    private static final Logger logger = Logger.getLogger(Trips2Plans.class);

    private static final String TripsListPath = "Z:/indiv/wei/Master Thesis/Thesis/ScenarioAnalysis/mucSP/trips.csv";
    private static final String PlansFilePath = "Z:/indiv/wei/Master Thesis/Thesis/ScenarioAnalysis/mucSP/10percent_plans_ptPassenger.xml.gz";

    private static int startTimeWindow_sec = 7 * 3600;
    private static int endTimeWindow_sec = 11 * 3600;

    static Map<Integer, Trips> tripMap = new HashMap<>();

    private static Population pop;
    private static Scenario scenario;

    public Trips2Plans() {
        Config config = ConfigUtils.createConfig();
        scenario = ScenarioUtils.loadScenario(config);
        pop = scenario.getPopulation();
    }


    public static void main(String[] args) throws IOException {
        Trips2Plans trips2plans = new Trips2Plans();
        trips2plans.readTrips(TripsListPath);
        Dataset.setTripMap(tripMap);
        trips2plans.trips2plans(PlansFilePath, startTimeWindow_sec, endTimeWindow_sec);
    }

    private void trips2plans(String plansFilePath, int startTimeWindow_sec, int endTimeWindow_sec) throws IOException {

        logger.info("Start converting plans.");

        for (int i = 0; i < tripMap.size(); i++) {

            double tId = tripMap.get(i).getTripId();
            double pId = tripMap.get(i).getPersonId();

            int origin = tripMap.get(i).getOrigin();
            Coord originCoords = tripMap.get(i).getOriginCoords();
            int destination = tripMap.get(i).getDestination();
            Coord destinationCoords = tripMap.get(i).getDestinationCoords();

            MitoMode mode = tripMap.get(i).getMode();
            MitoPurpose purpose = tripMap.get(i).getPurpose();

            double departureTime_sec = tripMap.get(i).getDepartureTime_min() * 60;
            double departureTimeReturning_sec = tripMap.get(i).getReturnDepartureTime_min() * 60;



            if (departureTime_sec >= startTimeWindow_sec && departureTime_sec <= endTimeWindow_sec) {
                Id<Person> personId = Id.createPersonId((long) tId);
                Person person = scenario.getPopulation().getFactory().createPerson(personId);
                Plan plan = scenario.getPopulation().getFactory().createPlan();

                Activity activity1 = scenario.getPopulation().getFactory().createActivityFromCoord("home", originCoords);
                activity1.setEndTime(departureTime_sec);
                plan.addActivity(activity1);

                Leg leg1 = scenario.getPopulation().getFactory().createLeg("pt");
                plan.addLeg(leg1);

                Activity activity2 = scenario.getPopulation().getFactory().createActivityFromCoord(purpose.toString(), destinationCoords);
                plan.addActivity(activity2);

                person.addPlan(plan);
                scenario.getPopulation().addPerson(person);
            }

            if (departureTimeReturning_sec >= startTimeWindow_sec && departureTimeReturning_sec <= endTimeWindow_sec) {
                Id<Person> personIdReturn = Id.createPersonId((long) (tId * -1));
                Person personReturn = scenario.getPopulation().getFactory().createPerson(personIdReturn);
                Plan planReturn = scenario.getPopulation().getFactory().createPlan();

                Activity activity3 = scenario.getPopulation().getFactory().createActivityFromCoord(purpose.toString(), destinationCoords);
                activity3.setEndTime(departureTimeReturning_sec);
                planReturn.addActivity(activity3);

                Leg leg2 = scenario.getPopulation().getFactory().createLeg("pt");
                planReturn.addLeg(leg2);

                Activity activity4 = scenario.getPopulation().getFactory().createActivityFromCoord("home", originCoords);
                planReturn.addActivity(activity4);

                personReturn.addPlan(planReturn);
                scenario.getPopulation().addPerson(personReturn);
            }

        }
        PopulationWriter pw = new PopulationWriter(scenario.getPopulation());
        pw.write(PlansFilePath);
        logger.info("Plan size: " + scenario.getPopulation().getPersons().size());
    }

    public void readTrips(String TripsListPath) throws IOException {

        logger.info("Start reading trip lists.");

        BufferedReader reader = new BufferedReader(new FileReader(TripsListPath));

        String[] header = reader.readLine().split(",");

        int indexTripId = Util.findPositionInArray("id", header);
        int indexPersonId = Util.findPositionInArray("person", header);

        int indexOrigin = Util.findPositionInArray("origin", header);
        int indexOrigX = Util.findPositionInArray("originX", header);
        int indexOrigY = Util.findPositionInArray("originY", header);
        int indexDestination = Util.findPositionInArray("destination", header);
        int indexDestX = Util.findPositionInArray("destinationX", header);
        int indexDestY = Util.findPositionInArray("destinationY", header);

        int indexTripPurpose = Util.findPositionInArray("purpose", header);
        int indexTripMode = Util.findPositionInArray("mode", header);

        int indexDepartTime = Util.findPositionInArray("departure_time", header);
        int indexDepartTimeReturnDaytrip = Util.findPositionInArray("departure_time_return", header);

        int id = 0;

        String line;
        while ((line = reader.readLine()) != null) {

            String[] splitLine = line.split(",");
            String tripMode = splitLine[indexTripMode];

            if (tripMode.equalsIgnoreCase("bus") || tripMode.equalsIgnoreCase("train") || tripMode.equalsIgnoreCase("tramOrMetro")) {

                int tripId = Integer.parseInt(splitLine[indexTripId]);
                int personId = Integer.parseInt(splitLine[indexPersonId]);

                int origin = Integer.parseInt(splitLine[indexOrigin]);
                double origX = Double.parseDouble(splitLine[indexOrigX]);
                double origY = Double.parseDouble(splitLine[indexOrigY]);

                int destination = Integer.parseInt(splitLine[indexDestination]);
                double destX = Double.parseDouble(splitLine[indexDestX]);
                double destY = Double.parseDouble(splitLine[indexDestY]);

                String tripPurpose = splitLine[indexTripPurpose];

                int departTime_min = Integer.parseInt(splitLine[indexDepartTime]);
                int departTimeReturn_min = 0;
                if (!splitLine[indexDepartTimeReturnDaytrip].equals("NA")){
                    departTimeReturn_min = Integer.parseInt(splitLine[indexDepartTimeReturnDaytrip]);
                }

                Trips trip = new Trips(tripId, personId,
                        origin, origX, origY, destination, destX, destY,
                        tripPurpose, tripMode,
                        departTime_min, departTimeReturn_min);

                tripMap.put(id, trip);
                id++;
            }
        }
        logger.info("Read " + id + " public transport trips");
        Dataset.setTripMap(tripMap);
    }
}
