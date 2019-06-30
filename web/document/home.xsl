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

    <xsl:template match="/">
        
        <link rel="stylesheet" href="../css/home.css"/>
        
        <br/>
        
        <xsl:choose>
            <xsl:when test="$categoryId = '0'">
                <xsl:for-each select="my:canvases/my:canvas">
                    <!--<xsl:apply-templates/>-->
                    <xsl:call-template name="test"/>
                </xsl:for-each>
            </xsl:when>
            <xsl:otherwise>
                <xsl:for-each select="my:canvases/my:canvas[my:canvasCategories = $categoryId]">
                    <!--<xsl:apply-templates/>-->
                    <xsl:call-template name="test"/>
                </xsl:for-each>
            </xsl:otherwise>
        </xsl:choose>

    </xsl:template>
    
    <xsl:template name="test">
        <span class="span-result">
            <img>
                <xsl:attribute name="src">
                    <xsl:value-of select="my:image"/>
                </xsl:attribute>
            </img>
            <span class="span-palette-small">
                <xsl:for-each select="my:canvasColors">
                    <div>
                        <xsl:attribute name="style">background-color:<xsl:value-of select="."/>;</xsl:attribute>
                    </div>
                </xsl:for-each>

            </span>
            <p>
                <xsl:value-of select="my:name"/>
            </p>
        </span>
    </xsl:template>

</xsl:stylesheet>
