<?xml version="1.0" encoding="UTF-8"?>

<!--
    Document   : home-location.xsl
    Created on : July 1, 2019, 10:43 AM
    Author     : thuyv
    Description:
        Purpose of transformation follows.
-->

<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
    <xsl:output method="html"/>
    <xsl:strip-space elements="*"/>
    
    <xsl:template match="/">
        <link rel="stylesheet" href="../css/home.css"/>
        <xsl:for-each select="locations/location">
            <div class="div-location">
                <input type="radio" name="rbLocation" id="r{id}" value="{id}">
                </input>
                <label for="r{id}">
                    <img src="{image}"/>
                    <p><xsl:value-of select="name"/></p>
                </label>
            </div>
        </xsl:for-each>
    </xsl:template>

</xsl:stylesheet>
