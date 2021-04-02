package com.lok.api.model.commons;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Data
@Component
public class Utils {
    public static String statusTextOk;
    public static String statusTextError;
    public static String messageOk;
    public static String messageNotFound;
    public static String messageBadRequest;

    @Value("${httpStatus.ok.statusText}")
    public void setStatusTextOk(String statusText) {
        Utils.statusTextOk = statusText;
    }
    @Value("${httpStatus.ok.messages}")
    public void setMessageOk(String messages) {
        Utils.messageOk = messages;
    }

    @Value("${httpStatus.notFound.messages}")
    public void setMessageNotFound(String messagesNotFound) {
        Utils.messageNotFound = messagesNotFound;
    }

    @Value("${httpStatus.badRequest.messages}")
    public void setStatusTextError(String messageBadRequest) {
        Utils.messageBadRequest = messageBadRequest;
    }

    public final ResponseEntity RESPONSE_OK(Class entity) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(entity);
    }

    public final ResponseEntity RESPONSE_CREATED(Class entity) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(entity);
    }

    public final ResponseEntity RESPONSE_NO_CONTENT() {
        return ResponseEntity
                .noContent().build();
    }

    public final ResponseEntity RESPONSE_NOT_FOUND() {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(messageNotFound);
    }

    public final ResponseEntity RESPONSE_BAD_REQUEST() {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(messageBadRequest);
    }

    public final ResponseEntity RESPONSE_INTERNAL_ERROR() {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(messageBadRequest);
    }

}
