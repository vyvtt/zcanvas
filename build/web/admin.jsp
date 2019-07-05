<%-- 
    Document   : admin
    Created on : Jun 26, 2019, 11:19:27 AM
    Author     : thuyv
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/xml" prefix="x"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="f" %>  
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="css/admin.css"/>
        <script type="text/javascript" src="js/admin.js"></script>
        <script type="text/javascript" src="js/utils.js"></script>
        <title>Admin</title>
    </head>

    <body>

        <div class="logo" id="logo">
            <img src="image/logo.png"/>
        </div>

        <br>



        <div class="div-content">

            <ul>
                <li id="li-crawl" class="nav-active" onclick="showCrawl()">Crawl Data</li>
                <li id="li-add" onclick="showAdd()">Add New Category</li>
                <li id="li-edit" onclick="showEdit()">Edit Category</li>
            </ul>

            <div id="item-crawl" class="div-item">
                <label id="lbCrawl" class="label-crawl" onclick="crawData()">Crawl</label>
                <img id="imgCrawl" src="image/loading.gif" width="70px" height="70px" style="display: none;"/>
            </div>

            <div id="item-edit" class="div-item">
                <c:set var="xmlLocation" value="${requestScope.XML_LOCATION}"/>

                <c:if test="${not empty xmlLocation}">
                    <c:import var="xsl" url="document/admin.xsl" charEncoding="UTF-8"/>
                    <x:transform doc="${xmlLocation}" xslt="${xsl}">
                        <x:param name="categoriesFile" value="WEB-INF/document/categories.xml"/>
                    </x:transform>
                </c:if>
            </div>

            <div id="item-add" class="div-item">
                add
            </div>
        </div>
    </body>


</html>
