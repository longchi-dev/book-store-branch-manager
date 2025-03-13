package vn.tdtu.finalterm.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.tdtu.finalterm.helper.observer.QuanLySPServiceObserver;
import vn.tdtu.finalterm.models.ChiNhanh;
import vn.tdtu.finalterm.models.QuanLySanPham;
import vn.tdtu.finalterm.models.ResponseObject;

@RestController
@RequestMapping(path = "")
public class QuanLySPController {
    // This part below is the observer code
    @Autowired
    QuanLySPServiceObserver quanLySPServiceObserver;

    @GetMapping("/quanLySP")
    public ResponseEntity<ResponseObject> findAllQuanLySP() {
        return quanLySPServiceObserver.findAllQuanLySP();
    }
    @GetMapping("/quanLySPByCN") // Custom Router
    public ResponseEntity<ResponseObject> findAllQuanLySPByChiNhanh(@Valid @RequestBody ChiNhanh chiNhanh) {
        return quanLySPServiceObserver.findAllQuanLySPByChiNhanh(chiNhanh);
    }
    @GetMapping("/quanLySPByCN/{id}") // Custom Router
    public ResponseEntity<ResponseObject> findAllQuanLySPByChiNhanhId(@PathVariable("id") Long chiNhanhId) {
        return quanLySPServiceObserver.findAllQuanLySPByChiNhanhId(chiNhanhId);
    }
    @GetMapping("/quanLySPBySP/{id}") // Custom Router
    public ResponseEntity<ResponseObject> findAllQuanLySPBySanPhamId(@PathVariable("id") Long sanPhamId) {
        return quanLySPServiceObserver.findAllQuanLySPBySanPhamId(sanPhamId);
    }
    @GetMapping("/quanLySP/{chiNhanhId}/{sanPhamId}") // Custom Router
    public ResponseEntity<ResponseObject> findAllQLSPByCNIdAndSPId(@PathVariable("chiNhanhId") Long chiNhanhId,
                                                                   @PathVariable("sanPhamId") Long sanPhamId) {
        return quanLySPServiceObserver.findAllQLSPByCNIdAndSPId(chiNhanhId, sanPhamId);
    }
    @GetMapping("/quanLySPQuery/{key}") // Find Request
    public ResponseEntity<ResponseObject> findQuanLySPByTenCN(@PathVariable("key") String key) {
        return quanLySPServiceObserver.findQuanLySPByTenCN(key);
    }
    @GetMapping("/quanLySPQuery/expired") // Find Request
    public ResponseEntity<ResponseObject> findQLSPThatExpired() {
        return quanLySPServiceObserver.findQLSPThatExpired();
    }
    @PutMapping("/quanLySP/{id}")
    public ResponseEntity<ResponseObject> updateQuanLySP(@RequestBody QuanLySanPham quanLySanPham,
                                                         @PathVariable("id") Long quanLySPId) {
        return quanLySPServiceObserver.updateQuanLySP(quanLySanPham, quanLySPId);
    }
    @PutMapping("/quanLySP/moveSP/{id}") // Custom Router
    public ResponseEntity<ResponseObject> moveSPInSameCN(@RequestBody QuanLySanPham quanLySanPham,
                                                         @PathVariable("id") Long quanLySPId) {
        return quanLySPServiceObserver.moveSPInSameCN(quanLySanPham, quanLySPId);
    }
    @PutMapping("/quanLySP/updateTrangThai") // Custom Router
    public ResponseEntity<ResponseObject> updateAllTrangThai() {
        return quanLySPServiceObserver.updateAllTrangThai();
    }



    // This part below is the original code
//    @Autowired
//    QuanLySPService quanLySPService;
//
//    @GetMapping("/quanLySP")
//    public ResponseEntity<ResponseObject> findAllQuanLySP() {
//        return quanLySPService.findAllQuanLySP();
//    }
//
//    @GetMapping("/quanLySPByCN") // Custom Router
//    public ResponseEntity<ResponseObject> findAllQuanLySPByChiNhanh(@Valid @RequestBody ChiNhanh chiNhanh) {
//        return quanLySPService.findAllQuanLySPByChiNhanh(chiNhanh);
//    }
//
//    @GetMapping("/quanLySPByCN/{id}") // Custom Router
//    public ResponseEntity<ResponseObject> findAllQuanLySPByChiNhanhId(@PathVariable("id") Long chiNhanhId) {
//        return quanLySPService.findAllQuanLySPByChiNhanhId(chiNhanhId);
//    }
//
//    @GetMapping("/quanLySPBySP/{id}") // Custom Router
//    public ResponseEntity<ResponseObject> findAllQuanLySPBySanPhamId(@PathVariable("id") Long sanPhamId) {
//        return quanLySPService.findAllQuanLySPBySanPhamId(sanPhamId);
//    }
//
//    @GetMapping("/quanLySP/{chiNhanhId}/{sanPhamId}") // Custom Router
//    public ResponseEntity<ResponseObject> findAllQLSPByCNIdAndSPId(@PathVariable("chiNhanhId") Long chiNhanhId, @PathVariable("sanPhamId") Long sanPhamId) {
//        return quanLySPService.findAllQLSPByCNIdAndSPId(chiNhanhId, sanPhamId);
//    }
//
//    @GetMapping("/quanLySPQuery/{key}") // Find Request
//    public ResponseEntity<ResponseObject> findQuanLySPByTenCN(@PathVariable("key") String key) {
//        return quanLySPService.findQuanLySPByTenCN(key);
//    }
//
//    @GetMapping("/quanLySPQuery/expired") // Find Request
//    public ResponseEntity<ResponseObject> findQLSPThatExpired() {
//        return quanLySPService.findQLSPThatExpired();
//    }
//
//    @PutMapping("/quanLySP/{id}")
//    public ResponseEntity<ResponseObject> updateQuanLySP(@RequestBody QuanLySanPham quanLySanPham, @PathVariable("id") Long quanLySPId) {
//        return quanLySPService.updateQuanLySP(quanLySanPham, quanLySPId);
//    }
//
//    @PutMapping("/quanLySP/moveSP/{id}") // Custom Router
//    public ResponseEntity<ResponseObject> moveSPInSameCN(@RequestBody QuanLySanPham quanLySanPham, @PathVariable("id") Long quanLySPId) {
//        return quanLySPService.moveSPInSameCN(quanLySanPham, quanLySPId);
//    }
//
//    @PutMapping("/quanLySP/updateTrangThai") // Custom Router
//    public ResponseEntity<ResponseObject> updateAllTrangThai() {
//        return quanLySPService.updateAllTrangThai();
//    }
}
