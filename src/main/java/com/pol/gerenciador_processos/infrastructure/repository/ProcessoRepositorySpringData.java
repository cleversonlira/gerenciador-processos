package com.pol.gerenciador_processos.infrastructure.repository;

import com.pol.gerenciador_processos.application.service.ProcessoRepository;
import com.pol.gerenciador_processos.domain.Processo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

interface ProcessoRepositorySpringData extends JpaRepository<Processo, Long>, ProcessoRepository {

        default List<Processo> obterTodos() {
            return findAll();
        }

        Optional<Processo> findByNumero(Long numero);

        default Optional<Processo> obterPelo(Long numero) {
            return findByNumero(numero);
        }

        default Processo persistir(Processo processo) {
            return save(processo);
        }
}
