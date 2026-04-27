package com.fatecpi.impacto_ext.repositories;

import com.fatecpi.impacto_ext.models.Orcamento;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrcamentoRepository extends MongoRepository<Orcamento, String> {
    // Criar buscas customizadas aqui depois, exemplo:
    // List<Orcamento> findByNomeClienteContaining(String nome);
}