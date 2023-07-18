package br.com.desafio.service;



import br.com.desafio.model.Solicitacao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RegraService {

    private final Connection connection;

    public RegraService(Connection connection) {
        this.connection = connection;
    }

    public boolean validaSolicitacao(Solicitacao solicitacao) {
        boolean existeRegra = false;

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sqlValidaSolicitacao());
            preparedStatement.setLong(1, solicitacao.getProcedimento().getId());
            preparedStatement.setInt(2, solicitacao.getIdade());
            preparedStatement.setString(3, solicitacao.getSexo().getValor());

            ResultSet resultSet = preparedStatement.executeQuery();
            existeRegra = resultSet.next();

            preparedStatement.close();
            resultSet.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return existeRegra;
    }

    private String sqlValidaSolicitacao() {
        StringBuilder sql = new StringBuilder();

        sql.append("SELECT 1 FROM regra WHERE idprocedimento = ? ");
        sql.append("AND idade = ? ");
        sql.append("AND sexo = ?");

        return sql.toString();
    }
}
