<!DOCTYPE html>
<html>
<head>
    <title>Calculadora Básica</title>
</head>
<body>
    <h2>Calculadora</h2>
    <form method="post" action="calcular">
        Número 1: <input type="text" name="num1" /><br/>
        Número 2: <input type="text" name="num2" /><br/>
        Operação:
        <select name="operacao">
            <option value="soma">+</option>
            <option value="subtracao">-</option>
            <option value="multiplicacao">*</option>
            <option value="divisao">/</option>
        </select><br/>
        <input type="submit" value="Calcular" />
    </form>

    <%
        if (request.getAttribute("resultado") != null) {
            out.println("<h3>Resultado: " + request.getAttribute("resultado") + "</h3>");
        }
    %>
</body>
</html>