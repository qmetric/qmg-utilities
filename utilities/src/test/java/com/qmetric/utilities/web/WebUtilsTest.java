package com.qmetric.utilities.web;

import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;

import java.util.Enumeration;
import java.util.Map;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public final class WebUtilsTest
{
    @Test
    public void shouldReturnParameterMap()
    {
        final MockHttpServletRequest mockRequest = new MockHttpServletRequest();
        mockRequest.addParameter("1", "a");
        mockRequest.addParameter("2", "b");

        final Map<String, String> map = WebUtils.getRequestParametersAsMap(mockRequest);

        assertRequestMapEquality(mockRequest, map);
    }

    @Test
    public void shouldOnlyReturnFirstEntryInRequestMapParameterArray()
    {
        final MockHttpServletRequest mockRequest = new MockHttpServletRequest();
        mockRequest.addParameter("1", new String[] {"a", "b"});

        final Map<String, String> map = WebUtils.getRequestParametersAsMap(mockRequest);

        assertRequestMapEquality(mockRequest, map);
    }

    private void assertRequestMapEquality(final MockHttpServletRequest mockRequest, final Map<String, String> map)
    {
        final Enumeration<String> parameterNames = mockRequest.getParameterNames();
        while (parameterNames.hasMoreElements())
        {
            final String key = parameterNames.nextElement();
            assertThat(map.get(key), equalTo(mockRequest.getParameter(key)));
        }
    }
}