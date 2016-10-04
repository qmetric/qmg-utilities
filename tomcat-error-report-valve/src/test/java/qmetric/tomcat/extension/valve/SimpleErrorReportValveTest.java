package qmetric.tomcat.extension.valve;

import org.apache.catalina.Valve;
import org.apache.catalina.connector.Request;
import org.apache.catalina.connector.Response;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import javax.servlet.RequestDispatcher;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class SimpleErrorReportValveTest
{
    private Request request = Mockito.mock(Request.class);

    private Response response = Mockito.mock(Response.class);

    private Valve nextValue = Mockito.mock(Valve.class);

    private SimpleErrorReportValve simpleErrorReportValve;

    @Before
    public void context()
    {
        simpleErrorReportValve = new SimpleErrorReportValve();

        simpleErrorReportValve.setNext(nextValue);
    }

    @Test
    public void shouldReturnWithoutReportWhenResponseIsCommitted() throws Exception
    {
        when(response.isCommitted()).thenReturn(true);

        simpleErrorReportValve.invoke(request, response);

        verify(response, never()).reset();
        verify(response, never()).setError();
        verify(response, never()).setSuspended(false);
        verify(response, never()).getReporter();
    }

    @Test
    public void shouldReturnWithoutReportWhenResponseIsNotError() throws Exception
    {
        when(response.isCommitted()).thenReturn(false);
        when(response.getStatus()).thenReturn(200);

        simpleErrorReportValve.invoke(request, response);

        verify(response, never()).reset();
        verify(response, never()).setError();
        verify(response, never()).getReporter();
    }

    @Test
    public void shouldCreateSimpleReportWhenResponseIsAnError() throws Exception
    {
        when(response.isCommitted()).thenReturn(false);
        when(request.getAttribute(RequestDispatcher.ERROR_EXCEPTION)).thenReturn(new Exception());
        when(response.getStatus()).thenReturn(400);

        simpleErrorReportValve.invoke(request, response);
    }
}
