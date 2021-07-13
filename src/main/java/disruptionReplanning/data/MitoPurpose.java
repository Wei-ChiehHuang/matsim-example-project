package disruptionReplanning.data;

public enum MitoPurpose {

    HBW, HBE, NHBW, HBS, HBR, HBO, NHBO;
    private int[] modes = {0, 1, 2, 3, 4, 5, 6};
    private String[] modeNames = {"HBW", "HBE", "NHBW", "HBS", "HBR", "HBO", "NHBO"};

    public String toString() {
        MitoPurpose p = this;
        if (p.equals(MitoPurpose.HBW)) return "HBW";
        else if (p.equals(MitoPurpose.HBE)) return "HBE";
        else if (p.equals(MitoPurpose.NHBW)) return "NHBW";
        else if (p.equals(MitoPurpose.HBS)) return "HBS";
        else if (p.equals(MitoPurpose.HBR)) return "HBR";
        else if (p.equals(MitoPurpose.HBO)) return "HBO";
        else return "NHBO";

    }

    public static MitoPurpose getPurpose(int p) {
        if (p == 0) return MitoPurpose.HBW;
        else if (p == 1) return MitoPurpose.HBE;
        else if (p == 2) return MitoPurpose.NHBW;
        else if (p == 3) return MitoPurpose.HBS;
        else if (p == 4) return MitoPurpose.HBR;
        else if (p == 5) return MitoPurpose.HBO;
        else return MitoPurpose.NHBO;
    }

    }
