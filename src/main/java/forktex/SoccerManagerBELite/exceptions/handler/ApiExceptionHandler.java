package forktex.SoccerManagerBELite.exceptions.handler;

import forktex.SoccerManagerBELite.exceptions.ApiException;
import forktex.SoccerManagerBELite.exceptions.resources.GeneralErrorResponse;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import forktex.SoccerManagerBELite.exceptions.resources.ExceptionExtractor;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({
            ApiException.class
    })
    public ResponseEntity<GeneralErrorResponse> handleApiException(ApiException exception) {
        log.info("handleApiException exception={}", exception.getMessage(), exception);
        return buildResponse(
                new GeneralErrorResponse()
                        .setHttpStatus(exception.getHttpStatus())
                        .setErrorMessage(exception.getMessage())
                        .setStackTrace(ExceptionExtractor.getStackTrace(exception))
        );
    }

    @ExceptionHandler({
            ConstraintViolationException.class
    })
    public ResponseEntity<GeneralErrorResponse> handleConstraintViolationException(
            ConstraintViolationException exception) {
        log.info("handleConstraintViolationException exception={}", exception.getMessage(), exception);

        return buildResponse(
                new GeneralErrorResponse()
                        .setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR)
                        .setErrorMessage(exception.getMessage())
                        .setStackTrace(ExceptionExtractor.getStackTrace(exception))
        );
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException exception, HttpHeaders headers, HttpStatus status, WebRequest request) {
        log.info("handleMethodArgumentNotValid exception={}", exception.getMessage(), exception);

        final List<String> details = new ArrayList<>();
        for(FieldError fieldError : exception.getFieldErrors()) {
            details.add(fieldError.getField() + " " + fieldError.getDefaultMessage());
        }

        return buildObjectResponse(
                new GeneralErrorResponse()
                        .setHttpStatus(HttpStatus.BAD_REQUEST)
                        .setErrorMessage(exception.getMessage())
                        .setDetails(details.toString())
                        .setStackTrace(ExceptionExtractor.getStackTrace(exception))
        );
    }

    @ExceptionHandler({
            Exception.class
    })
    public ResponseEntity<GeneralErrorResponse> handleAllOtherExceptions(Exception exception) {
        log.info("handleAllOtherExceptions exception={}", exception.getMessage(), exception);

        if (exception instanceof DataIntegrityViolationException) {
            return handleDataIntegrityViolationException((DataIntegrityViolationException) exception);
        }

        return buildResponse(
                new GeneralErrorResponse()
                        .setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR)
                        .setErrorMessage(exception.getMessage())
                        .setStackTrace(ExceptionExtractor.getStackTrace(exception))
        );
    }

    private ResponseEntity<GeneralErrorResponse> handleDataIntegrityViolationException(
            DataIntegrityViolationException exception) {
        return buildResponse(
                new GeneralErrorResponse()
                        .setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR)
                        .setErrorMessage(exception.getMessage())
                        .setStackTrace(ExceptionExtractor.getStackTrace(exception))
        );
    }

    private ResponseEntity<GeneralErrorResponse> buildResponse(GeneralErrorResponse generalErrorResponse) {
        return new ResponseEntity<>(generalErrorResponse, generalErrorResponse.getHttpStatus());
    }

    private ResponseEntity<Object> buildObjectResponse(GeneralErrorResponse generalErrorResponse) {
        return new ResponseEntity<>(generalErrorResponse, generalErrorResponse.getHttpStatus());
    }
}
