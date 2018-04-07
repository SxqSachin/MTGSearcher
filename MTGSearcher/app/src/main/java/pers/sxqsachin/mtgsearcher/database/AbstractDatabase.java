package pers.sxqsachin.mtgsearcher.database;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

import pers.sxqsachin.mtgsearcher.MTGSearcher;
import pers.sxqsachin.mtgsearcher.database.table.IDatabaseTable;
import pers.sxqsachin.mtgsearcher.database.table.value.IDatabaseTableValue;
import pers.sxqsachin.mtgsearcher.util.DatabaseCmdUtil;

/**
 *
 * AbstractDatabase
 *
 * Created by songxinqi-sachin on 16-7-22.
 */
public abstract class AbstractDatabase<T extends IDatabaseTable> implements IDatabase<T> {
    private SQLiteDatabase mDatabase;

    protected AbstractDatabase(String name, int version) {
        SQLiteOpenHelper helper = new DatabaseHelper(name, version);
        mDatabase = helper.getWritableDatabase();
    }

    @Override
    public void createTable(T table) {
        mDatabase.execSQL(DatabaseCmdUtil.createTable(table));
    }

    @Override
    public void dropTable(T table) {
        mDatabase.execSQL(DatabaseCmdUtil.dropTable(table));
    }

    @Override
    public long insert(T table, IDatabaseTableValue<T> data) {
        return mDatabase.insert(table.getName(), null, data.getContentValues());
    }

    @Override
    public int update(T table, IDatabaseTableValue<T> data) {
        return mDatabase.update(table.getName(), data.getContentValues(), data.getWhereClause(), data.getWhereArgs());
    }

    @Override
    public int getTableRowCount(T table) {
        return getTableRowCount(table, table.getMainColumns());
    }

    @Override
    public int getTableRowCount(T table, @Nullable String[] columns) {
        int ret;

        Cursor cursor = mDatabase.query(table.getName(), columns, null, null, null, null, null);
        ret = cursor.getCount();
        cursor.close();

        return ret;
    }

    @Override
    public Cursor getCursor(T table) {
        return getCursor(table, null);
    }

    @Override
    public Cursor getCursor(T table, String[] columns) {
        return mDatabase.query(table.getName(), columns, null, null, null, null, null);
    }

    abstract void   onCreate();
    abstract void   onUpgrade(int oldVersion, int newVersion);

    class DatabaseHelper extends SQLiteOpenHelper {
        private DatabaseHelper(String name, int version) {
            super(MTGSearcher.getInstance(), name, null, version);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            mDatabase = db;
            AbstractDatabase.this.onCreate();
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            mDatabase = db;
            AbstractDatabase.this.onUpgrade(oldVersion, newVersion);
        }
    }
}
