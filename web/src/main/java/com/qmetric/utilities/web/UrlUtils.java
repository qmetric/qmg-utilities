package com.qmetric.utilities.web;

import org.apache.commons.lang.StringUtils;

import javax.servlet.http.HttpServletRequest;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

public final class UrlUtils
{
    private static final String URL_PATH_SEPARATOR = "/";

    /**
     * Get the base url from the request. If 'includeContextPath' is false then [scheme]://[serverName]:[port] otherwise, [scheme]://[serverName]:[port]/[contextPath].
     *
     * @param request The request.
     * @param includeContextPath The context path, for example, such as '/app/frontend'.
     * @return The base url.
     */
    public static String getBaseUrl(final HttpServletRequest request, final boolean includeContextPath)
    {
        final StringBuilder baseUrl = new StringBuilder();
        baseUrl.append(request.getScheme()).append("://");
        baseUrl.append(request.getServerName());

        // only add port to url if not default http or https port.
        if (request.getServerPort() != 80 && request.getServerPort() != 443)
        {
            baseUrl.append(":").append(request.getServerPort());
        }

        // include context path or not.
        if (includeContextPath)
        {
            baseUrl.append(request.getContextPath());
        }

        return baseUrl.toString();
    }

    /**
     * Get the servlet path from a URL, e.g. "http://localhost/secure/login?param=1" => "/login".
     *
     * @param url Url including context.
     * @param expectedContext Expected context part to match within URL.
     * @return Servlet path, or null if the given url does not contain the expected context path.
     */
    public static String getServletPath(final String url, final String expectedContext)
    {
        final boolean containsContext = StringUtils.isNotBlank(expectedContext) && StringUtils.contains(url, expectedContext);
        final String path = StringUtils.substringBefore(StringUtils.substringAfterLast(url, expectedContext), "?");
        if (StringUtils.isNotBlank(path))
        {
            return (path.startsWith(URL_PATH_SEPARATOR) ? StringUtils.EMPTY : URL_PATH_SEPARATOR) + path;
        }
        else if (containsContext)
        {
            return URL_PATH_SEPARATOR;
        }
        else
        {
            return null;
        }
    }

    public static String buildEncodedParametersFrom(final Map<String, String> parameterMap) throws UnsupportedEncodingException
    {
        final StringBuilder paymentParameters = new StringBuilder();

        for (final Map.Entry<String, String> entrySet : parameterMap.entrySet())
        {
            paymentParameters.append(paymentParameters.length() == 0 ? "?" : "&");
            paymentParameters.append(entrySet.getKey()).append("=").append(URLEncoder.encode(entrySet.getValue(), "UTF-8"));
        }

        return paymentParameters.toString();
    }
}