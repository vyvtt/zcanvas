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
        <link rel="stylesheet" href="css/home.css"/>
        <title>Home</title>
    </head>
    <body>

        <div class="logo" id="logo">
            <img src="image/logo.png"/>
        </div>

        <br>

        <div class="content">
            <div align="center">
                <form method="POST" action="ProcessServlet" enctype="multipart/form-data">
                    Chọn hình ảnh không gian của bạn 
                    <input id="file" type="file" name="file" accept="image/*" onchange="loadFile(event)"/>
                    <label for="file" class="input-file-label">Choose an image</label>
                    <input id="btn-submit" type="submit" value="upload" name="btAction"/>
                    <label for="btn-submit" class="input-file-label">Start</label>
                </form>

                <br>

                <img id="preview" class="preview" src="${requestScope.IMAGE}"/>

                <span class="span-palette" id="palette">
                    <c:forEach var="color" items="${requestScope.COLOR}">
                        <div style="background-color:${color};"></div>
                    </c:forEach>
                </span>

            </div>


            <!--        <form action="ProcessServlet">
                        <input type="submit" value="crawl" name="btAction"/>
                    </form>-->

            </br>

            <c:forEach var="canvas" items="${requestScope.CANVAS}">
                <span class="span-result">
                    <img src="${canvas.image}" height="200px" width="auto"/>
                    <p>${canvas.name}</p>
                </span>
            </c:forEach>
        </div>
    </body>
</html>

<script>
    var loadFile = function (event) {
        var preview = document.getElementById('preview');
        preview.src = URL.createObjectURL(event.target.files[0]);
        var palette = document.getElementById('palette');
        palette.style.display = 'none';
    };
</script>
