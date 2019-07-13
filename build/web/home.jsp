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
            <img src="image/logo.png"/>
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
                            <label for="btn-submit" class="input-submit-label">Start</label>
                        </div>

                        <input type="hidden" value="match" name="btAction"/>
                    </div>

                    <div class="div-right">

                        <span id="span-type-image" style="display: none;">
                            <input id="file" type="file" name="mFile" accept="image/*" onchange="loadPreviewImg(event)"/>
                            <label for="file" class="input-file-label">Choose an image</label>
                        </span>

                        <span id="span-type-color" style="display: none;">                            
                            <!--<input id="color" type="color" name="mColor" onchange="loadPreviewColor(this)"/>-->
                            <!--<label for="color" class="input-file-label">Choose a color</label>-->
                        </span>

                        <div id="closePreview" class="div-close" style="display: none;">
                            <img src="image/close.svg"/>
                        </div>
                        <br style="clear: both;">

                        <span id="span-preview-image" style="display: none;">
                            <img id="previewImage" class="preview" src="image/placeholder.png"/>
                            <span class="span-palette" id="palette"></span>
                        </span>

                        <span id="previewColor" class="previewColor"/>
                    </div>

                </form>
                <br style="clear: both;">
            </div>

            <br/>

            <div style="text-align: center">
                <div id="div-category-wrap"></div>
                <br style="clear: both;">
                <div id="div-page-wrap"></div>
                <br style="clear: both;">
            </div>


            <div class="div-result-wrap">
                <div id="result"></div>
            </div>
        </div>

        <br/>

        <div class="div-spotlight">

            <p>Today's spotlight Palette</p>

            <c:set var="pImg" value="${requestScope.DAILY_IMG}"/>
            <c:if test="${not empty pImg}">
                <img class="spotlight-img" src="${pImg}" height="auto" width="auto"/>
            </c:if>

            <c:set var="pColor" value="${requestScope.DAILY_COLORS}"/>
            <c:if test="${not empty pColor}">
                <div class="spotlight-palatte-wrap">
                    <c:forEach var="color" items="${pColor}">
                        <span class="spotlight-palatte" style="background-color: ${color};"></span>
                    </c:forEach>
                </div>
            </c:if>    

            <!--<br style="clear: both;"/>-->

            <div class="slideshow-container">

                <c:set var="pCanvas" value="${requestScope.DAILY_CANVAS}"/>
                <c:if test="${not empty pCanvas}">
                    <c:forEach var="canvas" items="${pCanvas}">
                        <div class="mySlides fade">
                            <img  src='${canvas.image}'/>
                            <div>${canvas.name}</div>
                        </div>
                    </c:forEach>
                </c:if>  
                
                <a class="prev" onclick='plusSlides(-1)'>&#10094;</a>
                <a class="next" onclick='plusSlides(1)'>&#10095;</a>
            </div>
            <br/>

<!--            <div style='text-align: center;'>
                <span class="dot" onclick='currentSlide(1)'></span>
                <span class="dot" onclick='currentSlide(2)'></span>
                <span class="dot" onclick='currentSlide(3)'></span>
                <span class="dot" onclick='currentSlide(4)'></span>
            </div>-->

        </div>

    </div>

</body>

<script type="text/javascript" src="js/utils.js"></script>
<script type="text/javascript" src="js/home.js"></script>
</html>
