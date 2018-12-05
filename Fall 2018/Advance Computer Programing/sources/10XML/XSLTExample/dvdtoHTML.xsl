<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
  <xsl:output method="html" version="4.0"/>
  <xsl:template match="/">
    <html>
      <head>
        <title>DVD Library Listing</title>
        <link rel="stylesheet" type="text/css" href="style.css"/>
      </head>
      <body>


Here is a table of DVDs and CDs
	<table border="1">
	  <tr>
	    <th>Title</th>
	    <th>Format</th>
	    <th>Genre</th>
	  </tr>
	  <xsl:for-each select="/library/DVD">
	    <xsl:sort select="title"/>
	    <tr>
	      <td><xsl:value-of select="title"/></td>
	      <td><xsl:value-of select="format"/></td>
	      <td><xsl:value-of select="genre"/></td>
	    </tr>
	  </xsl:for-each>
	</table>
      </body>
    </html>
  </xsl:template>
</xsl:stylesheet>
