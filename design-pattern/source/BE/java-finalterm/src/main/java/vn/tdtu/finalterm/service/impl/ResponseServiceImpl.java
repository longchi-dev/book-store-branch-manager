package vn.tdtu.finalterm.service.impl;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import vn.tdtu.finalterm.service.ResponseService;

@Service
public class ResponseServiceImpl implements ResponseService {
    @Override
    public ResponseEntity response(HttpStatus status, Object data) {
        return new ResponseEntity(data, status);
    }

    @Override
    public ResponseEntity response(HttpStatus status) {
        return new ResponseEntity<>(status);
    }
}
