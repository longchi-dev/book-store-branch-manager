package vn.tdtu.finalterm.helper.factoryAndAdapter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import vn.tdtu.finalterm.helper.factoryAndAdapter.adapter.DatabaseAdapter;
import vn.tdtu.finalterm.helper.factoryAndAdapter.adapter.DatabaseAdapterFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Service
public class DatabaseService {
    private DatabaseAdapter databaseAdapter;

    public DatabaseService(@Value("${database.type}") String databaseType) {
        this.databaseAdapter = DatabaseAdapterFactory.getDatabaseAdapter(databaseType);
    }

    public Connection createConnection() {
        try {
            return DriverManager.getConnection(databaseAdapter.getConnectionString());
        } catch (SQLException e) {
            throw new RuntimeException("Error connecting to the database", e);
        }
    }
}
