package vn.tdtu.finalterm.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.tdtu.finalterm.helper.template_method.PhieuNhapCRUDOperation;
import vn.tdtu.finalterm.models.PhieuNhap;
import vn.tdtu.finalterm.models.ResponseObject;
import vn.tdtu.finalterm.service.PhieuNhapService;

@RestController
@RequestMapping(path = "")
public class PhieuNhapController {
    @Autowired
    PhieuNhapService phieuNhapService;

    @Autowired
    PhieuNhapCRUDOperation phieuNhapCRUDOperation;

    @GetMapping("/phieuNhap")
    public ResponseEntity<ResponseObject> findAllPhieuNhap() {
        return phieuNhapCRUDOperation.findAll();
    }

    @GetMapping("/phieuNhap/{chiNhanhId}") // Custom Router
    public ResponseEntity<ResponseObject> findAllPNByChiNhanhId(@PathVariable("chiNhanhId") Long chiNhanhId) {
        return phieuNhapCRUDOperation.findById(chiNhanhId);
    }

    @PostMapping("/phieuNhapQuery") // Custom Router
    public ResponseEntity<ResponseObject> findAllPNByNgayNhap(@RequestBody PhieuNhap phieuNhap) {
        return phieuNhapService.findAllPNByNgayNhap(phieuNhap);
    }

    @PutMapping("/phieuNhap/{id}")
    public ResponseEntity<ResponseObject> updatePhieuNhap(@Valid @RequestBody PhieuNhap phieuNhap, @PathVariable("id") Long id) {
        return phieuNhapCRUDOperation.update(phieuNhap, id);
    }

    @DeleteMapping("/phieuNhap/{phieuNhapId}")
    public ResponseEntity<ResponseObject> deletePhieuNhap(@PathVariable("phieuNhapId") Long phieuNhapId) {
        return phieuNhapCRUDOperation.delete(phieuNhapId);
    }
}
