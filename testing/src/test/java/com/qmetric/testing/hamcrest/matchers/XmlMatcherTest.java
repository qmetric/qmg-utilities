package com.qmetric.testing.hamcrest.matchers;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.assertFalse;

/**
 * Created by IntelliJ IDEA. User: dfarr Date: Jul 29, 2010 Time: 4:07:13 PM To change this template use File | Settings | File Templates.
 */
public class XmlMatcherTest
{
    private static String EXPECTED_XML = "<?xml version=\"1.0\"?><root><a id=\"1\" type=\"val\"></a><b></b></root>";

    private static String EXPECTED_XML_WITH_SPACES = "<?xml version=\"1.0\"?><root><a id=\"1\" type=\"val\"></a>\n\n\t<b>\n</b></root>";

    private static String EXPECTED_XML_WITH_COMMENT = "<?xml version=\"1.0\"?><root><a id=\"1\" type=\"val\"></a><!-- comment --><b></b></root>";

    private static String XML_WITH_ATTRIBUTES_IN_DIFFERENT_ORDER =
            "<?xml version=\"1.0\"?><root><a type=\"val\" id=\"1\"></a><!-- comment --><b></b></root>";

    private static String XML_WITH_DIFFERENT_ATTRIBUTES =
            "<?xml version=\"1.0\"?><root><a identity=\"1\" type=\"val\"></a><!-- comment --><b></b></root>";

    @Test
    public void shouldMatchSimpleXML()
    {
        Assert.assertTrue(XmlMatcher.equalToXml(EXPECTED_XML).matchesSafely(EXPECTED_XML));
    }

    @Test
    public void shouldMatchSimpleXmlIgnoringWhiteSpace()
    {
        Assert.assertTrue(XmlMatcher.equalToXml(EXPECTED_XML).matchesSafely(EXPECTED_XML_WITH_SPACES));
    }

    @Test
    public void shouldMatchSimpleXmlIgnoringComments()
    {
        Assert.assertTrue(XmlMatcher.equalToXml(EXPECTED_XML).matchesSafely(EXPECTED_XML_WITH_COMMENT));
    }

    @Test
    public void shouldMatchSimpleXmlIgnoringAttributeOrder()
    {
        Assert.assertTrue(XmlMatcher.equalToXml(EXPECTED_XML).matchesSafely(XML_WITH_ATTRIBUTES_IN_DIFFERENT_ORDER));
    }

    @Test
    public void shouldFailToMatchIfAttributesAreDifferent()
    {
        assertFalse(XmlMatcher.equalToXml(EXPECTED_XML).matchesSafely(XML_WITH_DIFFERENT_ATTRIBUTES));
    }
}
