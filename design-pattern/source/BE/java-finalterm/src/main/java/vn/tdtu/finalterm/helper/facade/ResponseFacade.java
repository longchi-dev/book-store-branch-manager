package vn.tdtu.finalterm.helper.facade;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import vn.tdtu.finalterm.dto.response.ResponseMessage;
import vn.tdtu.finalterm.service.impl.ResponseServiceImpl;


public class ResponseFacade {
    private static ResponseFacade uniqueInstance;
    private ResponseServiceImpl responseService;

    public ResponseFacade() {
        responseService = new ResponseServiceImpl();
    }

    public static ResponseFacade getInstance() {
        if (uniqueInstance == null) {
            uniqueInstance = new ResponseFacade();
        }
        return uniqueInstance;
    }

    public ResponseEntity response200(Object data) {
        return responseService.response(HttpStatus.OK, data);
    }

    public ResponseEntity response200(String data) {
        return responseService.response(HttpStatus.OK,
                ResponseMessage.builder().status(HttpStatus.OK.value()).message(data).build());
    }

    public ResponseEntity response400(Object data) {
        return responseService.response(HttpStatus.BAD_REQUEST,
                ResponseMessage.builder().status(HttpStatus.BAD_REQUEST.value()).message(data).build());
    }

    public ResponseEntity response401(Object data) {
        return responseService.response(HttpStatus.UNAUTHORIZED,
                ResponseMessage.builder().status(HttpStatus.UNAUTHORIZED.value()).message(data).build());
    }

    public ResponseEntity response500(Object data) {
        return responseService.response(HttpStatus.BAD_REQUEST,
                ResponseMessage.builder().status(HttpStatus.BAD_REQUEST.value()).message(data).build());
    }
}

