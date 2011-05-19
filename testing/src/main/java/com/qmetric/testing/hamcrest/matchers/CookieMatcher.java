package com.qmetric.testing.hamcrest.matchers;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;

import javax.servlet.http.Cookie;

/**
 * Matches cookie name, value, age, secure and path
 */
public class CookieMatcher extends BaseMatcher
{
    private final Cookie expectedCookie;

    public CookieMatcher(final Cookie expectedCookie)
    {
        this.expectedCookie = expectedCookie;
    }

    @Override public boolean matches(final Object o)
    {
        Cookie[] actualCookies = (Cookie[]) o;

        if ((actualCookies == null || actualCookies.length == 0) && expectedCookie == null)
        {
            return true;
        }

        boolean result = false;

        for (Cookie cookie : actualCookies)
        {
            result |= cookie.getName() == expectedCookie.getName() && cookie.getValue() == expectedCookie.getValue() &&
                      cookie.getPath() == expectedCookie.getPath() && cookie.getSecure() == expectedCookie.getSecure() &&
                      cookie.getMaxAge() == expectedCookie.getMaxAge();
        }

        return result;
    }

    public static CookieMatcher containsThis(final Cookie expectedCookie)
    {
        return new CookieMatcher(expectedCookie);
    }

    @Override public void describeTo(final Description description)
    {
        description.appendText("Cookies didn't match");
    }
}
