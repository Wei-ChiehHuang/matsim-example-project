package disruptionReplanning.identifyAffectedPassengers;

import disruptionReplanning.data.Dataset;
import disruptionReplanning.data.PublicTransportLines;
import disruptionReplanning.data.Trips;
import org.matsim.api.core.v01.Id;
import org.matsim.api.core.v01.events.PersonEntersVehicleEvent;
import org.matsim.api.core.v01.events.PersonLeavesVehicleEvent;
import org.matsim.api.core.v01.events.handler.PersonEntersVehicleEventHandler;
import org.matsim.api.core.v01.events.handler.PersonLeavesVehicleEventHandler;
import org.matsim.api.core.v01.population.Person;
import org.matsim.vehicles.Vehicle;


import java.util.HashMap;
import java.util.Map;

public class AffectedCommutersEventHandler implements PersonEntersVehicleEventHandler, PersonLeavesVehicleEventHandler {

    Map<Integer, PublicTransportLines> lineMap = Dataset.getLineMap();
    Map<Integer, Trips> tripMap = Dataset.getTripMap();
    Map<Id<Person>, Map<Id<Vehicle>, Double>> vehEnterHistory = new HashMap();

    double startDisruptionTime_sec = 8 * 3600;
    double endDisruptionTime_sec = 9 * 3600;

    @Override
    public void handleEvent(PersonEntersVehicleEvent personEntersVehicleEvent) {

        Id<Person> pId = personEntersVehicleEvent.getPersonId();
        Id<Vehicle> vehId = personEntersVehicleEvent.getVehicleId();
        double lineId = Double.parseDouble(vehId.toString().split("-")[0].split("train")[1]);
        double enterTime = personEntersVehicleEvent.getTime();

        if (!pId.toString().contains("pt")) {
            for (int i = 0; i < lineMap.size(); i++) {
                if (lineId == lineMap.get(i).getLine() && lineMap.get(i).isInterrupted() && enterTime <= endDisruptionTime_sec) {
                    vehEnterHistory.putIfAbsent(pId, new HashMap<>());
                    vehEnterHistory.get(pId).putIfAbsent(vehId, enterTime);
                }
            }
        }
    }

    @Override
    public void handleEvent(PersonLeavesVehicleEvent personLeavesVehicleEvent) {

        Id<Person> pId = personLeavesVehicleEvent.getPersonId();
        Id<Vehicle> vehId = personLeavesVehicleEvent.getVehicleId();
        double lineId = Double.parseDouble(vehId.toString().split("-")[0].split("train")[1]);
        double leaveTime = personLeavesVehicleEvent.getTime();
        double enterTime;
        System.out.println("pId" + pId + ";" + leaveTime);

        if (!pId.toString().contains("pt")) {
            for (int i = 0; i < lineMap.size(); i++) {
                if (lineId == lineMap.get(i).getLine() && lineMap.get(i).isInterrupted() && leaveTime > startDisruptionTime_sec) {
                    if (vehEnterHistory.containsKey(pId)) {
                        if (vehEnterHistory.get(pId).containsKey(vehId)) {
                            enterTime = vehEnterHistory.get(pId).get(vehId);
                            for (int t = 0; t < tripMap.size(); t++) {
                                Trips trip = tripMap.get(t);
                                if (trip.getTripId() == Math.abs(Integer.parseInt(pId.toString()))) {
                                    if (Integer.parseInt(pId.toString()) > 0) {
                                        if (!trip.isInterrupted()) {
                                            trip.setInterrupted(true);
                                            trip.setInterruptedVehicle(vehId);
                                            trip.setInterruptionTimePoint(startDisruptionTime_sec);
                                            if (enterTime < startDisruptionTime_sec) {
                                                trip.setInterruptionType("enRoute");
                                                trip.setInterruption10(true);
                                                trip.setInterruption20(true);
                                                trip.setInterruption60(true);
                                            } else {
                                                trip.setInterruptionType("atStation");
                                                trip.setInterruptionTimePoint(enterTime);
                                                double scenarioTime = enterTime - startDisruptionTime_sec / 60;
                                                if (scenarioTime <= 10.0) {
                                                    trip.setInterruption10(true);
                                                } else if (scenarioTime > 10 && scenarioTime <= 20) {
                                                    trip.setInterruption10(true);
                                                    trip.setInterruption20(true);
                                                } else {
                                                    trip.setInterruption10(true);
                                                    trip.setInterruption20(true);
                                                    trip.setInterruption60(true);
                                                }
                                            }
                                            break;
                                        }
                                    } else {
                                        if (!trip.isInterrupted_return()) {
                                            trip.setInterrupted_return(true);
                                            trip.setInterruptedVehicle_return(vehId);
                                            if (enterTime < startDisruptionTime_sec) {
                                                trip.setInterruptionType_return("enRoute");
                                                trip.setInterruptionTimePoint(startDisruptionTime_sec);
                                                trip.setInterruption10(true);
                                                trip.setInterruption20(true);
                                                trip.setInterruption60(true);
                                            } else {
                                                trip.setInterruptionType_return("atStation");
                                                trip.setInterruptionTimePoint(enterTime);
                                                double scenarioTime = enterTime - startDisruptionTime_sec / 60;
                                                if (scenarioTime <= 10.0) {
                                                    trip.setInterruption10(true);
                                                } else if (scenarioTime > 10 && scenarioTime <= 20) {
                                                    trip.setInterruption10(true);
                                                    trip.setInterruption20(true);
                                                } else {
                                                    trip.setInterruption10(true);
                                                    trip.setInterruption20(true);
                                                    trip.setInterruption60(true);
                                                }
                                            }
                                            break;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
