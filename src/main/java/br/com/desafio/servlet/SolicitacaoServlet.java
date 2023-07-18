package br.com.desafio.servlet;

import br.com.desafio.enums.AutorizacaoEnum;
import br.com.desafio.enums.SexoEnum;
import br.com.desafio.model.Procedimento;
import br.com.desafio.model.Solicitacao;
import br.com.desafio.service.ProcedimentoService;
import br.com.desafio.service.RegraService;
import br.com.desafio.service.SolicitacaoService;
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

    ProcedimentoService procedimentoService;
    SolicitacaoService solicitacaoService;
    RegraService regraService;

    @Override
    public void init() {
        Connection connection = ConnectionFactory.getConnection();
        procedimentoService = new ProcedimentoService(connection);
        solicitacaoService = new SolicitacaoService(connection);
        regraService = new RegraService(connection);
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
                    solicitacaoService.excluirSolicitacao(idSolicitacao);
                    break;
                case "cancelarSolicitacao":
                    solicitacaoService.cancelarSolicitacao(idSolicitacao);
                    break;
            }
        }

        populaIndex(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Procedimento procedimento = procedimentoService.buscaProcedimentoPorId(Long.parseLong(request.getParameter("procedimento")));
        String nome = request.getParameter("nome");
        int idade = Integer.parseInt(request.getParameter("idade"));
        SexoEnum sexo = SexoEnum.fromValue(request.getParameter("sexo"));

        Solicitacao solicitacao = new Solicitacao(procedimento, nome, idade, sexo, AutorizacaoEnum.NAO_AUTORIZADO);
        boolean autorizacaoDaSolicitacao = regraService.validaSolicitacao(solicitacao);

        if(request.getParameter("id") != null && !request.getParameter("id").isEmpty()) {
            solicitacao.setId(Long.parseLong(request.getParameter("id")));
            solicitacaoService.alterarSolicitacao(solicitacao, autorizacaoDaSolicitacao);
        } else {
            solicitacaoService.criaSolicitacao(solicitacao, autorizacaoDaSolicitacao);
        }

        populaIndex(request, response);
    }

    private void populaSolicitacaoParaEdicao(Long idSolicitacao, HttpServletRequest request) {
        Solicitacao solicitacao = solicitacaoService.buscaSolicitacaoPorId(idSolicitacao);
        request.setAttribute("solicitacao", solicitacao);
    }

    private void populaIndex(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("procedimentos", procedimentoService.buscaProcedimentos());
        request.setAttribute("sexos", SexoEnum.values());
        request.setAttribute("solicitacoes", solicitacaoService.buscaSolicitacoes());

        RequestDispatcher requestDispatcher = request.getRequestDispatcher("index.jsp");
        requestDispatcher.forward(request, response);
    }
}