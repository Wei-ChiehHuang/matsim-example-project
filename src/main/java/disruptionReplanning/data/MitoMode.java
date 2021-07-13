package disruptionReplanning.data;

public enum MitoMode {

    autoDriver, autoPassenger, bus, tramOrMetro, train, walk, bicycle;
    private int[] modes = {0, 1, 2, 3, 4, 5, 6};
    private String[] modeNames = {"autoDriver", "autoPassenger", "bus", "tramOrMetro", "train", "walk", "bicycle"};

    public String toString() {
        MitoMode m = this;
        if (m.equals(MitoMode.autoDriver)) return "autoDriver";
        else if (m.equals(MitoMode.autoPassenger)) return "autoPassenger";
        else if (m.equals(MitoMode.bus)) return "bus";
        else if (m.equals(MitoMode.tramOrMetro)) return "tramOrMetro";
        else if (m.equals(MitoMode.train)) return "train";
        else if (m.equals(MitoMode.walk)) return "walk";
        else return "bicycle";

    }

    public static MitoMode getMode(int m) {
        if (m == 0) return MitoMode.autoDriver;
        else if (m == 1) return MitoMode.autoPassenger;
        else if (m == 2) return MitoMode.bus;
        else if (m == 3) return MitoMode.tramOrMetro;
        else if (m == 4) return MitoMode.train;
        else if (m == 5) return MitoMode.train;
        else return MitoMode.bicycle;
    }

}
