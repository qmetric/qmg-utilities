<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
    xmlns:xs="http://www.w3.org/2001/XMLSchema" exclude-result-prefixes="xs" version="2.0">
    <xd:doc xmlns:xd="http://www.oxygenxml.com/ns/doc/xsl" scope="stylesheet">
        <xd:desc>
            <xd:p><xd:b>Created on:</xd:b> May 4, 2011</xd:p>
            <xd:p><xd:b>Author:</xd:b> Mike</xd:p>
            <xd:p>Property Sets for Policy Schedule</xd:p>
            <xd:p>MK20052011 - Removed Policy Issue Date from Property Info section</xd:p>
        </xd:desc>
    </xd:doc>
    
    <xsl:attribute-set name="base.font">
        <xsl:attribute name="font-family">Arial</xsl:attribute>
        <xsl:attribute name="color">#404040</xsl:attribute>  
    </xsl:attribute-set>
    
    <xsl:attribute-set name="document.title.table">
        <xsl:attribute name="height">42mm</xsl:attribute>
    </xsl:attribute-set>
        
    <xsl:attribute-set name="document.title" use-attribute-sets="base.font">
        <xsl:attribute name="font-size">24pt</xsl:attribute>
        <xsl:attribute name="line-height">26pt</xsl:attribute>
        <xsl:attribute name="font-weight">bold</xsl:attribute>
        <xsl:attribute name="margin-top">14mm</xsl:attribute>
    </xsl:attribute-set>
    
    <xsl:attribute-set name="document.sub-title" use-attribute-sets="base.font">
        <xsl:attribute name="font-size">24pt</xsl:attribute>
        <xsl:attribute name="line-height">26pt</xsl:attribute>
    </xsl:attribute-set>
    
    <xsl:attribute-set name="reference.account-number.label" use-attribute-sets="base.font">
        <xsl:attribute name="font-size">8pt</xsl:attribute>
        <xsl:attribute name="text-align">center</xsl:attribute>
        <xsl:attribute name="color">#FFFFFF</xsl:attribute>
    </xsl:attribute-set>
    
    <xsl:attribute-set name="reference.account-number.num" use-attribute-sets="base.font">
        <xsl:attribute name="font-size">14pt</xsl:attribute>
        <xsl:attribute name="font-weight">bold</xsl:attribute>
        <xsl:attribute name="text-align">center</xsl:attribute>
        <xsl:attribute name="color">#FFFFFF</xsl:attribute>
        <xsl:attribute name="space-before">1mm</xsl:attribute>
    </xsl:attribute-set>
    
    <xsl:attribute-set name="reference.policy-number.label" use-attribute-sets="reference.account-number.label"/>
    
    <xsl:attribute-set name="reference.policy-number.num" use-attribute-sets="reference.account-number.num"/>
    
    <xsl:attribute-set name="table">
        <xsl:attribute name="space-after">9mm</xsl:attribute>
        <xsl:attribute name="text-align">left</xsl:attribute>
    </xsl:attribute-set>
    
    <xsl:attribute-set name="table.title" use-attribute-sets="base.font">
        <xsl:attribute name="background-color">#CCCCCC</xsl:attribute>
        <xsl:attribute name="font-size">14pt</xsl:attribute>
        <xsl:attribute name="line-height">14pt</xsl:attribute>
        <xsl:attribute name="padding-left">4mm</xsl:attribute>
        <xsl:attribute name="padding-right">4mm</xsl:attribute>
        <xsl:attribute name="padding-top">2mm</xsl:attribute>
        <xsl:attribute name="padding-bottom">2mm</xsl:attribute>        
    </xsl:attribute-set>
    
    <xsl:attribute-set name="table.sub-title" use-attribute-sets="base.font">
        <xsl:attribute name="background-color">#CCCCCC</xsl:attribute>
        <xsl:attribute name="padding-left">4mm</xsl:attribute>
        <xsl:attribute name="padding-right">4mm</xsl:attribute>
        <xsl:attribute name="font-size">8pt</xsl:attribute>
        <xsl:attribute name="font-weight">bold</xsl:attribute>
    </xsl:attribute-set>
    
    <xsl:attribute-set name="table.header" use-attribute-sets="base.font">
        <xsl:attribute name="background-color">#CCCCCC</xsl:attribute>
        <xsl:attribute name="font-size">9pt</xsl:attribute>
        <!--<xsl:attribute name="font-weight">bold</xsl:attribute>-->
        <xsl:attribute name="padding-left">4mm</xsl:attribute>
        <xsl:attribute name="padding-right">4mm</xsl:attribute>        
        <xsl:attribute name="padding-bottom">2mm</xsl:attribute>
        <xsl:attribute name="padding-top">2mm</xsl:attribute>
    </xsl:attribute-set>
    
    <xsl:attribute-set name="table.header.icons">
        <xsl:attribute name="text-align">center</xsl:attribute>
        <xsl:attribute name="padding-left">0mm</xsl:attribute>
        <xsl:attribute name="padding-right">0mm</xsl:attribute>        
        <xsl:attribute name="padding-bottom">1mm</xsl:attribute>
        <xsl:attribute name="padding-top">0mm</xsl:attribute>
    </xsl:attribute-set>
    
    <xsl:attribute-set name="table.header.icons.world">
        <xsl:attribute name="text-align">left</xsl:attribute>
        <xsl:attribute name="padding-bottom">1mm</xsl:attribute>
        <xsl:attribute name="padding-top">0mm</xsl:attribute>
    </xsl:attribute-set>
    
    <xsl:attribute-set name="table.row">

    </xsl:attribute-set>
    
    <xsl:attribute-set name="table.row.odd" use-attribute-sets="table.row">
        <xsl:attribute name="background-color">#F2F2F2</xsl:attribute>
    </xsl:attribute-set>
    
    <xsl:attribute-set name="table.row.even" use-attribute-sets="table.row">
        <xsl:attribute name="background-color">#E6E6E6</xsl:attribute>
    </xsl:attribute-set>
    
    <xsl:attribute-set name="table.entry" use-attribute-sets="base.font">
        <xsl:attribute name="font-size">8pt</xsl:attribute>
        <xsl:attribute name="line-height">10pt</xsl:attribute>
        <xsl:attribute name="padding-left">4mm</xsl:attribute>   
        <xsl:attribute name="padding-right">4mm</xsl:attribute> 
        <xsl:attribute name="padding-top">0.4em</xsl:attribute>
        <xsl:attribute name="padding-bottom">0.4em</xsl:attribute>
    </xsl:attribute-set>
        
    <xsl:attribute-set name="emphasis">
        <xsl:attribute name="font-weight">bold</xsl:attribute>
    </xsl:attribute-set>
    
    <xsl:attribute-set name="contact-details">
        <xsl:attribute name="space-before">0.75em</xsl:attribute>
    </xsl:attribute-set>
    
    <xsl:attribute-set name="contact-details.question" use-attribute-sets="base.font">
        <xsl:attribute name="font-size">15pt</xsl:attribute>
        <xsl:attribute name="line-height">17pt</xsl:attribute>
    </xsl:attribute-set>
    
    <xsl:attribute-set name="contact-details.call" use-attribute-sets="base.font">
        <xsl:attribute name="font-size">11.5pt</xsl:attribute>
        <xsl:attribute name="line-height">14pt</xsl:attribute>
        <xsl:attribute name="color">#808080</xsl:attribute>
    </xsl:attribute-set>
    
    <xsl:attribute-set name="personal-possessions.key">
        <xsl:attribute name="text-align">right</xsl:attribute>
    </xsl:attribute-set>
    
    <xsl:attribute-set name="list" use-attribute-sets="base.font">
        <xsl:attribute name="provisional-distance-between-starts">4mm</xsl:attribute>
        <xsl:attribute name="provisional-label-separation">1mm</xsl:attribute>
    </xsl:attribute-set>    
    
    <xsl:attribute-set name="list.item-label" use-attribute-sets="base.font">
        <xsl:attribute name="text-align">left</xsl:attribute>
        <xsl:attribute name="end-indent">label-end()</xsl:attribute>
    </xsl:attribute-set>
    
    <xsl:attribute-set name="list.item-body" use-attribute-sets="base.font">
        <xsl:attribute name="start-indent">body-start()</xsl:attribute>
    </xsl:attribute-set>
    
    <xsl:attribute-set name="footer" use-attribute-sets="base.font">
        <xsl:attribute name="font-size">7.5pt</xsl:attribute>
        <xsl:attribute name="color">white</xsl:attribute>
        <xsl:attribute name="text-align">center</xsl:attribute>
        <xsl:attribute name="margin-bottom">12mm</xsl:attribute>
    </xsl:attribute-set>
        
</xsl:stylesheet>
