package com.shabbir.Jwt_Impl.Exception;

import io.jsonwebtoken.ExpiredJwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.security.authentication.AccountStatusException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.nio.file.AccessDeniedException;
import java.security.SignatureException;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ProblemDetail handleSecurityException(Exception e){
        ProblemDetail errorDetails = null;
        log.error(e.toString());
        if(e instanceof BadCredentialsException){
            errorDetails = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(401),e.getLocalizedMessage());
            errorDetails.setProperty("description","The username or password is incorrect");
            return errorDetails;
        }

        if(e instanceof AccountStatusException){
            errorDetails = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(403),e.getLocalizedMessage());
            errorDetails.setProperty("description","The account is locked");
            return errorDetails;
        }

        if(e instanceof AccessDeniedException){
            errorDetails = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(403),e.getLocalizedMessage());
            errorDetails.setProperty("description","you are not authorized to access this resource");
            return errorDetails;
        }

        if(e instanceof SignatureException){
            errorDetails = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(403),e.getLocalizedMessage());
            errorDetails.setProperty("description","The JWT signature is invalid");
            return errorDetails;
        }

        if(e instanceof ExpiredJwtException){
            errorDetails = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(403),e.getLocalizedMessage());
            errorDetails.setProperty("description","The JWT token has expired");
            return errorDetails;
        }

        errorDetails = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(500), e.getLocalizedMessage());
        errorDetails.setProperty("description","Unknown internal server error.");
        return errorDetails;
    }

}
