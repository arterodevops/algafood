package com.arterodevops.algafood.domain.service;

import com.arterodevops.algafood.domain.exception.CozinhaNotFoundException;
import com.arterodevops.algafood.domain.exception.EntityAlgafoodNotFoundException;
import com.arterodevops.algafood.domain.exception.EntityInUseException;
import com.arterodevops.algafood.domain.model.Cozinha;
import com.arterodevops.algafood.domain.repository.CozinhaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CozinhaService {

    private static final String MSG_COZINHA_EM_USO
            = "Cozinha de código %d não pode ser removida, pois está em uso";
    @Autowired
    private CozinhaRepository cozinhaRepository;

    public Cozinha add(Cozinha cozinha) {
        return cozinhaRepository.save(cozinha);
    }

    public Cozinha findOrElse(Long cozinhaId) {
        return cozinhaRepository.findById(cozinhaId)
                .orElseThrow(() -> new CozinhaNotFoundException(
                        String.format("Cozinha de código %d não pode ser encontrada", cozinhaId)));
    }

    public List<Cozinha> listAll() {
        return cozinhaRepository.findAll();
    }

    public Cozinha save(Cozinha cozinha) {
        return cozinhaRepository.save(cozinha);
    }

    public void remove(Long cozinhaId) {

        try {
            cozinhaRepository.deleteById(cozinhaId);

        } catch (EmptyResultDataAccessException e) {
            throw new CozinhaNotFoundException(cozinhaId);

        } catch (DataIntegrityViolationException e) {
            throw new EntityInUseException(
                    String.format(MSG_COZINHA_EM_USO, cozinhaId));
        }
    }

    public Cozinha findById(Long cozinhaId) {
        return findOrElse(cozinhaId);
    }
}
