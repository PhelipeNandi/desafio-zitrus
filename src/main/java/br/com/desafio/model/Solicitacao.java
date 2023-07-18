package br.com.desafio.model;

import br.com.desafio.enums.AutorizacaoEnum;
import br.com.desafio.enums.SexoEnum;

public class Solicitacao {

    private Long id;
    private Procedimento procedimento;
    private String nome;
    private int idade;
    private SexoEnum sexo;
    private AutorizacaoEnum autorizado;

    public Solicitacao(Long id, Procedimento procedimento, String nome, int idade, SexoEnum sexo, AutorizacaoEnum autorizado) {
        this.id = id;
        this.procedimento = procedimento;
        this.nome = nome;
        this.idade = idade;
        this.sexo = sexo;
        this.autorizado = autorizado;
    }

    public Solicitacao(Procedimento procedimento, String nome, int idade, SexoEnum sexo, AutorizacaoEnum autorizado) {
        this.procedimento = procedimento;
        this.nome = nome;
        this.idade = idade;
        this.sexo = sexo;
        this.autorizado = autorizado;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Procedimento getProcedimento() {
        return procedimento;
    }

    public void setProcedimento(Procedimento procedimento) {
        this.procedimento = procedimento;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getIdade() {
        return idade;
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }

    public SexoEnum getSexo() {
        return sexo;
    }

    public void setSexo(SexoEnum sexo) {
        this.sexo = sexo;
    }

    public AutorizacaoEnum getAutorizado() {
        return autorizado;
    }

    public void setAutorizado(AutorizacaoEnum autorizado) {
        this.autorizado = autorizado;
    }
}