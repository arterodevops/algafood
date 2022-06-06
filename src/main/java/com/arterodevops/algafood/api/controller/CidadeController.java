package com.arterodevops.algafood.api.controller;

import com.arterodevops.algafood.domain.model.Cidade;
import com.arterodevops.algafood.domain.service.CidadeService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping(value = "/cidades")
public class CidadeController {

    @Autowired
    private CidadeService cidadeService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Cidade add(@RequestBody Cidade cidade) {
        return cidadeService.add(cidade);
    }

    @GetMapping
    public List<Cidade> list() {
        return cidadeService.listAll();
    }

    @GetMapping("/{cidadeId}")
    public Cidade byId(@PathVariable Long cidadeId) {
        return cidadeService.findById(cidadeId);
    }

    @PutMapping("/{cidadeId}")
    public Cidade update(@PathVariable Long cidadeId, @RequestBody Cidade cidade) {
        Cidade cidadeAtual = cidadeService.findOrElse(cidadeId);

        BeanUtils.copyProperties(cidade, cidadeAtual, "id");

        return cidadeService.save(cidadeAtual);
    }

    @DeleteMapping("/{cidadeId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remove(@PathVariable Long cidadeId) {
        cidadeService.remove(cidadeId);
    }

}
