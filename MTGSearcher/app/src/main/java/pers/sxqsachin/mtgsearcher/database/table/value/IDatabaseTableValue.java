package pers.sxqsachin.mtgsearcher.database.table.value;

import android.content.ContentValues;

import pers.sxqsachin.mtgsearcher.database.table.IDatabaseTable;

/**
 *
 * IDatabaseTableValue
 *
 * Created by songxinqi-sachin on 16-7-22.
 */
public interface IDatabaseTableValue<T extends IDatabaseTable> {
    ContentValues   getContentValues();

    String          getWhereClause();
    String[]        getWhereArgs();

    void            setWhereClause(String whereClause);
    void            setWhereArgs(String[] whereArgs);
}
