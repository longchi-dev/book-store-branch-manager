package vn.tdtu.finalterm.helper.template_method;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import vn.tdtu.finalterm.models.HoaDon;
import vn.tdtu.finalterm.repositories.ChiNhanhRepository;
import vn.tdtu.finalterm.repositories.ChiTietHDRepository;
import vn.tdtu.finalterm.repositories.HoaDonRepository;

import java.util.List;
@Service
public class HoaDonCRUDOperation extends CRUDOperation<HoaDon>{

    @Autowired
    HoaDonRepository hoaDonRepository;
    @Autowired
    ChiNhanhRepository chiNhanhRepository;
    @Autowired
    ChiTietHDRepository chiTietHDRepository;

    @Override
    protected String getTypeName() {
        return "HoaDon";
    }

    @Override
    protected List<HoaDon> getAll() {
        return hoaDonRepository.findAll();
    }

    @Override
    protected HoaDon updateEntity(HoaDon existHoaDon, HoaDon newHoaDon) {
        if(existHoaDon == null) {
            return null;
        }

        existHoaDon.setNgayLap(newHoaDon.getNgayLap());
        return hoaDonRepository.save(existHoaDon);
    }

    @Override
    protected void deleteEntity(HoaDon hoaDon) {
        hoaDonRepository.delete(hoaDon);
    }

    @Override
    protected JpaRepository<HoaDon, Long> getRepository() {
        return hoaDonRepository;
    }
}


