package disruptionReplanning.io;

import disruptionReplanning.data.Dataset;
import disruptionReplanning.data.Trips;
import disruptionReplanning.identifyAffectedPassengers.IdentifyAffectedCommuters;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.Map;

public class WriteIntermediateList {

    private static final Logger logger = Logger.getLogger(WriteIntermediateList.class);

    Map<Integer, Trips> tripMap = Dataset.getTripMap();

    public void printCSV(String csvFile) throws FileNotFoundException {

        logger.info("Print intermediate trip list");

        PrintWriter pw = new PrintWriter(new File(csvFile));
        pw.println("id,person," +
                "origin,originX,originY,destination,destinationX,destinationY," +
                "purpose,mode,departure_time_min,departue_time_return_min," +
                "isInterrupted,interruptionType,interruptionX,interruptionY,interruptionTime_sec,delay10,delay20,delay60," +
                "isInterrupted_return,interruptionType_return,interruptionX_return,interruptionY_return,interruptionTime_sec_return,delay10_return,delay20_return,delay60_return");

        for (int i = 0; i < tripMap.size(); i++) {

            Trips trip = tripMap.get(i);

            if (trip.isInterrupted()){

            }

            StringBuilder sb = new StringBuilder();

            sb.append(trip.getTripId()).append(",");
            sb.append(trip.getPersonId()).append(",");

            sb.append(trip.getOrigin()).append(",");
            sb.append(trip.getOriginCoords().getX()).append(",");
            sb.append(trip.getOriginCoords().getY()).append(",");

            sb.append(trip.getDestination()).append(",");
            sb.append(trip.getDestinationCoords().getX()).append(",");
            sb.append(trip.getDestinationCoords().getY()).append(",");

            sb.append(trip.getPurpose()).append(",");
            sb.append(trip.getMode()).append(",");

            sb.append(trip.getDepartureTime_min()).append(",");
            sb.append(trip.getReturnDepartureTime_min()).append(",");

            sb.append(trip.isInterrupted()).append(",");
            sb.append(trip.getInterruptionType()).append(",");
            if (trip.isInterrupted()){
                sb.append(trip.getInterruptedCoords().getX()).append(",");
                sb.append(trip.getInterruptedCoords().getY()).append(",");
            } else{
                sb.append("null").append(",");
                sb.append("null").append(",");
            }
            sb.append(trip.getInterruptionTimePoint()).append(",");
            sb.append(trip.isInterruption10()).append(",");
            sb.append(trip.isInterruption20()).append(",");
            sb.append(trip.isInterruption60()).append(",");

            sb.append(trip.isInterrupted_return()).append(",");
            sb.append(trip.getInterruptionType_return()).append(",");
            if (trip.isInterrupted_return()){
                sb.append(trip.getInterruptedCoords_return().getX()).append(",");
                sb.append(trip.getInterruptedCoords_return().getY()).append(",");
            } else{
                sb.append("null").append(",");
                sb.append("null").append(",");
            }
            sb.append(trip.getInterruptionTimePoint_return()).append(",");
            sb.append(trip.isInterruption10_return()).append(",");
            sb.append(trip.isInterruption20_return()).append(",");
            sb.append(trip.isInterruption60_return()).append(",");

            pw.println(sb);
        }
        pw.close();
        logger.info("Finished printing intermediate trip list");
    }


}
