package vn.tdtu.finalterm.helper.factoryAndAdapter.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import vn.tdtu.finalterm.helper.factoryAndAdapter.DatabaseService;
import vn.tdtu.finalterm.models.ResponseObject;
import vn.tdtu.finalterm.models.SanPham;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
public class SanPhamServiceFactory {
    private DatabaseService databaseService;

    @Autowired
    public SanPhamServiceFactory(DatabaseService databaseService) {
        this.databaseService = databaseService;
    }

    public ResponseEntity<ResponseObject> findAllSanPham() {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<SanPham> sanPhams = new ArrayList<>();

        try {
            connection = databaseService.createConnection();
            preparedStatement = connection.prepareStatement("SELECT * FROM tbl_sanpham");
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                SanPham sanPham = new SanPham();
                sanPhams.add(sanPham);
            }

            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("ok", "Query All SanPham Success", sanPhams)
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

    public ResponseEntity<ResponseObject> findSanPhamById(Long sanPhamId) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        SanPham sanPham = null;

        try {
            connection = databaseService.createConnection();
            preparedStatement = connection.prepareStatement("SELECT * FROM tbl_sanpham WHERE id = ?");
            preparedStatement.setLong(1, sanPhamId);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                sanPham = new SanPham();
            }

            return sanPham != null ?
                    ResponseEntity.status(HttpStatus.OK).body(
                            new ResponseObject("ok", "Query SanPham Success", sanPham)
                    ) :
                    ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                            new ResponseObject("failed", "Cannot find SanPham with id = " + sanPhamId, "")
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

    public ResponseEntity<ResponseObject> insertSanPham(SanPham sanPham) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = databaseService.createConnection();
            preparedStatement = connection.prepareStatement("INSERT INTO tbl_sanpham (tenSanPham, loaiSanPham, thuongHieu, tacGia, theLoai) VALUES (?, ?, ?, ?, ?)");
            preparedStatement.setString(1, sanPham.getTenSanPham());
            preparedStatement.setString(2, sanPham.getLoaiSanPham());
            preparedStatement.setString(3, sanPham.getThuongHieu());
            preparedStatement.setString(4, sanPham.getTacGia());
            preparedStatement.setString(5, sanPham.getTheLoai());

            int affectedRows = preparedStatement.executeUpdate();

            return affectedRows > 0 ?
                    ResponseEntity.status(HttpStatus.OK).body(
                            new ResponseObject("ok", "Insert SanPham Success", sanPham)
                    ) :
                    ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(
                            new ResponseObject("failed", "Insert SanPham Failed", "")
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

    public ResponseEntity<ResponseObject> updateSanPham(SanPham newSanPham, Long id) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = databaseService.createConnection();
            preparedStatement = connection.prepareStatement("UPDATE tbl_sanpham SET tenSanPham = ?, loaiSanPham = ?, thuongHieu = ?, tacGia = ?, theLoai = ? WHERE id = ?");
            preparedStatement.setString(1, newSanPham.getTenSanPham());
            preparedStatement.setString(2, newSanPham.getLoaiSanPham());
            preparedStatement.setString(3, newSanPham.getThuongHieu());
            preparedStatement.setString(4, newSanPham.getTacGia());
            preparedStatement.setString(5, newSanPham.getTheLoai());
            preparedStatement.setLong(6, id);

            int affectedRows = preparedStatement.executeUpdate();

            return affectedRows > 0 ?
                    ResponseEntity.status(HttpStatus.OK).body(
                            new ResponseObject("ok", "Update SanPham Success", newSanPham)
                    ) :
                    ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(
                            new ResponseObject("failed", "Update SanPham Failed", "")
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

    public ResponseEntity<ResponseObject> deleteSanPham(Long sanPhamId) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = databaseService.createConnection();
            preparedStatement = connection.prepareStatement("DELETE FROM tbl_sanpham WHERE id = ?");
            preparedStatement.setLong(1, sanPhamId);

            int affectedRows = preparedStatement.executeUpdate();

            return affectedRows > 0 ?
                    ResponseEntity.status(HttpStatus.OK).body(
                            new ResponseObject("ok", "Delete SanPham Success", "")
                    ) :
                    ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(
                            new ResponseObject("failed", "Delete SanPham Failed", "")
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

    public ResponseEntity<ResponseObject> findSPByTenOrLoaiOrTHOrTGOrTL(String key) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<SanPham> sanPhams = new ArrayList<>();

        try {
            connection = databaseService.createConnection();
            preparedStatement = connection.prepareStatement("SELECT * FROM tbl_sanpham WHERE tenSanPham LIKE ? OR loaiSanPham LIKE ? OR thuongHieu LIKE ? OR tacGia LIKE ? OR theLoai LIKE ?");
            preparedStatement.setString(1, "%" + key + "%");
            preparedStatement.setString(2, "%" + key + "%");
            preparedStatement.setString(3, "%" + key + "%");
            preparedStatement.setString(4, "%" + key + "%");
            preparedStatement.setString(5, "%" + key + "%");
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                SanPham sanPham = new SanPham();
                // Set the properties of sanPham based on the resultSet
                sanPhams.add(sanPham);
            }

            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("ok", "Query SanPham By Ten Or Loai Or TH Or TG Or TL Success", sanPhams)
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
