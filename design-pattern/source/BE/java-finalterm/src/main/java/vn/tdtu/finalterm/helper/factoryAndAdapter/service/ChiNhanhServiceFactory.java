package vn.tdtu.finalterm.helper.factoryAndAdapter.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import vn.tdtu.finalterm.helper.factoryAndAdapter.DatabaseService;
import vn.tdtu.finalterm.models.ChiNhanh;
import vn.tdtu.finalterm.models.ResponseObject;
import vn.tdtu.finalterm.models.TaiKhoan;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
public class ChiNhanhServiceFactory {
    private DatabaseService databaseService;

    @Autowired
    public ChiNhanhServiceFactory(DatabaseService databaseService) {
        this.databaseService = databaseService;
    }

    public ResponseEntity<ResponseObject> findAllChiNhanh() {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<ChiNhanh> chiNhanhs = new ArrayList<>();

        try {
            connection = databaseService.createConnection();
            preparedStatement = connection.prepareStatement("SELECT * FROM tbl_chinhanh");
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                ChiNhanh chiNhanh = new ChiNhanh();
                chiNhanh.setId(resultSet.getLong("id"));
                chiNhanh.setTenChiNhanh(resultSet.getString("tenChiNhanh"));
                chiNhanh.setDiaChi(resultSet.getString("diaChi"));
                chiNhanhs.add(chiNhanh);
            }

            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("ok", "Query All ChiNhanh Success", chiNhanhs)
            );
        } catch (SQLException e) {
            throw new RuntimeException("Error executing select", e);
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (preparedStatement != null) preparedStatement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                throw new RuntimeException("Error closing resources", e);
            }
        }
    }

    public ResponseEntity<ResponseObject> findChiNhanhById(Long chiNhanhId) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        ChiNhanh chiNhanh = null;

        try {
            connection = databaseService.createConnection();
            preparedStatement = connection.prepareStatement("SELECT * FROM tbl_chinhanh WHERE id = ?");
            preparedStatement.setLong(1, chiNhanhId);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                chiNhanh = new ChiNhanh();
                chiNhanh.setId(resultSet.getLong("id"));
                chiNhanh.setTenChiNhanh(resultSet.getString("tenChiNhanh"));
                chiNhanh.setDiaChi(resultSet.getString("diaChi"));
            }

            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("ok", "Query ChiNhanh By Id Success", chiNhanh)
            );
        } catch (SQLException e) {
            throw new RuntimeException("Error executing select", e);
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (preparedStatement != null) preparedStatement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                throw new RuntimeException("Error closing resources", e);
            }
        }
    }

    public ResponseEntity<ResponseObject> insertChiNhanh(ChiNhanh chiNhanh, HttpServletRequest request) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = databaseService.createConnection();
            preparedStatement = connection.prepareStatement("INSERT INTO tbl_chinhanh (tenChiNhanh, diaChi, email) VALUES (?, ?, ?)");
            preparedStatement.setString(1, chiNhanh.getTenChiNhanh());
            preparedStatement.setString(2, chiNhanh.getDiaChi());
            preparedStatement.setString(3, chiNhanh.getEmail());
            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Inserting ChiNhanh failed, no rows affected.");
            }

            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("ok", "Insert ChiNhanh Success", null)
            );
        } catch (SQLException e) {
            throw new RuntimeException("Error executing insert", e);
        } finally {
            try {
                if (preparedStatement != null) preparedStatement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                throw new RuntimeException("Error closing resources", e);
            }
        }
    }

    public ResponseEntity<ResponseObject> updateChiNhanh(ChiNhanh newChiNhanh, Long id) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = databaseService.createConnection();
            preparedStatement = connection.prepareStatement("UPDATE tbl_chinhanh SET tenChiNhanh = ?, diaChi = ?, email = ? WHERE id = ?");
            preparedStatement.setString(1, newChiNhanh.getTenChiNhanh());
            preparedStatement.setString(2, newChiNhanh.getDiaChi());
            preparedStatement.setString(3, newChiNhanh.getEmail());
            preparedStatement.setLong(4, id);
            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Updating ChiNhanh failed, no rows affected.");
            }

            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("ok", "Update ChiNhanh Success", null)
            );
        } catch (SQLException e) {
            throw new RuntimeException("Error executing update", e);
        } finally {
            try {
                if (preparedStatement != null) preparedStatement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                throw new RuntimeException("Error closing resources", e);
            }
        }
    }

    @Transactional
    public ResponseEntity<ResponseObject> deleteChiNhanh(Long id) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = databaseService.createConnection();
            preparedStatement = connection.prepareStatement("DELETE FROM tbl_chinhanh WHERE id = ?");
            preparedStatement.setLong(1, id);
            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Deleting ChiNhanh failed, no rows affected.");
            }

            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("ok", "Delete ChiNhanh Success", null)
            );
        } catch (SQLException e) {
            throw new RuntimeException("Error executing delete", e);
        } finally {
            try {
                if (preparedStatement != null) preparedStatement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                throw new RuntimeException("Error closing resources", e);
            }
        }
    }

    public ResponseEntity<ResponseObject> findChiNhanhByTaiKhoan(TaiKhoan taiKhoan) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        ChiNhanh chiNhanh = null;

        try {
            connection = databaseService.createConnection();
            preparedStatement = connection.prepareStatement("SELECT * FROM tbl_chinhanh WHERE fk_taiKhoan = ?");
            preparedStatement.setString(1, taiKhoan.getTaiKhoan());
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                chiNhanh = new ChiNhanh();
                chiNhanh.setId(resultSet.getLong("id"));
                chiNhanh.setTenChiNhanh(resultSet.getString("tenChiNhanh"));
                chiNhanh.setDiaChi(resultSet.getString("diaChi"));
                chiNhanh.setEmail(resultSet.getString("email"));
            }

            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("ok", "Query ChiNhanh By TaiKhoan Success", chiNhanh)
            );
        } catch (SQLException e) {
            throw new RuntimeException("Error executing select", e);
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (preparedStatement != null) preparedStatement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                throw new RuntimeException("Error closing resources", e);
            }
        }
    }

    public ResponseEntity<ResponseObject> findCNByTenOrDiaChi(String key) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<ChiNhanh> chiNhanhs = new ArrayList<>();

        try {
            connection = databaseService.createConnection();
            preparedStatement = connection.prepareStatement("SELECT * FROM tbl_chinhanh WHERE tenChiNhanh LIKE ? OR diaChi LIKE ?");
            preparedStatement.setString(1, "%" + key + "%");
            preparedStatement.setString(2, "%" + key + "%");
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                ChiNhanh chiNhanh = new ChiNhanh();
                chiNhanh.setId(resultSet.getLong("id"));
                chiNhanh.setTenChiNhanh(resultSet.getString("tenChiNhanh"));
                chiNhanh.setDiaChi(resultSet.getString("diaChi"));
                chiNhanh.setEmail(resultSet.getString("email"));
                chiNhanhs.add(chiNhanh);
            }

            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("ok", "Query ChiNhanh By Ten Or DiaChi Success", chiNhanhs)
            );
        } catch (SQLException e) {
            throw new RuntimeException("Error executing select", e);
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (preparedStatement != null) preparedStatement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                throw new RuntimeException("Error closing resources", e);
            }
        }
    }

    public ResponseEntity<ResponseObject> findCNByTen(String key) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<ChiNhanh> chiNhanhs = new ArrayList<>();

        try {
            connection = databaseService.createConnection();
            preparedStatement = connection.prepareStatement("SELECT * FROM tbl_chinhanh WHERE tenChiNhanh LIKE ?");
            preparedStatement.setString(1, "%" + key + "%");
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                ChiNhanh chiNhanh = new ChiNhanh();
                chiNhanh.setId(resultSet.getLong("id"));
                chiNhanh.setTenChiNhanh(resultSet.getString("tenChiNhanh"));
                chiNhanh.setDiaChi(resultSet.getString("diaChi"));
                chiNhanh.setEmail(resultSet.getString("email"));
                chiNhanhs.add(chiNhanh);
            }

            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("ok", "Query ChiNhanh By Ten Success", chiNhanhs)
            );
        } catch (SQLException e) {
            throw new RuntimeException("Error executing select", e);
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (preparedStatement != null) preparedStatement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                throw new RuntimeException("Error closing resources", e);
            }
        }
    }
}
