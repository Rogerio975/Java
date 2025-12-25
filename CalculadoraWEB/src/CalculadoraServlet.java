package br.com.calculadora;

import java.io.IOException;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.http.HttpServlet;

public class CalculadoraServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        double num1 = Double.parseDouble(request.getParameter("num1"));
        double num2 = Double.parseDouble(request.getParameter("num2"));
        String operacao = request.getParameter("operacao");
        double resultado = 0;

        switch (operacao) {
            case "soma": resultado = num1 + num2; break;
            case "subtracao": resultado = num1 - num2; break;
            case "multiplicacao": resultado = num1 * num2; break;
            case "divisao":
                if (num2 == 0) {
                    resultado = Double.NaN;
                } else {
                    resultado = num1 / num2;
                }
                break;
        }

        request.setAttribute("resultado", resultado);
        RequestDispatcher dispatcher = request.getRequestDispatcher("index.jsp");
        dispatcher.forward(request, response);
    }
}