// Copyright (c) 2010, QMetric Group Limited. All rights reserved.

package com.qmetric.testing.context;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Base class for integration tests that only require partial context.xml fragments.
 * <p/>
 * This may need to be refactored to enable the entire context to be loaded in time...
 */
@RunWith(SpringJUnit4ClassRunner.class) @TestExecutionListeners({})
public abstract class BaseIntegrationTest
{
    private String contextFilename;

    private BeanFactory factory;

    /**
     * Default constructor.
     *
     * @param contextFilename Spring context file name.
     */
    public BaseIntegrationTest(final String contextFilename)
    {
        this.contextFilename = contextFilename;
    }

    /**
     * Initialises the spring bean factory only once, not per test method.
     */
    @Before
    public void initialiseContext()
    {
        // @BeforeClass is static so to avoid loading the context on every test check factory instantiation
        if (factory == null)
        {
            factory = new XmlBeanFactory(new ClassPathResource(contextFilename));
        }
    }

    /**
     * Load single bean from context without loading entire context.
     *
     * @param beanName Bean name.
     * @return Bean.
     */
    protected Object getBeanOnly(final String beanName)
    {
        return factory.getBean(beanName);
    }
}