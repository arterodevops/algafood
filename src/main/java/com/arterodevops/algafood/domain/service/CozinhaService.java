package com.arterodevops.algafood.domain.service;

import com.arterodevops.algafood.domain.exception.EntityAlgafoodNotFoundException;
import com.arterodevops.algafood.domain.model.Cozinha;
import com.arterodevops.algafood.domain.repository.CozinhaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CozinhaService {

    @Autowired
    private CozinhaRepository cozinhaRepository;

    public Cozinha add(Cozinha cozinha) {
        return cozinhaRepository.save(cozinha);
    }

    public Cozinha findOrElse(Long cozinhaId) {
        return cozinhaRepository.findById(cozinhaId)
                .orElseThrow(() -> new EntityAlgafoodNotFoundException(
                        String.format("Cozinha de código %d não pode ser encontrada", cozinhaId)));
    }

    public List<Cozinha> listAll() {
        return cozinhaRepository.findAll();
    }

    public Cozinha save(Cozinha cozinha) {
        return cozinhaRepository.save(cozinha);
    }

    public void remove(Long cozinhaId) {
        cozinhaRepository.deleteById(cozinhaId);
    }

    public Cozinha findById(Long cozinhaId) {
        return findOrElse(cozinhaId);
    }
}
