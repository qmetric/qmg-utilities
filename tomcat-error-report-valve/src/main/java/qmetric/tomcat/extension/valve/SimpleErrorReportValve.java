package qmetric.tomcat.extension.valve;

import org.apache.catalina.connector.Request;
import org.apache.catalina.connector.Response;
import org.apache.catalina.util.RequestUtil;
import org.apache.catalina.valves.ValveBase;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.Writer;

/**
 * Custom error report valve for tomcat.
 * <p/>
 * Configure in the server.xml by adding errorReportValveClass into the Host element.
 * <p/>
 * e.g. <Host appBase="webapps" errorReportValveClass="qmetric.tomcat.extension.valve.SimpleErrorReportValve" ... >
 * <p/>
 * See tomcat docs. http://tomcat.apache.org/tomcat-6.0-doc/config/host.html
 * <p/>
 * Created: May 27, 2011, Author: Dom Farr
 */

public final class SimpleErrorReportValve extends ValveBase
{
    public void invoke(Request request, Response response) throws IOException, ServletException
    {

        // Perform the request
        getNext().invoke(request, response);

        if (response.isCommitted())
        {
            return;
        }

        if (request.getAttribute(RequestDispatcher.ERROR_EXCEPTION) != null)
        {

            // The response is an error
            response.setError();

            // Reset the response (if possible)
            try
            {
                response.reset();
            }
            catch (IllegalStateException e)
            {
                // can't do any about that
            }

            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }

        response.setSuspended(false);

        try
        {
            report(response);
        }
        catch (Throwable t)
        {
            // can't do any about that
        }
    }

    /**
     * Prints out an error report.
     *
     * @param response The response being generated
     */
    private void report(Response response)
    {

        // Do nothing on a 1xx, 2xx and 3xx status
        // Do nothing if anything has been written already
        if (isNotError(response))
        {
            return;
        }

        String message = RequestUtil.filter(response.getMessage());

        if (message == null)
        {
            message = "";
        }

        // Do nothing if there is no report for the specified status code
        if (sm.getString("http." + response.getStatus(), message) == null)
        {
            return;
        }

        try
        {
            response.setContentType("text/html");

            response.setCharacterEncoding("utf-8");

            Writer writer = response.getReporter();

            writer.write(message);
        }
        catch (Throwable t)
        {
            if (container.getLogger().isDebugEnabled())
            {
                container.getLogger().debug("failed to write error report", t);
            }
        }
    }

    private boolean isNotError(final Response response)
    {
        return (response.getStatus() < 400) || (response.getContentWritten() > 0);
    }
}

