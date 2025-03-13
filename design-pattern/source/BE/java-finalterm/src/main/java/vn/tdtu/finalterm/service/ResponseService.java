package vn.tdtu.finalterm.service;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Map;
public interface ResponseService<T> {
    public ResponseEntity response(HttpStatus status, T data);
    public ResponseEntity response(HttpStatus status);
}
