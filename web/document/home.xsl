<?xml version="1.0" encoding="UTF-8"?>

<!--
    Document   : home.xsl
    Created on : June 30, 2019, 8:04 AM
    Author     : thuyv
    Description:
        Purpose of transformation follows.
-->

<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" 
                version="1.0" xmlns:my="http://canvas.com/vyvtt" exclude-result-prefixes="my">
    <xsl:output method="html"/>
    <xsl:strip-space elements="/"/>
    
    <xsl:param name="categoryId" select="categoryId"/>
    <xsl:param name="currentPage" select="currentPage"/>
    <xsl:param name="pageSize" select="pageSize"/>

    <xsl:template match="/">
        <link rel="stylesheet" href="../css/home.css"/>
        
        <br/>
        
        <xsl:choose>
            <xsl:when test="$categoryId = '0'">
                <xsl:for-each select="my:canvases/my:canvas">
                    <xsl:call-template name="CanvasHTML"/>
                </xsl:for-each>
            </xsl:when>
            <xsl:otherwise>
                <xsl:for-each select="my:canvases/my:canvas[my:canvasCategories = $categoryId]">
                    <xsl:call-template name="CanvasHTML"/>
                </xsl:for-each>
            </xsl:otherwise>
        </xsl:choose>
    </xsl:template>
    
    <xsl:template name="CanvasHTML">
        
        <xsl:if test="position() &gt;= ($currentPage * $pageSize) + 1">
            <xsl:if test="position() &lt;= $pageSize + ($currentPage * $pageSize)">
                <span class="span-result">
                    <img src="{my:image}"/>
                    <span class="span-palette-small">
                        <xsl:for-each select="my:canvasColors">
                            <div style="background-color:{.};"/>
                        </xsl:for-each>
                    </span>
                    <p><xsl:value-of select="my:name"/></p>
                </span>
            </xsl:if>
        </xsl:if>
    </xsl:template>

</xsl:stylesheet>
