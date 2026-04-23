package com.example.user_auth.exceptions;

import com.example.user_auth.controllers.AuthController;
import com.example.user_auth.dtos.ErrorResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Date;

@ControllerAdvice(assignableTypes = AuthController.class)
public class UserControllerAdvice {

    @ExceptionHandler(exception = UsernameNotFoundException.class)
    public ResponseEntity<ErrorResponseDTO> userNameNotFoundExceptionHandler() {

        return new ResponseEntity<ErrorResponseDTO>(
                new ErrorResponseDTO("Usuário não encontrado", HttpStatus.NOT_FOUND.value()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(exception = InvalidRequestException.class)
    public ResponseEntity<ErrorResponseDTO> invalidRequestException() {

        return new ResponseEntity<ErrorResponseDTO>(
                new ErrorResponseDTO("Requisição inválida", HttpStatus.BAD_REQUEST.value()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(exception = UserAlreadyExistsException.class)
    public ResponseEntity<ErrorResponseDTO> userAlreadyExistsException() {

        return new ResponseEntity<ErrorResponseDTO>(
                new ErrorResponseDTO("Usuário com o email já existe", HttpStatus.BAD_REQUEST.value()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(exception = Exception.class)
    public ResponseEntity<ErrorResponseDTO> serverException() {

        return new ResponseEntity<ErrorResponseDTO>(
                new ErrorResponseDTO("Error trying to register user",
                        HttpStatus.INTERNAL_SERVER_ERROR.value()), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
