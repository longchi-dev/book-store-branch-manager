package vn.tdtu.finalterm.helper.factoryAndAdapter.adapter;

public class MySQLAdapter implements DatabaseAdapter{
    private static final String MYSQL_HOST = "localhost";

    @Override
    public String getConnectionString() {
        String connection = "jdbc:mysql://" + MYSQL_HOST + ":3306/sql_java";
        return connection;
    }
}
