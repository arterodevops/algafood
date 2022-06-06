package com.arterodevops.algafood.domain.service;

import com.arterodevops.algafood.domain.exception.CidadeNotFoundException;
import com.arterodevops.algafood.domain.model.Cidade;
import com.arterodevops.algafood.domain.model.Estado;
import com.arterodevops.algafood.domain.repository.CidadeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CidadeService {

    @Autowired
    EstadoService estadoService;
    private static final String MSG_CIDADE_EM_USO
            = "Cidade de código %d não pode ser removida, pois está em uso";
    @Autowired
    private CidadeRepository cidadeRepository;

    public Cidade add(Cidade cidade) {
        return cidadeRepository.save(cidade);
    }

    public Cidade findOrElse(Long cidadeId) {
        return cidadeRepository.findById(cidadeId)
                .orElseThrow(() -> new CidadeNotFoundException(
                        String.format("Cidade de código %d não pode ser encontrada", cidadeId)));
    }

    public List<Cidade> listAll() {
        return cidadeRepository.findAll();
    }

    public Cidade save(Cidade cidade) {
        Long estadoId = cidade.getEstado().getId();

        Estado estado = estadoService.findOrElse(estadoId);

        cidade.setEstado(estado);

        return cidadeRepository.save(cidade);
    }

    public void remove(Long cidadeId) {

        try {
            cidadeRepository.deleteById(cidadeId);

        } catch (EmptyResultDataAccessException e) {
            throw new CidadeNotFoundException(cidadeId);

        } catch (DataIntegrityViolationException e) {
            throw new CidadeNotFoundException(
                    String.format(MSG_CIDADE_EM_USO, cidadeId));
        }
    }

    public Cidade findById(Long cidadeId) {
        return findOrElse(cidadeId);
    }
}
