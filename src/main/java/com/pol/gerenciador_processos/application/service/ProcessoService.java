package com.pol.gerenciador_processos.application.service;

import com.pol.gerenciador_processos.domain.Processo;
import com.pol.gerenciador_processos.interfaces.resource.dto.ProcessoDTO;
import jakarta.transaction.Transactional;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@Service
public class ProcessoService {

    private final ProcessoRepository repository;

    public ProcessoService(ProcessoRepository repository) {
        this.repository = repository;
    }

    public ResponseEntity<List<ProcessoDTO>> obterTodos() {
        List<Processo> processos = repository.obterTodos();
        List<Processo> processosNaoRemovidos = processos.stream()
                .filter(processo -> !processo.isRemovido())
                .toList();
        return ResponseEntity.ok(ProcessoDTO.converterLista(processosNaoRemovidos));
    }

    public ResponseEntity<ProcessoDTO> obterProcesso(Long numero) {
        Optional<Processo> processoOp = repository.obterPelo(numero);
        return processoOp
                .filter(processo -> !processo.isRemovido())
                .map(processo -> ResponseEntity.ok(ProcessoDTO.converter(processo)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Transactional
    public ResponseEntity<ProcessoDTO> criar(ProcessoDTO dto, UriComponentsBuilder uriBuilder) {
        Optional<Processo> processoOp = repository.obterPelo(dto.numero());
        if (processoOp.isPresent()) {
            Processo processoExistente = processoOp.get();
            String mensagemErro = (processoExistente.isRemovido())
                    ? "O numero de processo " + processoExistente.getNumero() + " ja foi utilizado."
                    : "O processo ja existe, considere altera-lo.";
            throw new IllegalArgumentException(mensagemErro);
        }
        Processo processoCriado = repository.persistir(dto.toEntity());
        URI uri = uriBuilder.path("/processo/{id}").buildAndExpand(processoCriado.getId()).toUri();
        return ResponseEntity.created(uri).body(ProcessoDTO.converter(processoCriado));
    }

    @Transactional
    public ResponseEntity<ProcessoDTO> atualizar(ProcessoDTO dto) {
        Optional<Processo> processoOp = repository.obterPelo(dto.numero());
        if (processoOp.isPresent()) {
            dto.update(processoOp.get());
            return ResponseEntity.ok(dto);
        }
        return ResponseEntity.notFound().build();
    }

    @Transactional
    public ResponseEntity<?> removerPeloNumero(Long numero) {
        Optional<Processo> processoOp = repository.obterPelo(numero);
        if (processoOp.isPresent()) {
            processoOp.get().setRemovido(true);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
