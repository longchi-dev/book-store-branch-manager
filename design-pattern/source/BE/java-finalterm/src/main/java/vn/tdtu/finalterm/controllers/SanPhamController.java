package vn.tdtu.finalterm.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.tdtu.finalterm.helper.factoryAndAdapter.service.SanPhamServiceFactory;
import vn.tdtu.finalterm.models.ResponseObject;
import vn.tdtu.finalterm.models.SanPham;
import vn.tdtu.finalterm.service.SanPhamService;

@RestController
@RequestMapping(path = "")
public class SanPhamController {
    @Autowired
    SanPhamServiceFactory sanPhamServiceFactory;

    @GetMapping("/sanPham")
    public ResponseEntity<ResponseObject> findAllSanPham() {
        return sanPhamServiceFactory.findAllSanPham();
    }

    @GetMapping("/sanPham/{id}")
    public ResponseEntity<ResponseObject> findSanPhamById(@PathVariable("id") Long id) {
        return sanPhamServiceFactory.findSanPhamById(id);
    }

    @GetMapping("/sanPhamQuery/{key}") // Find Request
    public ResponseEntity<ResponseObject> findSPByTenOrLoaiOrTHOrTGOrTL(@PathVariable("key") String key) {
        return sanPhamServiceFactory.findSPByTenOrLoaiOrTHOrTGOrTL(key);
    }

    @PostMapping("/sanPham")
    public ResponseEntity<ResponseObject> insertSanPham(@Valid @RequestBody SanPham sanPham) {
        return sanPhamServiceFactory.insertSanPham(sanPham);
    }

    @PutMapping("/sanPham/{id}")
    public ResponseEntity<ResponseObject> updateSanPham(@Valid @RequestBody SanPham sanPham, @PathVariable("id") Long id) {
        return sanPhamServiceFactory.updateSanPham(sanPham, id);
    }

    @DeleteMapping("/sanPham/{id}")
    public ResponseEntity<ResponseObject> deleteSanPham(@PathVariable("id") Long id) {
        return sanPhamServiceFactory.deleteSanPham(id);
    }

    // Uncomment this part below and comment above to revert back to the original implementation
//    @Autowired
//    SanPhamService sanPhamService;
//
//    @GetMapping("/sanPham")
//    public ResponseEntity<ResponseObject> findAllSanPham() {
//        return sanPhamService.findAllSanPham();
//    }
//
//    @GetMapping("/sanPham/{id}")
//    public ResponseEntity<ResponseObject> findSanPhamById(@PathVariable("id") Long id) {
//        return sanPhamService.findSanPhamById(id);
//    }
//
//    @GetMapping("/sanPhamQuery/{key}") // Find Request
//    public ResponseEntity<ResponseObject> findSPByTenOrLoaiOrTHOrTGOrTL(@PathVariable("key") String key) {
//        return sanPhamService.findSPByTenOrLoaiOrTHOrTGOrTL(key);
//    }
//
//    @PostMapping("/sanPham")
//    public ResponseEntity<ResponseObject> insertSanPham(@Valid @RequestBody SanPham sanPham) {
//        return sanPhamService.insertSanPham(sanPham);
//    }
//
//    @PutMapping("/sanPham/{id}")
//    public ResponseEntity<ResponseObject> updateSanPham(@Valid @RequestBody SanPham sanPham, @PathVariable("id") Long id) {
//        return sanPhamService.updateSanPham(sanPham, id);
//    }
//
//    @DeleteMapping("/sanPham/{id}")
//    public ResponseEntity<ResponseObject> deleteSanPham(@PathVariable("id") Long id) {
//        return sanPhamService.deleteSanPham(id);
//    }
}
