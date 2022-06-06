package com.arterodevops.algafood.domain.service;

import com.arterodevops.algafood.domain.exception.EstadoNotFoundException;
import com.arterodevops.algafood.domain.model.Estado;
import com.arterodevops.algafood.domain.repository.EstadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EstadoService {

    private static final String MSG_ESTADO_EM_USO
            = "Estado de código %d não pode ser removida, pois está em uso";
    @Autowired
    private EstadoRepository estadoRepository;

    public Estado add(Estado estado) {
        return estadoRepository.save(estado);
    }

    public Estado findOrElse(Long estadoId) {
        return estadoRepository.findById(estadoId)
                .orElseThrow(() -> new EstadoNotFoundException(
                        String.format("Estado de código %d não pode ser encontrada", estadoId)));
    }

    public List<Estado> listAll() {
        return estadoRepository.findAll();
    }

    public Estado save(Estado estado) {
        return estadoRepository.save(estado);
    }

    public void remove(Long estadoId) {

        try {
            estadoRepository.deleteById(estadoId);

        } catch (EmptyResultDataAccessException e) {
            throw new EstadoNotFoundException(estadoId);

        } catch (DataIntegrityViolationException e) {
            throw new EstadoNotFoundException(
                    String.format(MSG_ESTADO_EM_USO, estadoId));
        }
    }

    public Estado findById(Long estadoId) {
        return findOrElse(estadoId);
    }
}
