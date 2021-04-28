package server.libraries;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

abstract public class ResultArray<T> {


    //u can set which code to run before connecting to the database [may not be invoked by some databases
    public interface BeforeConnectToDatabase<T> {
        void run(ResultArray<T> sender);
    }
    private BeforeConnectToDatabase<T> beforeConnectToDatabase;
    public void setBeforeConnectToDatabase(BeforeConnectToDatabase<T> beforeConnectToDatabase) {
        this.beforeConnectToDatabase = beforeConnectToDatabase;
    }
    //called before connecting to the database
    private void beforeConnectToDatabase() {
        if (beforeConnectToDatabase != null) {
            beforeConnectToDatabase.run(this);
        }

    }

    //u can set which code to run before connecting to the database [may not be invoked by some databases
    public interface AfterConnectToDatabase<T> {
        void run(ResultArray<T> sender);
    }

    private AfterConnectToDatabase<T> afterConnectToDatabase;
    public void setAfterConnectToDatabase(AfterConnectToDatabase<T> afterConnectToDatabase) {
        this.afterConnectToDatabase = afterConnectToDatabase;
    }
    //called after connecting to the datavase, e.g to test if was successful
    private void afterConnectToDatabase() {
        if (afterConnectToDatabase != null) {
            afterConnectToDatabase.run(this);
        }

    }

    //u can set which to code run after connecting to the database but before executing the query
    public interface BeforeExecuteQuery<T> {
        void run(ResultArray<T> sender);
    }
    private BeforeExecuteQuery<T> beforeExecuteQuery;
    public void setBeforeExecuteQuery(BeforeExecuteQuery<T> beforeExecuteQuery) {
        this.beforeExecuteQuery = beforeExecuteQuery;
    }
    //will be called
    private void beforeExecuteQuery() {
        if (beforeExecuteQuery != null) {
            beforeExecuteQuery.run(this);
        }

    }

    //can set which code to run after executing the query, but before processing the results/rows returned
    public interface AfterExecuteQuery<T> {
        void run(ResultArray<T> sender);
    }
    private AfterExecuteQuery<T> afterExecuteQuery;
    public void setAfterExecuteQuery(AfterExecuteQuery<T> afterExecuteQuery) {
        this.afterExecuteQuery = afterExecuteQuery;
    }
    private void afterExecuteQuery() {
        if (afterExecuteQuery != null) {
            afterExecuteQuery.run(this);
        }
    }

    //---------------
    public interface BeforeProcessResultFromDb<T> {
        void run(ResultArray<T> sender);
    }

    private BeforeProcessResultFromDb<T> beforeProcessResultFromDb;

    public void setBeforeProcessResultFromDb(BeforeProcessResultFromDb<T> beforeProcessResultFromDb) {
        this.beforeProcessResultFromDb = beforeProcessResultFromDb;
    }

    private void beforeProcessResultFromDb() {
        if (beforeProcessResultFromDb != null) {
            beforeProcessResultFromDb.run(this);
        }
    }

    //--------------
    protected void afterCreateResultItem(T resultItem) {

    }

    public interface AfterProcessResultFromDb<T> {
        void run(ResultArray<T> sender);
    }

    private AfterProcessResultFromDb<T> afterProcessResultFromDb;

    public void setAfterProcessResultFromDb(AfterProcessResultFromDb<T> afterProcessResultFromDb) {
        this.afterProcessResultFromDb = afterProcessResultFromDb;
    }

    private void afterProcessResultFromDb() {
        if (afterProcessResultFromDb != null) {
            afterProcessResultFromDb.run(this);
        }
    }

    //============

    public ArrayList<T> get() throws Exception {

        beforeGetQuery();
        String query = getQuery();
        afterGetQuery(query);

        beforeConnectToDatabase();
        Statement stmt = connectToDatabase();
        afterConnectToDatabase();

        beforeExecuteQuery();
        ResultSet rs = stmt.executeQuery(query);
        afterExecuteQuery();

        beforeProcessResultFromDb();
        ArrayList<T> arrayList = processResult(rs);
        afterProcessResultFromDb();

        stmt.getConnection().commit();
        stmt.getConnection().close();
        stmt.close();

        return arrayList;

    }

    public static Statement connectToDatabase() throws Exception {
        //Class.forName("com.mysql.jdbc.Driver").newInstance();
        Connection conn = DbConnection.get();
        conn.setAutoCommit(false);
        Statement stmt = conn.createStatement();
        return stmt;
    }

    //================================================
    public interface BeforeGetQuery<T> {
        void run(ResultArray<T> sender);
    }

    private BeforeGetQuery beforeGetQuery;

    public void setBeforeGetQuery(BeforeGetQuery beforeGetQuery) {
        this.beforeGetQuery = beforeGetQuery;
    }

    private void beforeGetQuery() {
        if (beforeGetQuery != null) {
            beforeGetQuery.run(this);
        }
    }

    public interface AfterGetQuery<T> {
        void run(ResultArray<T> sender, String query);
    }

    private AfterGetQuery afterGetQuery;

    public void setAfterGetQuery(AfterGetQuery afterGetQuery) {
        this.afterGetQuery = afterGetQuery;
    }

    private void afterGetQuery(String query) {
        if (afterGetQuery != null) {
            afterGetQuery.run(this, query);
        }
    }


    public static interface AfterCreateResultItem<T> {
        void run(T resultItem);
    }

    private AfterCreateResultItem<T> afterCreateResultItem;

    public void setAfterCreateResultItem(AfterCreateResultItem<T> afterCreateResultItem) {
        this.afterCreateResultItem = afterCreateResultItem;
    }


    private ArrayList<T> processResult(ResultSet rs) throws Exception {
        ArrayList<T> finalResult = new ArrayList();
        while (rs.next()) {
            try {
                T resultItem = createResultItem(rs);
                finalResult.add(resultItem);
                afterCreateResultItem(resultItem);
                if (afterCreateResultItem != null) {
                    afterCreateResultItem.run(resultItem);
                }
            } catch (Exception ex) {
                continue;
            }

        }
        return finalResult;
    }

    private static class DbConnection {

        private static Connection instance = null;

        private static Connection get() throws Exception {
            Class.forName("com.mysql.jdbc.Driver").newInstance();

            String hostNameOrIp = "127.0.0.1";
            //String hostNameOrIp = "localhost";
            String port = "3306";
            String DbName = "bus_db2";
            String userName = "root";
            String password = "usbw";

            Connection conn = DriverManager.getConnection(
                    "jdbc:mysql://".concat(hostNameOrIp).concat(":").concat(port).concat("/").concat(DbName),
                    userName, password
            );
            return conn;


        }
    }

    abstract protected T createResultItem(ResultSet rs) throws Exception;

    abstract protected String getQuery();

}
