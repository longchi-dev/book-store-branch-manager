package vn.tdtu.finalterm.helper.observer;

import org.springframework.stereotype.Service;
import vn.tdtu.finalterm.models.QuanLySanPham;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuanLySPSubject {
    private List<QuanLySPObserver> observers = new ArrayList<>();

    public void addObserver(QuanLySPObserver observer) {
        observers.add(observer);
    }

    public void removeObserver(QuanLySPObserver observer) {
        observers.remove(observer);
    }

    public void notifyObservers(QuanLySanPham quanLySanPham) {
        for (QuanLySPObserver observer : observers) {
            observer.onQuanLySPChange(quanLySanPham);
        }
    }
}
