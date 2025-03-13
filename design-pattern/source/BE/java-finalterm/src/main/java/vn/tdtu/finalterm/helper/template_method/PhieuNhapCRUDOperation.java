package vn.tdtu.finalterm.helper.template_method;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import vn.tdtu.finalterm.models.PhieuNhap;
import vn.tdtu.finalterm.repositories.ChiNhanhRepository;
import vn.tdtu.finalterm.repositories.PhieuNhapRepository;

import java.util.List;

@Service
public class PhieuNhapCRUDOperation extends CRUDOperation<PhieuNhap>{
    @Autowired
    PhieuNhapRepository phieuNhapRepository;

    @Autowired
    ChiNhanhRepository chiNhanhRepository;
    @Override
    protected String getTypeName() {
        return "PhieuNhap";
    }

    @Override
    protected List<PhieuNhap> getAll() {
        return phieuNhapRepository.findAll();
    }

    @Override
    protected PhieuNhap updateEntity(PhieuNhap existPhieuNhap, PhieuNhap newPhieuNhap) {
        if(existPhieuNhap == null) {
            return null;
        }

        existPhieuNhap.setNgayNhap(newPhieuNhap.getNgayNhap());
        return phieuNhapRepository.save(existPhieuNhap);
    }

    @Override
    protected void deleteEntity(PhieuNhap phieuNhap) {
        phieuNhapRepository.delete(phieuNhap);
    }

    @Override
    protected JpaRepository<PhieuNhap, Long> getRepository() {
        return phieuNhapRepository;
    }
}
