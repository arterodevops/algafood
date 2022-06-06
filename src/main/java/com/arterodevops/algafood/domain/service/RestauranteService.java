package com.arterodevops.algafood.domain.service;

import com.arterodevops.algafood.domain.exception.EntityAlgafoodNotFoundException;
import com.arterodevops.algafood.domain.exception.RestauranteNotFoundException;
import com.arterodevops.algafood.domain.model.Cozinha;
import com.arterodevops.algafood.domain.model.Estado;
import com.arterodevops.algafood.domain.model.Restaurante;
import com.arterodevops.algafood.domain.repository.RestauranteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RestauranteService {

    @Autowired
    private RestauranteRepository restauranteRepository;

    @Autowired
    CozinhaService cozinhaService;

    public Restaurante add(Restaurante restaurante) {
        return restauranteRepository.save(restaurante);
    }

    public Restaurante findOrElse(Long restauranteId) {
        return restauranteRepository.findById(restauranteId)
                .orElseThrow(() -> new RestauranteNotFoundException(
                        String.format("Restaurante de código %d não pode ser encontrada", restauranteId)));
    }

    public List<Restaurante> listAll() {
        return restauranteRepository.findAll();
    }

    public Restaurante save(Restaurante restaurante) {

        Long cozinhaId = restaurante.getCozinha().getId();

        Cozinha cozinha = cozinhaService.findOrElse(cozinhaId);

        restaurante.setCozinha(cozinha);

        return restauranteRepository.save(restaurante);
    }

    public void remove(Long restauranteId) {
        restauranteRepository.deleteById(restauranteId);
    }

    public Restaurante findById(Long restauranteId) {
        return findOrElse(restauranteId);
    }
}
