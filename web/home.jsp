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

        <br>

        <div class="content">
            <div align="center">
                
                <form method="POST" action="ProcessServlet" enctype="multipart/form-data">
                    <h3>Chọn loại tranh</h3>
                    <div>
                        <c:set var="lastLocation" value="${requestScope.LOCATIONVALUE}"/>

                        <c:forEach var="location" items="${sessionScope.LOCATION}">
                            <div class="div-location">
                                <c:set var="curLocation" value="${location.id}"/>
                                <input type="radio" id="r${location.id}" name="rbLocation" value="${location.id}" 
                                       ${curLocation == lastLocation ? 'checked' : ''} 
                                       />
                                <label for="r${location.id}">
                                    <p>${location.name}</p>
                                    <img src="${location.image}">
                                </label>
                            </div>
                        </c:forEach>
                    </div>

                    <br>

                    <h3>Chọn hình ảnh không gian của bạn </h3>
                    <br>
                    <input id="file" type="file" name="file" accept="image/*" onchange="loadPreviewImg(event)"/>
                    <label for="file" class="input-file-label">Choose an image</label>
                    <input id="btn-submit" type="submit" value="match" name="btAction"/>
                    <label for="btn-submit" class="input-file-label">Start</label>
                </form>

                <br>

                <img id="preview" class="preview" src="${requestScope.IMAGE}"/>

                <span class="span-palette" id="palette">
                    <c:forEach var="color" items="${requestScope.COLOR}">
                        <div style="background-color:${color};"></div>
                    </c:forEach>
                </span>

                <br><br><br>

                <c:set var="total" value="${requestScope.TOTAL}"/>
                <c:if test="${not empty total}">
                    <div>Tìm thấy <b>${total}</b> sản phẩm phù hợp</div>
                </c:if>

                <br/>
                <br/>

                <div class="div-category">
                    <input type="radio" id="c0" name="rbCategory" value="0" checked="checked"/>
                    <label for="c0" onclick="renderCanvasNew('0');">
                        <p>Tất cả</p>
                    </label>
                </div>
                <c:forEach var="category" items="${requestScope.TEST_CATEGORIES}">
                    <div class="div-category">
                        <input type="radio" id="c${category.id}" name="rbCategory" value="${category.id}"/>
                        <label for="c${category.id}"
                               onclick="renderCanvasNew('${category.id}');">
                            <p>${category.name}</p>
                        </label>
                    </div>
                </c:forEach>

            </div>
            <br/>
            <br/>

            <div id="result" />

            <c:set var="a" value="${requestScope.TEST_XML}"/>
            <c:if test="${not empty a}">
                <script>initDocument('${a}', '${pageContext.servletContext.contextPath}');</script>
                <script>renderCanvasNew('0');</script>
            </c:if>
        </div>


    </body>

    <script type="text/javascript" src="js/home.js"></script>
</html>
