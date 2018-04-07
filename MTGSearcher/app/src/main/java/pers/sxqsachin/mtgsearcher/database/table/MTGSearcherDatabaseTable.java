package pers.sxqsachin.mtgsearcher.database.table;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import pers.sxqsachin.mtgsearcher.database.table.value.IDatabaseTableValue;

/**
 *
 * MTGSearcherDatabaseTable
 *
 * Created by songxinqi-sachin on 16-7-22.
 */
public abstract class MTGSearcherDatabaseTable<T extends IDatabaseTableValue> extends AbstractDatabaseTable<T> {
    public MTGSearcherDatabaseTable(@NonNull String tableName, @NonNull String args) {
        super(tableName, args);
    }

    public MTGSearcherDatabaseTable(@NonNull String tableName, @NonNull String args, @Nullable String[] mainColumns) {
        super(tableName, args, mainColumns);
    }
}
