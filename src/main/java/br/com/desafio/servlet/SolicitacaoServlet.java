package br.com.desafio.servlet;

import br.com.desafio.enums.AutorizacaoEnum;
import br.com.desafio.enums.SexoEnum;
import br.com.desafio.model.Procedimento;
import br.com.desafio.model.Solicitacao;
import br.com.desafio.dao.ProcedimentoDAO;
import br.com.desafio.dao.RegraDAO;
import br.com.desafio.dao.SolicitacaoDAO;
import br.com.desafio.util.ConnectionFactory;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;

@WebServlet("/solicitacao")
public class SolicitacaoServlet extends HttpServlet {

    ProcedimentoDAO procedimentoDAO;
    SolicitacaoDAO solicitacaoDAO;
    RegraDAO regraDAO;

    @Override
    public void init() {
        Connection connection = ConnectionFactory.getConnection();
        procedimentoDAO = new ProcedimentoDAO(connection);
        solicitacaoDAO = new SolicitacaoDAO(connection);
        regraDAO = new RegraDAO(connection);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getParameter("acao") != null) {
            Long idSolicitacao = Long.parseLong(request.getParameter("idSolicitacao"));

            switch (request.getParameter("acao")) {
                case "alterarSolicitacao":
                    populaSolicitacaoParaEdicao(idSolicitacao, request);
                    break;
                case "excluirSolicitacao":
                    solicitacaoDAO.excluirSolicitacao(idSolicitacao);
                    break;
                case "cancelarSolicitacao":
                    solicitacaoDAO.cancelarSolicitacao(idSolicitacao);
                    break;
            }
        }

        populaIndex(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Procedimento procedimento = procedimentoDAO.buscaProcedimentoPorId(Long.parseLong(request.getParameter("procedimento")));
        String nome = request.getParameter("nome");
        int idade = Integer.parseInt(request.getParameter("idade"));
        SexoEnum sexo = SexoEnum.fromValue(request.getParameter("sexo"));

        Solicitacao solicitacao = new Solicitacao(procedimento, nome, idade, sexo, AutorizacaoEnum.NAO_AUTORIZADO);
        boolean autorizacaoDaSolicitacao = regraDAO.validaSolicitacao(solicitacao);

        if(request.getParameter("id") != null && !request.getParameter("id").isEmpty()) {
            solicitacao.setId(Long.parseLong(request.getParameter("id")));
            solicitacaoDAO.alterarSolicitacao(solicitacao, autorizacaoDaSolicitacao);
        } else {
            solicitacaoDAO.criaSolicitacao(solicitacao, autorizacaoDaSolicitacao);
        }

        populaIndex(request, response);
    }

    private void populaSolicitacaoParaEdicao(Long idSolicitacao, HttpServletRequest request) {
        Solicitacao solicitacao = solicitacaoDAO.buscaSolicitacaoPorId(idSolicitacao);
        request.setAttribute("solicitacao", solicitacao);
    }

    private void populaIndex(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("procedimentos", procedimentoDAO.buscaProcedimentos());
        request.setAttribute("sexos", SexoEnum.values());
        request.setAttribute("solicitacoes", solicitacaoDAO.buscaSolicitacoes());

        RequestDispatcher requestDispatcher = request.getRequestDispatcher("index.jsp");
        requestDispatcher.forward(request, response);
    }
}