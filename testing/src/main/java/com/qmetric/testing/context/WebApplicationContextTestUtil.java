// Copyright (c) 2010, QMetric Group Limited. All rights reserved.

package com.qmetric.testing.context;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.PredicateUtils;
import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.mock.jndi.SimpleNamingContextBuilder;
import org.springframework.mock.web.MockServletContext;
import org.springframework.util.Assert;
import org.springframework.web.context.support.XmlWebApplicationContext;

import javax.naming.NamingException;
import javax.sql.DataSource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class WebApplicationContextTestUtil
{

    private String webappContextPath;

    private String servletContextResourcePath;

    private String dbConnectionPoolName;

    private String localOverridesContextPath;

    private ClassPathXmlApplicationContext rootContext;

    private String rootContextPath;

    private String overrideBeanPath;

    private List<String> extraConnectionNames;

    WebApplicationContextTestUtil(Builder builder)
    {
        this.rootContextPath = builder.rootContextPath;
        this.overrideBeanPath = builder.overrideBeanPath;
        this.webappContextPath = builder.webappContextPath;
        this.servletContextResourcePath = builder.servletContextResourcePath;
        this.localOverridesContextPath = builder.localOverridesContextPath;
        this.dbConnectionPoolName = builder.dbConnectionPoolName;
        extraConnectionNames = builder.extraConnectionNames;
    }

    public void assertContextLoad() throws NamingException
    {
        SimpleNamingContextBuilder namingContext = null;
        try
        {
            namingContext = initNamingContext();

            initApplicationContext(rootContextPath, localOverridesContextPath, overrideBeanPath);

            initWebAppContext();
        }
        finally
        {
            if (namingContext != null)
            {
                namingContext.deactivate();
            }
        }
    }

    private SimpleNamingContextBuilder initNamingContext() throws NamingException
    {
        SimpleNamingContextBuilder namingContext = new SimpleNamingContextBuilder();
        namingContext.bind(dbConnectionPoolName, createInMemoryDataSource());
        additionalDbConnectionPools(namingContext);
        namingContext.activate();
        return namingContext;
    }

    private void additionalDbConnectionPools(final SimpleNamingContextBuilder namingContext)
    {
        if( ! extraConnectionNames.isEmpty())
        {
            for (String name : extraConnectionNames)
            {
                namingContext.bind(name, createInMemoryDataSource());
            }
        }
    }

    private void initApplicationContext(final String rootContextPath, final String localOverridesContextPath, final String overrideBeanPath)
    {
        rootContext = new ClassPathXmlApplicationContext();
        final String[] configs = createConfigsArray(rootContextPath, localOverridesContextPath, overrideBeanPath);
        rootContext.setConfigLocations(configs);
        rootContext.refresh();
    }

    private String[] createConfigsArray(final String... contextPaths)
    {
        final List<String> tempList = new ArrayList<String>(Arrays.asList(contextPaths));
        CollectionUtils.filter(tempList, PredicateUtils.notNullPredicate());
        return tempList.toArray(new String[tempList.size()]);
    }

    private void initWebAppContext()
    {
        final XmlWebApplicationContext webappContext = new XmlWebApplicationContext();
        webappContext.setServletContext(new MockServletContext(servletContextResourcePath));
        webappContext.setConfigLocation(webappContextPath);
        webappContext.setParent(rootContext);
        webappContext.refresh();
    }

    private DataSource createInMemoryDataSource()
    {
        final BasicDataSource testDataSource = new BasicDataSource();
        testDataSource.setDriverClassName("org.hsqldb.jdbcDriver");
        testDataSource.setUrl("jdbc:hsqldb:mem:mymemdb");
        testDataSource.setUsername("sa");

        return testDataSource;
    }

    Object getBean(String name)
    {
        return rootContext.getBeanFactory().getBean(name);
    }

    public static class Builder
    {
        private String rootContextPath = "classpath:/spring/applicationContext.xml";

        private String webappContextPath = "classpath:/spring/webmvc-config.xml";

        private String servletContextResourcePath = "file:src/main/webapp";

        private String localOverridesContextPath = "classpath:/spring/global-overrides.xml";

        private String dbConnectionPoolName;

        private List<String> extraConnectionNames = new ArrayList<String>();

        private String overrideBeanPath;// = "classpath:/spring/overridden-beans.xml";

        public Builder rootContextPath(final String rootContextPath)
        {
            this.rootContextPath = rootContextPath;
            return this;
        }

        public Builder webAppContextPath(final String webappContextPath)
        {
            this.webappContextPath = webappContextPath;
            return this;
        }

        public Builder servletContextResourcePath(final String servletContextResourcePath)
        {
            this.servletContextResourcePath = servletContextResourcePath;
            return this;
        }

        public Builder dbConnectionPoolName(final String dbConnectionPoolName)
        {
            this.dbConnectionPoolName = dbConnectionPoolName;
            return this;
        }

        public Builder additionalConnectionPoolName(final String dbConnectionPoolName)
        {
            extraConnectionNames.add(dbConnectionPoolName);
            return this;
        }


        public Builder overrideBeanContextPath(final String overrideBeanPath)
        {
            this.overrideBeanPath = overrideBeanPath;
            return this;
        }

        public WebApplicationContextTestUtil build()
        {
            Assert.notNull(dbConnectionPoolName);
            return new WebApplicationContextTestUtil(this);
        }
    }
}