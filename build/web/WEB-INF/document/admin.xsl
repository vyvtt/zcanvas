<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet extension-element-prefixes="exsl" version="1.0" xmlns="http://canvas.com/vyvtt" xmlns:exsl="http://exslt.org/common" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:output method="html"/>
    
    <xsl:param name="categoriesFile"/>
    
    <xsl:template match="/">
        
        <link rel="stylesheet" href="../css/admin.css"/>
        <script type="text/javascript" src="../js/admin.js"></script>
        
        <xsl:variable name="categoriesXML" select="document($categoriesFile)"/>
        
        <!--<xsl:variable name="categoriesXML" select="document(WEB-INF/document/categories.xml)"/>-->
        <!--KO ĐƯỢC DÙNG !!!-->
        
        <xsl:for-each select="locations/location">
            
<!--            <button class="collapsible">Open Section 3</button>
            <div class="content">
                <p>Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.</p>
            </div>
            
            <div class="div-location">
                <h3>Location: 
                    <xsl:value-of select="name"/>
                </h3>
            </div>-->
            
            
            <xsl:variable name="aaa" select="categories"/>
            
            <button class="collapsible"><xsl:value-of select="name"/></button>
            <div class="content">
                <form action="ProcessServlet" method="POST">
                
                    <input type="hidden" name="locationId">
                        <xsl:attribute name="value">
                            <xsl:value-of select="id"/>
                        </xsl:attribute>
                    </input>

                    <xsl:for-each select="$categoriesXML/categories/category">
                        <xsl:variable name="currentId" select="id"/>

                        <span>
                            <label class="label-checkbox">
                                <input type="checkbox" name="category">
                                    <xsl:attribute name="value">
                                        <xsl:value-of select="id"/>
                                    </xsl:attribute>
                                    <xsl:for-each select="$aaa/category">
                                        <xsl:if test="$currentId=id">
                                            <xsl:attribute name="checked">checked</xsl:attribute>
                                        </xsl:if>
                                    </xsl:for-each>
                                </input>
                                <div>
                                    <xsl:value-of select="name"/>
                                </div>
                            </label>
                        </span>
                    </xsl:for-each>
                    
                    <br/>
                
                    <!--                <button type="submit" name="btAction" value="updateLocation">
                        Save &#8594;
                    </button>-->
                    <label class="label-submit">
                        <input type="submit" value="updateLocation" name="btAction"/>
                        <div>Save</div>
                    </label>
                </form>
            </div>
            
        </xsl:for-each>
    </xsl:template>
</xsl:stylesheet>