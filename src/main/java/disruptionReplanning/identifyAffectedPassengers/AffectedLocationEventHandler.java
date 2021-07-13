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
import org.matsim.core.api.experimental.events.AgentWaitingForPtEvent;
import org.matsim.core.api.experimental.events.VehicleArrivesAtFacilityEvent;
import org.matsim.core.api.experimental.events.handler.AgentWaitingForPtEventHandler;
import org.matsim.core.api.experimental.events.handler.VehicleArrivesAtFacilityEventHandler;
import org.matsim.core.network.NetworkUtils;
import org.matsim.pt.transitSchedule.api.TransitStopFacility;
import org.matsim.vehicles.Vehicle;

import java.util.HashMap;
import java.util.Map;

public class AffectedLocationEventHandler implements VehicleArrivesAtFacilityEventHandler, AgentWaitingForPtEventHandler {

    Map<Integer, PublicTransportLines> lineMap = Dataset.getLineMap();
    Map<Integer, Trips> tripMap = Dataset.getTripMap();
    Map<Id<Person>, Map<Id<Vehicle>, Double>> vehEnterHistory = new HashMap();

    double startDisruptionTime_sec = 8 * 3600;
    double endDisruptionTime_sec = 9 * 3600;

    @Override
    public void handleEvent(VehicleArrivesAtFacilityEvent vehicleArrivesAtFacilityEvent) {
        vehicleArrivesAtFacilityEvent.getVehicleId();
        vehicleArrivesAtFacilityEvent.getTime();
        vehicleArrivesAtFacilityEvent.getFacilityId();


    }

    @Override
    public void handleEvent(AgentWaitingForPtEvent agentWaitingForPtEvent) {
        agentWaitingForPtEvent.getPersonId();
        agentWaitingForPtEvent.getTime();
        agentWaitingForPtEvent.waitingAtStopId.toString();
    }


}
