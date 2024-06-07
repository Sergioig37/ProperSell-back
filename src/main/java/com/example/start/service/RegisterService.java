package com.example.start.service;

import com.example.start.auth.AuthService;
import com.example.start.auth.RegisterRequest;
import com.example.start.exception.CorreoYaExisteException;
import com.example.start.exception.DatosNoValidosException;
import com.example.start.exception.UsuarioYaExisteException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

@Service
public class RegisterService {

    @Autowired
    AuthService authService;

    public ResponseEntity<?> manejarErrores(@Valid RegisterRequest request, BindingResult bindingResult){
        try {
            authService.register(request, bindingResult);
            return ResponseEntity.status(HttpStatus.OK).body("Usuario registrado con éxito");
        } catch (UsuarioYaExisteException | CorreoYaExisteException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        } catch (
                DatosNoValidosException e) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(e.getMessage());
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ocurrió un error inesperado");
        }
    }


}