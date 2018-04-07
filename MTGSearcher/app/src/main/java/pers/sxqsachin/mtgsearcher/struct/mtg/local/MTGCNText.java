package pers.sxqsachin.mtgsearcher.struct.mtg.local;

/**
 *
 * MTGCNText
 *
 * Created by songxinqi-sachin on 16-7-23.
 */
public class MTGCNText implements MTGLocalText {
    @Override
    public String white() {
        return "白";
    }

    @Override
    public String blue() {
        return "蓝";
    }

    @Override
    public String black() {
        return "黑";
    }

    @Override
    public String red() {
        return "红";
    }

    @Override
    public String green() {
        return "绿";
    }

    @Override
    public String colorless() {
        return "无色";
    }

    @Override
    public String yes() {
        return "是";
    }

    @Override
    public String no() {
        return "否";
    }
}
