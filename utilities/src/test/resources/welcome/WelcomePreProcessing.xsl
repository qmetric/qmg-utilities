<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:xs="http://www.w3.org/2001/XMLSchema" exclude-result-prefixes="xs" version="2.0">
    <xd:doc xmlns:xd="http://www.oxygenxml.com/ns/doc/xsl" scope="stylesheet">
        <xd:desc>
            <xd:p><xd:b>Created on:</xd:b> May 9, 2011</xd:p>
            <xd:p><xd:b>Author:</xd:b> Mike Kelly</xd:p>
            <xd:p>Welcome Letter Pre-Processing</xd:p>
            <xd:p>MK23052011 - Amended address line mapping to check for empty nodes for story 13689721</xd:p>
        </xd:desc>
    </xd:doc>
    
    <xsl:template match="node()|@*">
        <xsl:copy>
            <xsl:apply-templates select="node()|@*"/>
        </xsl:copy>
    </xsl:template>
    
    <!-- Build document sections -->
    <xsl:template match="/*">
        <welcome-letter>
            <xsl:call-template name="DocumentDetails"/>
            <xsl:call-template name="DocumentTitle"/>
            <xsl:call-template name="Recipient"/>
            <xsl:call-template name="Reference"/>
            <xsl:call-template name="Salutation"/>
            <xsl:call-template name="LetterBody"/>
            <xsl:call-template name="Valediction"/>
            <xsl:call-template name="Footer"/>
        </welcome-letter>
    </xsl:template>
    
    <xsl:template name="DocumentDetails">
        <document-details>
            <product>
                <xsl:value-of select="policyDetails/product"/>
            </product>
            <external-images>
                <xsl:value-of select="resources/baseAssetPath"/>
            </external-images>
        </document-details>
    </xsl:template>
    
    <xsl:template name="DocumentTitle">
        <title>Hello and Welcome</title>
    </xsl:template>
    
    <xsl:template name="Recipient">
        <recipient>
            <name>
                <xsl:apply-templates select="userAccountDetails/title"/>
                <xsl:apply-templates select="userAccountDetails/firstName"/>
                <xsl:apply-templates select="userAccountDetails/lastName"/>
            </name>
            <address>
                <xsl:if test="correspondenceAddressDetails/propertyNumber != '' and correspondenceAddressDetails/addressLine1 != ''">
                    <line>
                        <xsl:if test="correspondenceAddressDetails/propertyNumber != ''">
                            <xsl:apply-templates select="correspondenceAddressDetails/propertyNumber"/>
                            <xsl:text> </xsl:text>
                        </xsl:if>
                        <xsl:apply-templates select="correspondenceAddressDetails/addressLine1[. != '']"/>
                    </line>
                </xsl:if>
                <xsl:apply-templates select="correspondenceAddressDetails/addressLine2[. != '']"/>
                <xsl:apply-templates select="correspondenceAddressDetails/town[. != '']"/>
                <xsl:apply-templates select="correspondenceAddressDetails/county[. != '']"/>
                <xsl:apply-templates select="correspondenceAddressDetails/postcode[. != '']"/>
            </address>
        </recipient>
    </xsl:template>
    
    <xsl:template name="Reference">
        <reference>
            <policy-online>
                <line>To access your online account:</line>
                <line>
                    <xsl:text>visit </xsl:text>
                    <xsl:apply-templates select="contactDetails/siteUrl"/>
                    <xsl:text> and log in</xsl:text>
                </line>
            </policy-online>
            <policy-change>
                <line>Need to change your policy?</line>
                <line>
                    <xsl:text>Call: </xsl:text>
                    <xsl:apply-templates select="contactDetails/helpline"/>
                </line>
            </policy-change>
            <email-address>
                <line>Email us on:</line>
                <line>
                    <xsl:apply-templates select="contactDetails/companyEmail"/>
                </line>
            </email-address>
            <account-number>
                <label>Your customer account reference:</label>
                <number>
                    <xsl:apply-templates select="userAccountDetails/customerId"/>
                </number>
            </account-number>            
        </reference>        
    </xsl:template>
    
    <xsl:template name="Salutation">
        <salutation>
            <greeting>Dear</greeting>
            <name>
                <xsl:apply-templates select="userAccountDetails/title"/>
                <xsl:apply-templates select="userAccountDetails/lastName"/>
                <xsl:text>,</xsl:text>
            </name>
        </salutation>
    </xsl:template>
    
    <xsl:template name="LetterBody">
        <letter-body>
            <paragraph>
                <para>
                    <xsl:text>We're delighted you've chosen to purchase your </xsl:text>
                    <xsl:apply-templates select="policyDetails/product"/>
                    <xsl:text> insurance with Policy Expert.</xsl:text>
                </para>
            </paragraph>
            
            <paragraph>
                <para>
                    <xsl:text>Please find enclosed your policy schedule along with your invoice. All of your policy documents are securely stored in your online
                    account which can be accessed at any time by visiting </xsl:text>
                    <url>
                        <xsl:apply-templates select="contactDetails/myAccountUrl"/>
                    </url>
                    <xsl:text> and entering your email address and the password you used when you set up your account.</xsl:text>
                </para>
            </paragraph>
            
            <paragraph>
                <para><emphasis>It's important to check through all of these documents carefully to understand exactly what's covered and any restrictions that may apply.</emphasis></para>
            </paragraph>
            
            <paragraph>
                <table>
                    <title>Brief summary of what you've bought</title>
                    <table-header>
                        <row>
                            <entry>Product Name</entry>
                            <entry>Policy Number</entry>
                            <entry>Insurer</entry>
                            <entry>Policy Start Date</entry>
                            <entry>Policy Expiry Date</entry>
                            <entry>If you need to claim</entry>
                        </row>
                    </table-header>
                    <table-body>
                        <!-- will need to handle multiple policies, asked Matt for more samples -->
                        <xsl:apply-templates select="policyDetails"/>
                    </table-body>
                </table>
            </paragraph>
            
            <paragraph>
                <para>Please get in touch if you need to make changes to your policy or update your information.</para>
            </paragraph>
            
            <paragraph>
                <para>
                    <xsl:text>Through your online account you can also join our interactive community and give us your feedback, at </xsl:text>
                    <url>
                        <xsl:apply-templates select="contactDetails/communityInfo"/>
                    </url>
                </para>
            </paragraph>
            
            <paragraph>
                <para>Your opinions really do shape our service, so please let us know your thoughts.</para>
                <para>We look forward to looking after your insurance needs for many years to come.</para>
            </paragraph>
        </letter-body>
    </xsl:template>
    
    <xsl:template name="Valediction">
        <valediction>
            <sign-off>Kind Regards</sign-off>
            <xsl:apply-templates select="signOffDetails/name"/>
            <xsl:apply-templates select="signOffDetails/title"/>
        </valediction>
    </xsl:template>
    
    <xsl:template name="Footer">
        <footer>
            <para>
                <xsl:apply-templates select="contactDetails/companyTradingName"/>
            </para>
            <para>
                <xsl:apply-templates select="contactDetails/companyNumber"/>
            </para>
            <para>
                <xsl:apply-templates select="contactDetails/companyFsaInfo"/>
            </para>
            <para>
                <xsl:apply-templates select="contactDetails/companyFsaNumber"/>
                <xsl:text> Visit www.fsa.gov.uk/Pages/Register for more information.</xsl:text>
            </para>
        </footer>
    </xsl:template>
    
    <xsl:template match="policyDetails">
        <xsl:apply-templates/>
    </xsl:template>
    
    <xsl:template match="policyDetail">
        <row>
            <entry>
                <xsl:apply-templates select="product"/>
                <xsl:text> Insurance - </xsl:text>
                <xsl:apply-templates select="productClass"/>
            </entry>
            <entry>
                <xsl:apply-templates select="policyNumber"/>
            </entry>
            <entry>
                <xsl:apply-templates select="insurerName"/>
            </entry>
            <entry>
                <xsl:apply-templates select="startDate"/>
                <xsl:text> at </xsl:text>
                <xsl:apply-templates select="startTime"/>
            </entry>
            <entry>
                <xsl:apply-templates select="expiryDate"/>
                <xsl:text> at </xsl:text>
                <xsl:apply-templates select="expiryTime"/>
            </entry>
            <entry>
                <xsl:text>Please telephone </xsl:text>
                <xsl:apply-templates select="/welcomeLetterDocument/claimsTelephoneNumber"/>
                <xsl:text> and quote your policy number</xsl:text>
            </entry>
        </row>
    </xsl:template>
    
    <xsl:template match="userAccountDetails/title">
        <xsl:apply-templates/>
        <xsl:text> </xsl:text>
    </xsl:template>
    
    <xsl:template match="userAccountDetails/firstName">
        <xsl:apply-templates/>
        <xsl:text> </xsl:text>
    </xsl:template>
    
    <xsl:template match="userAccountDetails/lastName">
        <xsl:apply-templates/>
    </xsl:template>
    
    <xsl:template match="propertyNumber|addressLine1">
        <xsl:apply-templates/>
    </xsl:template>
    
    <xsl:template match="addressLine2">
        <line>
            <xsl:apply-templates/>
        </line>
    </xsl:template>
    
    <xsl:template match="town">
        <line>
            <xsl:apply-templates/>
        </line>
    </xsl:template>
    
    <xsl:template match="county">
        <line>
            <xsl:apply-templates/>
        </line>
    </xsl:template>
    
    <xsl:template match="postcode">
        <line>
            <xsl:apply-templates/>
        </line>
    </xsl:template>
    
    <xsl:template match="helpline">
        <xsl:copy-of select="."/>
    </xsl:template>

    <xsl:template match="claimsTelephoneNumber">
        <xsl:apply-templates/>
    </xsl:template>
    
    <xsl:template match="customerId">
        <xsl:apply-templates/>
    </xsl:template>
    
    <xsl:template match="product">
        <xsl:apply-templates/>
    </xsl:template>
    
    <xsl:template match="productClass">
        <xsl:apply-templates/>
    </xsl:template>
    
    <xsl:template match="policyNumber">
        <xsl:apply-templates/>
    </xsl:template>
    
    <xsl:template match="myAccountUrl">
        <xsl:apply-templates/>
    </xsl:template>
    
    <xsl:template match="insurerName">
        <xsl:apply-templates/>
    </xsl:template>
    
    <xsl:template match="startDate">
        <xsl:apply-templates/>
    </xsl:template>
    
    <xsl:template match="startTime">
        <xsl:apply-templates/>
    </xsl:template>
    
    <xsl:template match="expiryTime">
        <xsl:apply-templates/>
    </xsl:template>
    
    <xsl:template match="expiryDate">
        <xsl:apply-templates/>
    </xsl:template>
    
    <xsl:template match="communityInfo">
        <xsl:apply-templates/>
    </xsl:template>
    
    <xsl:template match="companyTradingName">
        <xsl:apply-templates/>
    </xsl:template>
    
    <xsl:template match="companyNumber">
        <xsl:apply-templates/>
    </xsl:template>
    
    <xsl:template match="companyEmail">
        <xsl:apply-templates/>
    </xsl:template>
    
    <xsl:template match="companyFsaInfo">
        <xsl:apply-templates/>
    </xsl:template>
    
    <xsl:template match="companyFsaNumber">
        <xsl:apply-templates/>
    </xsl:template>
    
    <xsl:template match="signOffDetails/name">
        <name>
            <xsl:attribute name="signature-img">
                <xsl:value-of select="../signatureImageName"/>
            </xsl:attribute>
            <xsl:apply-templates/>
        </name>
    </xsl:template>
    
    <xsl:template match="signOffDetails/title">
        <job-title>
            <xsl:apply-templates/>
        </job-title>
    </xsl:template>
</xsl:stylesheet>
