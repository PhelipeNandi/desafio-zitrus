<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>Desafio Zitrus</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-9ndCyUaIbzAi2FUVXJi0CjmCapSmO7SnpJef0486qhLnuZ2cdeRhO02iuK6FUUVM" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js" integrity="sha384-geWF76RCwLtnZ8qwWowPQNguL3RmwHVBC9FhGdlKrxdiJJigb/j/68SIy3Te4Bkz" crossorigin="anonymous"></script>
</head>
<body>
    <jsp:include page="header.jsp"/>

    <div class="p-3">
        <h4>Cadastro de Solicitação</h4>

        <form action="solicitacao" method="post">
            <div class="d-flex gap-3 mb-3">

                <div class="visually-hidden">
                    <input type="text" name="id" value="<c:out value="${solicitacao.getId()}" />"/>
                </div>

                <div class="flex-grow-1">
                    <label for="nome" class="form-label">Nome:</label>
                    <input id="nome" type="text" name="nome" value="<c:out value="${solicitacao.getNome()}" />" required class="form-control"/>
                </div>

                <div class="w-25">
                    <label for="procedimento" class="form-label">Procedimento:</label>
                    <select id="procedimento" name="procedimento" required class="form-select">
                        <option value="">Selecione</option>
                        <c:forEach items="${procedimentos}" var="item">
                            <c:choose>
                                <c:when test="${item.id == solicitacao.getProcedimento().getId()}">
                                    <option selected value="${item.id}">${item.nome}</option>
                                </c:when>
                                <c:otherwise>
                                    <option value="${item.id}">${item.nome}</option>
                                </c:otherwise>
                            </c:choose>
                        </c:forEach>
                    </select>
                </div>

                <div class="w-10">
                    <label for="idade" class="form-label">Idade:</label>
                    <input id="idade" type="number" max="120" name="idade" value="<c:out value="${solicitacao.getIdade()}" />" required class="form-control"/>
                </div>

                <div class="w-25">
                    <label for="sexo" class="form-label">Sexo:</label>
                    <select id="sexo" name="sexo" required class="form-select"/>
                        <option value="">Selecione</option>
                        <c:forEach items="${sexos}" var="item">
                            <c:choose>
                                <c:when test="${item.valor == solicitacao.getSexo().getValor()}">
                                    <option selected value="${item.valor}">${item.descricao}</option>
                                </c:when>
                                <c:otherwise>
                                    <option value="${item.valor}">${item.descricao}</option>
                                </c:otherwise>
                            </c:choose>
                        </c:forEach>
                    </select>
                </div>

            </div>

            <button type="submit" class="btn btn-dark" id="cadastrarSolicitacao">Cadastrar</button>
        </form>

        <table class="mt-3 table table-hover caption-top">
            <caption class="h4">Lista de solicitações</caption>
            <thead class="table-light">
            <th>Procedimento</th>
            <th>Nome</th>
            <th>Idade</th>
            <th>Sexo</th>
            <th>Status</th>
            <th colspan=3>Ação</th>
            </thead>
            <tbody>
            <c:forEach var="solicitacao" items="${solicitacoes}">
                <tr>
                    <td>${solicitacao.getProcedimento().getNome()}</td>
                    <td>${solicitacao.getNome()}</td>
                    <td>${solicitacao.getIdade()}</td>
                    <td>${solicitacao.getSexo().getDescricao()}</td>
                    <td>${solicitacao.getAutorizado().getDescricao()}</td>
                    <td><a href="solicitacao?acao=alterarSolicitacao&idSolicitacao=<c:out value="${solicitacao.getId()}"/>">Alterar</a></td>
                    <td><a href="solicitacao?acao=excluirSolicitacao&idSolicitacao=<c:out value="${solicitacao.getId()}"/>">Excluir</a></td>
                    <td><a href="solicitacao?acao=cancelarSolicitacao&idSolicitacao=<c:out value="${solicitacao.getId()}"/>">Cancelar</a></td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>

    <jsp:include page="footer.jsp"/>
</body>
</html>