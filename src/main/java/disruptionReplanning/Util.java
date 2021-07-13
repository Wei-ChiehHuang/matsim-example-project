package disruptionReplanning;

import org.apache.log4j.Logger;

public class Util {

    private static final Logger logger = Logger.getLogger(Util.class);

    public static <T> int findPositionInArray(T element, T[] array) {
        int ind = -1;

        for(int a = 0; a < array.length; ++a) {
            if (array[a].equals(element)) {
                ind = a;
            }
        }

        if (ind == -1) {

            logger.error("Could not find element " + element + " in array (see method <findPositionInArray> in class <SiloUtil>");
        }

        return ind;
    }

}
