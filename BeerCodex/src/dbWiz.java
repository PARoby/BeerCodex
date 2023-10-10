import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Calendar;
import java.text.SimpleDateFormat;

interface tableSyntax {
    final static char ALT_DECIMAL_POINT = ',';
    final static char DECIMAL_POINT = '.';
    final static String[] DB_MAIN_COL_NAMES = {"Name", "Def", "Style", "Note", "Date"};
    final static String[] DB_RECIPE_COL_NAMES = {"name", "style", "overallSize", "waterSrc", "waterQty", "grains",
            "grainQty", "additives", "additivesQty", "Comments", "temperature", "rinseWaterQty", "boilTime", "yeast",
            "og", "fg"};
    final static int DATE_INDEX = 5;
}

public class dbWiz implements tableSyntax {

    public static double[][] runningDataSet;
    private static final String URL = "jdbc:sqlite:database/database.db";

    public static void save(String[] stringedData, String table, String[] colNames) throws Exception {
        int colNumber = colNames.length;
        int dateIndex = 0;
        if (stringedData.length < colNumber-1) throw new java.lang.NullPointerException();

        Connection connection = null;
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection(URL);
            String columns = "";
            for (int i = 0; i < colNumber; i++) {
                columns = i == 0 ?  columns.concat(colNames[i]):columns.concat(", " + colNames[i] );
                if (colNames[i].equalsIgnoreCase("date")) dateIndex = i;
            }
            String valuePlacements = "";
            for(int i =0; i < colNumber; i++) {
                valuePlacements = i == 0 ? valuePlacements.concat("?"):valuePlacements.concat(", ?");
            }

            String wrtLn = "INSERT INTO " + table
                    + "(" + columns + ") "
                    + "VALUES (" + valuePlacements + ")";
            PreparedStatement statement = connection.prepareStatement(wrtLn);

            // statement.setDouble(1, Double.parseDouble(stringedData[3].replace(ALT_DECIMAL_POINT,DECIMAL_POINT)));
            for (int i = 0; i < colNumber; i++) {
                if(i == dateIndex)
                    statement.setTimestamp(stringedData.length + 1,
                            new Timestamp(Calendar.getInstance().getTimeInMillis()));
                else statement.setString(i + 1, stringedData[i]);
            }
            statement.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }finally {if (connection != null) connection.close();}
    }

    public static String[][] load(String[] colNames, String table, int index) throws SQLException {
        Connection connection = null;
        try {
            String columns = "";
            for (String cat : colNames) {
                columns = cat.equalsIgnoreCase(colNames[colNames.length-1]) ?
                        columns.concat(cat):columns.concat(cat + ", ");
            }
            String indexCommand = index < 0 ? "" : (" WHERE rowid = " + String.valueOf(index));
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection(URL);
            String wrtLn = "SELECT * FROM " + table + indexCommand;
            PreparedStatement statement = connection.prepareStatement(wrtLn);
            ResultSet rs = statement.executeQuery();
            int row = 0;
            String[] ln;
            String[][] returnTable = new String[10][colNames.length];
            while (rs.next()) {
                ln = new String[colNames.length];

                for (int i = 0; i < colNames.length; i++) switch (i){

                    case (DATE_INDEX-1):
                        ln[i] = rs.getString(colNames[i])!=null ?
                                new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(rs.getDouble("Date")) : "null";
                        break;
                    default:
                        ln[i] = rs.getString(colNames[i])!=null ? rs.getString(colNames[i]) : "null";
                        break;
                };
                if (returnTable.length == row) returnTable = DynamicTable(returnTable);
                returnTable[row] = ln;
                row++;
            }

            return trimTable(returnTable);
        } catch (Exception e) {
            e.printStackTrace();
            return new String[0][0];
        }finally {
            if (connection != null) connection.close();}
    }
    public static void update(String[] stringedData, int id) throws Exception {

        if (stringedData.length < 11) throw new java.lang.NullPointerException();
        Connection connection = null;
        try {

            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection(URL);
            // System.out.println("Connection with database established.");
            String columns = "";
            for (String cat : DB_MAIN_COL_NAMES) {
                columns = cat.equalsIgnoreCase(DB_MAIN_COL_NAMES[DB_MAIN_COL_NAMES.length-1]) ?
                        columns.concat(cat + " = ? "):columns.concat(cat + " = ? , ");
            }
            String wrtLn = "UPDATE pastData SET "
                    + columns
                    + "WHERE  rowid = "
                    + String.valueOf(id);
            PreparedStatement statement = connection.prepareStatement(wrtLn);

            // statement.setDouble(1, Double.parseDouble(stringedData[3].replace(ALT_DECIMAL_POINT,DECIMAL_POINT)));
            for (int i = 1; i < stringedData.length; i++)
                statement.setString(1,stringedData[2]);
            statement.setTimestamp(stringedData.length, new Timestamp(Calendar.getInstance().getTimeInMillis()));

            statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }finally {if (connection != null) connection.close();}
    }
    public static Boolean createTable (String title, String[] colNames) throws SQLException {
        Connection connection = null;
        try{
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection(URL);
            String stmt = "CREATE TABLE IF NOT EXISTS [" + title + "](\n";
            for (String name:colNames) {
                stmt = stmt.concat("[" + name + "]\tTEXT,\n");
            }
            stmt = stmt.concat("Date NUMERIC)");
            PreparedStatement statement = connection.prepareStatement(stmt);
            statement.execute();
            return true;
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }finally{
            if (connection != null) {
                connection.close();
            }
        }
    }
    public static String[][] DynamicTable(String[][] actual){
        // System.out.println("DynamicTable used");
        String[][] newString = new String[actual.length*2][actual[0].length];
        for (int i = 0; i < actual.length;i++) newString[i] = (String[]) actual[i];
        return newString;
    }
    public static String[][] trimTable(String[][] untrimmed){
        int i;
        for (i = 0; i < untrimmed.length;i++) if (untrimmed[i][0] == null || untrimmed[i][0] == "")break;
        String[][] newString = new String[i][untrimmed[0].length];
        for (int j = 0; j < newString.length;j++) {newString[j] = (String[]) untrimmed[j];
        }
        return newString;
    }
}
