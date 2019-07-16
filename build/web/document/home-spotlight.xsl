<?xml version="1.0" encoding="UTF-8"?>

<!--
    Document   : home-spotlight.xsl
    Created on : July 13, 2019, 9:33 PM
    Author     : thuyv
    Description:
        Purpose of transformation follows.
-->

<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0" 
                xmlns:my="http://canvas.com/vyvtt" exclude-result-prefixes="my">
    <xsl:output method="html"/>

    <xsl:template match="/">
        
        <link rel="stylesheet" href="../css/home.css"/>
        <div class="div-spotlight">
            <h3>Today's spotlight Palette</h3>
        
            <span class="spotlight-img-wrap">
                <img class="spotlight-img" src="{my:spotlight/my:paletteImg}" height="auto" width="auto"/>
                <p class="spotlight-img-description">
                    <!--<xsl:value-of select="my:spotlight/my:imgName"/>-->
                    <!--<br/>-->
                    Image by <b><xsl:value-of select="my:spotlight/my:imgAuth"/></b> 
                    at <a href="{my:spotlight/my:imgLink}" target="_blank">Unsplash</a> 
                    <br/>
                </p>
            </span>
        
            <!--<img class="spotlight-img" src="{my:spotlight/my:paletteImg}" height="auto" width="auto"/>-->
        
            <div class="spotlight-palatte-wrap">
                <xsl:for-each select="my:spotlight/my:paletteColor">
                    <span class="spotlight-palatte" style="background-color: {.};"></span>
                </xsl:for-each>
            </div>
        
            <div class="slideshow-container">
                <xsl:for-each select="my:spotlight/my:canvases/my:canvas">
                    <div class="mySlides animate-fading">
                        <img src="{my:image}"/>
                        <p><xsl:value-of select="my:name"/></p>
                    </div>
                </xsl:for-each>  
                <a class="prev" onclick='plusSlides(-1)'>&#10094;</a>
                <a class="next" onclick='plusSlides(1)'>&#10095;</a>
            </div>
        </div>
    </xsl:template>

</xsl:stylesheet>
