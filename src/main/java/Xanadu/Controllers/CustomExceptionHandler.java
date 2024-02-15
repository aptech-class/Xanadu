package Xanadu.Controllers;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
@Slf4j
public class CustomExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<Object> error(Exception exception, HttpServletRequest request){
        exception.printStackTrace();
        return ResponseEntity.status(500).body(exception.getMessage());
    }
}
