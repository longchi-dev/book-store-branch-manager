package vn.tdtu.finalterm.helper.factoryAndAdapter.adapter;

public class SqlServerAdapter implements DatabaseAdapter {
    @Override
    public String getConnectionString() {
        String connection = "jdbc:sqlserver://localhost;databaseName=sql_java";
        return connection;
    }
}
