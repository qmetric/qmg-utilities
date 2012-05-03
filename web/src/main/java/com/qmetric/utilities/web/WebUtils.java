
package com.qmetric.utilities.web;

import javax.servlet.http.HttpServletRequest;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

public final class WebUtils
{
    private WebUtils()
    {
    }

    public static Map<String, String> getRequestParametersAsMap(final HttpServletRequest request)
    {
        final Map<String, String> requestParamMap = new HashMap<String, String>();
        final Enumeration paramEnum = request.getParameterNames();

        while (paramEnum.hasMoreElements())
        {
            final String paramName = (String) paramEnum.nextElement();
            requestParamMap.put(paramName, request.getParameter(paramName));
        }

        return requestParamMap;
    }
}