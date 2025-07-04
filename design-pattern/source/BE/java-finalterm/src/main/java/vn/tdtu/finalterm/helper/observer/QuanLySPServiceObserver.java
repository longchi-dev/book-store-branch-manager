package vn.tdtu.finalterm.helper.observer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import vn.tdtu.finalterm.enums.DefaultValues;
import vn.tdtu.finalterm.models.*;
import vn.tdtu.finalterm.repositories.ChiNhanhRepository;
import vn.tdtu.finalterm.repositories.QuanLySPRepository;
import vn.tdtu.finalterm.repositories.SanPhamRepository;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;

@Service
public class QuanLySPServiceObserver implements QuanLySPObserver {
    @Autowired
    QuanLySPRepository quanLySPRepository;
    @Autowired
    ChiNhanhRepository chiNhanhRepository;
    @Autowired
    SanPhamRepository sanPhamRepository;
    @Autowired
    QuanLySPSubject quanLySPSubject;

    @Autowired
    public QuanLySPServiceObserver(QuanLySPRepository quanLySPRepository,
                                   ChiNhanhRepository chiNhanhRepository,
                                   SanPhamRepository sanPhamRepository,
                                   QuanLySPSubject quanLySPSubject) {
        this.quanLySPRepository = quanLySPRepository;
        this.chiNhanhRepository = chiNhanhRepository;
        this.sanPhamRepository = sanPhamRepository;
        this.quanLySPSubject = quanLySPSubject;
        this.quanLySPSubject.addObserver(this);
    }
    @Override
    public void onQuanLySPChange(QuanLySanPham quanLySanPham) {
        System.out.println("QuanLySanPham object has changed: " + quanLySanPham);
    }

    public ResponseEntity<ResponseObject> findAllQuanLySP() {
        updateAllTrangThai();

        List<QuanLySanPham> allQuanLySP = quanLySPRepository.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("ok", "Query All QuanLySanPham Success", allQuanLySP)
        );
    }

    public ResponseEntity<ResponseObject> findAllQuanLySPByChiNhanh(ChiNhanh chiNhanh) {
        updateAllTrangThai();

        List<QuanLySanPham> allQuanLySPByChiNhanh = quanLySPRepository.findAllByMaCN(chiNhanh);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("ok", "Query All QuanLySanPham By ChiNhanh Success", allQuanLySPByChiNhanh)
        );
    }

    public ResponseEntity<ResponseObject> findAllQuanLySPByChiNhanhId(Long chiNhanhId) {
        updateAllTrangThai();

        Optional<ChiNhanh> chiNhanh = chiNhanhRepository.findById(chiNhanhId);
        if(!chiNhanh.isPresent()) return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                new ResponseObject("failed", "Cannot find ChiNhanh with id = " + chiNhanhId, "")
        );
        List<QuanLySanPham> allQuanLySPByChiNhanhId = quanLySPRepository.findAllByMaCN(chiNhanh.get());
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("ok", "Query All QuanLySanPham By ChiNhanhId Success", allQuanLySPByChiNhanhId)
        );
    }

    public ResponseEntity<ResponseObject> findAllQuanLySPBySanPhamId(Long sanPhamId) {
        updateAllTrangThai();

        Optional<SanPham> sanPham = sanPhamRepository.findById(sanPhamId);
        if(!sanPham.isPresent()) return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                new ResponseObject("failed", "Cannot find SanPham with id = " + sanPhamId, "")
        );
        List<QuanLySanPham> allQuanLySPBySanPhamId = quanLySPRepository.findAllByMaSP(sanPham.get());
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("ok", "Query All QuanLySanPham By SanPhamId Success", allQuanLySPBySanPhamId)
        );
    }

    public ResponseEntity<ResponseObject> findAllQLSPByCNIdAndSPId(Long chiNhanhId, Long sanPhamId) {
        Optional<ChiNhanh> chiNhanh = chiNhanhRepository.findById(chiNhanhId);
        Optional<SanPham> sanPham = sanPhamRepository.findById(sanPhamId);
        if(!chiNhanh.isPresent()) return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                new ResponseObject("failed", "Can't find ChiNhanh with id = " + chiNhanhId, "")
        );
        if(!sanPham.isPresent()) return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                new ResponseObject("failed", "Can't find SanPham with id = " + sanPhamId, "")
        );
        List<QuanLySanPham> allQLSPByCNIdAndSPId = quanLySPRepository.findAllByMaCNAndMaSP(chiNhanh, sanPham);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("ok", "Query All QuanLySanPham By ChiNhanh And SanPham Success", allQLSPByCNIdAndSPId)
        );
    }

    public ResponseEntity<ResponseObject> findQuanLySPByTenCN(String name) {
        updateAllTrangThai();

        List<ChiNhanh> chiNhanh = chiNhanhRepository.findByTenChiNhanhContains(name);
        if(chiNhanh.size() <= 0) return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                new ResponseObject("failed", "Cannot find ChiNhanh with tenChiNhanh like " + name, "")
        );
        List<QuanLySanPham> allQuanLySPByTenCN = quanLySPRepository.findAllByMaCNIn(chiNhanh);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("ok", "Query All QuanLySanPham Success", allQuanLySPByTenCN)
        );
    }

    public ResponseEntity<ResponseObject> findQLSPThatExpired() {
        updateAllTrangThai();

        List<QuanLySanPham> allQLSPThatExpired = quanLySPRepository.findAllByTrangThai(0);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("ok", "Query QuanLySanPham Success", allQLSPThatExpired)
        );
    }

    public ResponseEntity<ResponseObject> updateQuanLySP(QuanLySanPham quanLySanPham, Long quanLySPId) {
        QuanLySanPham updateQuanLySanPham = quanLySPRepository.findById(quanLySPId)
                .map((QLSP) -> {
                    QLSP.setGiaBan(quanLySanPham.getGiaBan());
                    return quanLySPRepository.save(QLSP);
                }).orElseGet(() -> {
                    return null;
                });

        if(updateQuanLySanPham == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject("failed", "Can't find QuanLySanPham with id = " + quanLySPId, "")
            );
        }

        // Notify observers of the change
        quanLySPSubject.notifyObservers(updateQuanLySanPham);

        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("ok", "Update QuanLySanPham Success", updateQuanLySanPham)
        );
    }

    public ResponseEntity<ResponseObject> moveSPInSameCN(QuanLySanPham quanLySanPham, Long quanLySPId) {
        Optional<QuanLySanPham> foundQuanLySanPham = quanLySPRepository.findById(quanLySPId);
        if(!foundQuanLySanPham.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject("failed", "Can't find QuanLySanPham with id = " + quanLySPId, "")
            );
        }
        // Move trenKe to trongKho
        if(quanLySanPham.getTrenKe() > 0) {
            // Valid trenKe
            if(quanLySanPham.getTrenKe() > foundQuanLySanPham.get().getTrongKho()) {
                return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(
                        new ResponseObject("failed", "Can't move with quantity > trongKho", "")
                );
            }
            foundQuanLySanPham.get().setTrenKe(foundQuanLySanPham.get().getTrenKe() + quanLySanPham.getTrenKe());
            foundQuanLySanPham.get().setTrongKho(foundQuanLySanPham.get().getTrongKho() - quanLySanPham.getTrenKe());
            // Move trongKho to trenKe
        } else if(quanLySanPham.getTrongKho() > 0) {
            // Valid trongKho
            if(quanLySanPham.getTrongKho() > foundQuanLySanPham.get().getTrenKe()) {
                return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(
                        new ResponseObject("failed", "Can't move with quantity > trenKe", "")
                );
            }
            foundQuanLySanPham.get().setTrongKho(foundQuanLySanPham.get().getTrongKho() + quanLySanPham.getTrongKho());
            foundQuanLySanPham.get().setTrenKe(foundQuanLySanPham.get().getTrenKe() - quanLySanPham.getTrongKho());
            // Value is negative
        } else {
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(
                    new ResponseObject("failed", "Can't move with negative quantity", "")
            );
        }
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("ok", "Update QuanLySanPham Success", quanLySPRepository.save(foundQuanLySanPham.get()))
        );
    }

    public ResponseEntity<ResponseObject> updateAllTrangThai() {
        List<QuanLySanPham> boxQuanLySanPham = quanLySPRepository.findAll();
        if(boxQuanLySanPham.size() <= 0) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject("failed", "Can't find any QuanLySanPham", "")
            );
        }
        for(QuanLySanPham quanLySanPham : boxQuanLySanPham) {
            if(quanLySanPham.getHanTon().before(new java.util.Date())) {
                quanLySanPham.setTrangThai(0);
            } else quanLySanPham.setTrangThai(1);

            // Notify observers of the change
            quanLySPSubject.notifyObservers(quanLySanPham);
        }
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("ok", "Update TrangThai Success", quanLySPRepository.saveAll(boxQuanLySanPham))
        );
    }


    // Use for System
    public void insertQuanLySP(Date ngayNhap, List<ChiTietPhieuNhap> chiTietPhieuNhapList) {
        // Take ngayNhap and Plus KYHAN for hanTon
        LocalDate date = ngayNhap.toLocalDate().plusDays((long) DefaultValues.KYHAN.getValue());
        Date hanTon = Date.valueOf(date);

        for(ChiTietPhieuNhap item : chiTietPhieuNhapList) {
            QuanLySanPham updatedQLSP = quanLySPRepository.findByMaSPAndMaCN(item.getSanPham(), item.getChiNhanh())
                    .map(QLSP -> {
                        // Set new giaBan
                        QLSP.setGiaBan(item.getGiaNhap() * DefaultValues.HESOGIATANG.getValue());

                        // trenKe no update
                        // trongKho update by Old + New soLuong
                        int sumSoLuong = QLSP.getTrongKho() + item.getSoLuong();
                        QLSP.setTrongKho(sumSoLuong);

                        // Update new tongHang = trongKho + trenKe
                        QLSP.setTongHang(sumSoLuong + QLSP.getTrenKe());

                        // Update new hanTon
                        QLSP.setHanTon(hanTon);

                        // Update trangThai
                        QLSP.setTrangThai(1);

                        return quanLySPRepository.save(QLSP);
                    }).orElseGet(() -> {
                        // Take giaNhap and multiply HESOGIATANG for giaBan
                        float giaBan = item.getGiaNhap() * DefaultValues.HESOGIATANG.getValue();

                        QuanLySanPham quanLySanPham = new QuanLySanPham(
                                null,
                                hanTon,
                                giaBan,
                                0,
                                item.getSoLuong(),
                                item .getSoLuong(),
                                1,
                                item.getSanPham(),
                                item.getChiNhanh());

                        return quanLySPRepository.save(quanLySanPham);
                    });

            // Notify observers of the change
            quanLySPSubject.notifyObservers(updatedQLSP);
        }
    }
    public void updateTrongKhoSameSoLuong(SanPham sanPham, ChiNhanh chiNhanh, int soLuongOld, int soLuongNew) {
        QuanLySanPham quanLySanPham = quanLySPRepository.findByMaSPAndMaCN(sanPham, chiNhanh)
                .map(QLSP -> {
                    QLSP.setTrongKho(QLSP.getTrongKho() - soLuongOld + soLuongNew);
                    return quanLySPRepository.save(QLSP);
                }).orElseGet(() -> {
                    return null;
                });

        // Notify observers of the change
        if (quanLySanPham != null) {
            quanLySPSubject.notifyObservers(quanLySanPham);
        }
    }
    public ResponseEntity<ResponseObject> updateEffectByDeleteChiTietPN(List<ChiTietPhieuNhap> chiTietPhieuNhapList) {
        List<QuanLySanPham> updatedQLSP = new ArrayList<>();
        List<Long> boxId = new ArrayList<>();

        for(ChiTietPhieuNhap chiTietPhieuNhap : chiTietPhieuNhapList) {
            QuanLySanPham quanLySanPham = quanLySPRepository.findByMaSPAndMaCN(chiTietPhieuNhap.getSanPham(),
                            chiTietPhieuNhap.getChiNhanh())
                    .map(QLSP -> {
                        int sLConLaiTrenKe = QLSP.getTrenKe() - chiTietPhieuNhap.getSoLuong();
                        if(sLConLaiTrenKe > 0) {
                            QLSP.setTrenKe(sLConLaiTrenKe);
                        } else if(sLConLaiTrenKe == 0) {
                            QLSP.setTrenKe(0);
                        } else {
                            QLSP.setTrenKe(0);

                            int sLConLaiTrongKho = QLSP.getTrongKho() + sLConLaiTrenKe;

                            if (sLConLaiTrongKho > 0) {
                                QLSP.setTrongKho(sLConLaiTrongKho);
                            } else if (sLConLaiTrongKho == 0) {
                                QLSP.setTrongKho(0);
                            } else {
                                // TrongKho is Negative
                                boxId.add(QLSP.getId());
                                return null;
                            }
                        }

                        return QLSP;
                    }).orElseGet(() -> {
                        return null;
                    });

            updatedQLSP.add(quanLySanPham);
        }

        for(QuanLySanPham item : updatedQLSP) {
            if(item == null) {
                return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(
                        new ResponseObject("failed",
                                "There aren't enough quantity for QuanLySanPham with id = " +
                                        boxId.get(0), "")
                );
            }

            // Notify observers of the change
            quanLySPSubject.notifyObservers(item);
        }
        quanLySPRepository.saveAll(updatedQLSP);
        return null;
    }
    public ResponseEntity<ResponseObject> deleteQuantityQuanLySP(Long[] sanPhamId, Long chiNhanhId,
                                                                 ChiTietHoaDon[] chiTietHoaDonList) {
        AtomicBoolean checkQuantity = new AtomicBoolean(false);

        // ChiNhanh objCN = new ChiNhanh(chiNhanhId, null, null, null, null, null, null, null, null);
        int indexCTHD = 0;
        List<QuanLySanPham> boxQLSP = new ArrayList<>();
        for(Long id : sanPhamId) {
            //SanPham objSP = new SanPham(id, null, null, null, null, null, null, null, null);
            Optional<QuanLySanPham> quanLySanPham = quanLySPRepository.findByMaSPIdAndMaCNId(id, chiNhanhId);

            if(quanLySanPham.isPresent()) {
                QuanLySanPham QLSP = quanLySanPham.get();

                int sLConLaiTrenKe = QLSP.getTrenKe() - chiTietHoaDonList[indexCTHD].getSoLuong();
                if(sLConLaiTrenKe > 0) {
                    QLSP.setTrenKe(sLConLaiTrenKe);
                } else if(sLConLaiTrenKe == 0) {
                    QLSP.setTrenKe(0);
                } else {
                    QLSP.setTrenKe(0);

                    int sLConLaiTrongKho = QLSP.getTrongKho() + sLConLaiTrenKe;

                    if (sLConLaiTrongKho > 0) {
                        QLSP.setTrongKho(sLConLaiTrongKho);
                    } else if (sLConLaiTrongKho == 0) {
                        QLSP.setTrongKho(0);
                    } else {
                        // trongKho is negative
                        checkQuantity.set(true);
                    }
                }

                boxQLSP.add(QLSP);

                // Notify observers of the change
                quanLySPSubject.notifyObservers(QLSP);
            } else {
                checkQuantity.set(true);
            }

            if(checkQuantity.get()) break;
            indexCTHD++;
        }
        if(checkQuantity.get()) {
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(
                    new ResponseObject("failed", "There aren't enough quantity for SanPham with id = " +
                            sanPhamId[indexCTHD], "")
            );
        }
        quanLySPRepository.saveAll(boxQLSP);
        return null;
    }
}
