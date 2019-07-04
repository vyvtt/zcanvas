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
                <input type="radio" name="rbLocation">
                    <xsl:attribute name="id">
                        r<xsl:value-of select="id"/>
                    </xsl:attribute>
                    <xsl:attribute name="value">
                        <xsl:value-of select="id"/>
                    </xsl:attribute>
                </input>
                <label>
                    <xsl:attribute name="for">
                        r<xsl:value-of select="id"/>
                    </xsl:attribute>
                    
                    <img>
                        <xsl:attribute name="src">
                            <xsl:value-of select="image"/>
                        </xsl:attribute>
                    </img>
                    <p>
                        <xsl:value-of select="name"/>
                    </p>
                </label>
            </div>
        </xsl:for-each>
    </xsl:template>

</xsl:stylesheet>
