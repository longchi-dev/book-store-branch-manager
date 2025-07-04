package vn.tdtu.finalterm.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.tdtu.finalterm.helper.template_method.HoaDonCRUDOperation;
import vn.tdtu.finalterm.models.HoaDon;
import vn.tdtu.finalterm.models.PhieuNhap;
import vn.tdtu.finalterm.models.ResponseObject;
import vn.tdtu.finalterm.service.HoaDonService;

@RestController
@RequestMapping(path = "")
public class HoaDonController {
    @Autowired
    HoaDonService hoaDonService;

    @Autowired
    HoaDonCRUDOperation hoaDonCRUDOperation;

    @GetMapping("/hoaDon")
    public ResponseEntity<ResponseObject> findAllHoaDon() {
        return hoaDonCRUDOperation.findAll();
    }

    @GetMapping("/hoaDon/{chiNhanhId}") // Custom Router
    public ResponseEntity<ResponseObject> findAllHDByChiNhanhId(@PathVariable("chiNhanhId") Long chiNhanhId) {
        return hoaDonCRUDOperation.findById(chiNhanhId);
    }

    @GetMapping("/hoaDonQuery/{key}") // Find Request
    public ResponseEntity<ResponseObject> findHDByTenChiNhanh(@PathVariable("key") String key) {
        return hoaDonService.findHDByTenChiNhanh(key);
    }

    @PostMapping("/hoaDonQuery") // Custom Router
    public ResponseEntity<ResponseObject> findAllHDByNgayLap(@RequestBody HoaDon hoaDon) {
        return hoaDonService.findHDByNgayLap(hoaDon);
    }

    @PutMapping("/hoaDon/{id}")
    public ResponseEntity<ResponseObject> updateHoaDon(@Valid @RequestBody HoaDon hoaDon, @PathVariable("id") Long id) {
        return hoaDonCRUDOperation.update(hoaDon, id);
    }

    @DeleteMapping("/hoaDon/{hoaDonId}")
    public ResponseEntity<ResponseObject> deleteHoaDon(@PathVariable("hoaDonId") Long hoaDonId) {
        return hoaDonCRUDOperation.delete(hoaDonId);
    }
}
