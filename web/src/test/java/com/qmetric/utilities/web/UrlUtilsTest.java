package com.qmetric.utilities.web;

import org.hamcrest.core.IsEqual;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;

import java.util.LinkedHashMap;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;

public final class UrlUtilsTest
{
    @Test
    public void testGetBaseUrl()
    {
        final MockHttpServletRequest mockRequest = new MockHttpServletRequest();
        mockRequest.setScheme("http");
        mockRequest.setServerName("www.policyexpert.co.uk");
        mockRequest.setServerPort(80);
        mockRequest.setContextPath("/secure");

        assertEquals("Unexpected base url without context", "http://www.policyexpert.co.uk", UrlUtils.getBaseUrl(mockRequest, false));
        assertEquals("Unexpected base url with context", "http://www.policyexpert.co.uk/secure", UrlUtils.getBaseUrl(mockRequest, true));
    }

    @Test
    public void testGetServletPath()
    {
        assertEquals("Unexpected servlet path", null, UrlUtils.getServletPath(null, null));
        assertEquals("Unexpected servlet path", null, UrlUtils.getServletPath(null, "/no-match"));
        assertEquals("Unexpected servlet path", null, UrlUtils.getServletPath("", ""));
        assertEquals("Unexpected servlet path", null, UrlUtils.getServletPath(" ", ""));
        assertEquals("Unexpected servlet path", null, UrlUtils.getServletPath("http://www.policyexpert.co.uk/secure", ""));
        assertEquals("Unexpected servlet path", null, UrlUtils.getServletPath("http://www.policyexpert.co.uk/secure/abc", "/no-match"));
        assertEquals("Unexpected servlet path", "/", UrlUtils.getServletPath("http://www.policyexpert.co.uk/secure/", "/secure"));
        assertEquals("Unexpected servlet path", "/", UrlUtils.getServletPath("http://www.policyexpert.co.uk/secure/", "/secure/"));
        assertEquals("Unexpected servlet path", "/", UrlUtils.getServletPath("http://www.policyexpert.co.uk/secure", "/secure"));
        assertEquals("Unexpected servlet path", "/abc", UrlUtils.getServletPath("http://www.policyexpert.co.uk/secure/abc", "/secure"));
        assertEquals("Unexpected servlet path", "/abc/def", UrlUtils.getServletPath("http://www.policyexpert.co.uk/secure/abc/def", "/secure"));
        assertEquals("Unexpected servlet path", "/def", UrlUtils.getServletPath("http://www.policyexpert.co.uk/secure/abc/def", "/secure/abc"));
    }

    @Test
    public void shouldParameterMapAsEncodedParameterUrl() throws Exception
    {
        final Map parameterMap = new LinkedHashMap<String, String>()
        {
            {
                put("key1", "value1");
                put("key2", "value+2");
                put("key3", "value 3");
            }
        };

        final String encodedParameters = UrlUtils.buildEncodedParametersFrom(parameterMap);

        assertThat(encodedParameters, IsEqual.equalTo("?key1=value1&key2=value%2B2&key3=value+3"));
    }
}