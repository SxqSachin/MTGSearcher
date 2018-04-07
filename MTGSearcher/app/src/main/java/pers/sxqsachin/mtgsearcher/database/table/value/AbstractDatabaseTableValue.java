package pers.sxqsachin.mtgsearcher.database.table.value;

import android.content.ContentValues;

import pers.sxqsachin.mtgsearcher.database.table.IDatabaseTable;

/**
 *
 * AbstractDatabaseTableValue
 *
 * Created by songxinqi-sachin on 16-7-22.
 */
public class AbstractDatabaseTableValue<T extends IDatabaseTable> implements IDatabaseTableValue<T> {

    private ContentValues   mContentValues;
    private String          mClause;
    private String[]        mArgs;

    public AbstractDatabaseTableValue() {
        mContentValues = new ContentValues();
    }

    @Override
    public ContentValues getContentValues() {
        return mContentValues;
    }

    @Override
    public String getWhereClause() {
        return mClause;
    }

    @Override
    public String[] getWhereArgs() {
        return mArgs;
    }

    @Override
    public void setWhereClause(String whereClause) {
        mClause = whereClause;
    }

    @Override
    public void setWhereArgs(String[] whereArgs) {
        mArgs = new String[whereArgs.length];
        System.arraycopy(whereArgs, 0, mArgs, 0, mArgs.length);
    }
}
