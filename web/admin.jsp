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
            <c:set var="xmlLocation" value="${requestScope.XML_LOCATION}"/>

            <c:if test="${not empty xmlLocation}">
                <c:import var="xsl" url="WEB-INF/document/admin.xsl" charEncoding="UTF-8"/>
                <x:transform doc="${xmlLocation}" xslt="${xsl}">
                    <x:param name="categoriesFile" value="WEB-INF/document/categories.xml"/>
                </x:transform>
            </c:if>
        </div>
    </body>


</html>
