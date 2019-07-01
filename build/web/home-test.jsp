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
        <script type="text/javascript" src="js/home-test.js"></script>
        <title>Home</title>
    </head>
    <body>

        <div class="logo" id="logo">
            <img src="image/logo.png"/>
        </div>

        <br>

        <div class="content">
            <div align="center">
                <span id="span-loading" style="display: none;">Loading . . .</span>
                <form method="POST" action="ProcessServlet" enctype="multipart/form-data" id="form">
                    <h3>Chọn loại tranh</h3>
                    <div class="div-location-wrap" id="test"></div>

                    <br>

                    <h3>Chọn hình ảnh không gian của bạn </h3>
                    <br>
                    <input id="file" type="file" name="file" accept="image/*" onchange="loadPreviewImg(event)"/>
                    <label for="file" class="input-file-label">Choose an image</label>
                    <input id="btn-submit" type="submit"/>
                    <label for="btn-submit" class="input-file-label">Start</label>
                    <input type="hidden" value="match" name="btAction"/>
                </form>

                <br>

                <img id="preview" class="preview" src="${requestScope.IMAGE}"/>


                <span class="span-palette" id="palette">
                    <c:forEach var="color" items="${requestScope.COLOR}">
                        <div style="background-color:${color};"></div>
                    </c:forEach>
                </span>

                <br/>
                <br/>

                <div id="div-category-wrap"></div>
            </div>

            <div class="div-result-wrap">
                <div id="result" />
            </div>   

        </div>

    </body>

    <script type="text/javascript" src="js/utils.js"></script>
    <script type="text/javascript" src="js/home-test.js"></script>
</html>
