package com.qmetric.utilities.string;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created: Oct 11, 2011, Author: Dom Farr
 */

public class StringUtils
{
    private static final String ONLY_CAPITAL_LETTERS = "[A-Z]";

    private static final Pattern MATCH_CAPTIAL_LETTERS = Pattern.compile(ONLY_CAPITAL_LETTERS);

    public String splitOnCapitalLetter(final String value)
    {
        StringBuilder out = new StringBuilder(value);

        Matcher matcher = MATCH_CAPTIAL_LETTERS.matcher(value);

        int numberOfInserts = 0;
        while (matcher.find())
        {
            if (NotFirstLetterInString(matcher))
            {
                out = out.insert(matcher.start() + numberOfInserts++, " ");
            }
        }

        return out.toString();
    }

    private boolean NotFirstLetterInString(final Matcher m)
    {
        return m.start() != 0;
    }
}
