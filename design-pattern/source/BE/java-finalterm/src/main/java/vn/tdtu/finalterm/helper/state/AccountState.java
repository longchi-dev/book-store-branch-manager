package vn.tdtu.finalterm.helper.state;

import vn.tdtu.finalterm.models.TaiKhoan;

public interface AccountState {
    void handle(TaiKhoan taiKhoan);
}
