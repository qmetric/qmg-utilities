// Copyright (c) 2010, QMetric Group Limited. All rights reserved.

package com.qmetric.testing.context;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.mock.jndi.SimpleNamingContextBuilder;
import org.springframework.util.Assert;

import javax.naming.NamingException;
import javax.sql.DataSource;

public final class ApplicationContextTestUtil
{
    private String dbConnectionPoolName;

    private ClassPathXmlApplicationContext rootContext;

    private String rootContextPath;

    ApplicationContextTestUtil(Builder builder)
    {
        this.rootContextPath = builder.rootContextPath;
        this.dbConnectionPoolName = builder.dbConnectionPoolName;
    }

    public void assertContextLoad() throws NamingException
    {
        SimpleNamingContextBuilder namingContext = null;
        try
        {
            namingContext = initNamingContext();

            initApplicationContext(rootContextPath);
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
        namingContext.activate();
        return namingContext;
    }

    private void initApplicationContext(final String rootContextPath)
    {
        rootContext = new ClassPathXmlApplicationContext();
        rootContext.setConfigLocation(rootContextPath);
        rootContext.refresh();
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

        private String dbConnectionPoolName;

        public Builder rootContextPath(final String rootContextPath)
        {
            this.rootContextPath = rootContextPath;
            return this;
        }

        public Builder dbConnectionPoolName(final String dbConnectionPoolName)
        {
            this.dbConnectionPoolName = dbConnectionPoolName;
            return this;
        }

        public ApplicationContextTestUtil build()
        {
            Assert.notNull(dbConnectionPoolName);
            return new ApplicationContextTestUtil(this);
        }
    }
}