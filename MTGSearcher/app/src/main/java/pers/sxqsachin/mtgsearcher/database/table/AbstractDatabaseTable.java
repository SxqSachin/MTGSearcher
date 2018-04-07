package pers.sxqsachin.mtgsearcher.database.table;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import pers.sxqsachin.mtgsearcher.database.table.value.IDatabaseTableValue;

/**
 *
 * AbstractDatabaseTable
 *
 * Created by songxinqi-sachin on 16-7-22.
 */
public abstract class AbstractDatabaseTable<T extends IDatabaseTableValue> implements IDatabaseTable<T> {
    private String      mTableName;
    private String      mTableArgs;
    private String[]    mTableMainColumns;

    public AbstractDatabaseTable(@NonNull String tableName, @NonNull String args) {
        this(tableName, args, null);
    }

    public AbstractDatabaseTable(@NonNull String tableName, @NonNull String args, @Nullable String[] mainColumns) {
        if (tableName.isEmpty()) {
            throw new RuntimeException("TableName cannot be empty");
        }
        if (args.isEmpty()) {
            throw new RuntimeException("Table Args cannot be empty");
        }

        mTableName = tableName;
        mTableArgs = args;

        if (mainColumns != null) {
            mTableMainColumns = new String[mainColumns.length];
            System.arraycopy(mainColumns, 0, mTableMainColumns, 0, mainColumns.length);
        }
    }

    @Override
    public String getName() {
        return mTableName;
    }

    @Override
    public String getArgs() {
        return mTableArgs;
    }

    @Override
    @Nullable
    public String[] getMainColumns() {
        return mTableMainColumns;
    }
}
