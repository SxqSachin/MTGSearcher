package pers.sxqsachin.mtgsearcher.util;

import pers.sxqsachin.mtgsearcher.database.table.IDatabaseTable;

/**
 *
 * DatabaseCmdUtil
 *
 * Created by songxinqi-sachin on 16-7-22.
 */
public class DatabaseCmdUtil {
    public static String    createTable(IDatabaseTable table) {
         return "create table IF NOT EXISTS " + table.getName() + "(" + table.getArgs() + ")";
    }

    public static String    dropTable(IDatabaseTable table) {
        return "drop table IF EXISTS " + table.getName();
    }

    public static String    addNewColumn(IDatabaseTable table, String column, String type) {
        return "alert TABLE " + table.getName() + " add column " + column + " " + type + ";";
    }
}
