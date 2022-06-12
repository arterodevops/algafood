package com.arterodevops.algafood.api.controller;

import com.arterodevops.algafood.domain.model.Estado;
import com.arterodevops.algafood.domain.service.EstadoService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController()
@RequestMapping(value = "/estados")
public class EstadoController {

    @Autowired
    private EstadoService estadoService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Estado add(@RequestBody @Valid Estado estado) {
        return estadoService.add(estado);
    }

    @GetMapping
    public List<Estado> list() {
        return estadoService.listAll();
    }

    @GetMapping("/{estadoId}")
    public Estado byId(@PathVariable Long estadoId) {
        return estadoService.findById(estadoId);
    }

    @PutMapping("/{estadoId}")
    public Estado update(@PathVariable Long estadoId, @RequestBody @Valid Estado estado) {
        Estado estadoAtual = estadoService.findOrElse(estadoId);

        BeanUtils.copyProperties(estado, estadoAtual, "id");

        return estadoService.save(estadoAtual);
    }

    @DeleteMapping("/{estadoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remove(@PathVariable Long estadoId) {
        estadoService.remove(estadoId);
    }

}
