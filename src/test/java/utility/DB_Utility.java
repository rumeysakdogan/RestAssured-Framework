package utility;

import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class DB_Utility {

    private static Connection conn;
    private static Statement stmnt;
    private static ResultSet rs;
    private static ResultSetMetaData rsmd;


    public static void createConnection(){

        String connectionStr =ConfigurationReader.getProperty("database.url");
        String username = ConfigurationReader.getProperty("database.username") ;
        String password = ConfigurationReader.getProperty("database.password") ;

        try {
             conn = DriverManager.getConnection(connectionStr,username,password) ;
            System.out.println("CONNECTION SUCCESSFUL!!!");
        } catch (SQLException e) {
            System.out.println("CONNECTION HAS FAILED!!!" +  e.getMessage());
        }

    }


    /**
     *  Overload createConnection method to accept url, username, password
     *     * so we can provide those information for different database
     * @param url  The connection String that used to connect to the database
     * @param username the username of database
     * @param password the password of database
     */
    public static void createConnection(String url, String username, String password){
        try{
            conn = DriverManager.getConnection(url,username,password) ;
            System.out.println("CONNECTION SUCCESSFUL");
        }catch(SQLException e){
            System.out.println("ERROR WHILE CONNECTING WITH PARAMETERS " + e.getMessage());
        }
    }

    public static void createConnection(String env){
        // add validation to avoid invalid input
        // because we currently only have 2 env : test , dev
        // or add some condition for invalid env
        //  to directly get the information as database.url , database.username, database.password
        // without any env
        System.out.println("You are in "+env+" environment");
        String connectionStr = ConfigurationReader.getProperty(env+".database.url");
        String username = ConfigurationReader.getProperty(env+".database.username");
        String password = ConfigurationReader.getProperty(env+".database.password");
        createConnection(connectionStr,username,password);
    }


    public static ResultSet runQuery(String query){

        try {
            stmnt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            rs   =   stmnt.executeQuery(query) ;

        } catch (SQLException e) {
            System.out.println("Error while getting resultset " + e.getMessage());
        }

        return rs;
    }

    /**
     * Count how many row we have
     * @return the row count of the result set we got
     */
    public static int getRowCount(){

        int rowCount = 0;

        try {
            rs.last();
            rowCount = rs.getRow();

            //move the cursor back to the before first location to avoid accident
            rs.beforeFirst();
        } catch (SQLException e) {
            System.out.println("Error while getting row count" + e.getMessage());
        }

        return rowCount;
    }


    /**
     * Get the column count
     * @return count of the column the result set have
     */
    public static int getColumnCount(){

        int columnCount = 0;
        try {

            rsmd = rs.getMetaData();
            columnCount = rsmd.getColumnCount();

        } catch (SQLException e) {
            System.out.println("Error while getting column count" + e.getMessage());
        }

        return columnCount;
    }


    /**
     * a method that return all column names as List<String>
     * @return List<String>
     */
    public static List<String> getColumnNameList(){

        List<String> columnNameList = new ArrayList<>();

        try {
            rsmd = rs.getMetaData();

            for (int colNum = 1; colNum <= getColumnCount() ; colNum++) {

                String columnName = rsmd.getColumnLabel(colNum);
                columnNameList.add( columnName );
            }
            rs.beforeFirst();
        } catch (SQLException e) {
            System.out.println("Error while getting all column data as list: " + e.getMessage());
        }

        return  columnNameList;
    }


    /**
     * a method that return all column names as List<String>
     * @param colNum the column number you want to get the list out of
     * @return List<String>
     */
    public static List<String> getColumnDataAsList(int colNum){

        List<String> cellValueList = new ArrayList<>();

        try {
           while (rs.next()){
               String cellValue = rs.getString(colNum);
               cellValueList.add(cellValue);
           }

            rs.beforeFirst();
        } catch (SQLException e) {
            System.out.println("Error while getting one column data as list: " + e.getMessage());
        }

        return  cellValueList;
    }


    /**
     * a method that return all column names as List<String>
     * @param colName the column number you want to get the list out of
     * @return List<String>
     */
    public static List<String> getColumnDataAsList(String colName){

        List<String> cellValueList = new ArrayList<>();

        try {
            while (rs.next()){
                String cellValue = rs.getString(colName);
                cellValueList.add(cellValue);
            }

            rs.beforeFirst();
        } catch (SQLException e) {
            System.out.println("Error while getting one column data as list: " + e.getMessage());
        }

        return  cellValueList;
    }

    /**
     * a method that return  the cell value at certain row and certain column
     * @param rowNum
     * @param colNum
     * @return  cell value as String
     */
    public static String getColumnDataAtRow(int rowNum, int colNum){

        String cellValue = "";

        try {

            rs.absolute(rowNum);
            cellValue = rs.getString(colNum);
            rs.beforeFirst();

        } catch (SQLException e) {
            System.out.println("Error while getting cell value at at rowNum and colNum" + e.getMessage());
        }

        return cellValue;
    }

    /**
     * a method that return  the cell value at certain row and certain column
     * @param rowNum
     * @param colName
     * @return  cell value as String
     */
    public static String getColumnDataAtRow(int rowNum, String colName){

        String cellValue = "";

        try {

            rs.absolute(rowNum);
            cellValue = rs.getString(colName);
            rs.beforeFirst();

        } catch (SQLException e) {
            System.out.println("Error while getting cell value at at rowNum and colNum" + e.getMessage());
        }

        return cellValue;
    }


    /**
     * a method that return all row data as a List<String>
     * @param rowNum Row number you want to get the data from
     * @return List<String> row data as a List of string
     */
    public static List<String> getRowDataAsList(int rowNum){

        List<String> rowDataList = new ArrayList<>();

        try {
            rs.absolute(rowNum);
            for (int colNum = 1; colNum <= getColumnCount(); colNum++) {
                rowDataList.add( rs.getString(colNum));
            }

        } catch (SQLException e) {
            System.out.println("Error while getting all column data as list: " + e.getMessage());
        }

        return  rowDataList;
    }




    /**
     *
     * a method that display all the result set data on console
     */
    public static void displayAllData(){

        try {
            rs.beforeFirst();
            while (rs.next()){  // iterating over all rows
                for (int colNum = 1; colNum <= getColumnCount(); colNum++) { // iterating over all columns
                    System.out.printf( "%-25s  ", rs.getString(colNum) );
                }
                System.out.println();
            }

            rs.beforeFirst();
        } catch (SQLException e) {
            System.out.println("Error while displaying all data" + e.getMessage());
        }

    }

    /**
     * a method that return the row data along with column name as Map object
     * @param rowNum row number you want to get data from
     * @return Map object -->> column name as key and cell  value as value
     */
    public static Map<String,String> getRowMap(int rowNum){

        Map<String,String> rowMap = new LinkedHashMap<>();

        try {
            rs.absolute(rowNum);
            ResultSetMetaData rsmd = rs.getMetaData();

            for (int colNum = 1; colNum <= rsmd.getColumnCount(); colNum++) {

                String columnName = rsmd.getColumnLabel(colNum);
                String cellValue = rs.getString(colNum);
                rowMap.put(columnName, cellValue);
            }
            rs.beforeFirst();
        }catch (SQLException e){
            System.out.println("Error while getting row map:" + e.getMessage());
        }
        return rowMap;
    }


    /**
     * Getting all rowMaps as List of Maps
     * @return List<Map<String, String>>
     */
    public static List<Map<String, String>> getAllDataAsListOfMap(){

        List< Map<String, String> > rowMapList = new ArrayList<>();

        for (int rowNum = 1; rowNum <= getRowCount() ; rowNum++) {

            rowMapList.add( getRowMap(rowNum)  );
        }

        return rowMapList;
    }
    /**
     * create a method to clean up all the connection statement and resultset
     */

    public static void destroy(){
        try {
            if(rs != null){
                rs.close();
            }
           if(stmnt != null){
               stmnt.close();
           }
            if(conn != null){
                conn.close();
            }
        } catch (SQLException e) {
            System.out.println("Error while closing resources : " + e.getMessage());
        }
    }

}
