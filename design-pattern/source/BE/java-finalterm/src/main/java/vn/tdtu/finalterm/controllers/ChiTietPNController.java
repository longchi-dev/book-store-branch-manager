package vn.tdtu.finalterm.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.tdtu.finalterm.dto.TaoPhieuNhapDTO;
import vn.tdtu.finalterm.helper.strategy.ChiTietStrategy;
import vn.tdtu.finalterm.models.*;

@RestController
@RequestMapping(path = "")
public class ChiTietPNController {
    ChiTietStrategy chiTietStrategy;

    @Autowired
    public ChiTietPNController(@Qualifier("chiTietPNServiceImpl") ChiTietStrategy chiTietStrategy) {
        this.chiTietStrategy = chiTietStrategy;
    }

    @GetMapping("/chiTietPN")
    public ResponseEntity<ResponseObject> findAllChiTietPN() {
        return chiTietStrategy.findAllChiTiet();
    }

    @GetMapping("/chiTietPN/{id}") // Custom Router
    public ResponseEntity<ResponseObject> findAllChiTietPNByChiNhanhId(@PathVariable("id") Long chiNhanhId) {
        return chiTietStrategy.findAllChiTietByChiNhanhId(chiNhanhId);
    }

//    @GetMapping("/chiTietPNBySanPham/{id}") // Custom Router
//    public ResponseEntity<ResponseObject> findAllChiTietPNBySanPhamId(@PathVariable("id") Long sanPhamId) {
//        return chiTietPNServiceImpl.findAllChiTietPNBySanPhamId(sanPhamId);
//    }

    @GetMapping("/chiTietPN/{chiNhanhId}/{sanPhamId}") // Custom Router
    public ResponseEntity<ResponseObject> findAllChiTietPNByCNIdAndSPId(@PathVariable("chiNhanhId") Long chiNhanhId, @PathVariable("sanPhamId") Long sanPhamId) {
        return chiTietStrategy.findAllChiTietByCNIdAndSPId(chiNhanhId, sanPhamId);
    }

    @PostMapping("/chiTietPN") // Custom Router
    public ResponseEntity<ResponseObject> insertChiTietPNAndPNAndQLSP(@Valid @RequestBody TaoPhieuNhapDTO taoPhieuNhapDTO) {
        return chiTietStrategy.insertChiTietAndProcess(taoPhieuNhapDTO);
    }

    @PutMapping("/chiTietPN/{id}")
    public ResponseEntity<ResponseObject> updateChiTietPN(@Valid @RequestBody ChiTietPhieuNhap chiTietPhieuNhap, @PathVariable("id") Long chiTietPNId) {
        return chiTietStrategy.updateChiTiet(chiTietPhieuNhap, chiTietPNId);
    }

    @DeleteMapping("/chiTietPN/{id}")
    public ResponseEntity<ResponseObject> deleteChiTietPN(@PathVariable("id") Long chiTietPNId) {
        return chiTietStrategy.deleteChiTiet(chiTietPNId);
    }
}
