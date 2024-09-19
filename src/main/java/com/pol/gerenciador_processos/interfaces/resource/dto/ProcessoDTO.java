package com.pol.gerenciador_processos.interfaces.resource.dto;

import com.pol.gerenciador_processos.domain.Processo;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.util.List;
import java.util.Objects;

public record ProcessoDTO(@NotNull(message = "O número do processo precisa ser informado")
                          @Min(value = 1, message = "Número do processo não pode ser igual ou menor que zero")
                          Long numero,
                          String nomeReu) {

    public static ProcessoDTO converter(Processo processo) {
        String reu = processo.getNomeReu();
        if (Objects.isNull(reu) || reu.isBlank()) reu = "Nenhum reu informado.";
        return new ProcessoDTO(processo.getNumero(), reu);
    }

    public static List<ProcessoDTO> converterLista(List<Processo> processos) {
        return processos.stream().map(ProcessoDTO::converter).toList();
    }

    public Processo toEntity() {
        return new Processo(this.numero, this.nomeReu);
    }

    public void update(Processo processo) {
        processo.setNumero(this.numero);
        processo.setNomeReu(this.nomeReu);
    }
}
