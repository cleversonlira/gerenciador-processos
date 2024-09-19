package com.pol.gerenciador_processos;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import com.pol.gerenciador_processos.application.service.ProcessoService;
import com.pol.gerenciador_processos.interfaces.resource.ProcessoResource;
import com.pol.gerenciador_processos.interfaces.resource.dto.ProcessoDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

class ProcessoResourceTests {

	@InjectMocks
	private ProcessoResource processoResource;

	@Mock
	private ProcessoService processoService;


	@BeforeEach
	void setup() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	@DisplayName("Teste para listar todos os processos")
	void listarProcessosTest() {
		List<ProcessoDTO> processos = Arrays.asList(new ProcessoDTO(1L, "Processo A"), new ProcessoDTO(2L, "Processo B"));
		when(processoService.obterTodos())
				.thenReturn(ResponseEntity.ok(processos));
		ResponseEntity<List<ProcessoDTO>> response = processoResource.listarProcessos();
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(2, Objects.requireNonNull(response.getBody()).size());
		assertEquals("Processo A", response.getBody().get(0).nomeReu());
	}

	@Test
	@DisplayName("Teste para buscar um processo pelo número")
	void obterPeloNumeroTest() {
		ProcessoDTO processo = new ProcessoDTO(1L, "Processo A");
		when(processoService.obterProcesso(1L))
				.thenReturn(ResponseEntity.ok(processo));
		ResponseEntity<ProcessoDTO> response = processoResource.obterPeloNumero(1L);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals("Processo A", Objects.requireNonNull(response.getBody()).nomeReu());
		assertEquals(1L, response.getBody().numero());
	}

	@Test
	@DisplayName("Teste para criar um novo processo")
	void criarProcessoTest() {
		ProcessoDTO novoProcesso = new ProcessoDTO(null, "Processo Novo");
		ProcessoDTO processoCriado = new ProcessoDTO(1L, "Processo Novo");
		when(processoService.criar(any(ProcessoDTO.class), any()))
				.thenReturn(ResponseEntity.ok(processoCriado));
		ResponseEntity<ProcessoDTO> response = processoResource.criar(novoProcesso, null);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(1L, Objects.requireNonNull(response.getBody()).numero());
		assertEquals("Processo Novo", response.getBody().nomeReu());
	}

	@Test
	@DisplayName("Teste para adicionar Réu a um processo")
	void adicionarReuTest() {
		ProcessoDTO processoAtualizado = new ProcessoDTO(1L, "Processo Atualizado com Réu");
		when(processoService.atualizar(any(ProcessoDTO.class)))
				.thenReturn(ResponseEntity.ok(processoAtualizado));
		ResponseEntity<ProcessoDTO> response = processoResource.adicionarReu(processoAtualizado);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(1L, Objects.requireNonNull(response.getBody()).numero());
		assertEquals("Processo Atualizado com Réu", response.getBody().nomeReu());
	}

	@Test
	@DisplayName("Teste para remover um processo")
	void removerProcessoTest() {
		when(processoService.removerPeloNumero(1L)).thenReturn(ResponseEntity.ok().build());
		ResponseEntity<?> response = processoResource.remover(1L);
		assertEquals(HttpStatus.OK, response.getStatusCode());
	}
}
