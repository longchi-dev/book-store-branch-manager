package vn.tdtu.finalterm.helper.factoryAndAdapter.adapter;

public class DatabaseAdapterFactory {
    public static DatabaseAdapter getDatabaseAdapter(String databaseType) {
        switch (databaseType) {
            case "mysql":
                return new MySQLAdapter();
            case "sqlserver":
                return new SqlServerAdapter();
            case "sqlite":
                return new SqliteAdapter();
            case "access":
                return new AccessAdapter();
            default:
                throw new IllegalArgumentException("Invalid database type");
        }
    }
}
