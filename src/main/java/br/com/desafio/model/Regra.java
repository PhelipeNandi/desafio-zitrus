package br.com.desafio.model;

import br.com.desafio.enums.SexoEnum;

public class Regra {

    private Long id;
    private Procedimento procedimento;
    private int idade;
    private SexoEnum sexo;

    public Regra(Long id, Procedimento procedimento, int idade, SexoEnum sexo) {
        this.id = id;
        this.procedimento = procedimento;
        this.idade = idade;
        this.sexo = sexo;
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
}
