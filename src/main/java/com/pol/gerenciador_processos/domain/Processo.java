package com.pol.gerenciador_processos.domain;

import jakarta.persistence.*;

@Entity(name = "processo")
public class Processo {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private Long numero;
    private String nomeReu;
    private boolean removido;

    public Processo() {}

    public Processo(Long numero, String nomeReu) {
        this.numero = numero;
        this.nomeReu = nomeReu;
        this.removido = false;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getNumero() {
        return numero;
    }

    public void setNumero(Long numero) {
        this.numero = numero;
    }

    public String getNomeReu() {
        return nomeReu;
    }

    public void setNomeReu(String nomeReu) {
        this.nomeReu = nomeReu;
    }

    public boolean isRemovido() {
        return removido;
    }

    public void setRemovido(boolean removido) {
        this.removido = removido;
    }

}
