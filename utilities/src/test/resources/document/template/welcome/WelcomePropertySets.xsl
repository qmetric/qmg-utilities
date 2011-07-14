<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:xs="http://www.w3.org/2001/XMLSchema" exclude-result-prefixes="xs" version="2.0">
    <xd:doc xmlns:xd="http://www.oxygenxml.com/ns/doc/xsl" scope="stylesheet">
        <xd:desc>
            <xd:p><xd:b>Created on:</xd:b> May 10, 2011</xd:p>
            <xd:p><xd:b>Author:</xd:b> Mike</xd:p>
            <xd:p>Welcome Letter styles</xd:p>
        </xd:desc>
    </xd:doc>
    
    <xsl:attribute-set name="base.font">
        <xsl:attribute name="font-family">Arial</xsl:attribute>
        <xsl:attribute name="color">#000000</xsl:attribute>  
    </xsl:attribute-set>
    
    <xsl:attribute-set name="document.title" use-attribute-sets="base.font">
        <xsl:attribute name="font-size">22pt</xsl:attribute>
        <xsl:attribute name="line-height">24pt</xsl:attribute>
        <xsl:attribute name="color">#5B4B9F</xsl:attribute>
    </xsl:attribute-set>
    
    <xsl:attribute-set name="document.title.bold" use-attribute-sets="base.font">
        <xsl:attribute name="font-weight">bold</xsl:attribute>
        <xsl:attribute name="color">#CF028D</xsl:attribute>
    </xsl:attribute-set>
    
    <xsl:attribute-set name="recipient">
        <xsl:attribute name="padding-top">22mm</xsl:attribute>
    </xsl:attribute-set>
    
    <xsl:attribute-set name="recipient.name" use-attribute-sets="base.font">
        <xsl:attribute name="font-size">11pt</xsl:attribute>
        <xsl:attribute name="line-height">12.5pt</xsl:attribute>          
        <xsl:attribute name="space-after">1em</xsl:attribute>
        <xsl:attribute name="font-weight">bold</xsl:attribute> 
    </xsl:attribute-set>
    
    <xsl:attribute-set name="recipient.address" use-attribute-sets="base.font">
        <xsl:attribute name="font-size">9.5pt</xsl:attribute>
        <xsl:attribute name="line-height">12pt</xsl:attribute>
        <xsl:attribute name="font-weight">bold</xsl:attribute>
    </xsl:attribute-set>
    
    <xsl:attribute-set name="salutation" use-attribute-sets="base.font">
        <xsl:attribute name="font-size">9pt</xsl:attribute>
        <xsl:attribute name="line-height">9pt</xsl:attribute>
        <xsl:attribute name="font-weight">bold</xsl:attribute>
        <xsl:attribute name="padding-top">0.5em</xsl:attribute>
    </xsl:attribute-set>
    
    <xsl:attribute-set name="letter-body" use-attribute-sets="base.font">
        <xsl:attribute name="font-size">9pt</xsl:attribute>
        <xsl:attribute name="line-height">11pt</xsl:attribute>
    </xsl:attribute-set>
    
    <xsl:attribute-set name="reference" use-attribute-sets="base.font">
        <xsl:attribute name="text-align">right</xsl:attribute>
    </xsl:attribute-set>
    
    <xsl:attribute-set name="paragraph" use-attribute-sets="base.font">
        <xsl:attribute name="space-after">1.5em</xsl:attribute>
    </xsl:attribute-set>
    
    <xsl:attribute-set name="url" use-attribute-sets="base.font">
        <xsl:attribute name="color">#CF028D</xsl:attribute>
    </xsl:attribute-set>
    
    <xsl:attribute-set name="emphasis" use-attribute-sets="base.font">
        <xsl:attribute name="color">#CF028D</xsl:attribute>
        <xsl:attribute name="font-weight">bold</xsl:attribute>
    </xsl:attribute-set>
    
    <xsl:attribute-set name="table.title" use-attribute-sets="base.font">
        <xsl:attribute name="font-size">16pt</xsl:attribute>
        <xsl:attribute name="line-height">20pt</xsl:attribute>
        <xsl:attribute name="color">#5B4B9F</xsl:attribute>
        <xsl:attribute name="border-bottom">1pt solid #5B4B9F</xsl:attribute>
    </xsl:attribute-set>
    
    <xsl:attribute-set name="table" use-attribute-sets="base.font">
        <xsl:attribute name="font-size">7.5pt</xsl:attribute>
        <xsl:attribute name="space-before">2em</xsl:attribute>
    </xsl:attribute-set>
    
    <xsl:attribute-set name="table.header" use-attribute-sets="base.font">
        <xsl:attribute name="background-color">#C5E5F7</xsl:attribute>
        <xsl:attribute name="line-height">7.5pt</xsl:attribute>
        <xsl:attribute name="font-weight">bold</xsl:attribute>
        <xsl:attribute name="padding-left">2mm</xsl:attribute>
        <xsl:attribute name="padding-right">2mm</xsl:attribute>        
        <xsl:attribute name="padding-bottom">1.5mm</xsl:attribute>
        <xsl:attribute name="padding-top">3.5mm</xsl:attribute>
        <xsl:attribute name="display-align">after</xsl:attribute>
    </xsl:attribute-set>
    
    <xsl:attribute-set name="table.header.center" use-attribute-sets="base.font">
        <xsl:attribute name="text-align">center</xsl:attribute>
    </xsl:attribute-set>
    
    <xsl:attribute-set name="table.row.odd">
        <xsl:attribute name="background-color">#F2F2F2</xsl:attribute>
    </xsl:attribute-set>
    
    <xsl:attribute-set name="table.row.even">
        <xsl:attribute name="background-color">#E6E6E6</xsl:attribute>
    </xsl:attribute-set>
    
    <xsl:attribute-set name="table.entry" use-attribute-sets="base.font">
        <xsl:attribute name="line-height">8pt</xsl:attribute>
        <xsl:attribute name="padding-left">2mm</xsl:attribute>   
        <xsl:attribute name="padding-right">2mm</xsl:attribute> 
        <xsl:attribute name="padding-top">0.7em</xsl:attribute>
        <xsl:attribute name="padding-bottom">0.6em</xsl:attribute>
    </xsl:attribute-set>
    
    <xsl:attribute-set name="footer" use-attribute-sets="base.font">
        <xsl:attribute name="font-size">7.5pt</xsl:attribute>
        <xsl:attribute name="color">white</xsl:attribute>
        <xsl:attribute name="text-align">center</xsl:attribute>
        <xsl:attribute name="margin-bottom">12mm</xsl:attribute>
    </xsl:attribute-set>
    
    <xsl:attribute-set name="valediction">
        <xsl:attribute name="font-size">9pt</xsl:attribute>
        <xsl:attribute name="line-height">11pt</xsl:attribute>
    </xsl:attribute-set>
    
    <xsl:attribute-set name="valediction.sign-off" use-attribute-sets="base.font valediction">
        <xsl:attribute name="padding-top">1.5em</xsl:attribute>
    </xsl:attribute-set>
    
    <xsl:attribute-set name="valediction.name" use-attribute-sets="base.font valediction">
        <xsl:attribute name="font-weight">bold</xsl:attribute>
    </xsl:attribute-set>
    
    <xsl:attribute-set name="valediction.job-title" use-attribute-sets="base.font valediction"/>
        
    
        
    
</xsl:stylesheet>
