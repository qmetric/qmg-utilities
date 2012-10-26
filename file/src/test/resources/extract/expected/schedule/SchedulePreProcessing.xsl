<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
    xmlns:xs="http://www.w3.org/2001/XMLSchema" exclude-result-prefixes="xs" version="2.0">
    <xd:doc xmlns:xd="http://www.oxygenxml.com/ns/doc/xsl" scope="stylesheet">
        <xd:desc>
            <xd:p><xd:b>Created on:</xd:b> May 4, 2011</xd:p>
            <xd:p><xd:b>Author:</xd:b> Mike Kelly</xd:p>
            <xd:p>Creates content for PolicySchedule.xsl</xd:p>
            <xd:p>MK24052011 - Address mapping updated</xd:p>
            <xd:p>MK23052011 - Added footer mapping</xd:p>
            <xd:p>MK20052011 - Fixed Bug #13633181, corrected spelling of specific in the Endorsements section sub-title</xd:p>
            <xd:p>MK19052011 - Corrected policy details mapping and added postal address, which is currently hard-coded</xd:p>
        </xd:desc>
    </xd:doc>

    <xsl:template match="node()|@*">
        <xsl:copy>
            <xsl:apply-templates select="node()|@*"/>
        </xsl:copy>
    </xsl:template>

    <!-- Build document sections -->
    <xsl:template match="/*">
        <policy-schedule>
            <xsl:call-template name="DocumentDetails"/>
            
            <xsl:call-template name="DocumentTitle"/>

            <xsl:call-template name="ReferenceSection"/>

            <xsl:call-template name="PolicyInfo"/>
            
            <xsl:call-template name="PolicyholderInfo"/>           

            <xsl:call-template name="PropertyInfo"/>
            
            <xsl:call-template name="ContactSection"/>
            
            <xsl:if test="buildingsCoverElements[count(*)gt 0] or contentsCoverElements[count(*)gt 0]">
                <xsl:call-template name="StandardCover"/>
            </xsl:if>
                        
            <xsl:if test="additionalItems[count(*)gt 0] or valuableItems[count(*)gt 0] or possessionsStandardCover[count(*)gt 0]">
                <xsl:call-template name="PersonalPossessions"/>
            </xsl:if>

            <xsl:apply-templates select="endorsements[count(*)gt 0]"/>           
            
            <xsl:call-template name="Notices"/>
            
            <xsl:apply-templates select="changesFromPreviousSchedule[count(*)gt 0]"/>
            
            <xsl:call-template name="Faqs"/>
            
            <xsl:call-template name="Footer"/>
        </policy-schedule>
    </xsl:template>
    
    <xsl:template name="DocumentDetails">
        <document-details>
            <product>
                <xsl:value-of select="scheduleDetails/policyDetails/policyDetail[1]/product"/>
            </product>
            <external-images>
                <xsl:value-of select="scheduleDetails/resources/baseAssetPath"/>
            </external-images>
            <insurer-logo-name>
                <xsl:value-of select="scheduleDetails/policyDetails/policyDetail/insurer"/>
            </insurer-logo-name>
            <xsl:copy-of select="stationery"/>
        </document-details>
    </xsl:template>
    
    <xsl:template name="DocumentTitle">
        <title>
            <xsl:apply-templates select="scheduleDetails/policyDetails/policyDetail[1]/product"/>
            <xsl:text> Insurance</xsl:text>
        </title>
        <sub-title>schedule</sub-title>
    </xsl:template>
    
    <xsl:template name="ReferenceSection">
        <references>
            <account-number>
                <label>
                    <line>Your Policy Expert</line>
                    <line>account number is:</line>
                </label>
                <number>
                    <xsl:apply-templates select="scheduleDetails/userAccount/customerId"/>
                </number>
            </account-number>
            <policy-number>
                <label>
                    <line>Your policy number is:</line>
                </label>
                <number>
                    <xsl:apply-templates select="scheduleDetails/policyDetails/policyDetail[1]/policyNumber"/>
                </number>
            </policy-number>
        </references>
    </xsl:template>
    
    <xsl:template name="PolicyInfo">
        <policy-information-table>
            <title>Policy information</title>
            <table-body>
                <row>
                    <entry>Insurer name</entry>
                    <entry>
                        <xsl:apply-templates select="scheduleDetails/policyDetails/policyDetail[1]/insurerName"/>
                    </entry>
                </row>
                <row>
                    <entry>Type of cover</entry>
                    <entry>
                        <xsl:apply-templates select="scheduleDetails/policyDetails/policyDetail[1]/product"/>
                        <xsl:text> Insurance - </xsl:text>
                        <xsl:apply-templates select="scheduleDetails/policyDetails/policyDetail[1]/productClass"
                        />
                    </entry>
                </row>
                <row>
                    <entry>Period of cover</entry>
                    <entry>
                        <xsl:text>from </xsl:text>
                        <xsl:apply-templates select="scheduleDetails/policyDetails/policyDetail[1]/startDate"/>
                        <xsl:text> at </xsl:text>
                        <xsl:apply-templates select="scheduleDetails/policyDetails/policyDetail[1]/startTime"/>
                        <xsl:text> to </xsl:text>
                        <xsl:apply-templates select="scheduleDetails/policyDetails/policyDetail[1]/expiryDate"/>
                        <xsl:text> at </xsl:text>                           
                        <xsl:apply-templates select="scheduleDetails/policyDetails/policyDetail[1]/expiryTime"/>  
                    </entry>
                </row>
                <row>
                    <entry>Reason for issue</entry>
                    <entry>New Policy</entry>
                </row>
                <row>
                    <entry>Policy issue date</entry>
                    <entry>
                        <xsl:apply-templates select="scheduleDetails/policyDetails/policyDetail[1]/issueDate"/>
                    </entry>
                </row>
                <row>
                    <entry>Premium details</entry>
                    <entry>
                        <xsl:text>Total premium </xsl:text>
                        <xsl:apply-templates select="scheduleDetails/policyDetails/policyDetail[1]/totalCost"/>
                        <xsl:text> including Insurance Premium Tax at </xsl:text>
                        <xsl:apply-templates select="scheduleDetails/policyDetails/policyDetail[1]/iptPercentage"/>
                        <xsl:text>%</xsl:text>
                    </entry>
                </row>                    
            </table-body>
        </policy-information-table>
    </xsl:template>
    
    <xsl:template name="PolicyholderInfo">
        <policyholder-info-table>
            <title>Policyholder information</title>
            <table-header>
                <row>
                    <entry>
                        <xsl:apply-templates select="scheduleDetails/policyHolderDefinition"/>
                    </entry>
                </row>
            </table-header>
            <table-body>
                <row>
                    <entry>
                        <xsl:apply-templates select="scheduleDetails/userAccount/title"/>
                        <xsl:apply-templates select="scheduleDetails/userAccount/firstName"/>
                        <xsl:apply-templates select="scheduleDetails/userAccount/lastName"/>                            
                    </entry>
                    <entry>
                        <xsl:apply-templates select="scheduleDetails/policyHolderOccupation"/>
                    </entry>
                </row>
                <xsl:apply-templates select="scheduleDetails/jointPolicyHolder"/>
            </table-body>
        </policyholder-info-table>
    </xsl:template>
    
    <xsl:template name="PropertyInfo">
        <property-information-table>
            <title>Property information</title>
            <table-body>                
                <row>
                    <entry>Address of insured property</entry>
                    <entry>
                        <xsl:apply-templates select="scheduleDetails/riskAddress/singleLineOutput"/>
                    </entry>
                </row>
                <row>
                    <entry>Property details</entry>
                    <entry>
                        <xsl:apply-templates select="scheduleDetails/noOfBedrooms"/>
                        <xsl:text> bedroom </xsl:text>
                        <xsl:apply-templates select="scheduleDetails/propertyType"/>                            
                    </entry>
                </row>
                <row>
                    <entry>Property use</entry>
                    <entry>
                        <xsl:apply-templates select="scheduleDetails/propertyUse"/>
                    </entry>
                </row>
                <row>
                    <entry>Correspondence address</entry>
                    <entry>
                        <xsl:choose>
                            <xsl:when test="scheduleDetails/correspondenceAddressDetails/singleLineOutput != ''">
                                <xsl:apply-templates select="scheduleDetails/correspondenceAddressDetails/singleLineOutput"/>   
                            </xsl:when>
                            <xsl:otherwise>
                                <xsl:apply-templates select="scheduleDetails/riskAddress/singleLineOutput"/>
                            </xsl:otherwise>
                        </xsl:choose>
                    </entry>
                </row>
            </table-body>
        </property-information-table>
    </xsl:template>   
    
    <xsl:template name="ContactSection">
        <contact-details>
            <claim>
                <para>Need to make a claim?</para>
                <para>
                    <xsl:text>Call us on </xsl:text>
                    <xsl:apply-templates select="claimsTelephoneNumber"/>                    
                </para>
                <para>
                    <xsl:text>quoting policy </xsl:text>
                    <xsl:apply-templates select="scheduleDetails/policyDetails/policyDetail[1]/policyNumber"/>
                </para>
            </claim>
            <policy>
                <para>Need to change your policy?</para>
                <para>
                    <xsl:text>Call us on </xsl:text>
                    <xsl:apply-templates select="contactDetails/helpline"/>                    
                </para>
                <para>
                    <xsl:text>quoting reference </xsl:text>
                    <xsl:apply-templates select="scheduleDetails/userAccount/customerId"/>    
                </para>
            </policy>
            <postal>
                <para>Or simply write to us at:</para>
                <address>
                    <line>Policy Expert</line>
                    <line>Silbury Court East</line>
                    <line>402 - 420 Silbury Boulevard</line>
                    <line>Milton Keynes</line>
                    <line>MK9 2AF</line>
                </address>
            </postal>
        </contact-details>
    </xsl:template>
    
    <xsl:template name="StandardCover">
        <policy-standard-cover-table>
            <title>Policy standard cover</title>
            <xsl:if test="buildingsCoverElements[count(*)gt 0]">
                <table-group>
                    <table-header>
                        <row>
                            <entry>Buildings cover</entry>
                            <entry>Located in your policy wording</entry>
                            <entry>Limit</entry>
                        </row>
                    </table-header>
                    <table-body>
                        <xsl:apply-templates select="buildingsCoverElements"/>                       
                    </table-body>
                </table-group>
            </xsl:if>
            <xsl:if test="contentsCoverElements[count(*)gt 0]">
                <table-group>
                    <table-header>
                        <row>
                            <entry>Contents cover</entry>
                            <entry>Located in your policy wording</entry>
                            <entry>Limit</entry>
                        </row>
                    </table-header>
                    <table-body>
                        <xsl:apply-templates select="contentsCoverElements"/>
                    </table-body>
                </table-group>
            </xsl:if>
        </policy-standard-cover-table>
    </xsl:template>
    
    <xsl:template name="PersonalPossessions">
        <personal-possessions>
            <xsl:if test="additionalItems[count(*)gt 0] or valuableItems[count(*)gt 0]">
                <specified-items-table>
                    <title>Valuables and personal items that you have specified</title>
                    <table-header>
                        <row>
                            <entry>Item type</entry>
                            <entry>Item description</entry>
                            <entry>Item value</entry>
                            <entry>home</entry>
                            <entry>away</entry>
                            <entry>distance</entry>
                        </row>
                    </table-header>
                    <table-body>
                        <xsl:apply-templates select="additionalItems"/>
                        <xsl:apply-templates select="valuableItems"/>
                    </table-body>
                </specified-items-table>
            </xsl:if>
            
            <!-- Standard Cover for Possessions -->
            <xsl:apply-templates select="possessionsStandardCover[count(*)gt 0]"/>               
        </personal-possessions>
    </xsl:template>
       
    <xsl:template match="endorsements">
        <endorsements-table>
            <title>Endorsements</title>
            <sub-title>All specific terms, conditions and/or endorsements applied to your cover - to be read in conjunction with your policy wording document</sub-title>
            <table-header>
                <row>
                    <entry>Endorsement no.</entry>
                    <entry>Endorsement title</entry>
                    <entry>Endorsement text</entry>
                </row>
            </table-header>
            <table-body>
                <xsl:apply-templates/>                    
            </table-body>                
        </endorsements-table>
    </xsl:template>
    
    <xsl:template name="Notices">
        <notices-table>
            <title>Notices</title>
            <table-body>
                <!-- static content with dynamic insurer -->
                <row>
                    <entry>Legal Expenses</entry>
                    <entry>
                        <xsl:text>Not insured by </xsl:text>
                        <xsl:apply-templates select="scheduleDetails/policyDetails/policyDetail[1]/insurerName"/>
                        <xsl:text> within this policy. If this cover was purchased as an extra through Policy Expert
                            then you will have a separate policy for this</xsl:text>
                    </entry>
                </row>
                <row>
                    <entry>Home Emergency</entry>
                    <entry>
                        <xsl:text>Not insured by </xsl:text>
                        <xsl:apply-templates select="scheduleDetails/policyDetails/policyDetail[1]/insurerName"/>
                        <xsl:text> within this policy. If this cover was purchased as an extra through Policy Expert
                            then you will have a separate policy for this</xsl:text>
                    </entry>
                </row>
                <row>
                    <entry>Claims &amp; Underwriting Exchange Register</entry>
                    <entry>
                        <para>Insurers pass information to the Claims and Underwriting Exchange Register, run by Insurance Database Services Ltd (IDS
                            Ltd), and other databases. The aim is to help us check information provided and also to prevent fraudulent claims.
                            When you tell us about an incident (such as fire, water damage or theft) which may or may not give rise to a claim, we
                            will pass information relating to it to the register</para>
                        <para>It is a condition of renewing your policy that you agree to the information on your application form and any incidents
                            you tell us about being passed to IDS Ltd.</para>
                        <para>It is also a condition of renewing your policy that you agree that IDS Ltd may pass us information that it has received from
                            other insurers about other incidents involving anyone insured under the policy.
                            You can ask us for more information about this.</para></entry>
                </row>
                <row>
                    <entry>Data Protection Act</entry>
                    <entry>
                        <para>For Data Protection Act purposes, we will hold and process your personal data for insurance administration. For this
                            purpose, the information may also be passed to selected third parties and reinsurers.</para>
                        <para>By renewing this policy you consent to our processing personal data including sensitive data about you and other
                            persons who may be insured under the contract.</para>
                        <para>You understand that all personal data you supply must be accurate, and you have the specific consent of those other
                            persons insured to disclose their personal data.</para>
                    </entry>
                </row>
                <row>
                    <entry>Material Facts</entry>
                    <entry>
                        <para>You are reminded of your obligation to advise the Company of any change in material information previously supplied
                            in relation to this insurance. Failure to do so could result in your insurance being invalidated.</para>
                        <para>Material facts are those which might influence the acceptance or assessment of the insurance. If you are in any doubt
                            as to whether a fact is material you should disclose it.</para>
                    </entry>
                </row>
                <row>
                    <entry>Interested Parties</entry>
                    <entry>You should show this notice to anyone who has an interest in property insured under the policy.</entry>
                </row>
            </table-body>
        </notices-table>
    </xsl:template>
    
    <xsl:template match="changesFromPreviousSchedule">
        <changes-to-schedule-table>
            <title>Change from your previous schedule</title>
            <table-header>
                <row>
                    <entry>Date</entry>
                    <entry>Change of premium</entry>
                    <entry>Detail of change</entry>
                </row>
            </table-header>
            <table-body>
                <xsl:apply-templates/>                    
            </table-body>
        </changes-to-schedule-table>
    </xsl:template>
    
    <xsl:template name="Faqs">
        <faqs>
            <statement>
                <line>It is important to notify us of</line>
                <line>any changes to your circumstances</line>
                <line>or additional cover you may require.</line>
            </statement>
            <contact>
                <xsl:text>Call us on </xsl:text>
                <xsl:apply-templates select="contactDetails/helpline"/>
            </contact>
            <question>
                <line>Some of the answers</line>
                <line>I gave when I took the</line>
                <line>policy have now</line>
                <line>changed</line>
            </question>
            <question>
                <line>I have changed</line>
                <line>my email address</line>
            </question>
            <question>
                <line>I would like to insure</line>
                <line>a ring my boyfriend</line>
                <line>bought for my birthday</line>
            </question>
            <question>
                <line>I have builders in</line>
                <line>doing an extension</line>
                <line>to my property</line>            
            </question>
            <question>
                <line>I have taken</line>
                <line>in a lodger</line> 
            </question>
            <question>
                <line>I recently got married</line>
                <line>and have changed</line>
                <line>my surname</line> 
            </question>
        </faqs>
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
    
    <xsl:template match="companyTradingName">
        <xsl:apply-templates/>
    </xsl:template>
    
    <xsl:template match="companyNumber">
        <xsl:apply-templates/>
    </xsl:template>
    
    <xsl:template match="companyFsaInfo">
        <xsl:apply-templates/>
    </xsl:template>
    
    <xsl:template match="companyFsaNumber">
        <xsl:apply-templates/>
    </xsl:template>

    <xsl:template match="customerId">
        <xsl:apply-templates/>
    </xsl:template>

    <xsl:template match="policyNumber">
        <xsl:apply-templates/>
    </xsl:template>

    <xsl:template match="product">
        <xsl:apply-templates/>
    </xsl:template>

    <xsl:template match="productClass">
        <xsl:apply-templates/>
    </xsl:template>
    
    <xsl:template match="startDate">
        <xsl:apply-templates/>
    </xsl:template>
    
    <xsl:template match="startTime">
        <xsl:apply-templates/>
    </xsl:template>
    
    <xsl:template match="expiryTime">
        <emphasis>
            <xsl:apply-templates/>
        </emphasis>
    </xsl:template>
    
    <xsl:template match="expiryDate">
        <xsl:apply-templates/>
    </xsl:template>
    
    <xsl:template match="issueDate">
        <xsl:apply-templates/>
    </xsl:template>
    
    <xsl:template match="totalCost">
        <xsl:apply-templates/>
    </xsl:template>
    
    <xsl:template match="iptPercentage">
        <xsl:value-of select="number(.)"/>
    </xsl:template>
    
    <xsl:template match="policyHolderDefinition">
        <xsl:text>The </xsl:text>
        <xsl:apply-templates/>
    </xsl:template>
    
    <xsl:template match="userAccount/title">
        <xsl:apply-templates/>
        <xsl:text> </xsl:text>
    </xsl:template>
    
    <xsl:template match="userAccount/firstName">
        <xsl:apply-templates/>
        <xsl:text> </xsl:text>
    </xsl:template>
    
    <xsl:template match="userAccount/lastName">
        <xsl:apply-templates/>
    </xsl:template>
    
    <xsl:template match="policyHolderOccupation">
        <xsl:apply-templates/>
    </xsl:template>
    
    <xsl:template match="jointPolicyHolder">
        <row>
            <entry>
                <xsl:apply-templates/>
            </entry>
            <entry></entry>
        </row>
    </xsl:template>
    
    <xsl:template match="riskAddress/singleLineOutput">
        <xsl:apply-templates/>
    </xsl:template>
    
    <xsl:template match="noOfBedrooms">
        <xsl:apply-templates/>
    </xsl:template>
    
    <xsl:template match="propertyType">
        <xsl:apply-templates/>
    </xsl:template>
    
    <xsl:template match="propertyUse">
        <xsl:apply-templates/>
    </xsl:template>
    
    <xsl:template match="correspondenceAddressDetails/singleLineOutput">
        <xsl:apply-templates/>
    </xsl:template>
    
    <xsl:template match="claimsTelephoneNumber">
        <xsl:apply-templates/>
    </xsl:template>
    
    <xsl:template match="policyExpertHelpline">
        <xsl:apply-templates/>
    </xsl:template>
    
    <xsl:template match="helpline">
        <xsl:apply-templates/>
    </xsl:template>
    
    <xsl:template match="buildingsCoverElements">
       <xsl:apply-templates/>
    </xsl:template>
    
    <xsl:template match="contentsCoverElements">
        <xsl:apply-templates/>
    </xsl:template>
    
    <xsl:template match="coverDetailItem">
        <row>
            <entry>
                <xsl:apply-templates select="text"/>
            </entry>
            <entry>
                <xsl:apply-templates select="moreInfo"/>
            </entry>
            <entry>
                <xsl:apply-templates select="value"/>
            </entry>
        </row>
    </xsl:template>
    
    <xsl:template match="coverDetailItem/text">
        <xsl:apply-templates/>
    </xsl:template>
    
    <xsl:template match="coverDetailItem/moreInfo">
        <xsl:apply-templates/>
    </xsl:template>
    
    <xsl:template match="coverDetailItem/value">
        <xsl:apply-templates/>
    </xsl:template>
    
    <xsl:template match="additionalItems">
        <xsl:apply-templates/>
    </xsl:template>
    
    <xsl:template match="valuableItems">
        <xsl:apply-templates/>
    </xsl:template>
    
    <xsl:template match="possessionItem">
        <row>
            <entry>
                <xsl:apply-templates select="type"/>                
            </entry>
            <entry>
                <xsl:apply-templates select="description"/>
            </entry>
            <entry>
                <xsl:apply-templates select="value"/>
            </entry>
            <entry>
                <xsl:text>yes</xsl:text>
            </entry>
            <entry>
                <xsl:apply-templates select="coverAwayFromHome"/>
            </entry>
            <entry>
                <xsl:apply-templates select="distanceFromHome"/>
            </entry>
        </row>
    </xsl:template>
    
    <xsl:template match="possessionItem/type">
        <xsl:apply-templates/>
    </xsl:template>
    
    <xsl:template match="possessionItem/description">
        <xsl:apply-templates/>
    </xsl:template>
    
    <xsl:template match="possessionItem/value">
        <xsl:apply-templates/>
    </xsl:template>
    
    <xsl:template match="possessionItem/coverAwayFromHome">
        <xsl:apply-templates/>
    </xsl:template>
    
    <xsl:template match="possessionItem/distanceFromHome">
        <xsl:apply-templates/>
    </xsl:template>
    
    <xsl:template match="possessionsStandardCover">
        <standard-cover-table>
            <title>Valuables and personal items - standard cover in the home</title>
            <table-header>
                <row>
                    <entry>Item type</entry>
                    <entry>Level of cover included</entry>
                    <entry>home</entry>
                    <entry>away</entry>
                </row>
            </table-header>
            <table-body>
                <xsl:apply-templates />
            </table-body>
        </standard-cover-table>
    </xsl:template>
    
    <xsl:template match="possessionsStandardCover/standardCoverItem">
        <row>
            <entry>
                <xsl:apply-templates select="itemType"/>
            </entry>
            <entry>
                <xsl:apply-templates select="levelOfCover"/>
            </entry>
            <entry>
                <xsl:apply-templates select="coverWithinTheHome"/>
            </entry>
            <entry>
                <xsl:apply-templates select="coverAwayFromHome"/>
            </entry>
        </row>
    </xsl:template>
    
    <xsl:template match="endorsementDetail">
        <row>
            <entry>
                <xsl:apply-templates select="@code"/>
            </entry>
            <entry>
                <xsl:apply-templates select="title"/>
            </entry>
            <entry>
                <xsl:apply-templates select="endorsementContent"/>
            </entry>
        </row>
    </xsl:template>
    
    <xsl:template match="endorsementDetail/@code">
        <xsl:value-of select="."/>
    </xsl:template>
    
    <xsl:template match="endorsementDetail/title">
        <xsl:apply-templates/>
    </xsl:template>
    
    <xsl:template match="endorsementDetail/endorsementContent">
        <xsl:apply-templates/>
    </xsl:template>
    
    <xsl:template match="endorsementContent/contentBlock">
        <xsl:apply-templates/>
    </xsl:template>
    
    <xsl:template match="contentBlock/contentText">
        <para>
            <xsl:apply-templates/>
        </para>
    </xsl:template>
    
    <xsl:template match="contentBlock/contentList">
        <list>
            <xsl:if test="@type">
                <xsl:attribute name="type">
                    <xsl:value-of select="lower-case(@type)"/>
                </xsl:attribute>
            </xsl:if>
           <xsl:apply-templates/>
        </list>
    </xsl:template>
    
    <xsl:template match="contentList/contentText">
        <item>
            <xsl:apply-templates/>
        </item>
    </xsl:template>
    
    <xsl:template match="insurerName">
        <xsl:apply-templates/>
    </xsl:template> 
    
    <xsl:template match="change">
        <row>
            <entry>
                <xsl:apply-templates select="changeDate"/>
            </entry>
            <entry>
                <xsl:apply-templates select="changeOfPremium"/>
            </entry>
            <entry>
                <xsl:apply-templates select="changeType"/>
                <xsl:apply-templates select="changeFrom"/>
                <xsl:apply-templates select="changeTo"/>
            </entry>
        </row>
    </xsl:template>
    
    <xsl:template match="changeDate">       
        <xsl:apply-templates/>       
    </xsl:template>
    
    <xsl:template match="changeOfPremium">
       <xsl:apply-templates/>
    </xsl:template>
    
    <xsl:template match="changeType">
        <xsl:text>Changed </xsl:text>
        <xsl:apply-templates/>
        <xsl:text> </xsl:text>
    </xsl:template>
    
    <xsl:template match="changeFrom">
        <xsl:text>from </xsl:text>
        <xsl:apply-templates/>
        <xsl:text> </xsl:text>
    </xsl:template>
    
    <xsl:template match="changeTo">
        <xsl:text>to </xsl:text>
        <xsl:apply-templates/>
    </xsl:template>

</xsl:stylesheet>
