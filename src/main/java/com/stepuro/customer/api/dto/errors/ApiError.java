package com.stepuro.customer.api.dto.errors;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
public class ApiError {
    private HttpStatus status;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private Timestamp timestamp;
    private String message;
    private String debugMessage;
    private String exceptionName;
    private List<ApiSubError> subErrors;

    private ApiError() {
        subErrors = new ArrayList<>();
        timestamp = Timestamp.from(Instant.now());
    }

    public ApiError(HttpStatus status) {
        this();
        this.status = status;
    }

    public ApiError(HttpStatus status, Throwable ex) {
        this();
        this.status = status;
        this.message = "Unexpected error";
        this.debugMessage = ex.getLocalizedMessage();
        this.exceptionName = ex.getClass().getName();
    }

    public ApiError(HttpStatus status, String message, Throwable ex) {
        this();
        this.status = status;
        this.message = message;
        this.debugMessage = ex.getLocalizedMessage();
        this.exceptionName = ex.getClass().getName();
    }

    public ApiError(HttpStatus status, String message, Throwable ex, List<ApiSubError> subErrors) {
        this();
        this.status = status;
        this.message = message;
        this.debugMessage = ex.getLocalizedMessage();
        this.exceptionName = ex.getClass().getName();
        this.subErrors = subErrors;
    }

    public void addSubError(ApiSubError subError){
        this.subErrors.add(subError);
    }
}
