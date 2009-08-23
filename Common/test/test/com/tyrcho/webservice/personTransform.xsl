<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:fo="http://www.w3.org/1999/XSL/Format">
	<xsl:output method="text" />
	<xsl:template match="//return">
		<xsl:value-of select="fullName" />
		
	</xsl:template>
	
</xsl:stylesheet>
