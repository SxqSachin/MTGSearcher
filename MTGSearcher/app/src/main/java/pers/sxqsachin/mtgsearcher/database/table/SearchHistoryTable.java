package pers.sxqsachin.mtgsearcher.database.table;

import pers.sxqsachin.mtgsearcher.database.table.value.AbstractDatabaseTableValue;
import pers.sxqsachin.mtgsearcher.database.table.value.SearchHistoryValue;

/**
 *
 * SearchHistoryTable
 *
 * Created by songxinqi-sachin on 16-7-22.
 */
public class SearchHistoryTable extends MTGSearcherDatabaseTable<SearchHistoryValue> {
    public SearchHistoryTable() {
        super("search_history",
                "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "history TEXT, " +
                "url TEXT",
                new String[] {"_id"});
    }
}
