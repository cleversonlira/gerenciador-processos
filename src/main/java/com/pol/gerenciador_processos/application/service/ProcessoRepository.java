package com.pol.gerenciador_processos.application.service;

import com.pol.gerenciador_processos.domain.Processo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface ProcessoRepository {
    List<Processo> findAll();

    Optional<Processo> obterPelo(Long numero);

    Processo persistir(Processo entity);
}
