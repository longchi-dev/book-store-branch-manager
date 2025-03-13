package vn.tdtu.finalterm.helper.strategy;

import org.springframework.http.ResponseEntity;
import vn.tdtu.finalterm.models.ResponseObject;

public interface ChiTietStrategy {
    ResponseEntity<ResponseObject> findAllChiTiet();

    ResponseEntity<ResponseObject> findAllChiTietByChiNhanhId(Long chiNhanhId);

    ResponseEntity<ResponseObject> findAllChiTietByCNIdAndSPId(Long chiNhanhId, Long sanPhamId);

    ResponseEntity<ResponseObject> insertChiTietAndProcess(Object dto);

    ResponseEntity<ResponseObject> updateChiTiet(Object chiTiet, Long chiTietId);

    ResponseEntity<ResponseObject> deleteChiTiet(Long chiTietId);
}
