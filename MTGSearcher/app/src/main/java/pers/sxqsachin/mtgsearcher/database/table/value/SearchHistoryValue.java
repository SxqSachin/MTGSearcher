package pers.sxqsachin.mtgsearcher.database.table.value;

import pers.sxqsachin.mtgsearcher.database.table.MTGSearcherDatabaseTable;
import pers.sxqsachin.mtgsearcher.database.table.SearchHistoryTable;

/**
 *
 * SearchHistoryValue
 *
 * Created by songxinqi-sachin on 16-7-22.
 */
public class SearchHistoryValue extends AbstractDatabaseTableValue<MTGSearcherDatabaseTable> {
    public SearchHistoryValue(String history, String url) {
        super();
        getContentValues().put("history", history);
        getContentValues().put("url", url);
    }
}
