package br.com.desafio.enums;

public enum AutorizacaoEnum {
    AUTORIZADO("S", "Autorizado"),
    NAO_AUTORIZADO("N", "Não Autorizado"),
    CANCELADO("C", "Cancelado");

    private final String valor;
    private final String descricao;

    AutorizacaoEnum(String valor, String descricao) {
        this.valor = valor;
        this.descricao = descricao;
    }

    public String getValor() {
        return valor;
    }

    public String getDescricao() {
        return descricao;
    }

    public static AutorizacaoEnum fromValue(String valor) {
        for (AutorizacaoEnum autorizacao : AutorizacaoEnum.values()) {
            if (autorizacao.valor.equals(valor)) {
                return autorizacao;
            }
        }
        throw new IllegalArgumentException("Valor de autorização inválido: " + valor);
    }
}
