package pers.sxqsachin.mtgsearcher.database;

import android.database.Cursor;
import android.support.annotation.Nullable;

import pers.sxqsachin.mtgsearcher.database.table.IDatabaseTable;
import pers.sxqsachin.mtgsearcher.database.table.value.IDatabaseTableValue;

/**
 *
 * IDatabase
 *
 * Created by songxinqi-sachin on 16-7-22.
 */
public interface IDatabase<T extends IDatabaseTable> {
    void    createTable(T table);
    void    dropTable(T table);

    long    insert(T table, IDatabaseTableValue<T> data);

    int     update(T table, IDatabaseTableValue<T> data);

    int     getTableRowCount(T table);
    int     getTableRowCount(T table, @Nullable String[] columns);

    Cursor  getCursor(T table);
    Cursor  getCursor(T table, String[] columns);
}
