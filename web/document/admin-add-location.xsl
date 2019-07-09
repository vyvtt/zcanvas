<?xml version="1.0" encoding="UTF-8"?>

<!--
    Document   : admin-add-location.xsl
    Created on : July 6, 2019, 1:54 PM
    Author     : thuyv
    Description:
        Purpose of transformation follows.
-->

<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
    <xsl:output method="html"/>

    <xsl:template match="/">
        
        <link rel="stylesheet" href="../css/admin.css"/>
        <script type="text/javascript" src="../js/admin.js"></script>
        
        <form action="ProcessServlet" method="POST" enctype="multipart/form-data">
            
            <h3>Phân loại </h3> 
            <input type="text" name="txtName"/>
            
            <br/>
            
            <h3>Icon</h3> 
            <input type="file" name="imgIcon" accept="image/*"/>
            
            <br/>
            <h3>Category </h3>
            <xsl:for-each select="categories/category">
                <span>
                    <label class="label-checkbox">
                        <input type="checkbox" name="rbCategory" value="{id}"/>
                        <div>
                            <xsl:value-of select="name"/>
                        </div>
                    </label>
                </span>
            </xsl:for-each>
            
            <br/>
            
            <label class="label-submit">
                <input type="submit" value="addLocation" name="btAction"/>
                <div>Add New</div>
            </label>
        </form>
    </xsl:template>

</xsl:stylesheet>
