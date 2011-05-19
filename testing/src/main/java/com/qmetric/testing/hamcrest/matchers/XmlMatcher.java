package com.qmetric.testing.hamcrest.matchers;

/**
 * Created by IntelliJ IDEA. User: dfarr Date: Jul 29, 2010 Time: 10:08:51 AM To change this template use File | Settings | File Templates.
 */
import org.custommonkey.xmlunit.Diff;
import org.custommonkey.xmlunit.XMLUnit;
import org.hamcrest.Description;
import org.junit.internal.matchers.TypeSafeMatcher;

public class XmlMatcher extends TypeSafeMatcher<String>
{

    private String expectedXml;

    private XmlMatcher(String actualXml)
    {
        this.expectedXml = actualXml;
        XMLUnit.setTransformerFactory("org.apache.xalan.processor.TransformerFactoryImpl");
        XMLUnit.setIgnoreComments(true);
        XMLUnit.setIgnoreWhitespace(true);
        XMLUnit.setIgnoreAttributeOrder(true);
    }

    @Override
    public boolean matchesSafely(String actualXml)
    {
        try
        {
            Diff diff = new Diff(expectedXml, actualXml);

            return diff.similar();
        }
        catch (Exception e)
        {
            return false;
        }
    }

    public void describeTo(Description description)
    {
        description.appendValue(expectedXml);
    }

    public static XmlMatcher equalToXml(String actualXml)
    {
        return new XmlMatcher(actualXml);
    }
}

