package com.example.movies.config;

import com.example.movies.exception.BadRequestException;
import com.example.movies.exception.InternalServerErrorException;
import com.example.movies.exception.NoContentException;
import com.example.movies.model.ExceptionResponse;
import com.example.movies.preference.ConstantPreference;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class AppExceptionHandler
{
    @ExceptionHandler(BadRequestException.class)
    protected ResponseEntity badRequestException()
    {
        return new ResponseEntity<>(
                new ExceptionResponse(HttpStatus.BAD_REQUEST.toString(), ConstantPreference.RESPONSE_MESSAGE_BAD_REQUEST)
                , HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NoContentException.class)
    protected ResponseEntity noContentException()
    {
        return new ResponseEntity<>(
                new ExceptionResponse(HttpStatus.NO_CONTENT.toString(), ConstantPreference.RESPONSE_MESSAGE_NO_CONTENT)
                , HttpStatus.NO_CONTENT);
    }

    @ExceptionHandler(InternalServerErrorException.class)
    protected ResponseEntity internalServerErrorException()
    {
        return new ResponseEntity<>(
                new ExceptionResponse(HttpStatus.INTERNAL_SERVER_ERROR.toString(), ConstantPreference.RESPONSE_MESSAGE_INTERNAL_SERVER_ERROR)
                , HttpStatus.INTERNAL_SERVER_ERROR);
    }
}