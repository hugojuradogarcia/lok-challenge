package com.lok.api.controller.exception;

import com.lok.api.model.commons.Logging;
import com.lok.api.model.exception.GenericNotFoundException;
import com.lok.api.model.out.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.annotation.Resource;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.UnexpectedTypeException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;


@ControllerAdvice
@PropertySource("classpath:application.properties")
public class GlobalException {

    private static final Logger logger = LoggerFactory.getLogger(GlobalException.class);

    @Resource
    Environment environment;

    // GenericNotFound
    @ResponseBody
    @ExceptionHandler
    public ResponseEntity<Response> handlerNotFound(GenericNotFoundException e) {
        e.printStackTrace();
        Logging.info(logger, e.toString());

        Response responseError = Response.builder()
                .status(HttpStatus.NOT_FOUND.toString())
                .statusText(environment.getProperty("httpStatus.error.statusText"))
                .message(environment.getProperty("httpStatus.notFound.messages"))
                .build();

        return new ResponseEntity<>(responseError, HttpStatus.NOT_FOUND);
    }

    // ArgumentTypeMismatch
    @ResponseBody
    @ExceptionHandler
    public ResponseEntity<Response> handleArgumentTypeMismatch(MethodArgumentTypeMismatchException e) {
        e.printStackTrace();
        Logging.error(logger, e.toString());

        Response responseError = Response.builder()
                .status(HttpStatus.BAD_REQUEST.toString())
                .statusText(environment.getProperty("httpStatus.error.statusText"))
                .message(environment.getProperty("httpStatus.badRequest.messages") + " - " + e.getMostSpecificCause().getMessage())
                .build();

        return new ResponseEntity<>(responseError, HttpStatus.BAD_REQUEST);
    }

    // MethodArgumentNotValidException
    @ResponseBody
    @ExceptionHandler
    public ResponseEntity<Response> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        e.printStackTrace();
        e.getMessage();
        Logging.error(logger, e.toString());

        List<String> details = new ArrayList<>();
        for(ObjectError error : e.getBindingResult().getAllErrors()) {
            details.add(error.getDefaultMessage());
        }

        Response responseError = Response.builder()
                .status(HttpStatus.BAD_REQUEST.toString())
                .statusText(environment.getProperty("httpStatus.error.statusText"))
                .message(environment.getProperty("httpStatus.badRequest.messages") + " - " + details)
                .build();

        return new ResponseEntity<>(responseError, HttpStatus.BAD_REQUEST);
    }

    // ConstraintViolationException
    @ResponseBody
    @ExceptionHandler
    public ResponseEntity<Response> handleConstraintViolationException(ConstraintViolationException e) {
        e.printStackTrace();
        Logging.error(logger, e.toString());

        Set<ConstraintViolation<?>> violations = e.getConstraintViolations();
        StringBuilder strBuilder = new StringBuilder();

        for (ConstraintViolation<?> violation : violations ) {
            strBuilder.append(violation.getMessage() + "\n");
        }

        Response responseError = Response.builder()
                .status(HttpStatus.BAD_REQUEST.toString())
                .statusText(environment.getProperty("httpStatus.error.statusText"))
                .message(environment.getProperty("httpStatus.badRequest.messages") + strBuilder.toString())
                .build();

        return new ResponseEntity<>(responseError, HttpStatus.BAD_REQUEST);
    }

    // Bad Request
    @ExceptionHandler
    public ResponseEntity<Response> handlerBadRequest(HttpMessageNotReadableException e){
        e.printStackTrace();
        Logging.error(logger, e.toString());

        Response responseError = Response.builder()
                .status(HttpStatus.BAD_REQUEST.toString())
                .statusText(environment.getProperty("httpStatus.error.statusText"))
                .message(environment.getProperty("httpStatus.badRequest.messages") + e.getMessage())
                .build();

        return new ResponseEntity<>(responseError, HttpStatus.BAD_REQUEST);
    }

    // UnexpectedTypeException
    @ExceptionHandler
    public ResponseEntity<Response> handlerUnexpectedTypeException(UnexpectedTypeException e){
        e.printStackTrace();

        Logging.error(logger, e.toString());
        Response responseError = Response.builder()
                .status(HttpStatus.BAD_REQUEST.toString())
                .statusText(environment.getProperty("httpStatus.error.statusText"))
                .message(environment.getProperty("httpStatus.badRequest.messages") + e.getMessage())
                .build();

        return new ResponseEntity<>(responseError, HttpStatus.BAD_REQUEST);
    }

    // All
    @ResponseBody
    @ExceptionHandler
    public ResponseEntity<Response> handlerException(Exception e) {
        e.printStackTrace();
        Logging.error(logger, e.toString());
        Response responseError = Response.builder()
                .status(HttpStatus.INTERNAL_SERVER_ERROR.toString())
                .statusText(environment.getProperty("httpStatus.error.statusText"))
                .message(environment.getProperty("httpStatus.ioException.messages") + e.getMessage())
                .build();

        return new ResponseEntity<>(responseError, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
