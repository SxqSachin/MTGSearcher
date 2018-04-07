package pers.sxqsachin.mtgsearcher.struct.mtg;

/**
 *
 * ManaColor
 *
 * Created by songxinqi-sachin on 16-7-2.
 */
public enum ManaColor {
    WHITE("w"),
    BLUE("u"),
    BLACK("b"),
    RED("r"),
    GREEN("g"),
    COLORLESS("c");

    private String  mS;
    ManaColor(String s) {
        mS = s;
    }

    public String   getS() {
        return mS;
    }
}
