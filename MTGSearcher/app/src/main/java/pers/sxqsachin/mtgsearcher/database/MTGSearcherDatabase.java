package pers.sxqsachin.mtgsearcher.database;

import pers.sxqsachin.mtgsearcher.database.table.MTGSearcherDatabaseTable;
import pers.sxqsachin.mtgsearcher.database.table.SearchHistoryTable;
import pers.sxqsachin.mtgsearcher.util.LogUtil;

/**
 *
 * MTGSearcherDatabase
 *
 * Created by songxinqi-sachin on 16-7-22.
 */
public class MTGSearcherDatabase extends AbstractDatabase<MTGSearcherDatabaseTable> {
    private final static int    VERSION     =   2;

    @Override
    void onCreate() {
        createTable(new SearchHistoryTable());
    }

    @Override
    void onUpgrade(int oldVersion, int newVersion) {
        if (oldVersion == 1) {
            if (newVersion == 2) {
                dropTable(new SearchHistoryTable());
                createTable(new SearchHistoryTable());
            }
        }
    }

    public static void  init() {
        getInstance();
    }

    public static MTGSearcherDatabase   getInstance() {
        return InstanceHolder.mInstance;
    }

    private MTGSearcherDatabase() {
        super("MTGSearcher", VERSION);
    }

    private static class InstanceHolder {
        private static MTGSearcherDatabase  mInstance   =   new MTGSearcherDatabase();
    }
}
