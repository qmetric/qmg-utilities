package qmetric.tomcat.extension.valve;

import org.apache.catalina.connector.Request;
import org.apache.catalina.connector.Response;
import org.apache.catalina.valves.ErrorReportValve;

import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class SimpleErrorReportValve extends ErrorReportValve {
    private static final Logger LOG = Logger.getLogger(SimpleErrorReportValve.class.getName());

    @Override
    protected void report(Request request, Response response, Throwable throwable) {
        try (PrintWriter out = response.getWriter()) {
            int statusCode = response.getStatus();
            if (statusCode >= 400) {
                out.write("Bad Request");
            }
        } catch (Exception e) {
            LOG.log(Level.SEVERE, "Error reporting error", e);
        }
    }
}

