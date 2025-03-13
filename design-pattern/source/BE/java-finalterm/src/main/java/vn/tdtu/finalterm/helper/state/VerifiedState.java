package vn.tdtu.finalterm.helper.state;

import vn.tdtu.finalterm.models.TaiKhoan;

public class VerifiedState implements AccountState{
    @Override
    public void handle(TaiKhoan taiKhoan) {
        System.out.println("Account is in verified state.");
        taiKhoan.setEnabled(true);
    }
}
