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
        <script type="text/javascript" src="js/home.js"></script>
        <title>Home</title>
    </head>
    <body>

        <div class="logo" id="logo">
            <a href="http://localhost:8084/ZCanvas/">
                <img src="image/logo.png"/>
            </a>
        </div>

        <div class="content">
            <div class="div-form">

                <div class="div-left" id="div-loading">
                    <img src="image/loading.gif"/>
                </div>

                <form method="POST" action="ProcessServlet" enctype="multipart/form-data" id="form" name="mForm">

                    <div class="div-left" id="div-form">
                        <h3>Chọn loại tranh</h3>
                        <span class="span-error" id="errLocation"></span>
                        <div class="div-location-wrap" id="test"></div>

                        <br style="clear: both;">
                        <br/>
                        <br/>

                        <h3>Chọn màu sắc</h3>

                        <span class="span-error" id="errImage"></span>

                        <div class="div-type">
                            <input id="type1Image" type="radio" name="rbType" value="typeImage" checked="checked"/>
                            <label id="lbTypeImage" for="type1Image" class="label-type">Upload hình ảnh</label>

                            <input id="type1Color" type="radio" name="rbType" value="type1Color"/>
                            <label id="lbTypeColor" for="type1Color" class="label-type">Chọn màu đơn</label>
                        </div>

                        <br/>

                        <input id="btn-submit" type="submit"/>
                        <div>
                            <label for="btn-submit" class="input-submit-label">Tìm kiếm</label>
                        </div>

                        <input type="hidden" value="match" name="btAction"/>
                    </div>

                    <div class="div-right">

                        <span id="span-type-image" style="display: none;">
                            <input id="file" type="file" name="mFile" accept="image/*" onchange="loadPreviewImg(event)"/>
                            <label for="file" class="input-file-label">Choose an image</label>
                        </span>

                        <span id="span-type-color" style="display: none;"></span>
                        <div id="previewColor" class="span-preview-color"></div>

                        <div id="closePreview" class="div-close" style="display: none;">
                            <img src="image/close.svg"/>
                        </div>
                        <br style="clear: both;"/>

                        <span id="span-preview-image" style="display: none;">
                            <img id="previewImage" class="preview" src="image/placeholder.png"/>
                            <span class="span-palette" id="palette"></span>
                        </span>

                        <!--<span id="previewColor" class="previewColor"/>-->
                    </div>

                </form>
                <br style="clear: both;">
            </div>

            <br/>

            <div style="text-align: center">
                <div id="div-category-wrap" class="m-left-right"></div>
                <br style="clear: both;">
                <div id="div-page-wrap" class="m-left-right"></div>
                <br style="clear: both;">
            </div>


            <div class="div-result-wrap">
                <div id="result"></div>
            </div>
        </div>

        <br/>

        <div id="spotlight"></div>

    </div>

</body>
<footer>
    <form method="GET" action="ProcessServlet">
        <input type="hidden" value="admin" name="btAction"/>
        <input id="btn-admin" type="submit"/>
        <label for="btn-admin" class="input-submit-label-admin">Admin</label>
    </form>
</footer>

<script type="text/javascript" src="js/utils.js"></script>
<script type="text/javascript" src="js/home.js"></script>
</html>
