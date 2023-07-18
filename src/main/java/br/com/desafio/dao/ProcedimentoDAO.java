package br.com.desafio.dao;

import br.com.desafio.model.Procedimento;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProcedimentoDAO {

    private final Connection connection;

    public ProcedimentoDAO(Connection connection) {
        this.connection = connection;
    }

    public List<Procedimento> buscaProcedimentos() {
        ArrayList<Procedimento> procedimentos = new ArrayList<>();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT id, nome FROM procedimento");

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Procedimento procedimento = new Procedimento(resultSet.getLong("id"), resultSet.getString("nome"));
                procedimentos.add(procedimento);
            }

            preparedStatement.close();
            resultSet.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return procedimentos;
    }

    public Procedimento buscaProcedimentoPorId(Long id) {
        Procedimento procedimento = null;

        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT id, nome FROM procedimento WHERE id = ?");
            preparedStatement.setLong(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                 procedimento = new Procedimento(resultSet.getLong("id"), resultSet.getString("nome"));
            }

            preparedStatement.close();
            resultSet.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return procedimento;
    }
}