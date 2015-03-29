package GMsis.parts.backend;

import lib.DBConn;
import GMsis.parts.DateUtils;

import java.sql.ResultSet;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Created by filipt on 3/3/15.
 * To be used for code re-use by classes
 * which represent records in the database.
 * This is a case of Abstraciton-Occurence where
 * this class is obviously the abstraction and Part,
 * Supplier, and PartOrder are Occurences
 */
class DatabaseRecord {

    private DBConn dbConn;

    private String SQLSetString = "UPDATE %s SET \"%%s\"=\'%%s\' WHERE \"%s\"=%d;";
    private String SQLSetInteger = "UPDATE %s SET \"%%s\"=%%d WHERE \"%s\"=%d;";
    private String SQLSetDouble = "UPDATE %s SET \"%%s\"=%%f WHERE \"%s\"=%d;";
    private String SQLSetDate = "UPDATE %s SET \"%%s\"=%%ld WHERE \"%s\"=%d;";

    private String SQLGetRecord = "SELECT * FROM \"%s\" WHERE \"%s\"=%d;";

    public DatabaseRecord(int ID, String tableName, String primaryKey) {
        dbConn = DBConn.getInstance();

        SQLSetString = String.format(SQLSetString, tableName, primaryKey, ID);
        SQLSetInteger = String.format(SQLSetInteger, tableName, primaryKey, ID);
        SQLSetDouble = String.format(SQLSetDouble, tableName, primaryKey, ID);
        SQLSetDate = String.format(SQLSetDate, tableName, primaryKey, ID);
        SQLGetRecord = String.format(SQLGetRecord, tableName, primaryKey, ID);
    }

    public void writeCell(final String colName, String content) {
        dbConn.updateDB(String.format(SQLSetString, colName, content));
    }

    public void writeCell(final String colName, double content) {
        dbConn.updateDB(String.format(SQLSetDouble, colName, content));
    }

    public void writeCell(final String colName, int content) {
        dbConn.updateDB(String.format(SQLSetInteger, colName, content));
    }

    public void writeCell(final String colName, Date date) {
        long seconds = DateUtils.milli2sec(date.getTime());
        dbConn.updateDB(String.format(SQLSetDate, colName, seconds));
    }

    public ResultSet getCells() {
        return dbConn.queryDB(SQLGetRecord);
    }
}
