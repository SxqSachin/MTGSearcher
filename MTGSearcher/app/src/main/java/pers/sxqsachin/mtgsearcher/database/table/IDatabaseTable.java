package pers.sxqsachin.mtgsearcher.database.table;

import android.support.annotation.Nullable;

import pers.sxqsachin.mtgsearcher.database.table.value.IDatabaseTableValue;

/**
 *
 * IDatabaseTable
 *
 * Created by songxinqi-sachin on 16-7-22.
 */
public interface IDatabaseTable<T extends IDatabaseTableValue> {
    String  getName();
    String  getArgs();

    @Nullable
    String[]    getMainColumns();
}
