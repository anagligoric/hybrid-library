package com.hybridlibrary.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Slf4j
//@RestController
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


        log.error("Unexpected error occurred while handling exception: ", ex);
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public String getErrorPath() {
        return "/error";
    }
}
