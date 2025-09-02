package com.kakaopaystocks.exception;

import com.kakaopaystocks.dto.ErrorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.Locale;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @Autowired
    private MessageSource messageSource;

    @ExceptionHandler(StockNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleStockNotFoundException(StockNotFoundException ex, WebRequest request) {
        logger.warn("Stock not found: {}", ex.getMessage());
        ErrorResponse errorResponse = createErrorResponse("error.stock.notfound", new Object[]{ex.getStockId()}, request);
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InvalidStockDataException.class)
    public ResponseEntity<ErrorResponse> handleInvalidStockDataException(InvalidStockDataException ex, WebRequest request) {
        logger.error("Invalid stock data: {}", ex.getMessage(), ex);
        ErrorResponse errorResponse = createErrorResponse("error.stock.invaliddata", null, request);
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(
            NoHandlerFoundException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        logger.warn("No handler found for {} {}", ex.getHttpMethod(), ex.getRequestURL());
        ErrorResponse errorResponse = createErrorResponse("error.path.not.found", new Object[]{ex.getRequestURL()}, request);
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(
            HttpRequestMethodNotSupportedException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        logger.warn("Method not supported: {}", ex.getMessage());
        ErrorResponse errorResponse = createErrorResponse("error.method.not.supported", new Object[]{ex.getMethod()}, request);
        return new ResponseEntity<>(errorResponse, HttpStatus.METHOD_NOT_ALLOWED);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(
            HttpMediaTypeNotSupportedException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        logger.warn("Media type not supported: {}", ex.getMessage());
        ErrorResponse errorResponse = createErrorResponse("error.media.type.not.supported", new Object[]{ex.getContentType()}, request);
        return new ResponseEntity<>(errorResponse, HttpStatus.UNSUPPORTED_MEDIA_TYPE);
    }

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(
            MissingServletRequestParameterException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        logger.warn("Missing request parameter: {}", ex.getMessage());
        ErrorResponse errorResponse = createErrorResponse("error.missing.parameter", new Object[]{ex.getParameterName()}, request);
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGlobalException(Exception ex, WebRequest request) {
        logger.error("Unexpected error occurred", ex);
        ErrorResponse errorResponse = createErrorResponse("error.unexpected", null, request);
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private ErrorResponse createErrorResponse(String messageKey, Object[] args, WebRequest request) {
        Locale locale = request.getLocale();
        String message = messageSource.getMessage(messageKey, args, locale);
        String path = request.getDescription(false).substring(4);
        return new ErrorResponse(message, messageKey, path, LocalDateTime.now());
    }
}
