package pers.sxqsachin.mtgsearcher.database.table;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 *
 * SimpleTable
 *
 * Created by songxinqi-sachin on 16-7-22.
 */
public class SimpleTable extends AbstractDatabaseTable {
    public SimpleTable(@NonNull String tableName, @NonNull String args) {
        super(tableName, args);
    }

    public SimpleTable(@NonNull String tableName, @NonNull String args, @Nullable String[] mainColumns) {
        super(tableName, args, mainColumns);
    }
}
