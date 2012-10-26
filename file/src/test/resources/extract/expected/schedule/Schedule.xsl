<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
    xmlns:fo="http://www.w3.org/1999/XSL/Format" 
    xmlns:xs="http://www.w3.org/2001/XMLSchema"
    xmlns:fox="http://xmlgraphics.apache.org/fop/extensions"
    exclude-result-prefixes="xs" version="2.0">
    
    <xsl:import href="res:document/template/schedule/SchedulePropertySets.xsl"/>
    <xsl:include href="res:document/template/schedule/SVGIcons.xsl"/>
    
    <xd:doc xmlns:xd="http://www.oxygenxml.com/ns/doc/xsl" scope="stylesheet">
        <xd:desc>
            <xd:p><xd:b>Created on:</xd:b> May 4, 2011</xd:p>
            <xd:p><xd:b>Author:</xd:b> Mike</xd:p>
            <xd:p>Main Policy Schedule stylesheet</xd:p>
            <xd:p>MK23052011 - faq page now conditional on there being a blank page at end of schedule i.e. if the doc ends on an odd page the faqs are inserted, if even they are omitted.</xd:p>
            <xd:p>N.B faq text is now static and held in the stylesheet NOT the src XML</xd:p>
            <xd:p>MK23052011 - added conditional header and footer</xd:p>
            <xd:p>MK19052011 - amended contact details to include postal address, currently hard-coded in pre-processing</xd:p>
            <xd:p>MK11052011 - fox:orphan-content-limit and fox:widow-content-limit extensions used to handle table widows and orphans</xd:p>
        </xd:desc>
    </xd:doc>
    
    <xsl:variable name="imgPath" select="/policy-schedule/document-details/external-images"/>
    <xsl:variable name="insurerLogoName" select="/policy-schedule/document-details/insurer-logo-name" />

    <xsl:template match="/policy-schedule">
        <fo:root>
            <fo:layout-master-set>
                <fo:simple-page-master master-name="policyScheduleFirst" page-height="29.7cm"
                    page-width="21cm" margin-top="10mm">
                    <fo:region-body margin-top="18mm" margin-bottom="42mm" margin-left="10mm"
                        margin-right="10mm"/>
                    <fo:region-before region-name="header" extent="18mm"/>                   
                    <fo:region-after region-name="footer" extent="32mm" display-align="after"/>
                </fo:simple-page-master>
                
                <fo:simple-page-master master-name="policyScheduleRest" page-height="29.7cm"
                    page-width="21cm" margin-top="10mm">
                    <fo:region-body margin-top="30mm" margin-bottom="42mm" margin-left="10mm"
                        margin-right="10mm"/>
                    <fo:region-before region-name="header" extent="18mm"/>                   
                    <fo:region-after region-name="footer" extent="32mm" display-align="after"/>
                </fo:simple-page-master>
                
                <fo:simple-page-master master-name="policyScheduleLast" page-height="29.7cm"
                    page-width="21cm" margin-top="10mm">
                    <fo:region-body margin-bottom="42mm" margin-left="10mm"
                        margin-right="10mm"/>
                    <fo:region-before region-name="headerLast" extent="255mm"/>                   
                    <fo:region-after region-name="footer" extent="32mm" display-align="after"/>
                </fo:simple-page-master>
                
                <fo:page-sequence-master master-name="policySchedule">
                    <fo:repeatable-page-master-alternatives> 
                        <fo:conditional-page-master-reference master-reference="policyScheduleLast"
                            blank-or-not-blank="blank"/>
                        <fo:conditional-page-master-reference master-reference="policyScheduleFirst"
                            page-position="first"/>
                        <fo:conditional-page-master-reference master-reference="policyScheduleRest"
                            page-position="rest"/>                           
                    </fo:repeatable-page-master-alternatives>
                </fo:page-sequence-master>               
               
            </fo:layout-master-set>
            
            <fo:page-sequence master-reference="policySchedule" force-page-count="end-on-even">
                <fo:static-content flow-name="header">
                    <xsl:call-template name="Header"/>
                </fo:static-content>
                
                <fo:static-content flow-name="headerLast">
                    <xsl:call-template name="Header"/>
                    <xsl:call-template name="Faqs"/>
                </fo:static-content>

                <fo:static-content flow-name="footer">
                    <xsl:call-template name="Footer"/>            
                </fo:static-content>
                
                <fo:flow flow-name="xsl-region-body">                    
                    <xsl:call-template name="PolicyScheduleTitle"/>
                    <xsl:apply-templates select="*[local-name() != 'title' and local-name() != 'sub-title' and local-name() != 'references' and local-name() != 'document-details'  and local-name() != 'faqs' and local-name() != 'footer']"/>
                </fo:flow>
            </fo:page-sequence>                     
        </fo:root>
    </xsl:template>
    
    <xsl:template name="PolicyScheduleTitle">
        <fo:table table-layout="fixed" width="190mm" >
            <fo:table-column column-width="95mm" column-number="1"/>
            <fo:table-column column-width="95mm" column-number="2"/>
            <fo:table-body>
                <fo:table-row xsl:use-attribute-sets="document.title.table">
                    <fo:table-cell>                        
                        <xsl:apply-templates select="title"/>
                        <xsl:apply-templates select="sub-title"/>
                    </fo:table-cell>                    
                    <fo:table-cell>
                        <xsl:apply-templates select="references"/>
                    </fo:table-cell>
                </fo:table-row>                
            </fo:table-body>
        </fo:table>        
    </xsl:template>
    
    <xsl:template match="policy-schedule/title">
        <fo:block xsl:use-attribute-sets="document.title">
            <xsl:apply-templates/>
        </fo:block>
    </xsl:template>
    
    <xsl:template match="policy-schedule/sub-title">
        <fo:block xsl:use-attribute-sets="document.sub-title">
            <xsl:apply-templates/>
        </fo:block>
    </xsl:template>
    
    <!-- SVG version of account/policy number speech bubbles -->
    <xsl:template match="references">
        <fo:block>
            <fo:instream-foreign-object content-width="88mm">
                <svg version="1.1" xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink" x="0px" y="0px"
                    width="225.17px" height="121.769px" viewBox="0 0 225.17 121.769" enable-background="new 0 0 225.17 121.769"
                    xml:space="preserve">
                    <!-- left speech bubble -->
                    <g id="left_bubble">
                        <g>
                            <path fill="#808080" d="M163.66,14.678c31.256,0,56.594,17.746,56.594,39.635c0,13.368-9.457,25.177-23.928,32.356
                                c0.207,0.664,6.002,18.82,23.816,29.301c0,0-22.402,1.924-47.964-22.469c-2.778,0.295-5.622,0.447-8.519,0.447
                                c-31.26,0-56.596-17.742-56.596-39.636C107.064,32.424,132.399,14.678,163.66,14.678z"/>
                        </g>
                    </g>
                    <!-- right speech bubble -->
                    <g id="right_bubble">
                        <path fill="#404040" d="M61.042,3.157c-31.141,0-56.384,17.563-56.384,39.225c0,13.229,9.421,24.917,23.837,32.021
                            c-0.205,0.659-5.978,18.625-23.727,28.997c0,0,22.32,1.904,47.785-22.237c2.77,0.292,5.602,0.444,8.488,0.444
                            c31.144,0,56.388-17.561,56.388-39.226C117.43,20.719,92.186,3.157,61.042,3.157z"/>
                    </g>
                    <!-- right text -->
                    <g id="right_text">
                        <text transform="matrix(1 0 0 1 28.1416 29.3706)"><tspan x="0" y="0" fill="#FFFFFF" font-family="'Arial'" font-size="8"><xsl:value-of select="account-number/label/line[1]"/></tspan><tspan x="-0.893" y="9.6" fill="#FFFFFF" font-family="'Arial'" font-size="8"><xsl:value-of select="account-number/label/line[2]"/></tspan></text>
                        <text transform="matrix(1 0 0 1 29.9136 53.6538)" fill="#FFFFFF" font-family="'Arial'" font-weight="bold" font-size="10"><xsl:value-of select="account-number/number"/></text>
                    </g>
                    <!-- left text -->
                    <g id="left_text">
                        <text transform="matrix(1 0 0 1 124.7026 49.957)" fill="#FFFFFF" font-family="'Arial'" font-size="8"><xsl:value-of select="policy-number/label/line[1]"/></text>
                        <text transform="matrix(1 0 0 1 125.3442 64.0303)" fill="#FFFFFF" font-family="'Arial'" font-weight="bold" font-size="10"><xsl:value-of select="policy-number/number"/></text>
                    </g>
                </svg>
            </fo:instream-foreign-object>
        </fo:block>
    </xsl:template>
       
    <xsl:template match="policy-information-table|policyholder-info-table|property-information-table">
        <fo:table table-layout="fixed" width="190mm" xsl:use-attribute-sets="table" fox:widow-content-limit="2 * 1em" fox:orphan-content-limit="2 * 1em">
            <fo:table-column column-width="47mm" column-number="1"/>
            <fo:table-column column-width="143mm" column-number="2"/>
            <xsl:apply-templates select="title"/>    
            <xsl:apply-templates select="table-body"/>
        </fo:table>
    </xsl:template>
    
    <xsl:template match="policy-information-table/title|policyholder-info-table/title|property-information-table/title">
        <fo:table-header>
            <fo:table-row>
                <fo:table-cell number-columns-spanned="2" xsl:use-attribute-sets="table.title">
                    <fo:block>
                        <xsl:apply-templates/>
                    </fo:block>
                </fo:table-cell>
            </fo:table-row>
            <xsl:apply-templates select="../table-header"/>
        </fo:table-header>
    </xsl:template>
    
    <xsl:template match="policyholder-info-table/table-header">
        <fo:table-row>
            <fo:table-cell xsl:use-attribute-sets="table.header">
                <fo:block>
                    <xsl:apply-templates select="row/entry[1]"/>
                </fo:block>
            </fo:table-cell>
            <fo:table-cell xsl:use-attribute-sets="table.header">
                <fo:block>
                    <xsl:apply-templates select="row/entry[2]"/>
                </fo:block>
            </fo:table-cell>
        </fo:table-row>
    </xsl:template>
    
    <xsl:template match="policy-information-table/table-body|policyholder-info-table/table-body|property-information-table/table-body">
        <fo:table-body>
            <xsl:apply-templates/>
        </fo:table-body>
    </xsl:template>
    
    <xsl:template match="policy-information-table/table-body/row[position() mod 2 = 1]|policyholder-info-table/table-body/row[position() mod 2 = 1]|property-information-table/table-body/row[position() mod 2 = 1]">
        <fo:table-row xsl:use-attribute-sets="table.row.odd">            
            <xsl:apply-templates/>
        </fo:table-row>        
    </xsl:template>
    
    <xsl:template match="policy-information-table/table-body/row[position() mod 2 != 1]|policyholder-info-table/table-body/row[position() mod 2 != 1]|property-information-table/table-body/row[position() mod 2 != 1]">
        <fo:table-row xsl:use-attribute-sets="table.row.even">            
            <xsl:apply-templates/>
        </fo:table-row>        
    </xsl:template>
    
    <xsl:template match="policy-information-table/table-body/row/entry|policyholder-info-table/table-body/row/entry|property-information-table/table-body/row/entry">
        <fo:table-cell xsl:use-attribute-sets="table.entry">
            <xsl:choose>
                <xsl:when test="following-sibling::entry">
                    <xsl:attribute name="border-right">1pt solid white</xsl:attribute>
                </xsl:when>
            </xsl:choose>            
            <fo:block>
                <xsl:apply-templates/>
            </fo:block>
        </fo:table-cell>
    </xsl:template>
    
    <xsl:template match="contact-details">
        <fo:block  />
        <fo:table table-layout="fixed" width="190mm" xsl:use-attribute-sets="contact-details" keep-together="always">
            <fo:table-column column-width="26mm" column-number="1"/>
            <fo:table-column column-width="74mm" column-number="2"/>
            <fo:table-column column-width="25mm" column-number="3"/>
            <fo:table-column column-width="65mm" column-number="4"/>
            <fo:table-body>
                <fo:table-row>
                    <fo:table-cell number-rows-spanned="3">
                        <fo:block>                            
                            <fo:instream-foreign-object content-width="24mm">
                                <xsl:call-template name="TelephoneIcon"/>
                            </fo:instream-foreign-object>
                        </fo:block>
                    </fo:table-cell>
                    <fo:table-cell padding-top="4mm">
                        <xsl:apply-templates select="claim/para"/>
                    </fo:table-cell>
                    <fo:table-cell number-rows-spanned="3">
                        <fo:block>                            
                            <fo:instream-foreign-object content-width="24mm">
                                <xsl:call-template name="LetterIcon"/>
                            </fo:instream-foreign-object>
                        </fo:block>
                    </fo:table-cell>
                    <fo:table-cell number-rows-spanned="3" padding-top="4mm">
                        <xsl:apply-templates select="postal/para"/>
                        <xsl:apply-templates select="postal/address/line"/>
                    </fo:table-cell>
                </fo:table-row>
                <fo:table-row height="1em">                   
                    <fo:table-cell>
                        <fo:block/>
                    </fo:table-cell>
                </fo:table-row>
                <fo:table-row>                   
                    <fo:table-cell>
                        <xsl:apply-templates select="policy/para"/>
                    </fo:table-cell>
                </fo:table-row>                
            </fo:table-body>
        </fo:table>
        <!--<fo:block break-after="page"/>-->
    </xsl:template>
    
    <xsl:template match="claim/para[1]|policy/para[1]|postal/para[1]">
        <fo:block xsl:use-attribute-sets="contact-details.question">
            <xsl:apply-templates/>
        </fo:block>
    </xsl:template>
    
    <xsl:template match="claim/para[position() != 1]|policy/para[position() != 1]|postal/address/line">
        <fo:block xsl:use-attribute-sets="contact-details.call">
            <xsl:apply-templates/>
        </fo:block>
    </xsl:template>
    
    <xsl:template match="emphasis">
        <fo:inline xsl:use-attribute-sets="emphasis">
            <xsl:apply-templates/>
        </fo:inline>
    </xsl:template>
    
    <xsl:template match="policy-standard-cover-table">
        <fo:table table-layout="fixed" width="190mm" xsl:use-attribute-sets="table" fox:widow-content-limit="2 * 1em" fox:orphan-content-limit="2 * 1em">
            <fo:table-column column-width="proportional-column-width(1)" column-number="1"/>
            <fo:table-column column-width="proportional-column-width(1)" column-number="2"/>
            <fo:table-column column-width="proportional-column-width(1)" column-number="3"/>            
            <xsl:apply-templates select="title"/>            
            <fo:table-body>
                <fo:table-row>
                    <fo:table-cell number-columns-spanned="3">
                        <fo:block>
                            <xsl:apply-templates select="table-group"/>
                        </fo:block>
                    </fo:table-cell>
                </fo:table-row>
            </fo:table-body>
        </fo:table>
    </xsl:template>
    
    <xsl:template match="policy-standard-cover-table/title">
        <fo:table-header>
            <fo:table-row>
                <fo:table-cell number-columns-spanned="3" xsl:use-attribute-sets="table.title">
                    <fo:block>
                        <xsl:apply-templates/>
                    </fo:block>
                </fo:table-cell>
            </fo:table-row>           
        </fo:table-header>
    </xsl:template>
    
    <!-- nested table to enable correct table headings when page breaks -->
    <xsl:template match="policy-standard-cover-table/table-group">
        <fo:table table-layout="fixed" width="100%" fox:widow-content-limit="2 * 1em" fox:orphan-content-limit="2 * 1em">
            <fo:table-column column-width="proportional-column-width(1)" column-number="1"/>
            <fo:table-column column-width="proportional-column-width(1)" column-number="2"/>
            <fo:table-column column-width="proportional-column-width(1)" column-number="3"/>
            <xsl:apply-templates/>
        </fo:table>                    
    </xsl:template>
    
    <xsl:template match="policy-standard-cover-table/table-group/table-header">
        <fo:table-header>        
            <fo:table-row>
                <fo:table-cell xsl:use-attribute-sets="table.header">
                    <fo:block>
                        <xsl:apply-templates select="row/entry[1]"/>
                    </fo:block>
                </fo:table-cell>
                <fo:table-cell xsl:use-attribute-sets="table.header">
                    <fo:block>
                        <xsl:apply-templates select="row/entry[2]"/>
                    </fo:block>
                </fo:table-cell>
                <fo:table-cell xsl:use-attribute-sets="table.header">
                    <fo:block>
                        <xsl:apply-templates select="row/entry[3]"/>
                    </fo:block>
                </fo:table-cell>
            </fo:table-row>
        </fo:table-header>
    </xsl:template>
    
    <xsl:template match="policy-standard-cover-table/table-group/table-body">
       <fo:table-body>
        <xsl:apply-templates/>
       </fo:table-body>
    </xsl:template>
    
    <xsl:template match="policy-standard-cover-table/table-group/table-body/row[position() mod 2 = 1]">
        <fo:table-row xsl:use-attribute-sets="table.row.odd">            
            <xsl:apply-templates/>
        </fo:table-row> 
    </xsl:template>
    
    <xsl:template match="policy-standard-cover-table/table-group/table-body/row[position() mod 2 != 1]">
        <fo:table-row xsl:use-attribute-sets="table.row.even">            
            <xsl:apply-templates/>
        </fo:table-row> 
    </xsl:template>
    
    <xsl:template match="policy-standard-cover-table/table-group/table-body/row/entry">
        <fo:table-cell xsl:use-attribute-sets="table.entry">
            <xsl:choose>
                <xsl:when test="following-sibling::entry">
                    <xsl:attribute name="border-right">1pt solid white</xsl:attribute>
                </xsl:when>
            </xsl:choose>            
            <fo:block>
                <xsl:apply-templates/>
            </fo:block>
        </fo:table-cell>
    </xsl:template>
    
    <xsl:template match="personal-possessions">        
        <xsl:apply-templates/>
    </xsl:template>
    
    <xsl:template match="specified-items-table">
        <fo:block xsl:use-attribute-sets="personal-possessions.key" keep-with-next.within-page="always">            
            <fo:instream-foreign-object content-width="118mm">
                <xsl:call-template name="BannerHomeFeetWorld"/>
            </fo:instream-foreign-object>
        </fo:block>
        <fo:table table-layout="fixed" width="190mm" xsl:use-attribute-sets="table" fox:widow-content-limit="2 * 1em" fox:orphan-content-limit="2 * 1em">
            <fo:table-column column-width="37mm" column-number="1"/>
            <fo:table-column column-width="37mm" column-number="2"/>
            <fo:table-column column-width="24mm" column-number="3"/>
            <fo:table-column column-width="14mm" column-number="4"/>
            <fo:table-column column-width="14mm" column-number="5"/>
            <fo:table-column column-width="64mm" column-number="6"/>
            <xsl:apply-templates select="title"/>
            <xsl:apply-templates select="table-body"/>
        </fo:table>
    </xsl:template>
    
    <xsl:template match="specified-items-table/title">
        <fo:table-header>
            <fo:table-row>
                <fo:table-cell number-columns-spanned="6" xsl:use-attribute-sets="table.title">
                    <fo:block>
                        <xsl:apply-templates/>
                    </fo:block>
                </fo:table-cell>
            </fo:table-row>
            <xsl:apply-templates select="../table-header"/>
        </fo:table-header>
    </xsl:template>
    
    <xsl:template match="specified-items-table/table-header">
        <fo:table-row>
            <fo:table-cell xsl:use-attribute-sets="table.header">
                <fo:block>
                    <xsl:apply-templates select="row/entry[1]"/>
                </fo:block>
            </fo:table-cell>
            <fo:table-cell xsl:use-attribute-sets="table.header">
                <fo:block>
                    <xsl:apply-templates select="row/entry[2]"/>
                </fo:block>
            </fo:table-cell>
            <fo:table-cell xsl:use-attribute-sets="table.header">
                <fo:block>
                    <xsl:apply-templates select="row/entry[3]"/>
                </fo:block>
            </fo:table-cell>
            <fo:table-cell xsl:use-attribute-sets="table.header table.header.icons">
                <fo:block>                    
                    <fo:instream-foreign-object content-width="8mm"> 
                        <xsl:call-template name="HomeIcon"/>
                    </fo:instream-foreign-object>
                </fo:block>
            </fo:table-cell>
            <fo:table-cell xsl:use-attribute-sets="table.header table.header.icons">
                <fo:block>                    
                    <fo:instream-foreign-object content-width="8mm"> 
                        <xsl:call-template name="FeetIcon"/>
                    </fo:instream-foreign-object>
                </fo:block>
            </fo:table-cell>
            <fo:table-cell xsl:use-attribute-sets="table.header table.header.icons.world">
                <fo:block>                   
                    <fo:instream-foreign-object content-width="8mm"> 
                        <xsl:call-template name="WorldIcon"/>
                    </fo:instream-foreign-object>
                </fo:block>
            </fo:table-cell>
        </fo:table-row>
    </xsl:template>
    
    <xsl:template match="specified-items-table/table-body">
        <fo:table-body>
            <xsl:apply-templates/>
        </fo:table-body>
    </xsl:template>
    
    <xsl:template match="specified-items-table/table-body/row[position() mod 2 = 1]">
        <fo:table-row xsl:use-attribute-sets="table.row.odd">            
            <xsl:apply-templates/>
        </fo:table-row> 
    </xsl:template>
    
    <xsl:template match="specified-items-table/table-body/row[position() mod 2 != 1]">
        <fo:table-row xsl:use-attribute-sets="table.row.even">            
            <xsl:apply-templates/>
        </fo:table-row> 
    </xsl:template>
    
    <xsl:template match="specified-items-table/table-body/row/entry">
        <fo:table-cell xsl:use-attribute-sets="table.entry">
            <xsl:choose>
                <xsl:when test="following-sibling::entry">
                    <xsl:attribute name="border-right">1pt solid white</xsl:attribute>
                </xsl:when>
            </xsl:choose>
            <!-- override padding for icons -->
            <xsl:if test="lower-case(.) eq 'yes' or lower-case(.) eq 'no'">
                <xsl:attribute name="padding-left">0</xsl:attribute>   
                <xsl:attribute name="padding-right">0</xsl:attribute> 
                <xsl:attribute name="padding-top">0</xsl:attribute>
                <xsl:attribute name="padding-bottom">0</xsl:attribute>
            </xsl:if>
            <xsl:choose>
                <xsl:when test="lower-case(.) eq 'yes'">
                    <fo:block xsl:use-attribute-sets="table.header.icons">
                        <fo:instream-foreign-object content-height="6mm"> 
                            <xsl:call-template name="YesIcon"/>
                        </fo:instream-foreign-object>
                    </fo:block>
                </xsl:when>
                <xsl:when test="lower-case(.) eq 'no'">
                    <fo:block xsl:use-attribute-sets="table.header.icons">
                        <fo:instream-foreign-object content-height="6mm"> 
                            <xsl:call-template name="NoIcon"/>
                        </fo:instream-foreign-object>
                    </fo:block>
                </xsl:when>
                <xsl:otherwise>
                    <fo:block>
                        <xsl:apply-templates/>
                    </fo:block>
                </xsl:otherwise>
            </xsl:choose>       
        </fo:table-cell>
    </xsl:template>
    
    <xsl:template match="standard-cover-table">
        <fo:block xsl:use-attribute-sets="personal-possessions.key" keep-with-next.within-page="always">           
            <fo:instream-foreign-object content-width="70mm">
                <xsl:call-template name="BannerHomeFeet"/>
            </fo:instream-foreign-object>
        </fo:block>
        <fo:table table-layout="fixed" width="190mm" xsl:use-attribute-sets="table" fox:widow-content-limit="2 * 1em" fox:orphan-content-limit="2 * 1em">
            <fo:table-column column-width="92mm" column-number="1"/>
            <fo:table-column column-width="41mm" column-number="2"/>
            <fo:table-column column-width="28.5mm" column-number="3"/>
            <fo:table-column column-width="28.5mm" column-number="4"/>
            <xsl:apply-templates select="title"/>
            <xsl:apply-templates select="table-body"/>
        </fo:table>
    </xsl:template>
    
    <xsl:template match="standard-cover-table/title">
        <fo:table-header>
            <fo:table-row>
                <fo:table-cell number-columns-spanned="4" xsl:use-attribute-sets="table.title">
                    <fo:block>
                        <xsl:apply-templates/>
                    </fo:block>
                </fo:table-cell>
            </fo:table-row>
            <xsl:apply-templates select="../table-header"/>
        </fo:table-header>
    </xsl:template>
    
    <xsl:template match="standard-cover-table/table-header">
        <fo:table-row>
            <fo:table-cell xsl:use-attribute-sets="table.header">
                <fo:block>
                    <xsl:apply-templates select="row/entry[1]"/>
                </fo:block>
            </fo:table-cell>
            <fo:table-cell xsl:use-attribute-sets="table.header">
                <fo:block>
                    <xsl:apply-templates select="row/entry[2]"/>
                </fo:block>
            </fo:table-cell>
            <fo:table-cell xsl:use-attribute-sets="table.header table.header.icons">
                <fo:block>                    
                    <fo:instream-foreign-object content-width="8mm"> 
                        <xsl:call-template name="HomeIcon"/>
                    </fo:instream-foreign-object>
                </fo:block>
            </fo:table-cell>
            <fo:table-cell xsl:use-attribute-sets="table.header table.header.icons">
                <fo:block>                    
                    <fo:instream-foreign-object content-width="8mm"> 
                       <xsl:call-template name="FeetIcon"/>
                    </fo:instream-foreign-object>
                </fo:block>
            </fo:table-cell>
        </fo:table-row>
    </xsl:template>
    
    <xsl:template match="standard-cover-table/table-body">
        <fo:table-body>
            <xsl:apply-templates/>
        </fo:table-body>
    </xsl:template>
    
    <xsl:template match="standard-cover-table/table-body/row[position() mod 2 = 1]">
        <fo:table-row xsl:use-attribute-sets="table.row.odd">            
            <xsl:apply-templates/>
        </fo:table-row> 
    </xsl:template>
    
    <xsl:template match="standard-cover-table/table-body/row[position() mod 2 != 1]">
        <fo:table-row xsl:use-attribute-sets="table.row.even">            
            <xsl:apply-templates/>
        </fo:table-row> 
    </xsl:template>
    
    <xsl:template match="standard-cover-table/table-body/row/entry">
        <fo:table-cell xsl:use-attribute-sets="table.entry">
            <xsl:choose>
                <xsl:when test="following-sibling::entry">
                    <xsl:attribute name="border-right">1pt solid white</xsl:attribute>
                </xsl:when>
            </xsl:choose>           
           
            <xsl:choose>
                <xsl:when test="lower-case(.) eq 'yes'">
                    <fo:block xsl:use-attribute-sets="table.header.icons">
                        <fo:instream-foreign-object content-height="6mm"> 
                            <xsl:call-template name="YesIcon"/>
                        </fo:instream-foreign-object>
                    </fo:block>
                </xsl:when>
                <xsl:when test="lower-case(.) eq 'no'">
                    <fo:block xsl:use-attribute-sets="table.header.icons">
                        <fo:instream-foreign-object content-height="6mm"> 
                            <xsl:call-template name="NoIcon"/>
                        </fo:instream-foreign-object>
                    </fo:block>
                </xsl:when>
                <xsl:otherwise>
                    <fo:block>
                        <xsl:apply-templates/>
                    </fo:block>
                </xsl:otherwise>
            </xsl:choose>               
           
        </fo:table-cell>
    </xsl:template>
    
    <xsl:template match="endorsements-table">
        <fo:table table-layout="fixed" width="190mm" xsl:use-attribute-sets="table" fox:widow-content-limit="2 * 1em" fox:orphan-content-limit="2 * 1em">
            <fo:table-column column-width="33mm" column-number="1"/>
            <fo:table-column column-width="47mm" column-number="2"/>
            <fo:table-column column-width="110mm" column-number="3"/>
            <xsl:apply-templates select="title"/>
            <xsl:apply-templates select="table-body"/>
        </fo:table>
    </xsl:template>
    
    <xsl:template match="endorsements-table/title">
        <fo:table-header>
            <fo:table-row>
                <fo:table-cell number-columns-spanned="3" xsl:use-attribute-sets="table.title">
                    <fo:block>
                        <xsl:apply-templates/>
                    </fo:block>
                </fo:table-cell>
            </fo:table-row>
            <xsl:apply-templates select="../sub-title"/>
            <xsl:apply-templates select="../table-header"/>
        </fo:table-header>
    </xsl:template>
    
    <xsl:template match="endorsements-table/sub-title">
        <fo:table-row>
            <fo:table-cell number-columns-spanned="3" xsl:use-attribute-sets="table.sub-title">
                <fo:block>  
                    <xsl:apply-templates/>
                </fo:block>
            </fo:table-cell>
        </fo:table-row>
    </xsl:template>
    
    <xsl:template match="endorsements-table/table-header">
        <fo:table-row>
            <fo:table-cell xsl:use-attribute-sets="table.header">
                <fo:block>
                    <xsl:apply-templates select="row/entry[1]"/>
                </fo:block>
            </fo:table-cell>
            <fo:table-cell xsl:use-attribute-sets="table.header">
                <fo:block>
                    <xsl:apply-templates select="row/entry[2]"/>
                </fo:block>
            </fo:table-cell>
            <fo:table-cell xsl:use-attribute-sets="table.header">
                <fo:block>
                    <xsl:apply-templates select="row/entry[3]"/>
                </fo:block>
            </fo:table-cell>
        </fo:table-row>            
    </xsl:template>
    
    <xsl:template match="endorsements-table/table-body">
        <fo:table-body>
            <xsl:apply-templates/>
        </fo:table-body>
    </xsl:template>
    
    <xsl:template match="endorsements-table/table-body/row[position() mod 2 = 1]">
        <fo:table-row xsl:use-attribute-sets="table.row.odd">            
            <xsl:apply-templates/>
        </fo:table-row> 
    </xsl:template>
    
    <xsl:template match="endorsements-table/table-body/row[position() mod 2 != 1]">
        <fo:table-row xsl:use-attribute-sets="table.row.even">            
            <xsl:apply-templates/>
        </fo:table-row> 
    </xsl:template>
    
    <xsl:template match="endorsements-table/table-body/row/entry">
        <fo:table-cell xsl:use-attribute-sets="table.entry">
            <xsl:choose>
                <xsl:when test="following-sibling::entry">
                    <xsl:attribute name="border-right">1pt solid white</xsl:attribute>
                </xsl:when>
            </xsl:choose>       
            <fo:block>
                <xsl:apply-templates/>
            </fo:block>
        </fo:table-cell>
    </xsl:template>
    
    <xsl:template match="endorsements-table/table-body/row/entry/para">
        <fo:block>
            <xsl:if test="preceding-sibling::*[1][self::list]">
                <xsl:attribute name="space-before">1em</xsl:attribute>
            </xsl:if>
            <xsl:apply-templates/>
        </fo:block>
    </xsl:template>
    
    <xsl:template match="list">
        <fo:list-block xsl:use-attribute-sets="list">
            <xsl:apply-templates/>
        </fo:list-block>        
    </xsl:template>
    
    <xsl:template match="list[not(@type)]/item|list[@type='']/item">
        <fo:list-item>
            <fo:list-item-label xsl:use-attribute-sets="list.item-label">
                <fo:block>
                    <xsl:text>&#8226;</xsl:text>
                </fo:block>
            </fo:list-item-label>
            <fo:list-item-body  xsl:use-attribute-sets="list.item-body">
                <fo:block>
                    <xsl:apply-templates/>
                </fo:block>
            </fo:list-item-body>
        </fo:list-item>
    </xsl:template>
    
    <xsl:template match="list[@type='alpha']/item">
        <fo:list-item>
            <fo:list-item-label xsl:use-attribute-sets="list.item-label">
                <fo:block>
                    <xsl:number count="item" format="A"/>
                    <xsl:text>.</xsl:text>
                </fo:block>
            </fo:list-item-label>
            <fo:list-item-body  xsl:use-attribute-sets="list.item-body">
                <fo:block>
                    <xsl:apply-templates/>
                </fo:block>
            </fo:list-item-body>
        </fo:list-item>
    </xsl:template>
    
    <xsl:template match="list[@type='numeric']/item">
        <fo:list-item>
            <fo:list-item-label xsl:use-attribute-sets="list.item-label">
                <fo:block>
                    <xsl:number count="item" format="1"/>
                    <xsl:text>.</xsl:text>
                </fo:block>
            </fo:list-item-label>
            <fo:list-item-body xsl:use-attribute-sets="list.item-body">
                <fo:block>
                    <xsl:apply-templates/>
                </fo:block>
            </fo:list-item-body>
        </fo:list-item>
    </xsl:template>
    
    <xsl:template match="notices-table">
        <fo:table table-layout="fixed" width="190mm" xsl:use-attribute-sets="table" fox:widow-content-limit="2 * 1em" fox:orphan-content-limit="2 * 1em">
            <fo:table-column column-width="38mm" column-number="1"/>
            <fo:table-column column-width="152mm" column-number="2"/>
            <xsl:apply-templates select="title"/>
            <xsl:apply-templates select="table-body"/>
        </fo:table>
    </xsl:template>
    
    <xsl:template match="notices-table/title">
        <fo:table-header>
            <fo:table-row>
                <fo:table-cell number-columns-spanned="2" xsl:use-attribute-sets="table.title">
                    <fo:block>
                        <xsl:apply-templates/>
                    </fo:block>
                </fo:table-cell>
            </fo:table-row>           
        </fo:table-header>
    </xsl:template>
    
    <xsl:template match="notices-table/table-body">
        <fo:table-body>
            <xsl:apply-templates/>
        </fo:table-body>
    </xsl:template>
    
    <xsl:template match="notices-table/table-body/row[position() mod 2 = 1]">
        <fo:table-row xsl:use-attribute-sets="table.row.odd">            
            <xsl:apply-templates/>
        </fo:table-row> 
    </xsl:template>
    
    <xsl:template match="notices-table/table-body/row[position() mod 2 != 1]">
        <fo:table-row xsl:use-attribute-sets="table.row.even">            
            <xsl:apply-templates/>
        </fo:table-row> 
    </xsl:template>
    
    <xsl:template match="notices-table/table-body/row/entry">
        <fo:table-cell xsl:use-attribute-sets="table.entry">
            <xsl:choose>
                <xsl:when test="following-sibling::entry">
                    <xsl:attribute name="border-right">1pt solid white</xsl:attribute>
                </xsl:when>
            </xsl:choose>       
            <fo:block>
                <xsl:apply-templates/>
            </fo:block>
        </fo:table-cell>
    </xsl:template>
    
    <xsl:template match="notices-table/table-body/row/entry/para">
        <fo:block>
            <xsl:apply-templates/>
        </fo:block>
    </xsl:template>
    
    <xsl:template match="changes-to-schedule-table">
        <fo:table table-layout="fixed" width="190mm" xsl:use-attribute-sets="table" fox:widow-content-limit="2 * 1em" fox:orphan-content-limit="2 * 1em">
            <fo:table-column column-width="25mm" column-number="1"/>
            <fo:table-column column-width="37mm" column-number="2"/>
            <fo:table-column column-width="128mm" column-number="3"/>
            <xsl:apply-templates select="title"/>
            <xsl:apply-templates select="table-body"/>
        </fo:table>
    </xsl:template>
    
    <xsl:template match="changes-to-schedule-table/title">
        <fo:table-header>
            <fo:table-row>
                <fo:table-cell number-columns-spanned="3" xsl:use-attribute-sets="table.title">
                    <fo:block>
                        <xsl:apply-templates/>
                    </fo:block>
                </fo:table-cell>
            </fo:table-row>
            <xsl:apply-templates select="../table-header"/>
        </fo:table-header>
    </xsl:template>
    
    <xsl:template match="changes-to-schedule-table/table-header">
        <fo:table-row>
            <fo:table-cell xsl:use-attribute-sets="table.header">
                <fo:block>
                    <xsl:apply-templates select="row/entry[1]"/>
                </fo:block>
            </fo:table-cell>
            <fo:table-cell xsl:use-attribute-sets="table.header">
                <fo:block>
                    <xsl:apply-templates select="row/entry[2]"/>
                </fo:block>
            </fo:table-cell>
            <fo:table-cell xsl:use-attribute-sets="table.header">
                <fo:block>
                    <xsl:apply-templates select="row/entry[3]"/>
                </fo:block>
            </fo:table-cell>
        </fo:table-row>            
    </xsl:template>
    
    <xsl:template match="changes-to-schedule-table/table-body">
        <fo:table-body>
            <xsl:apply-templates/>
        </fo:table-body>
    </xsl:template>
    
    <xsl:template match="changes-to-schedule-table/table-body/row[position() mod 2 = 1]">
        <fo:table-row xsl:use-attribute-sets="table.row.odd">            
            <xsl:apply-templates/>
        </fo:table-row> 
    </xsl:template>
    
    <xsl:template match="changes-to-schedule-table/table-body/row[position() mod 2 != 1]">
        <fo:table-row xsl:use-attribute-sets="table.row.even">            
            <xsl:apply-templates/>
        </fo:table-row> 
    </xsl:template>
    
    <xsl:template match="changes-to-schedule-table/table-body/row/entry">
        <fo:table-cell xsl:use-attribute-sets="table.entry">
            <xsl:choose>
                <xsl:when test="following-sibling::entry">
                    <xsl:attribute name="border-right">1pt solid white</xsl:attribute>
                </xsl:when>
            </xsl:choose>       
            <fo:block>
                <xsl:apply-templates/>
            </fo:block>
        </fo:table-cell>
    </xsl:template>
    
    <xsl:template match="footer">
        <fo:block xsl:use-attribute-sets="footer">
            <xsl:apply-templates/>
        </fo:block>
    </xsl:template>
    
    <xsl:template match="footer/para">
        <fo:block>
            <xsl:apply-templates/>
        </fo:block>
    </xsl:template>
           
    <xsl:template name="Faqs">
        <!-- text is now hard coded here as dyamic text isn't possible within a header without the use of markers, which can't be used on this occasion -->
        <fo:block margin-left="10mm" margin-right="10mm" margin-top="35mm">
            <fo:instream-foreign-object content-width="190mm">               
                <svg version="1.1" id="Layer_1" xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink" x="0px" y="0px"
                    width="521px" height="453px" viewBox="0 0 521 453" enable-background="new 0 0 521 453" xml:space="preserve">
                    <!-- Central Statement / Bubble -->
                    <path fill="#BCBDBF" d="M261.468,132.774c69.199,0,125.292,39.289,125.292,87.749c0,29.599-20.935,55.743-52.97,71.636
                        c0.456,1.471,13.285,41.662,52.726,64.866c0,0-49.599,4.262-106.186-49.745c-6.152,0.653-12.448,0.994-18.862,0.994
                        c-69.205,0-125.301-39.283-125.301-87.751C136.167,172.063,192.262,132.774,261.468,132.774z"/>
                    <text transform="matrix(1 0 0 1 187.8718 196.7747)" fill="#424243" font-family="'Arial'" font-size="11.2445">It is important to notify us of</text>
                    <text transform="matrix(1 0 0 1 168.4929 210.429)" fill="#424243" font-family="'Arial'" font-size="11.2445">any changes to your circumstances</text>
                    <text transform="matrix(1 0 0 1 168.804 224.0823)" fill="#424243" font-family="'Arial'" font-size="11.2445">or additional cover you may require.</text>
                    <text transform="matrix(1 0 0 1 190.5359 251.3909)" fill="#424243" font-family="'Arial'" font-weight="bold" font-size="11.2445">Call us on 0845 4 100 800</text>
                    <!-- Question 1 -->
                    <path fill="#E7E8E9" d="M80.528,12.611c41.089,0,74.396,23.329,74.396,52.104c0,17.574-12.431,33.099-31.452,42.536
                        c0.271,0.874,7.888,24.738,31.308,38.517c0,0-29.45,2.531-63.051-29.538c-3.654,0.388-7.393,0.59-11.2,0.59
                        c-41.093,0-74.402-23.326-74.402-52.104C6.126,35.94,39.435,12.611,80.528,12.611z"/>
                    <g>
                        <text transform="matrix(1 0 0 1 28.8225 53.8772)" fill="#424243" font-family="'Arial'" font-size="16.774">“</text>
                        <text transform="matrix(1 0 0 1 73.3308 88.7874)" fill="#424243" font-family="'Arial'" font-size="16.774">”</text>
                        <text transform="matrix(1 0 0 1 35.5911 50.178)" fill="#424243" font-family="'Arial'" font-size="9.1495">Some of the answers</text>
                        <text transform="matrix(1 0 0 1 35.5911 61.6155)" fill="#424243" font-family="'Arial'" font-size="9.1495">I gave when I took the</text>
                        <text transform="matrix(1 0 0 1 35.5911 73.053)" fill="#424243" font-family="'Arial'" font-size="9.1495">policy have now</text>
                        <text transform="matrix(1 0 0 1 35.5911 84.4895)" fill="#424243" font-family="'Arial'" font-size="9.1495">changed</text>
                    </g>
                    <!-- Question 2 -->                    
                    <path fill="#E7E8E9" d="M248.136,13.385c31.256,0,56.594,17.746,56.594,39.635c0,13.368-9.457,25.178-23.928,32.356
                        c0.206,0.666,6.002,18.82,23.815,29.301c0,0-22.402,1.925-47.964-22.469c-2.778,0.295-5.623,0.448-8.519,0.448
                        c-31.261,0-56.597-17.743-56.597-39.637C191.541,31.131,216.876,13.385,248.136,13.385z"/>                    
                    <g>
                        <text transform="matrix(1 0 0 1 210.5022 48.4856)" fill="#424243" font-family="'Arial'" font-size="9.2556">I have changed</text>
                        <text transform="matrix(1 0 0 1 210.5022 60.054)" fill="#424243" font-family="'Arial'" font-size="9.2556">my email address</text>
                        <text transform="matrix(1 0 0 1 284.2947 64.9768)" fill="#424243" font-family="'Arial'" font-size="16.9685">”</text>
                        <text transform="matrix(1 0 0 1 203.7781 51.2268)" fill="#424243" font-family="'Arial'" font-size="16.9685">“</text>
                    </g>                    
                    <!-- Question 3 -->
                    <path fill="#E7E8E9" d="M441.23,7.82c-41.735,0-75.564,23.695-75.564,52.924c0,17.849,12.625,33.617,31.946,43.203
                        c-0.275,0.888-8.013,25.129-31.798,39.124c0,0,29.912,2.569,64.042-30.003c3.709,0.394,7.508,0.602,11.374,0.602
                        c41.739,0,75.57-23.694,75.57-52.925C516.802,31.516,482.97,7.82,441.23,7.82z"/>
                    <g>
                        <text transform="matrix(1 0 0 1 387.9138 49.6477)" fill="#424243" font-family="'Arial'" font-size="9.9437">I would like to insure</text>
                        <text transform="matrix(1 0 0 1 387.9138 62.0784)" fill="#424243" font-family="'Arial'" font-size="9.9437">a ring my boyfriend</text>
                        <text transform="matrix(1 0 0 1 387.9138 74.5071)" fill="#424243" font-family="'Arial'" font-size="9.9437">bought for my birthday</text>
                        <text transform="matrix(1 0 0 1 379.7908 53.3323)" fill="#424243" font-family="'Arial'" font-size="18.2301">“</text>
                        <text transform="matrix(1 0 0 1 490.5564 79.4231)" fill="#424243" font-family="'Arial'" font-size="18.2301">”</text>
                    </g>
                    <!-- Question 4 -->
                    <path fill="#E7E8E9" d="M67.885,197.832c27.519,0,49.823,15.625,49.823,34.895c0,11.771-8.325,22.166-21.063,28.485
                        c0.182,0.585,5.283,16.567,20.967,25.795c0,0-19.724,1.696-42.226-19.78c-2.447,0.259-4.951,0.396-7.502,0.396
                        c-27.52,0-49.825-15.622-49.825-34.896C18.059,213.457,40.365,197.832,67.885,197.832z"/>
                    <g>
                        <text transform="matrix(1 0 0 1 34.6956 222.7229)" fill="#424243" font-family="'Arial'" font-size="8.1412">I have builders in</text>
                        <text transform="matrix(1 0 0 1 34.6956 232.8987)" fill="#424243" font-family="'Arial'" font-size="8.1412">doing an extension</text>
                        <text transform="matrix(1 0 0 1 34.6956 243.0754)" fill="#424243" font-family="'Arial'" font-size="8.1412">to my property</text>
                        <text transform="matrix(1 0 0 1 27.9241 227.0081)" fill="#424243" font-family="'Arial'" font-size="14.9255">“</text>
                        <text transform="matrix(1 0 0 1 89.3674 247.3811)" fill="#424243" font-family="'Arial'" font-size="14.9255">”</text>
                    </g>
                    <!-- Question 5 -->
                    <path fill="#E7E8E9" d="M463.568,184.143c-29.25,0-52.96,16.607-52.96,37.091c0,12.511,8.849,23.563,22.39,30.279
                        c-0.191,0.623-5.614,17.611-22.286,27.42c0,0,20.966,1.801,44.885-21.027c2.602,0.275,5.262,0.421,7.973,0.421
                        c29.253,0,52.964-16.605,52.964-37.093C516.532,200.75,492.821,184.143,463.568,184.143z"/>
                    <g>
                        <text transform="matrix(1 0 0 1 436.5232 216.6096)" fill="#424243" font-family="'Arial'" font-size="10.2214">I have taken</text>
                        <text transform="matrix(1 0 0 1 436.5232 229.386)" fill="#424243" font-family="'Arial'" font-size="10.2214">in a lodger</text>
                        <text transform="matrix(1 0 0 1 427.9294 219.4553)" fill="#424243" font-family="'Arial'" font-size="18.7392">“</text>
                        <text transform="matrix(1 0 0 1 488.0486 234.8391)" fill="#424243" font-family="'Arial'" font-size="18.7392">”</text>
                    </g>
                    <!-- Question 6 -->
                    <path fill="#E7E8E9" d="M197.267,317.363c39.901,0,72.248,22.654,72.248,50.601c0,17.064-12.073,32.142-30.545,41.307
                        c0.264,0.85,7.661,24.025,30.402,37.405c0,0-28.6,2.458-61.229-28.686c-3.548,0.377-7.18,0.573-10.876,0.573
                        c-39.907,0-72.253-22.654-72.253-50.601C125.014,340.018,157.36,317.363,197.267,317.363z"/>
                    <g>
                        <text transform="matrix(1 0 0 1 147.2732 362.5725)" fill="#424243" font-family="'Arial'" font-size="10.0537">I recently got married</text>
                        <text transform="matrix(1 0 0 1 147.2732 375.1389)" fill="#424243" font-family="'Arial'" font-size="10.0537">and have changed</text>
                        <text transform="matrix(1 0 0 1 147.2732 387.7073)" fill="#424243" font-family="'Arial'" font-size="10.0537">my surname</text>
                        <text transform="matrix(1 0 0 1 139.7322 365.4768)" fill="#424243" font-family="'Arial'" font-size="18.4318">“</text>
                        <text transform="matrix(1 0 0 1 204.8982 393.2581)" fill="#424243" font-family="'Arial'" font-size="18.4318">”</text>
                    </g> 
                </svg>   
            </fo:instream-foreign-object>
        </fo:block>
    </xsl:template>  

    <xsl:template name="PolicyExpertLogo">
        <fo:instream-foreign-object content-height="12mm">
            <svg version="1.1" id="Layer_1" xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink" x="0px" y="0px"
                width="141.904px" height="34.588px" viewBox="0 0 141.904 34.588" enable-background="new 0 0 141.904 34.588"
                xml:space="preserve">
                <g>
                    <linearGradient id="SVGID_1_" gradientUnits="userSpaceOnUse" x1="29.2866" y1="-28.9082" x2="107.0918" y2="48.897">
                        <stop  offset="0" style="stop-color:#B81188"/>
                        <stop  offset="0.1735" style="stop-color:#A42E8D"/>
                        <stop  offset="0.5731" style="stop-color:#794696"/>
                        <stop  offset="0.8585" style="stop-color:#5E4D9C"/>
                        <stop  offset="1" style="stop-color:#52509D"/>
                    </linearGradient>
                    <path fill="url(#SVGID_1_)" d="M33.701,5.499c0.118,0.117,0.187,0.281,0.187,0.448v13.409c0,0.168-0.067,0.328-0.187,0.448
                        c-0.119,0.119-0.279,0.184-0.447,0.184h-0.707c-0.17,0-0.328-0.064-0.448-0.184c-0.12-0.12-0.186-0.28-0.186-0.448V5.946
                        c0-0.167,0.068-0.331,0.186-0.448c0.117-0.118,0.28-0.185,0.448-0.185h0.707C33.42,5.313,33.583,5.381,33.701,5.499z M33.254,0
                        h-0.707c-0.168,0-0.331,0.067-0.448,0.185c-0.118,0.118-0.186,0.281-0.186,0.447v2.302c0,0.169,0.066,0.329,0.186,0.448
                        c0.12,0.119,0.278,0.185,0.448,0.185h0.707c0.168,0,0.328-0.065,0.447-0.185c0.12-0.119,0.187-0.278,0.187-0.448V0.633
                        c0-0.167-0.068-0.33-0.187-0.447C33.583,0.068,33.42,0,33.254,0z M38.612,8.54c1.33-1.163,2.328-1.592,4.113-1.632l0.025-0.001
                        c0,0,0.004,0,0.035,0c1.817,0.005,4.297,0.99,5.203,3.6c0.089,0.254,0.328,0.422,0.597,0.422h0.785
                        c0.202,0,0.395-0.097,0.513-0.262c0.119-0.165,0.15-0.379,0.086-0.571c-1.16-3.452-4.326-5.119-7.279-5.11
                        c-2.197-0.001-4.133,0.838-5.505,2.227C35.809,8.6,34.999,10.53,35,12.676c0,1.842,0.648,3.741,1.961,5.198
                        c1.306,1.457,3.293,2.441,5.835,2.442h0.02c3.268,0.003,6.103-1.988,7.125-4.981c0.067-0.195,0.036-0.406-0.083-0.573
                        c-0.12-0.167-0.31-0.265-0.516-0.265H48.56c-0.272,0-0.515,0.175-0.601,0.435c-0.565,1.727-2.504,3.442-5.275,3.461
                        c-3.09-0.015-5.703-2.295-5.711-5.743C36.965,11.003,37.686,9.368,38.612,8.54z M29.004,0h-0.709c-0.166,0-0.33,0.067-0.447,0.185
                        s-0.186,0.281-0.186,0.447v18.722c0,0.168,0.066,0.328,0.186,0.448c0.12,0.119,0.278,0.184,0.447,0.184h0.709
                        c0.169,0,0.327-0.064,0.448-0.184c0.12-0.12,0.184-0.28,0.184-0.448V0.633c0-0.167-0.067-0.33-0.184-0.447
                        C29.334,0.068,29.171,0,29.004,0z M129.277,5.109c-1.188,0.025-2.586,0.348-3.693,1.337c0-0.109,0-0.217,0-0.326
                        c0-0.167-0.067-0.329-0.185-0.446c-0.118-0.119-0.28-0.186-0.448-0.186h-1.779c-0.167,0-0.329,0.067-0.447,0.186
                        c-0.119,0.117-0.187,0.28-0.187,0.08v13.232c0,0.535,0.067,0.696,0.187,0.814c0.118,0.117,0.28,0.186,0.447,0.186h1.863
                        c0.167,0,0.33-0.068,0.447-0.186c0.119-0.118,0.186-0.279,0.186-0.447v-7.385c0-1.452,0.344-2.474,0.9-3.113
                        c0.557-0.637,1.357-0.985,2.545-0.991h0.186c0.167,0,0.326-0.067,0.445-0.185c0.121-0.12,0.186-0.279,0.186-0.447v-1.49
                        c0-0.173-0.068-0.336-0.191-0.456C129.614,5.168,129.449,5.104,129.277,5.109z M139.159,5.289c-0.118-0.118-0.28-0.185-0.448-0.185
                        h-2.338V0.633c0-0.166-0.066-0.33-0.186-0.447C136.07,0.069,135.906,0,135.74,0h-1.863c-0.169,0-0.331,0.068-0.447,0.186
                        c-0.119,0.118-0.187,0.281-0.187,0.448v4.471h-2.034c-0.167,0-0.329,0.066-0.447,0.185s-0.186,0.28-0.186,0.448v1.497
                        c0,0.168,0.066,0.328,0.186,0.447c0.12,0.12,0.278,0.185,0.447,0.185h2.034v11.489c0,0.168,0.068,0.329,0.187,0.447
                        c0.116,0.117,0.279,0.186,0.447,0.186h1.863c0.166,0,0.33-0.068,0.447-0.186c0.119-0.118,0.186-0.279,0.186-0.447V7.865h2.338
                        c0.17,0,0.329-0.065,0.448-0.186c0.12-0.12,0.186-0.278,0.186-0.446V5.736C139.344,5.569,139.277,5.407,139.159,5.289z
                        M61.914,5.313h-0.835c-0.254,0-0.482,0.151-0.581,0.385l-4.688,11.009L51.42,5.711c-0.096-0.24-0.33-0.398-0.588-0.398h-0.783
                        c-0.21,0-0.406,0.104-0.523,0.276c-0.118,0.175-0.143,0.396-0.065,0.59l5.214,13.137l-2.271,5.172
                        c-0.087,0.196-0.068,0.422,0.049,0.602c0.117,0.179,0.315,0.285,0.529,0.285h0.81c0.252,0,0.481-0.15,0.581-0.381l8.122-18.798
                        C62.58,6,62.559,5.777,62.442,5.599C62.325,5.419,62.127,5.313,61.914,5.313z M74.423,0H64.042c-0.167,0-0.329,0.068-0.447,0.186
                        c-0.118,0.118-0.187,0.281-0.187,0.448v18.721c0,0.168,0.068,0.329,0.187,0.447c0.118,0.117,0.28,0.186,0.447,0.186h10.381
                        c0.168,0,0.327-0.065,0.447-0.186c0.119-0.119,0.184-0.277,0.184-0.447v-1.848c0-0.166-0.066-0.329-0.184-0.447
                        c-0.118-0.118-0.281-0.185-0.447-0.185h-7.657v-5.417h7.596c0.17,0,0.328-0.065,0.448-0.185c0.12-0.119,0.185-0.279,0.185-0.447
                        V8.977c0-0.166-0.066-0.329-0.184-0.447c-0.119-0.117-0.282-0.186-0.449-0.186h-7.596v-5.23h7.657c0.168,0,0.327-0.065,0.447-0.186
                        c0.119-0.119,0.184-0.278,0.184-0.446V0.633c0-0.166-0.066-0.33-0.184-0.447C74.752,0.069,74.589,0,74.423,0z M83.984,12.365
                        l4.3-6.272c0.132-0.191,0.147-0.444,0.038-0.652c-0.107-0.207-0.326-0.338-0.56-0.338h-2.226c-0.214,0-0.411,0.107-0.528,0.286
                        L82.1,9.817L79.203,5.39c-0.117-0.179-0.316-0.286-0.53-0.286h-2.277c-0.231,0-0.45,0.13-0.56,0.337
                        c-0.107,0.208-0.093,0.46,0.039,0.653l4.316,6.272l-4.519,6.631c-0.133,0.193-0.146,0.444-0.036,0.652
                        c0.109,0.207,0.324,0.336,0.56,0.336h2.284c0.214,0,0.41-0.106,0.528-0.285l3.091-4.683l3.094,4.683
                        c0.117,0.179,0.314,0.285,0.527,0.285h2.277c0.234,0,0.449-0.131,0.559-0.337c0.109-0.208,0.095-0.459-0.037-0.652L83.984,12.365z
                        M12.202,5.946c-0.012,1.887-0.832,3.727-2.156,4.709c-1.383,1.024-2.861,1.242-5.054,1.235H2.032v7.464
                        c0,0.168-0.066,0.328-0.186,0.448c-0.12,0.119-0.279,0.184-0.447,0.184H0.64c-0.168,0-0.328-0.064-0.448-0.184
                        c-0.119-0.12-0.185-0.28-0.185-0.448V0.633c0-0.167,0.068-0.33,0.185-0.447C0.311,0.068,0.473,0,0.64,0h4.352v0
                        c2.24-0.007,3.677,0.215,5.052,1.235C11.37,2.218,12.19,4.059,12.202,5.946z M10.178,5.972c0.016-1.291-0.606-2.646-1.286-3.109
                        C7.951,2.179,7.115,1.958,5.018,1.949H2.032v7.994h2.986c2.07-0.008,2.938-0.233,3.878-0.917
                        C9.573,8.567,10.194,7.204,10.178,5.972z M119.226,6.84c1.532,1.473,2.352,3.473,2.35,5.568c0,0.241-0.012,0.482-0.033,0.724
                        c-0.027,0.327-0.301,0.579-0.63,0.579H109.21c0.184,1.181,0.761,2.042,1.522,2.67c0.896,0.733,2.067,1.102,3.07,1.099
                        c0,0,0.002,0,0.011,0c0.006,0,0.014,0,0.025,0c1.892-0.012,3.318-0.899,4.104-2.454c0.105-0.215,0.326-0.351,0.566-0.351h1.948
                        c0.214,0,0.411,0.106,0.528,0.285c0.117,0.178,0.137,0.401,0.053,0.598c-0.68,1.575-1.734,2.794-2.998,3.604
                        c-1.256,0.803-2.719,1.195-4.219,1.195h-0.019c-2.521,0-4.507-1.032-5.817-2.521c-1.315-1.493-1.979-3.42-1.98-5.261
                        c-0.002-2.159,0.836-4.126,2.235-5.548c1.396-1.425,3.359-2.304,5.573-2.303C115.88,4.724,117.804,5.483,119.226,6.84z
                        M118.34,10.951c-0.443-1.747-2.245-3.368-4.369-3.352c-0.05,0-0.087,0.001-0.112,0.003l-0.037,0.001
                        c-2.344,0.003-4.084,1.502-4.533,3.348H118.34z M20.925,13.942c-0.295,0-0.583-0.02-0.867-0.05
                        c-1.012,0.944-2.77,2.232-4.879,2.289c1.514-1.012,2.157-2.129,2.422-2.984c-1.463-0.73-2.422-1.927-2.422-3.283
                        c0-2.225,2.573-4.028,5.746-4.028c1.202,0,2.318,0.26,3.24,0.703c-1.303-1.013-2.967-1.604-4.801-1.603
                        c-2.171-0.001-4.104,0.825-5.484,2.198c-1.381,1.373-2.209,3.293-2.208,5.442v0.025c-0.001,2.171,0.825,4.1,2.208,5.473
                        c1.38,1.376,3.315,2.194,5.484,2.192h0.007c2.166,0,4.098-0.817,5.477-2.192c1.382-1.374,2.208-3.302,2.207-5.473
                        c0-0.88-0.139-1.722-0.395-2.504C26.488,12.262,23.987,13.942,20.925,13.942z M97.679,4.724c-0.963-0.004-1.853,0.16-2.657,0.458
                        c0.023,0,0.046,0,0.069,0c3.174,0,5.746,1.803,5.746,4.028c0,1.355-0.959,2.552-2.423,3.282c0.267,0.855,0.908,1.974,2.423,2.984
                        c-2.109-0.057-3.867-1.343-4.88-2.288c-0.283,0.03-0.571,0.05-0.866,0.05c-2.591,0-4.782-1.204-5.498-2.857v14.127
                        c0,0.167,0.068,0.33,0.186,0.447c0.118,0.117,0.281,0.186,0.448,0.186h1.864c0.17,0,0.328-0.066,0.449-0.186
                        c0.117-0.119,0.184-0.277,0.184-0.447v-6.026c1.104,1.048,2.789,1.879,4.962,1.884c2.278,0.004,4.206-0.91,5.536-2.343
                        c1.334-1.432,2.08-3.372,2.08-5.431c-0.002-1.893-0.643-3.839-1.924-5.342C102.099,5.748,100.15,4.719,97.679,4.724z"/>
                    <g>
                        <rect y="28.729" fill="#B81188" width="0.938" height="4.652"/>
                        <g>
                            <path fill="#B81188" d="M1.715,29.93H2.49c0,0.146,0,0.297-0.001,0.461c0.146-0.271,0.456-0.574,1.07-0.57
                                c1.131,0.006,1.346,0.873,1.346,1.476v2.086H4.078v-1.878c0-0.512-0.172-0.911-0.75-0.906c-0.585,0.002-0.787,0.393-0.787,0.88
                                v1.904H1.715V29.93z"/>
                            <path fill="#B81188" d="M7.121,30.863C7.116,30.68,7.02,30.487,6.7,30.489c-0.278,0-0.41,0.18-0.405,0.35
                                c0.004,0.218,0.258,0.336,0.583,0.431c0.553,0.153,1.149,0.314,1.159,1.058c0.007,0.713-0.587,1.164-1.31,1.164
                                c-0.534,0-1.261-0.277-1.322-1.118h0.821c0.034,0.374,0.355,0.45,0.51,0.45c0.257,0,0.478-0.168,0.474-0.404
                                c-0.003-0.295-0.26-0.373-0.894-0.596c-0.466-0.146-0.843-0.403-0.847-0.9c0-0.68,0.573-1.102,1.239-1.102
                                c0.465,0.002,1.159,0.211,1.225,1.042H7.121z"/>
                            <path fill="#B81188" d="M11.729,33.381h-0.774c0-0.15,0-0.301,0.001-0.459c-0.146,0.273-0.458,0.572-1.077,0.568
                                C8.745,33.485,8.53,32.62,8.53,32.015V29.93h0.827v1.877c0,0.514,0.172,0.912,0.753,0.908c0.59-0.002,0.792-0.393,0.792-0.881
                                V29.93h0.828V33.381z"/>
                            <path fill="#B81188" d="M12.463,29.93h0.779v0.452c0.154-0.308,0.435-0.558,0.96-0.562v0.829H14.16
                                c-0.578,0-0.871,0.275-0.871,0.871v1.861h-0.827V29.93z"/>
                            <path fill="#B81188" d="M15.161,31.669c0.001,0.429,0.31,1.052,1.05,1.052c0.458,0,0.762-0.24,0.915-0.553
                                c0.083-0.15,0.125-0.313,0.132-0.481c0.01-0.169-0.024-0.337-0.094-0.49c-0.142-0.323-0.462-0.604-0.961-0.604
                                c-0.666,0-1.042,0.539-1.042,1.072V31.669z M18.079,33.381h-0.826v-0.498c-0.218,0.402-0.681,0.607-1.164,0.607
                                c-1.104,0-1.754-0.862-1.754-1.837c0-1.087,0.788-1.833,1.754-1.833c0.631,0,1.016,0.332,1.164,0.608V29.93h0.826V33.381z"/>
                            <path fill="#B81188" d="M18.819,29.93h0.774c0,0.146,0,0.297,0,0.461c0.146-0.271,0.456-0.574,1.071-0.57
                                c1.129,0.006,1.344,0.873,1.344,1.476v2.086h-0.827v-1.878c0-0.512-0.171-0.911-0.748-0.906c-0.585,0.002-0.788,0.393-0.788,0.88
                                v1.904h-0.826V29.93z"/>
                            <path fill="#B81188" d="M26.151,32.124c-0.2,0.765-0.883,1.363-1.778,1.363c-1.09,0-1.847-0.848-1.847-1.837
                                c0-1.015,0.784-1.829,1.82-1.829c0.864,0,1.604,0.551,1.802,1.39h-0.841c-0.149-0.364-0.48-0.62-0.93-0.618
                                c-0.303-0.008-0.535,0.096-0.735,0.296c-0.177,0.184-0.285,0.463-0.285,0.766c0,0.618,0.433,1.062,1.017,1.062
                                c0.515-0.002,0.806-0.307,0.931-0.592H26.151z"/>
                            <path fill="#B81188" d="M29.275,31.296c-0.028-0.323-0.376-0.782-0.977-0.774c-0.612,0-0.947,0.441-0.979,0.774H29.275z
                                M27.308,31.932c0.037,0.551,0.529,0.855,0.999,0.854c0.405,0,0.672-0.188,0.832-0.465h0.849c-0.165,0.381-0.408,0.673-0.7,0.867
                                c-0.288,0.198-0.629,0.299-0.983,0.299c-1.064,0-1.826-0.858-1.826-1.822c0-0.98,0.768-1.844,1.813-1.844
                                c0.513,0,0.964,0.195,1.289,0.526c0.422,0.437,0.575,0.981,0.505,1.585H27.308z"/>
                            <path fill="#B81188" d="M35.372,31.652c-0.002-0.494-0.357-1.06-1.056-1.06c-0.301,0-0.564,0.119-0.752,0.313
                                c-0.183,0.189-0.294,0.461-0.294,0.76c0,0.296,0.113,0.561,0.302,0.749c0.188,0.188,0.452,0.306,0.749,0.306
                                c0.627,0,1.051-0.496,1.051-1.061V31.652z M32.48,29.93h0.78v0.504c0.244-0.348,0.643-0.613,1.22-0.613
                                c1.202,0,1.716,1.027,1.716,1.861c0,0.984-0.698,1.809-1.712,1.809c-0.672,0-1.04-0.35-1.178-0.565v0.457v1.206H32.48V29.93z"/>
                            <path fill="#B81188" d="M37.398,31.654c0,0.603,0.458,1.052,1.021,1.052c0.565,0,1.022-0.449,1.022-1.059
                                c0-0.607-0.458-1.057-1.022-1.057c-0.564,0-1.021,0.449-1.021,1.057V31.654z M36.571,31.647c0-0.883,0.646-1.822,1.849-1.822
                                c1.204,0,1.85,0.939,1.85,1.829c0,0.891-0.646,1.829-1.85,1.829c-1.202,0-1.849-0.938-1.849-1.829V31.647z"/>
                        </g>
                        <polygon fill="#B81188" points="40.367,29.93 41.226,29.93 41.91,32.333 42.571,30.045 43.266,30.045 43.941,32.333 44.614,29.93 
                            45.47,29.93 44.361,33.382 43.585,33.382 42.912,31.047 42.25,33.382 41.475,33.382 		"/>
                        <g>
                            <path fill="#B81188" d="M48.331,31.296c-0.028-0.323-0.376-0.782-0.977-0.774c-0.613,0-0.948,0.441-0.979,0.774H48.331z
                                M46.364,31.932c0.037,0.551,0.529,0.855,1,0.854c0.404,0,0.67-0.188,0.831-0.465h0.851c-0.166,0.381-0.409,0.673-0.701,0.867
                                c-0.288,0.198-0.629,0.299-0.983,0.299c-1.063,0-1.824-0.858-1.824-1.822c0-0.98,0.766-1.844,1.813-1.844
                                c0.512,0,0.963,0.195,1.289,0.526c0.422,0.437,0.574,0.981,0.505,1.585H46.364z"/>
                            <path fill="#B81188" d="M49.677,29.93h0.779v0.452c0.155-0.308,0.435-0.558,0.96-0.562v0.829h-0.041
                                c-0.58,0-0.872,0.275-0.872,0.871v1.861h-0.827V29.93z"/>
                            <path fill="#B81188" d="M54.316,31.296c-0.027-0.323-0.376-0.782-0.977-0.774c-0.612,0-0.946,0.441-0.979,0.774H54.316z
                                M52.349,31.932c0.037,0.551,0.529,0.855,0.999,0.854c0.406,0,0.672-0.188,0.833-0.465h0.85
                                c-0.165,0.381-0.408,0.673-0.701,0.867c-0.288,0.198-0.628,0.299-0.982,0.299c-1.065,0-1.826-0.858-1.826-1.822
                                c0-0.98,0.768-1.844,1.813-1.844c0.512,0,0.963,0.195,1.289,0.526c0.421,0.437,0.574,0.981,0.504,1.585H52.349z"/>
                            <path fill="#B81188" d="M56.339,31.651c0,0.588,0.417,1.064,1.056,1.064c0.298,0,0.572-0.119,0.765-0.314
                                c0.194-0.195,0.307-0.466,0.295-0.77c0-0.3-0.119-0.559-0.308-0.742c-0.189-0.188-0.452-0.297-0.748-0.297
                                c-0.67,0-1.061,0.516-1.061,1.053V31.651z M59.234,33.381h-0.789v-0.498c-0.151,0.258-0.478,0.604-1.183,0.604
                                c-1.066,0-1.75-0.826-1.75-1.837c0-1.116,0.802-1.829,1.736-1.829c0.664,0,1.03,0.352,1.159,0.551v-0.503v-1.141h0.826V33.381z"
                            />
                            <path fill="#B81188" d="M64.691,31.647c0-0.567-0.433-1.055-1.037-1.055c-0.639,0-1.045,0.502-1.045,1.057
                                c0,0.58,0.406,1.07,1.027,1.07c0.669,0,1.053-0.532,1.055-1.065V31.647z M61.816,28.728h0.826v1.212v0.419
                                c0.339-0.426,0.839-0.535,1.149-0.538c0.816,0,1.727,0.6,1.727,1.818c0,1.168-0.821,1.852-1.759,1.852
                                c-0.544,0-0.966-0.264-1.149-0.603v0.493h-0.793V28.728z"/>
                        </g>
                        <polygon fill="#B81188" points="66.853,33.249 65.552,29.93 66.435,29.93 67.277,32.222 68.105,29.93 68.986,29.93 67.17,34.588 
                            66.291,34.588 		"/>
                        <g>
                            <path fill="#B81188" d="M74.055,31.652c-0.003-0.494-0.358-1.06-1.056-1.06c-0.3,0-0.563,0.119-0.753,0.313
                                c-0.183,0.189-0.294,0.461-0.294,0.76c0,0.296,0.113,0.561,0.303,0.749c0.188,0.188,0.452,0.306,0.748,0.306
                                c0.628,0,1.052-0.496,1.052-1.061V31.652z M71.165,29.93h0.778v0.504c0.246-0.348,0.645-0.613,1.222-0.613
                                c1.202,0,1.716,1.027,1.716,1.861c0,0.984-0.697,1.809-1.712,1.809c-0.673,0-1.04-0.35-1.177-0.565v0.457v1.206h-0.827V29.93z"/>
                            <path fill="#B81188" d="M78.038,31.296c-0.028-0.323-0.376-0.782-0.978-0.774c-0.611,0-0.945,0.441-0.979,0.774H78.038z
                                M76.072,31.932c0.035,0.551,0.527,0.855,0.998,0.854c0.404,0,0.672-0.188,0.832-0.465h0.851
                                c-0.165,0.381-0.409,0.673-0.701,0.867c-0.288,0.198-0.629,0.299-0.982,0.299c-1.064,0-1.825-0.858-1.825-1.822
                                c0-0.98,0.766-1.844,1.813-1.844c0.513,0,0.964,0.195,1.289,0.526c0.422,0.437,0.573,0.981,0.504,1.585H76.072z"/>
                            <path fill="#B81188" d="M80.06,31.654c0,0.603,0.458,1.052,1.022,1.052c0.565,0,1.022-0.449,1.022-1.059
                                c0-0.607-0.457-1.057-1.022-1.057c-0.564,0-1.022,0.449-1.022,1.057V31.654z M79.234,31.647c0-0.883,0.646-1.822,1.849-1.822
                                s1.849,0.939,1.849,1.829c0,0.891-0.646,1.829-1.849,1.829s-1.849-0.938-1.849-1.829V31.647z"/>
                            <path fill="#B81188" d="M86.373,31.652c-0.002-0.494-0.357-1.06-1.056-1.06c-0.301,0-0.563,0.119-0.752,0.313
                                c-0.183,0.189-0.294,0.461-0.294,0.76c0,0.296,0.113,0.561,0.302,0.749s0.454,0.306,0.749,0.306c0.627,0,1.051-0.496,1.051-1.061
                                V31.652z M83.483,29.93h0.778v0.504c0.245-0.348,0.645-0.613,1.221-0.613c1.203,0,1.717,1.027,1.717,1.861
                                c0,0.984-0.699,1.809-1.713,1.809c-0.672,0-1.038-0.35-1.178-0.565v0.457v1.206h-0.825V29.93z"/>
                        </g>
                        <rect x="87.759" y="28.728" fill="#B81188" width="0.825" height="4.653"/>
                        <g>
                            <path fill="#B81188" d="M91.917,31.296c-0.029-0.323-0.377-0.782-0.978-0.774c-0.612,0-0.947,0.441-0.979,0.774H91.917z
                                M89.95,31.932c0.037,0.551,0.529,0.855,0.999,0.854c0.404,0,0.672-0.188,0.832-0.465h0.852c-0.166,0.381-0.41,0.673-0.701,0.867
                                c-0.288,0.198-0.631,0.299-0.984,0.299c-1.064,0-1.824-0.858-1.824-1.822c0-0.98,0.767-1.844,1.813-1.844
                                c0.512,0,0.963,0.195,1.289,0.526c0.421,0.437,0.574,0.981,0.504,1.585H89.95z"/>
                        </g>
                    </g>
                </g>
            </svg>           
        </fo:instream-foreign-object>
    </xsl:template>
    
    <xsl:template name="Header">
        <fo:table table-layout="fixed" width="190mm">
            <fo:table-column column-width="10mm" column-number="1"/>
            <fo:table-column column-width="95mm" column-number="2"/>
            <fo:table-column column-width="95mm" column-number="3"/>
            <fo:table-column column-width="10mm" column-number="4"/>
            <fo:table-body>
                <fo:table-row>
                    <!-- spacer column -->
                    <fo:table-cell>
                        <fo:block/>
                    </fo:table-cell>
                    <fo:table-cell>
                        <fo:block text-align="left">
                            <xsl:if test="lower-case(document-details/stationery/header) = 'y'">
                                <xsl:call-template name="PolicyExpertLogo"/>
                            </xsl:if>
                        </fo:block>
                    </fo:table-cell>
                    <fo:table-cell>
                        <fo:block text-align="right">
                            <xsl:if test="$insurerLogoName != ''">
                                <!-- image path needs to be relative to the stylesheet -->
                                <fo:external-graphic src="res:document/template/common_assets/insurer/{$insurerLogoName}/blackandwhite/img_logo.png" content-height="11mm"/>
                            </xsl:if>
                        </fo:block>
                    </fo:table-cell>
                    <!-- spacer column -->
                    <fo:table-cell>
                        <fo:block/>
                    </fo:table-cell>
                </fo:table-row>                            
            </fo:table-body>
        </fo:table>
    </xsl:template>
        
    <xsl:template name="Footer">
        <xsl:choose>
            <xsl:when test="lower-case(document-details/stationery/footer) = 'y'">
                <fo:block-container display-align="after" block-progression-dimension="100%" >                        
                    <fo:block-container absolute-position="absolute" block-progression-dimension="100%" inline-progression-dimension="100%" left="0" top="0">
                        <fo:block>
                            <fo:external-graphic src="res:document/template/schedule/assets/bottom-of-page.png" content-width="210mm"/>
                        </fo:block>
                    </fo:block-container>
                    <fo:block-container absolute-position="absolute" block-progression-dimension="100%" inline-progression-dimension="100%" left="0" top="0">
                        <xsl:apply-templates select="footer"/>
                    </fo:block-container>
                </fo:block-container>      
            </xsl:when>
            <xsl:otherwise>
                <fo:block/>
            </xsl:otherwise>
        </xsl:choose>        
    </xsl:template>

</xsl:stylesheet>
