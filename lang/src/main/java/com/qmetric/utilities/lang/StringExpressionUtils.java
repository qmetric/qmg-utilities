package com.qmetric.utilities.lang;

import org.apache.commons.lang.StringUtils;

import java.util.Map;
import java.util.regex.Pattern;

/**
 * Utils based around the evaluation of string expressions.
 */
public final class StringExpressionUtils
{
    private static final String VARIABLE_EXPRESSION_START = "{";

    private static final String VARIABLE_EXPRESSION_END = "}";

    private static final String VARIABLE_EXPRESSION_REGEX_START = "\\{";

    private static final String VARIABLE_EXPRESSION_REGEX_END = "\\}";

    /**
     * Must contain at least one occurrence of "${variable_name}" within expression for it to be classes as dynamic.
     */
    private static final Pattern DYNAMIC_EXPRESSION_PATTERN =
            Pattern.compile("^.*" + VARIABLE_EXPRESSION_REGEX_START + ".+" + VARIABLE_EXPRESSION_REGEX_END + ".*$");

    private StringExpressionUtils()
    {

    }

    /**
     * Evaluate a string expression in the following example format "this is a test string with {param_name_1} and {param_name_n}", where
     * "param_name_..." will be resolved based on the supplied map of parameter names to parameter values.
     *
     * @param expression The string expression to evaluate.
     * @param params Map of parameters which include the parameter names defined within the expression.
     * @return Evaluate string.
     */
    public static String evaluateExpression(final String expression, final Map<String, String> params)
    {
        String result = expression;
        if (StringUtils.isNotBlank(expression) && DYNAMIC_EXPRESSION_PATTERN.matcher(expression).find())
        {
            final String[] variableNames = StringUtils.substringsBetween(expression, VARIABLE_EXPRESSION_START, VARIABLE_EXPRESSION_END);
            if (variableNames.length > 0)
            {
                for (final String variableName : variableNames)
                {
                    if (StringUtils.isNotBlank(variableName))
                    {
                        final String value = params.get(variableName);

                        final StringBuilder variableExpression = new StringBuilder();
                        variableExpression.append(VARIABLE_EXPRESSION_REGEX_START).append(variableName).append(VARIABLE_EXPRESSION_REGEX_END);

                        result = result.replaceAll(variableExpression.toString(), value != null ? value : StringUtils.EMPTY);
                    }
                }
            }
        }

        return result;
    }
}