<%-- 
    Document   : home
    Created on : Jun 21, 2019, 8:46:08 PM
    Author     : thuyv
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Home</title>
    </head>
    <body>
        <form method="POST" action="ProcessServlet" enctype="multipart/form-data">
            Choose a file: <input type="file" name="file" accept="image/*" onchange="loadFile(event)"/>
            <input type="submit" value="upload" name="btAction"/>
        </form>
        <img id="preview" width="auto" height="400px" src="${requestScope.IMAGE}"/>
        <!--<img src="${requestScope.IMAGE}" width="auto" height="400px"/>-->
        <form action="ProcessServlet">
            <input type="submit" value="crawl" name="btAction"/>
        </form>

        <c:forEach var="canvas" items="${requestScope.CANVAS}">
            <img src="${canvas.image}"/>
            <p>${canvas.name}</p>
        </c:forEach>

    </body>
</html>

<script>
    var loadFile = function (event) {
        var output = document.getElementById('preview');
        output.src = URL.createObjectURL(event.target.files[0]);
    };
</script>
