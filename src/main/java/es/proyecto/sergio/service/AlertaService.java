package es.proyecto.sergio.service;

import es.proyecto.sergio.dao.AlertaDAO;
import es.proyecto.sergio.dao.UsuarioDAO;
import es.proyecto.sergio.dto.AlertaDTO;
import es.proyecto.sergio.entity.Alerta;
import es.proyecto.sergio.entity.Usuario;
import es.proyecto.sergio.exception.DatosNoValidosException;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class AlertaService {

    @Autowired
    AlertaDAO alertaDAO;

    @Autowired
    UsuarioDAO usuarioDAO;
    @Autowired
    private ModelMapper modelMapper;


    public void borrarAlerta(Long id) {
        Optional<Alerta> alertaOptional = alertaDAO.findById(id);

        if (alertaOptional.isPresent()) {

            Set<Usuario> usuarios = alertaOptional.get().getUsuarios();

            for (Usuario usuario : usuarios) {

                Set<Alerta> alertasUsuario = usuario.getAlertas();

                alertasUsuario.remove(alertaOptional.get());

                usuario.setAlertas(alertasUsuario);

                usuarioDAO.save(usuario);

            }

            usuarios.removeAll(alertaOptional.get().getUsuarios());
            alertaOptional.get().setUsuarios(usuarios);


            alertaDAO.delete(alertaOptional.get());

        }
    }

    public List<AlertaDTO> encontrarAlertasPopulares() {


        List<Alerta> alertas = (List<Alerta>) alertaDAO.findAll();

        List<AlertaDTO> alertasEncontradas = new ArrayList<>();


        for (Alerta alerta : alertas) {
            if (alerta.getUsuarios().size() > 1) {

                alertasEncontradas.add(convertirAAlertaDTO(alerta));

            }
        }

        return alertasEncontradas;

    }

    public List<AlertaDTO> getAlertasMasLargas(Long size) {

        List<Alerta> alertas = (List<Alerta>) alertaDAO.findAll();

        List<AlertaDTO> alertasEncontradas = new ArrayList<>();

        for (Alerta alerta : alertas) {
            if (alerta.getDescripcion().length() > size) {

                alertasEncontradas.add(convertirAAlertaDTO(alerta));
            }
        }

        return alertasEncontradas;
    }


    public ResponseEntity<?> editarAlerta(@Valid AlertaDTO alertaDTO, Long id, BindingResult bindingResult) {

        Optional<Alerta> alertaExiste = alertaDAO.findById(id);

        try {
            this.validarDatos(alertaDTO, bindingResult);
            if (alertaExiste.isPresent()) {
                alertaExiste.get().setDescripcion(alertaDTO.getDescripcion());
                alertaExiste.get().setNombre(alertaDTO.getNombre());
                alertaDAO.save(alertaExiste.get());
                return ResponseEntity.status(HttpStatus.OK).body(null);
            }
            return  ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (DatosNoValidosException e) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(e.getMessage());
        }


    }

    public ResponseEntity<?> guardarAlerta(@Valid AlertaDTO alertaDTO, BindingResult bindingResult) {

        Alerta alerta = new Alerta();

        try {
            this.validarDatos(alertaDTO, bindingResult);
            alerta.setDescripcion(alertaDTO.getDescripcion());

            alerta.setNombre(alertaDTO.getNombre());

            alertaDAO.save(alerta);

            return  ResponseEntity.status(HttpStatus.OK).body(null);
        } catch (DatosNoValidosException e) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(e.getMessage());
        }


    }

    private void validarDatos(@Valid AlertaDTO alertaDTO, BindingResult bindingResult) throws DatosNoValidosException {
        if (bindingResult.hasErrors()) {
            throw new DatosNoValidosException("Algunos campos del formulario no son válidos");
        }
    }
    public ResponseEntity<?> existe(Long id){

        Optional<Alerta> alerta = alertaDAO.findById(id);

        if(alerta.isPresent()){
            return ResponseEntity.status(HttpStatus.OK).body(alertaDAO.findById(id).get());
        }
        else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

    }

    public List<AlertaDTO> convertirAListaAlertaDTO(List<Alerta> alertas){

        List<AlertaDTO> alertasDTO = new ArrayList<>();

        for(Alerta alerta: alertas){



            AlertaDTO alertaDTO = modelMapper.map(alerta,AlertaDTO.class);

            alertaDTO.setNumeroUsuarios((long) alerta.getUsuarios().size());

            alertasDTO.add(alertaDTO);

        }


        return alertasDTO;


    }

    public AlertaDTO convertirAAlertaDTO(Alerta alerta){

        AlertaDTO alertaDTO = modelMapper.map(alerta,AlertaDTO.class);

        alertaDTO.setNumeroUsuarios((long) alerta.getUsuarios().size());

        return alertaDTO;
    }
}
