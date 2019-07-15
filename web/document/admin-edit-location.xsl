<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet extension-element-prefixes="exsl" version="1.0" xmlns="http://canvas.com/vyvtt" xmlns:exsl="http://exslt.org/common" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:output method="html"/>
    
    <xsl:param name="categoriesFile"/>
    
    <xsl:template match="/">
        
        <xsl:variable name="categoriesXML" select="document($categoriesFile)"/>
        <!--<xsl:variable name="categoryDoc" select="document('WEB-INF/document/categories.xml')/*"/>-->
        
        <!--aaa <xsl:copy-of select="document('WEB-INF/document/categories.xml')/categories/category/id"/>-->
        
        <link rel="stylesheet" href="../css/admin.css"/>
        <script type="text/javascript" src="../js/admin.js"></script>
        <script type="text/javascript" src="../js/utils.js"></script>

        <xsl:for-each select="locations/location">           
            
            <xsl:variable name="aaa" select="categories"/>
            
            <button class="collapsible">
                <xsl:value-of select="name"/>
            </button>
            <div class="content">
                <form action="ProcessServlet" method="POST">
                
                    <input type="hidden" name="txtLocationId" value="{id}"/>
                    <input type="hidden" name="txtOldLocationName" value="{name}"/>
                    
                    <h3>Phân loại </h3> 
                    <input type="text" name="txtLocationName" value="{name}"/>
                    
                    <br/>
                    <br/>

                    <h3>Category </h3>
                    <xsl:for-each select="$categoriesXML/categories/category">
                        <!--<xsl:for-each select="$categoryDoc//category">-->
                        <xsl:variable name="currentId" select="id"/>

                        <span>
                            <label class="label-checkbox">
                                <input type="checkbox" name="category" value="{id}">
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
                    
                    <label id="lbDelete" class="label-delete">Delete
                        <input type="submit" value="deleteLocation" name="btAction"/>
                    </label>
                </form>
            </div>
            
        </xsl:for-each>
    </xsl:template>
</xsl:stylesheet>