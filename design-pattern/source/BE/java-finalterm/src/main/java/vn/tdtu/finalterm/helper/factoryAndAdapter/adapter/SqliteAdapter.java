package vn.tdtu.finalterm.helper.factoryAndAdapter.adapter;

public class SqliteAdapter implements DatabaseAdapter {
    @Override
    public String getConnectionString() {
        String connection = "jdbc:sqlite:sql_java.db";
        return connection;
    }
}
