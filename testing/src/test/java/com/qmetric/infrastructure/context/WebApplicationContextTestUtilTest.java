package com.qmetric.infrastructure.context;

import com.rabbitmq.client.ConnectionFactory;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;

import javax.naming.NamingException;

import static org.hamcrest.core.IsEqual.equalTo;

public class WebApplicationContextTestUtilTest
{
    @Test
    public void shouldOverrideBeans() throws NamingException
    {
        final WebApplicationContextTestUtil contextTestUtil =
                new WebApplicationContextTestUtil.Builder().rootContextPath("classpath:/spring/applicationContext.xml").webAppContextPath("classpath:/spring/webmvc.xml")
                        .servletContextResourcePath("/").dbConnectionPoolName("jdbc/testdb").overrideBeanContextPath("classpath:/spring/override.xml").build();

        contextTestUtil.assertContextLoad();

        final Object bean = contextTestUtil.getBean("first");

        Assert.assertThat(bean.toString(), equalTo("override"));
        assertAllFieldsSet(contextTestUtil, "webappContextPath", "servletContextResourcePath", "dbConnectionPoolName");
    }

    @Test
    public void shouldInstantiateCorrectBeans() throws NamingException
    {
        final WebApplicationContextTestUtil contextTestUtil = new WebApplicationContextTestUtil.Builder().dbConnectionPoolName("jdbc/testdb").build();
        contextTestUtil.assertContextLoad();
        final ConnectionFactory connFactory = (ConnectionFactory) contextTestUtil.getBean("rabbitClientConnectionFactory");


        Assert.assertThat(connFactory.getVirtualHost(), equalTo("int_vhost"));
        Assert.assertThat(connFactory.getUsername(), equalTo("qmg_int"));
        Assert.assertThat(connFactory.getPassword(), equalTo("int"));
    }

    private void assertAllFieldsSet(final WebApplicationContextTestUtil webApplicationContextTestUtil, final String... fields)
    {
        for (String field : fields)
        {
            Assert.assertNotNull("Did not set " + field, ReflectionTestUtils.getField(webApplicationContextTestUtil, field));
        }
    }
}
