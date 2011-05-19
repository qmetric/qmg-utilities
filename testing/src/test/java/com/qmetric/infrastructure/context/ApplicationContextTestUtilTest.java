package com.qmetric.infrastructure.context;

import com.rabbitmq.client.ConnectionFactory;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;

import javax.naming.NamingException;

import static org.hamcrest.core.IsEqual.equalTo;

public class ApplicationContextTestUtilTest
{
    @Test
    public void shouldOverrideBeans() throws NamingException
    {
        final ApplicationContextTestUtil contextTestUtil = new ApplicationContextTestUtil.Builder().rootContextPath("classpath:/spring/applicationContext.xml").dbConnectionPoolName("jdbc/testdb").build();

        contextTestUtil.assertContextLoad();

        Assert.assertNotNull("Did not set dbConnectionPoolName!", ReflectionTestUtils.getField(contextTestUtil, "dbConnectionPoolName"));
    }

    @Test
    public void shouldInstantiateCorrectBeans() throws NamingException
    {
        final ApplicationContextTestUtil contextTestUtil = new ApplicationContextTestUtil.Builder().dbConnectionPoolName("jdbc/testdb").build();
        contextTestUtil.assertContextLoad();
        final ConnectionFactory connFactory = (ConnectionFactory) contextTestUtil.getBean("rabbitClientConnectionFactory");

        Assert.assertThat(connFactory.getVirtualHost(), equalTo("non_vhost"));
        Assert.assertThat(connFactory.getUsername(), equalTo("qmg_non"));
        Assert.assertThat(connFactory.getPassword(), equalTo("non"));
    }
}