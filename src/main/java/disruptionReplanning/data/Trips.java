package disruptionReplanning.data;

import org.matsim.api.core.v01.Coord;
import org.matsim.api.core.v01.Id;
import org.matsim.vehicles.Vehicle;

public class Trips {

    double tripId;
    double personId;

    int origin;
    Coord originCoords;
    int destination;
    Coord destinationCoords;

    MitoPurpose purpose;
    MitoMode mode;

    double departureTime_min;
    double returnDepartureTime_min;

    boolean interrupted = false;
    String interruptionType;
    Coord interruptedCoords;
    Id<Vehicle> interruptedVehicle;
    boolean interruption10 = false;
    boolean interruption20 = false;
    boolean interruption60 = false;
    double interruptionTimePoint;

    boolean interrupted_return = false;
    String interruptionType_return;
    Coord interruptedCoords_return;
    Id<Vehicle> interruptedVehicle_return;
    boolean interruption10_return = false;
    boolean interruption20_return = false;
    boolean interruption60_return = false;
    double interruptionTimePoint_return;

    public Trips (double tripId, double personId,
                  int origin, double originX, double originY, int destination, double destinationX, double destinationY,
                  String purpose, String mode,
                  double departureTime_min, double returnDepartureTime_min) {

        this.tripId = tripId;
        this.personId = personId;
        this.origin = origin;
        this.originCoords = new Coord(originX, originY);
        this.destination = destination;
        this.destinationCoords = new Coord(destinationX, destinationY);
        this.purpose = MitoPurpose.valueOf(purpose);
        this.mode = MitoMode.valueOf(mode);
        this.departureTime_min = departureTime_min;
        this.returnDepartureTime_min = returnDepartureTime_min;
    }

    public double getTripId() {
        return tripId;
    }

    public double getPersonId() {
        return personId;
    }

    public int getOrigin() {
        return origin;
    }

    public Coord getOriginCoords() {
        return originCoords;
    }

    public int getDestination() {
        return destination;
    }

    public Coord getDestinationCoords() {
        return destinationCoords;
    }

    public MitoPurpose getPurpose() {
        return purpose;
    }

    public MitoMode getMode() {
        return mode;
    }

    public double getDepartureTime_min() {
        return departureTime_min;
    }

    public double getReturnDepartureTime_min() {
        return returnDepartureTime_min;
    }

    public boolean isInterrupted() {
        return interrupted;
    }

    public void setInterrupted(boolean interrupted) {
        this.interrupted = interrupted;
    }

    public String getInterruptionType() {
        return interruptionType;
    }

    public void setInterruptionType(String interruptionType) {
        this.interruptionType = interruptionType;
    }

    public Coord getInterruptedCoords() {
        return interruptedCoords;
    }

    public void setInterruptedCoords(Coord interruptedCoords) {
        this.interruptedCoords = interruptedCoords;
    }

    public boolean isInterrupted_return() {
        return interrupted_return;
    }

    public void setInterrupted_return(boolean interrupted_return) {
        this.interrupted_return = interrupted_return;
    }

    public String getInterruptionType_return() {
        return interruptionType_return;
    }

    public void setInterruptionType_return(String interruptionType_return) {
        this.interruptionType_return = interruptionType_return;
    }

    public Coord getInterruptedCoords_return() {
        return interruptedCoords_return;
    }

    public void setInterruptedCoords_return(Coord interruptedCoords_return) {
        this.interruptedCoords_return = interruptedCoords_return;
    }

    public Id<Vehicle> getInterruptedVehicle() {
        return interruptedVehicle;
    }

    public void setInterruptedVehicle(Id<Vehicle> interruptedVehicle) {
        this.interruptedVehicle = interruptedVehicle;
    }

    public Id<Vehicle> getInterruptedVehicle_return() {
        return interruptedVehicle_return;
    }

    public void setInterruptedVehicle_return(Id<Vehicle> interruptedVehicle_return) {
        this.interruptedVehicle_return = interruptedVehicle_return;
    }

    public boolean isInterruption10() {
        return interruption10;
    }

    public void setInterruption10(boolean interruption10) {
        this.interruption10 = interruption10;
    }

    public boolean isInterruption20() {
        return interruption20;
    }

    public void setInterruption20(boolean interruption20) {
        this.interruption20 = interruption20;
    }

    public boolean isInterruption60() {
        return interruption60;
    }

    public void setInterruption60(boolean interruption60) {
        this.interruption60 = interruption60;
    }

    public boolean isInterruption10_return() {
        return interruption10_return;
    }

    public void setInterruption10_return(boolean interruption10_return) {
        this.interruption10_return = interruption10_return;
    }

    public boolean isInterruption20_return() {
        return interruption20_return;
    }

    public void setInterruption20_return(boolean interruption20_return) {
        this.interruption20_return = interruption20_return;
    }

    public boolean isInterruption60_return() {
        return interruption60_return;
    }

    public void setInterruption60_return(boolean interruption60_return) {
        this.interruption60_return = interruption60_return;
    }


    public double getInterruptionTimePoint() {
        return interruptionTimePoint;
    }

    public void setInterruptionTimePoint(double interruptionTimePoint) {
        this.interruptionTimePoint = interruptionTimePoint;
    }

    public double getInterruptionTimePoint_return() {
        return interruptionTimePoint_return;
    }

    public void setInterruptionTimePoint_return(double interruptionTimePoint_return) {
        this.interruptionTimePoint_return = interruptionTimePoint_return;
    }

}
