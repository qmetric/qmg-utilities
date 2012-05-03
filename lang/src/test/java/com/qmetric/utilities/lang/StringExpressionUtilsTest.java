package com.qmetric.utilities.lang;

import org.junit.Test;

import java.util.HashMap;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNull;

public final class StringExpressionUtilsTest
{
    @Test
    public void handleNullEvaluation()
    {
        assertNull("Null expected", StringExpressionUtils.evaluateExpression(null, new HashMap<String, String>()));
    }

    @Test
    public void handleEmptyEvaluation()
    {
        assertEquals("Unexpected string", "", StringExpressionUtils.evaluateExpression("", new HashMap<String, String>()));
    }

    @Test
    public void handleStringEvaluationWithoutDynamicParams()
    {
        assertEquals("Unexpected string", "a string to evaluate",
                     StringExpressionUtils.evaluateExpression("a string to evaluate", new HashMap<String, String>()));
    }

    @Test
    public void handleStringEvaluationWithDynamicParams()
    {

        final HashMap<String, String> params = new HashMap<String, String>();
        params.put("param1", "hello");
        params.put("param2", "abc");

        assertEquals("Unexpected string", "a hello string to abc evaluate",
                     StringExpressionUtils.evaluateExpression("a {param1} string to {param2} evaluate", params));
    }

    @Test
    public void handleUnusedParams()
    {

        final HashMap<String, String> params = new HashMap<String, String>();
        params.put("param1", "hello");
        params.put("param2", "abc");
        params.put("param3", "abc");

        assertEquals("Unexpected string", "a string to hello evaluate",
                     StringExpressionUtils.evaluateExpression("a string to {param1} evaluate", params));
    }

    @Test
    public void handleNullParamValue()
    {

        final HashMap<String, String> params = new HashMap<String, String>();
        params.put("param1", null);

        assertEquals("Unexpected string", "a string to  evaluate", StringExpressionUtils.evaluateExpression("a string to {param1} evaluate", params));
    }

    @Test
    public void handleBlankParamValue()
    {

        final HashMap<String, String> params = new HashMap<String, String>();
        params.put("param1", "");

        assertEquals("Unexpected string", "a string to  evaluate", StringExpressionUtils.evaluateExpression("a string to {param1} evaluate", params));
    }
}