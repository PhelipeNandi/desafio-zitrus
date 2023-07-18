package br.com.desafio.model;

public class Procedimento {

    private Long id;
    private String nome;

    public Procedimento(Long id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }
}