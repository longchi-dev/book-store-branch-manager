package vn.tdtu.finalterm.helper.factoryAndAdapter.adapter;

public class AccessAdapter implements DatabaseAdapter {
    @Override
    public String getConnectionString() {
        String connection = "jdbc:ucanaccess://path_to_database_file";
        return connection;
    }
}