package com.arterodevops.algafood.domain.repository;

import com.arterodevops.algafood.domain.model.Cidade;
import com.arterodevops.algafood.domain.model.Cozinha;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CidadeRepository extends JpaRepository<Cidade, Long> {
}
