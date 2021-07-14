package disruptionReplanning.identifyAffectedPassengers;

import disruptionReplanning.data.Dataset;
import disruptionReplanning.data.PublicTransportLines;
import disruptionReplanning.data.Trips;
import org.apache.log4j.Logger;
import org.matsim.api.core.v01.Id;
import org.matsim.api.core.v01.events.PersonLeavesVehicleEvent;
import org.matsim.api.core.v01.events.handler.PersonLeavesVehicleEventHandler;
import org.matsim.api.core.v01.population.Person;
import org.matsim.core.api.experimental.events.AgentWaitingForPtEvent;
import org.matsim.core.api.experimental.events.handler.AgentWaitingForPtEventHandler;
import org.matsim.pt.transitSchedule.api.TransitSchedule;
import org.matsim.pt.transitSchedule.api.TransitStopFacility;
import org.matsim.vehicles.Vehicle;


import java.util.HashMap;
import java.util.Map;

public class AffectedCommutersEventHandler implements AgentWaitingForPtEventHandler, PersonLeavesVehicleEventHandler {

    private static final Logger logger = Logger.getLogger(AffectedCommutersEventHandler.class);

    Map<Integer, PublicTransportLines> lineMap = Dataset.getLineMap();
    Map<Integer, Trips> tripMap = Dataset.getTripMap();
    Map<Id<Person>, Map<Double, Double>> enterPtLineHistory = new HashMap();
    Map<Id<Person>, Map<Double, Id<TransitStopFacility>>> enterPtStopHistory = new HashMap();

    double startDisruptionTime_sec = 8 * 3600;
    double endDisruptionTime_sec = 9 * 3600;

    TransitSchedule transitSchedule;
    Map<Id<TransitStopFacility>, TransitStopFacility> transitStops;

    public AffectedCommutersEventHandler(TransitSchedule transitSchedule) {
        logger.info("Start affected commuters event handler.");
        this.transitSchedule = transitSchedule;
        this.transitStops = transitSchedule.getFacilities();
    }

    @Override
    public void handleEvent(AgentWaitingForPtEvent agentWaitingForPtEvent) {
        Id<Person> pId = agentWaitingForPtEvent.getPersonId();
        Id<TransitStopFacility> stopId = agentWaitingForPtEvent.getWaitingAtStopId();
        double lineId = Double.parseDouble(stopId.toString().split("-")[1]);
        double enterTime = agentWaitingForPtEvent.getTime();

        if (!pId.toString().contains("pt")) {
            for (int i = 0; i < lineMap.size(); i++) {
                if (lineId == lineMap.get(i).getLine() && lineMap.get(i).isInterrupted() && enterTime <= endDisruptionTime_sec) {
                    enterPtLineHistory.putIfAbsent(pId, new HashMap<>());
                    enterPtLineHistory.get(pId).putIfAbsent(lineId, enterTime);
                    enterPtStopHistory.putIfAbsent(pId, new HashMap<>());
                    enterPtStopHistory.get(pId).putIfAbsent(lineId, stopId);
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

        if (leaveTime > startDisruptionTime_sec) {
            if (enterPtLineHistory.containsKey(pId)) {
                if (enterPtLineHistory.get(pId).containsKey(lineId)) {

                    double enterTime = enterPtLineHistory.get(pId).get(lineId);
                    Id<TransitStopFacility> stop = enterPtStopHistory.get(pId).get(lineId);
                    double scenarioTime = (enterTime - startDisruptionTime_sec) / 60;

                    for (Trips trip : tripMap.values()) {
                        if (trip.getTripId() == Math.abs(Integer.parseInt(pId.toString()))) {
                            if (Integer.parseInt(pId.toString()) > 0) {
                                if (!trip.isInterrupted()) {
                                    trip.setInterrupted(true);
                                    trip.setInterruptedStopFacility(stop);
                                    trip.setInterruptedCoords(transitStops.get(stop).getCoord());
                                    if (scenarioTime < 0) {
                                        trip.setInterruptionType("enRoute");
                                        trip.setInterruptionTimePoint(startDisruptionTime_sec);
                                        trip.setInterruption10(true);
                                        trip.setInterruption20(true);
                                        trip.setInterruption60(true);
                                    } else {
                                        trip.setInterruptionType("atStation");
                                        trip.setInterruptionTimePoint(enterTime);
                                        if (scenarioTime >= 0.0 && scenarioTime < 10.0) {
                                            trip.setInterruption10(true);
                                            trip.setInterruption20(true);
                                            trip.setInterruption60(true);
                                        } else if (scenarioTime >= 10 && scenarioTime < 20) {
                                            trip.setInterruption10(true);
                                            trip.setInterruption20(true);
                                        } else {
                                            trip.setInterruption10(true);
                                        }
                                    }
                                    break;
                                }
                            } else {
                                if (!trip.isInterrupted_return()) {
                                    trip.setInterrupted_return(true);
                                    trip.setInterruptedStopFacility_return(stop);
                                    trip.setInterruptedCoords_return(transitStops.get(stop).getCoord());
                                    if (scenarioTime < 0) {
                                        trip.setInterruptionType_return("enRoute");
                                        trip.setInterruptionTimePoint_return(startDisruptionTime_sec);
                                        trip.setInterruption10_return(true);
                                        trip.setInterruption20_return(true);
                                        trip.setInterruption60_return(true);
                                    } else {
                                        trip.setInterruptionType_return("atStation");
                                        trip.setInterruptionTimePoint_return(enterTime);
                                        if (scenarioTime >= 0.0 && scenarioTime < 10.0) {
                                            trip.setInterruption10_return(true);
                                            trip.setInterruption20_return(true);
                                            trip.setInterruption60_return(true);
                                        } else if (scenarioTime >= 10 && scenarioTime < 20) {
                                            trip.setInterruption10_return(true);
                                            trip.setInterruption20_return(true);
                                        } else {
                                            trip.setInterruption10_return(true);
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
