package com.pol.gerenciador_processos.interfaces.resource;

import io.swagger.v3.oas.annotations.Operation;
import com.pol.gerenciador_processos.application.service.ProcessoService;
import com.pol.gerenciador_processos.interfaces.resource.dto.ProcessoDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("api/v1/processos")
public class ProcessoResource {

    private final ProcessoService service;

    public ProcessoResource(ProcessoService service) {
        this.service = service;
    }

    @GetMapping
    @Operation(summary = "Listar Processos", description = "Permite listar todos os processos cadastrados.")
    public ResponseEntity<List<ProcessoDTO>> listarProcessos() {
        return service.obterTodos();
    }

    @GetMapping("/{numeroProcesso}")
    @Operation(summary = "Buscar Processo", description = "Permite buscar um processo pelo numero.")
    public ResponseEntity<ProcessoDTO> obterPeloNumero(@PathVariable @Min(1) Long numeroProcesso) {
        return service.obterProcesso(numeroProcesso);
    }

    @PostMapping
    @Operation(summary = "Criar Processo", description = "Cria e persiste um novo processo.")
    public ResponseEntity<ProcessoDTO> criar(@RequestBody @Valid ProcessoDTO processo, UriComponentsBuilder uriBuilder) {
        return service.criar(processo, uriBuilder);
    }

    @PutMapping
    @Operation(summary = "Adicionar Réu ao Processo", description = "Permite adicionar o nome de um Réu ao processo.")
    public ResponseEntity<ProcessoDTO> adicionarReu(@RequestBody @Valid ProcessoDTO processo) {
        return service.atualizar(processo);
    }

    @DeleteMapping("/{numeroProcesso}")
    @Operation(summary = "Remover Processo", description = "Permite remover um processo prescrito ou concluído.")
    public ResponseEntity<?> remover(@PathVariable Long numeroProcesso) {
        return service.removerPeloNumero(numeroProcesso);
    }

}
