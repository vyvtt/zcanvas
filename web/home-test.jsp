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

        <div class="content">
            <div class="div-form">

                <div class="div-left" id="div-loading">
                    <img src="image/loading.gif"/>
                </div>

                <div class="div-left" id="div-form">
                    <form method="POST" action="ProcessServlet" enctype="multipart/form-data" id="form" name="mForm">

                        <h3>Chọn loại tranh</h3>
                        <span class="span-error" id="errLocation"></span>
                        <div class="div-location-wrap" id="test"></div>

                        <br>

                        <h3>Chọn hình ảnh không gian của bạn </h3>

                        <span class="span-error" id="errImage"></span>
                        <input id="file" type="file" name="mFile" accept="image/*" onchange="loadPreviewImg(event)"/>
                        <label for="file" class="input-file-label">Choose an image</label>
                        <input id="btn-submit" type="submit"/>
                        <br/><br/>
                        <div>
                            <label for="btn-submit" class="input-submit-label">Start</label>
                        </div>

                        <input type="hidden" value="match" name="btAction"/>

                    </form>
                </div>

                <div class="div-right">
                    <img id="previewImage" class="preview" src="image/placeholder.png"/>
                    <span class="span-palette" id="palette"></span>
                </div>

                <br style="clear: both;">
            </div>

            <br/><br/><br/>

            <div id="div-category-wrap"></div>

            <div class="div-result-wrap">
                <div id="result"></div>
            </div>
        </div>

    </body>

    <script type="text/javascript" src="js/utils.js"></script>
    <script type="text/javascript" src="js/home-test.js"></script>
</html>
