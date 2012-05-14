package com.qmetric.utilities.web;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.servlet.ModelAndView;

public final class SpringUtils
{
    private static final String URL_PATH_SEPARATOR = "/";

    public static ModelAndView redirectToNextPageWithPathParmeters(final Object... urlElements)
    {
        return new ModelAndView("redirect:" + StringUtils.join(urlElements, URL_PATH_SEPARATOR));
    }
}