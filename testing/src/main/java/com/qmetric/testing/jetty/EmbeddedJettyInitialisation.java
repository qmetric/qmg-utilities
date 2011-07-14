package com.qmetric.testing.jetty;

import org.mortbay.jetty.Server;
import org.mortbay.jetty.servlet.Context;
import org.mortbay.jetty.servlet.ServletHolder;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

public class EmbeddedJettyInitialisation implements ApplicationListener
{
    @Override public void onApplicationEvent(final ApplicationEvent event)
    {
         if (contextIsReadyToUse(event))
         {
             registerServlet(event);

             startServer(event);
         }
    }

    private boolean contextIsReadyToUse(final ApplicationEvent event)
    {
        return event instanceof ContextRefreshedEvent;
    }

    private void startServer(final ApplicationEvent event)
    {
        final Server server = ((ContextRefreshedEvent) event).getApplicationContext().getBean(Server.class);
        try
        {
            server.start();
        }
        catch (Exception e)
        {
            throw new RuntimeException("Could not start embedded Jetty server", e);
        }
    }

    private void registerServlet(final ApplicationEvent event)
    {
        final ServletHolder servletHolder = ((ContextRefreshedEvent) event).getApplicationContext().getBean(ServletHolder.class);
        final Context context = ((ContextRefreshedEvent) event).getApplicationContext().getBean(Context.class);
        context.addServlet(servletHolder, "/*");
    }
}

