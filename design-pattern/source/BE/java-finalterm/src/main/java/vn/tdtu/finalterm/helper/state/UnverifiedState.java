package vn.tdtu.finalterm.helper.state;

import vn.tdtu.finalterm.models.TaiKhoan;

public class UnverifiedState implements AccountState{
    @Override
    public void handle(TaiKhoan taiKhoan) {
        System.out.println("Account is in unverified state.");
        taiKhoan.setEnabled(false);
    }
}
