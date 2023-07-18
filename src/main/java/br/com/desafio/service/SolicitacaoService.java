package br.com.desafio.service;

import br.com.desafio.enums.AutorizacaoEnum;
import br.com.desafio.enums.SexoEnum;
import br.com.desafio.model.Procedimento;
import br.com.desafio.model.Solicitacao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SolicitacaoService {

    private final Connection connection;

    public SolicitacaoService(Connection connection) {
        this.connection = connection;
    }

    public void criaSolicitacao(Solicitacao solicitacao, boolean autorizacaoDaSolicitacao) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sqlCriaSolicitacao());
            preparedStatement.setLong(1, solicitacao.getProcedimento().getId());
            preparedStatement.setString(2, solicitacao.getNome());
            preparedStatement.setInt(3, solicitacao.getIdade());
            preparedStatement.setString(4, solicitacao.getSexo().getValor());
            preparedStatement.setString(5, autorizacaoDaSolicitacao ? AutorizacaoEnum.AUTORIZADO.getValor() : AutorizacaoEnum.NAO_AUTORIZADO.getValor());

            preparedStatement.executeUpdate();

            preparedStatement.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Solicitacao> buscaSolicitacoes() {
        ArrayList<Solicitacao> solicitacoes = new ArrayList<>();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT id, idprocedimento, nome, idade, sexo, autorizado FROM solicitacao");

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Solicitacao solicitacao = populaSolicitacao(resultSet);
                solicitacoes.add(solicitacao);
            }

            preparedStatement.close();
            resultSet.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return solicitacoes;
    }

    public Solicitacao buscaSolicitacaoPorId(Long id) {
        Solicitacao solicitacao = null;

        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT id, idprocedimento, nome, idade, sexo, autorizado FROM solicitacao WHERE id = ?");
            preparedStatement.setLong(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                solicitacao = populaSolicitacao(resultSet);
            }

            preparedStatement.close();
            resultSet.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return solicitacao;
    }

    public void alterarSolicitacao(Solicitacao solicitacao, boolean autorizacaoDaSolicitacao) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sqlAlterarSolicitacao());
            preparedStatement.setLong(1, solicitacao.getId());
            preparedStatement.setLong(2, solicitacao.getProcedimento().getId());
            preparedStatement.setString(3, solicitacao.getNome());
            preparedStatement.setInt(4, solicitacao.getIdade());
            preparedStatement.setString(5, solicitacao.getSexo().getValor());
            preparedStatement.setString(6, autorizacaoDaSolicitacao ? AutorizacaoEnum.AUTORIZADO.getValor() : AutorizacaoEnum.NAO_AUTORIZADO.getValor());
            preparedStatement.setLong(7, solicitacao.getId());

            preparedStatement.executeUpdate();

            preparedStatement.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Solicitacao populaSolicitacao(ResultSet resultSet) throws SQLException {
        Long id = resultSet.getLong("id");
        Long idProcedimento = resultSet.getLong("idprocedimento");
        String nome = resultSet.getString("nome");
        int idade = resultSet.getInt("idade");
        SexoEnum sexo = SexoEnum.fromValue(resultSet.getString("sexo"));
        AutorizacaoEnum autorizado = AutorizacaoEnum.fromValue(resultSet.getString("autorizado"));

        ProcedimentoService procedimentoService = new ProcedimentoService(connection);
        Procedimento procedimento = procedimentoService.buscaProcedimentoPorId(idProcedimento);

        Solicitacao solicitacao = new Solicitacao(id, procedimento, nome, idade, sexo, autorizado);
        return solicitacao;
    }

    public void excluirSolicitacao(Long idSolicitacao) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM solicitacao WHERE id = ?");
            preparedStatement.setLong(1, idSolicitacao);

            preparedStatement.executeUpdate();

            preparedStatement.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void cancelarSolicitacao(Long idSolicitacao) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE solicitacao SET autorizado = ? WHERE id = ?");
            preparedStatement.setString(1, AutorizacaoEnum.CANCELADO.getValor());
            preparedStatement.setLong(2, idSolicitacao);

            preparedStatement.executeUpdate();

            preparedStatement.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private String sqlCriaSolicitacao() {
        StringBuilder sql = new StringBuilder();

        sql.append("INSERT INTO solicitacao ");
        sql.append("(idprocedimento, nome, idade, sexo, autorizado) ");
        sql.append("VALUES ");
        sql.append("(?, ?, ?, ?, ?)");

        return sql.toString();
    }

    private String sqlAlterarSolicitacao() {
        StringBuilder sql = new StringBuilder();

        sql.append("UPDATE solicitacao ");
        sql.append("SET id = ?, ");
        sql.append("idprocedimento = ?, ");
        sql.append("nome = ?, ");
        sql.append("idade = ?, ");
        sql.append("sexo = ?, ");
        sql.append("autorizado = ? ");
        sql.append("WHERE id = ? ");

        return sql.toString();
    }
}
