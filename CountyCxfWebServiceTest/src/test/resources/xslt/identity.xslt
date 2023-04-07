<?xml version='1.0'?>
<xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

    <xsl:output media-type="xml" version="1.0" indent="yes" encoding="UTF-8" />
    <xsl:strip-space elements="*"/>
  
  	<!-- 
	<xsl:template match="*[namespace-uri()='urn:us:gov:treasury:irs:ext:aca:air:ty22']" priority="1">
	    <xsl:element name="{local-name()}" namespace="urn:us:gov:treasury:irs:ext:aca:air:ty23">
	      <xsl:apply-templates select="@*|node()"/>
	    </xsl:element>
	</xsl:template>
  	-->
  	
    <xsl:template match="@*|node()">
        <xsl:copy>
            <xsl:apply-templates select="@*|node()" />
        </xsl:copy>
    </xsl:template>

</xsl:stylesheet>