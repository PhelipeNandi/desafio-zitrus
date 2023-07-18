package br.com.desafio.enums;

public enum SexoEnum {
    MASCULINO("M", "Masculino"),
    FEMININO("F", "Feminino");

    private final String valor;
    private final String descricao;

    SexoEnum(String valor, String descricao) {
        this.valor = valor;
        this.descricao = descricao;
    }

    public String getValor() {
        return valor;
    }

    public String getDescricao() {
        return descricao;
    }

    public static SexoEnum fromValue(String valor) {
        for (SexoEnum sexo : SexoEnum.values()) {
            if (sexo.valor.equals(valor)) {
                return sexo;
            }
        }
        throw new IllegalArgumentException("Valor de sexo inválido: " + valor);
    }
}
