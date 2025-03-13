package vn.tdtu.finalterm.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.tdtu.finalterm.dto.TaoHoaDonDTO;
import vn.tdtu.finalterm.helper.strategy.ChiTietStrategy;
import vn.tdtu.finalterm.models.ChiTietHoaDon;
import vn.tdtu.finalterm.models.ResponseObject;

@RestController
@RequestMapping(path = "")
public class ChiTietHDController {
    ChiTietStrategy chiTietStrategy;

    @Autowired
    public ChiTietHDController(@Qualifier("chiTietHDServiceImpl") ChiTietStrategy chiTietStrategy) {
        this.chiTietStrategy = chiTietStrategy;
    }

    @GetMapping("/chiTietHD")
    public ResponseEntity<ResponseObject> findAllChiTietHD() {
        return chiTietStrategy.findAllChiTiet();
    }

    @GetMapping("/chiTietHD/{id}") // Custom Router
    public ResponseEntity<ResponseObject> findAllChiTietHDByChiNhanhId(@PathVariable("id") Long chiNhanhId) {
        return chiTietStrategy.findAllChiTietByChiNhanhId(chiNhanhId);
    }

    @GetMapping("/chiTietHD/{chiNhanhId}/{sanPhamId}") // Custom Router
    public ResponseEntity<ResponseObject> findAllChiTietHDByCNIdAndSPId(@PathVariable("chiNhanhId") Long chiNhanhId, @PathVariable("sanPhamId") Long sanPhamId) {
        return chiTietStrategy.findAllChiTietByCNIdAndSPId(chiNhanhId, sanPhamId);
    }

    @PostMapping("/chiTietHD") // Custom Router
    public ResponseEntity<ResponseObject> insertChiTietHDAndHD(@Valid @RequestBody TaoHoaDonDTO taoHoaDonDTO) {
        return chiTietStrategy.insertChiTietAndProcess(taoHoaDonDTO);
    }

    @PutMapping("/chiTietHD/{id}")
    public ResponseEntity<ResponseObject> updateChiTietHD(@Valid @RequestBody ChiTietHoaDon chiTietHoaDon, @PathVariable("id") Long chiTietHDId) {
        return chiTietStrategy.updateChiTiet(chiTietHoaDon, chiTietHDId);
    }

    @DeleteMapping("/chiTietHD/{id}")
    public ResponseEntity<ResponseObject> deleteChiTietHD(@PathVariable("id") Long chiTietHDId) {
        return chiTietStrategy.deleteChiTiet(chiTietHDId);
    }
}
