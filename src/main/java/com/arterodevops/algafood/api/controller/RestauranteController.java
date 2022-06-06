package com.arterodevops.algafood.api.controller;

import com.arterodevops.algafood.domain.exception.CozinhaNotFoundException;
import com.arterodevops.algafood.domain.exception.NegocioException;
import com.arterodevops.algafood.domain.model.Restaurante;
import com.arterodevops.algafood.domain.service.RestauranteService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping(value = "/restaurantes")
public class RestauranteController {

    @Autowired
    private RestauranteService restauranteService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Restaurante add(@RequestBody Restaurante restaurante) {
        return restauranteService.add(restaurante);
    }

    @GetMapping
    public List<Restaurante> list() {
        return restauranteService.listAll();
    }

    @GetMapping("/{restauranteId}")
    public Restaurante byId(@PathVariable Long restauranteId) {
        return restauranteService.findById(restauranteId);
    }

    @PutMapping("/{restauranteId}")
    public Restaurante update(@PathVariable Long restauranteId, @RequestBody Restaurante restaurante) {

        try {
            Restaurante restauranteAtual = restauranteService.findOrElse(restauranteId);

            BeanUtils.copyProperties(restaurante, restauranteAtual,
                    "id", "formasPagamento", "endereco", "dataCadastro", "produtos");

            return restauranteService.save(restauranteAtual);
        } catch (CozinhaNotFoundException e) {
            throw new NegocioException(e.getMessage());
        }
    }

    @DeleteMapping("/{restauranteId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remove(@PathVariable Long restauranteId) {
        restauranteService.remove(restauranteId);
    }

}
