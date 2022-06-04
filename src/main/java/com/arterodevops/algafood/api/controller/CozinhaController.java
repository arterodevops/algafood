package com.arterodevops.algafood.api.controller;

import com.arterodevops.algafood.domain.model.Cozinha;
import com.arterodevops.algafood.domain.service.CozinhaService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping(value = "/cozinhas")
public class CozinhaController {

    @Autowired
    private CozinhaService cozinhaService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Cozinha add(@RequestBody Cozinha cozinha) {
        return cozinhaService.add(cozinha);
    }

    @GetMapping
    public List<Cozinha> list() {
        return cozinhaService.listAll();
    }

    @GetMapping("/{cozinhaId}")
    public Cozinha byId(@PathVariable Long cozinhaId) {
        return cozinhaService.findById(cozinhaId);
    }

    @PutMapping("/{cozinhaId}")
    public Cozinha update(@PathVariable Long cozinhaId, @RequestBody Cozinha cozinha) {
        Cozinha cozinhaAtual = cozinhaService.findOrElse(cozinhaId);

        BeanUtils.copyProperties(cozinha, cozinhaAtual, "id");

        return cozinhaService.save(cozinhaAtual);
    }

    @DeleteMapping("/{cozinhaId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remove(@PathVariable Long cozinhaId) {
        cozinhaService.remove(cozinhaId);
    }

}
