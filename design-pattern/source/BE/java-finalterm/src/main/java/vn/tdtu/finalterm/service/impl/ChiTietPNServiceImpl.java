package vn.tdtu.finalterm.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import vn.tdtu.finalterm.dto.TaoPhieuNhapDTO;
import vn.tdtu.finalterm.helper.observer.QuanLySPServiceObserver;
import vn.tdtu.finalterm.helper.strategy.ChiTietStrategy;
import vn.tdtu.finalterm.models.*;
import vn.tdtu.finalterm.repositories.ChiNhanhRepository;
import vn.tdtu.finalterm.repositories.ChiTietPNRepository;
import vn.tdtu.finalterm.repositories.PhieuNhapRepository;
import vn.tdtu.finalterm.repositories.SanPhamRepository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service("chiTietPNServiceImpl")
public class ChiTietPNServiceImpl implements ChiTietStrategy {

    @Autowired
    ChiTietPNRepository chiTietPNRepository;
    @Autowired
    ChiNhanhRepository chiNhanhRepository;
    @Autowired
    SanPhamRepository sanPhamRepository;
    @Autowired
    PhieuNhapRepository phieuNhapRepository;
//    @Autowired
//    QuanLySPService quanLySPService;
    @Autowired
    QuanLySPServiceObserver quanLySPServiceObserver;

    @Override
    public ResponseEntity<ResponseObject> findAllChiTiet() {
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("ok", "Query All ChiTietPN Success", chiTietPNRepository.findAll())
        );
    }

    @Override
    public ResponseEntity<ResponseObject> findAllChiTietByChiNhanhId(Long chiNhanhId) {
        Optional<ChiNhanh> chiNhanh = chiNhanhRepository.findById(chiNhanhId);

        if (!chiNhanh.isPresent()) return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                new ResponseObject("failed", "Can't find Chi Nhanh with id = " + chiNhanhId, "")
        );

        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("ok", "Query All ChiTietPN By ChiNhanh Success", chiTietPNRepository.findAllByChiNhanh(chiNhanh))
        );
    }

    @Override
    public ResponseEntity<ResponseObject> findAllChiTietByCNIdAndSPId(Long chiNhanhId, Long sanPhamId) {
        Optional<ChiNhanh> chiNhanh = chiNhanhRepository.findById(chiNhanhId);
        Optional<SanPham> sanPham = sanPhamRepository.findById(sanPhamId);

        if (!chiNhanh.isPresent()) return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                new ResponseObject("failed", "Can't find ChiNhanh with id = " + chiNhanhId, "")
        );
        if (!sanPham.isPresent()) return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                new ResponseObject("failed", "Can't find SanPham with id = " + sanPhamId, "")
        );

        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("ok", "Query All ChiTietPN By ChiNhanh And SanPham Success", chiTietPNRepository.findAllByChiNhanhAndSanPham(chiNhanh, sanPham))
        );
    }

    @Override
    public ResponseEntity<ResponseObject> insertChiTietAndProcess(Object dto) {
        if (!(dto instanceof TaoPhieuNhapDTO)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ResponseObject("failed", "Invalid DTO type", "")
            );
        }

        TaoPhieuNhapDTO taoPhieuNhapDTO = (TaoPhieuNhapDTO) dto;

        List<SanPham> boxSanPham = new ArrayList<>();

        // Check All SanPham Id
        for (Long sanPhamId : taoPhieuNhapDTO.getSanPhamId()) {
            Optional<SanPham> sanPham = sanPhamRepository.findById(sanPhamId);
            if (!sanPham.isPresent()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                        new ResponseObject("failed", "Can't find SanPham with id = " + sanPhamId, "")
                );
            } else boxSanPham.add(sanPham.get());
        }

        // Check Chi Nhanh Id
        Optional<ChiNhanh> chiNhanh = chiNhanhRepository.findById(taoPhieuNhapDTO.getChiNhanhId());
        if (!chiNhanh.isPresent()) return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                new ResponseObject("failed", "Can't find ChiNhanh with id = " + taoPhieuNhapDTO.getChiNhanhId(), "")
        );

        // Add PhieuNhap
        taoPhieuNhapDTO.getPhieuNhap().setChiNhanh(chiNhanh.get());
        PhieuNhap phieuNhapAfterSave = phieuNhapRepository.save(taoPhieuNhapDTO.getPhieuNhap());

        // Find PhieuNhap just added
        Optional<PhieuNhap> phieuNhap = phieuNhapRepository.findById(phieuNhapAfterSave.getId());

        int indexBox = 0;
        for (ChiTietPhieuNhap chiTietPhieuNhap : taoPhieuNhapDTO.getChiTietPhieuNhap()) {
            if (boxSanPham != null) {
                chiTietPhieuNhap.setSanPham(boxSanPham.get(indexBox));
                chiTietPhieuNhap.setPhieuNhap(phieuNhap.get());
                chiTietPhieuNhap.setChiNhanh(chiNhanh.get());
            }
            indexBox++;
        }

        // Take tongTien from ChiTietPhieuNhap just saved and sum them for tongCong of PhieuNhap
        List<ChiTietPhieuNhap> chiTietPhieuNhapList = chiTietPNRepository.saveAll(Arrays.stream(taoPhieuNhapDTO.getChiTietPhieuNhap()).toList());
        float sumTongTien = 0.0f;
        for (ChiTietPhieuNhap chiTietPhieuNhap : chiTietPhieuNhapList) {
            sumTongTien += chiTietPhieuNhap.getTongTien();
        }

        // Assign TongCong
        phieuNhap.get().setTongCong(sumTongTien);
        PhieuNhap resPhieuNhap = phieuNhapRepository.save(phieuNhap.get());

        // Add QuanlySanPham
        quanLySPServiceObserver.insertQuanLySP(resPhieuNhap.getNgayNhap(), chiTietPhieuNhapList);

        // Update All TrangThai of QuanLySP
        quanLySPServiceObserver.updateAllTrangThai();

        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("ok", "Insert ChiTietPhieuNhap and PhieuNhap Success", chiTietPhieuNhapList)
        );
    }

    @Override
    public ResponseEntity<ResponseObject> updateChiTiet(Object chiTiet, Long chiTietId) {
        if (!(chiTiet instanceof ChiTietPhieuNhap)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ResponseObject("failed", "Invalid ChiTiet type", "")
            );
        }

        ChiTietPhieuNhap chiTietPhieuNhap = (ChiTietPhieuNhap) chiTiet;
        Optional<ChiTietPhieuNhap> foundCTPN = chiTietPNRepository.findById(chiTietId);

        if (!foundCTPN.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject("failed", "Can't find ChiTietPhieuNhap with id = " + chiTietId, "")
            );
        }

        // Update for quantity of QuanLySP
        int soLuongOld = foundCTPN.get().getSoLuong();

        // Find PhieuNhap to Update TongCong (Get Old TongTien and subtract it)
        Optional<PhieuNhap> phieuNhap = phieuNhapRepository.findById(foundCTPN.get().getPhieuNhap().getId());
        phieuNhap.get().setTongCong(phieuNhap.get().getTongCong() - foundCTPN.get().getTongTien());

        ChiTietPhieuNhap updatedCTPN = foundCTPN
                .map(CTPN -> {
                    CTPN.setGiaNhap(chiTietPhieuNhap.getGiaNhap());
                    CTPN.setSoLuong(chiTietPhieuNhap.getSoLuong());

                    return chiTietPNRepository.save(CTPN);
                }).orElseGet(() -> {
                    return null;
                });

        // Update Phieu Nhap
        phieuNhap.get().setTongCong(phieuNhap.get().getTongCong() + updatedCTPN.getTongTien());
        phieuNhapRepository.save(phieuNhap.get());

        // Update QuanLySP
        quanLySPServiceObserver.updateTrongKhoSameSoLuong(foundCTPN.get().getSanPham(), foundCTPN.get().getChiNhanh(), soLuongOld, updatedCTPN.getSoLuong());

        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("ok", "Update ChiTietPN, PhieuNhap, QuanLySP Success", updatedCTPN)
        );
    }

    @Override
    public ResponseEntity<ResponseObject> deleteChiTiet(Long chiTietId) {
        Optional<ChiTietPhieuNhap> foundCTPN = chiTietPNRepository.findById(chiTietId);

        if(!foundCTPN.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject("failed", "Can't find ChiTietPhieuNhap with id = " + chiTietId, "")
            );
        }

        // Delete Quantity for QuanLySP from ChiTietPN
        List<ChiTietPhieuNhap> termCTPN = new ArrayList<>();
        termCTPN.add(foundCTPN.get());
        ResponseEntity<ResponseObject> checkQuantity = quanLySPServiceObserver.updateEffectByDeleteChiTietPN(termCTPN);
        if(checkQuantity != null) {
            return checkQuantity;
        }

        // Find PhieuNhap to Update TongCong (Get Old TongTien and subtract it)
        Optional<PhieuNhap> phieuNhap = phieuNhapRepository.findById(foundCTPN.get().getPhieuNhap().getId());
        phieuNhap.get().setTongCong(phieuNhap.get().getTongCong() - foundCTPN.get().getTongTien());
        phieuNhapRepository.save(phieuNhap.get());

        // Delete chiTietPN
        chiTietPNRepository.deleteById(chiTietId);

        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("ok", "Delete ChiTietPhieuNhap Success", "")
        );
    }
}
