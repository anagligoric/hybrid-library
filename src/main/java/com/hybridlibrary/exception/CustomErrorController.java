package com.hybridlibrary.exception;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;


@RestController
public class CustomErrorController implements ErrorController {
    @GetMapping("/error")
    public ResponseEntity<ErrorMessage> handleError(Exception ex, HttpServletRequest request) {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

        ErrorMessage errors = new ErrorMessage();
        errors.setTimeStamp(new Date());
        errors.setMessage(request.getAttribute(RequestDispatcher.ERROR_MESSAGE).toString());
        if(status != null){
            if (status.equals(HttpStatus.NOT_FOUND.value())) {
                errors.setStatus(HttpStatus.NOT_FOUND.value());
                return new ResponseEntity<>(errors, HttpStatus.NOT_FOUND);
            } else if (status.equals(HttpStatus.INTERNAL_SERVER_ERROR.value())){
                errors.setStatus(HttpStatus.NOT_FOUND.value());
                return new ResponseEntity<>(errors, HttpStatus.NOT_FOUND);
            }else if (status.equals(HttpStatus.CONFLICT.value())) {
                errors.setStatus(HttpStatus.CONFLICT.value());
                return new ResponseEntity<>(errors, HttpStatus.CONFLICT);
            }
        }


        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public String getErrorPath() {
        return "/error";
    }
}
