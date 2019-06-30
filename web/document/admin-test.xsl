<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet 
                version="1.0" 
                xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:output method="html"/>
    
    <xsl:param name="categoriesFile"/>
    <xsl:param name="a"/>
    
    
    
    <xsl:template match="/">
        
        
        
        <link rel="stylesheet" href="../css/admin.css"/>
        <script type="text/javascript" src="../js/admin.js"></script>
        
        param : <xsl:value-of select="$categoriesFile"/>
        
        <xsl:variable name="categoriesXML" select="document($categoriesFile)/*"/>
        
        variable: <xsl:value-of select="$categoriesXML"/>
        
        copy: <xsl:copy-of select="document('file:///d:/categories.xml')/*"/>
        
        <xsl:for-each select="locations/location">
            
            <xsl:variable name="aaa" select="categories"/>
            
            <button class="collapsible">
                <xsl:value-of select="name"/>
            </button>
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

                    <label class="label-submit">
                        <input type="submit" value="updateLocation" name="btAction"/>
                        <div>Save</div>
                    </label>
                </form>
            </div>
            
        </xsl:for-each>
    </xsl:template>
</xsl:stylesheet>