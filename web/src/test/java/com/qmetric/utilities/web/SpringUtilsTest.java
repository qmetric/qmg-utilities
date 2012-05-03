package com.qmetric.utilities.web;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public final class SpringUtilsTest
{
    @Test
    public void shouldReturnRedirectUrlWithSuppliedPathVariablesSeparatedByForwardSlashes()
    {
        String expectedViewName = "redirect:urlPart1/urlPart2/urlPart3";

        assertThat(SpringUtils.redirectToNextPageWithPathParmeters("urlPart1", "urlPart2", "urlPart3").getViewName(), equalTo(expectedViewName));
    }
}