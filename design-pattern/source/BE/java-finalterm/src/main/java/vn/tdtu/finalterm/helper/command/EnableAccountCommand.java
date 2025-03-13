package vn.tdtu.finalterm.helper.command;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;
import vn.tdtu.finalterm.models.TaiKhoan;
import vn.tdtu.finalterm.repositories.TaiKhoanRepository;
import vn.tdtu.finalterm.service.TaiKhoanService;

import java.util.Optional;

@Component
public class EnableAccountCommand implements Command {
    private TaiKhoanRepository taiKhoanRepository;
//    private String taiKhoanId;
    private HttpServletRequest request;

    public EnableAccountCommand(TaiKhoanRepository taiKhoanRepository, HttpServletRequest request) {
        this.taiKhoanRepository = taiKhoanRepository;
        this.request = request;
    }
    @Override
    public void execute() {
        String taiKhoanId = request.getParameter("taiKhoanId");
        Optional<TaiKhoan> taiKhoanOptional = taiKhoanRepository.findById(taiKhoanId);

        if (taiKhoanOptional.isPresent()) {
            TaiKhoan taiKhoan = taiKhoanOptional.get();
            taiKhoan.setEnabled(true);
            taiKhoanRepository.save(taiKhoan);
        } else {
            throw new IllegalArgumentException("Account not exist");
        }
    }
}


