package com.qmetric.utilities.jdbc;

import org.hibernate.Hibernate;
import org.hibernate.proxy.HibernateProxy;

/**
 * Convenience wrapper for Hibernate static.
 */
public class HibernateUtils
{
    public boolean isInitialized(final Object proxy)
    {
        return Hibernate.isInitialized(proxy);
    }

    public Object getProxyImplementation(final Object proxy)
    {
        return proxy instanceof HibernateProxy ? ((HibernateProxy) proxy).getHibernateLazyInitializer().getImplementation() : proxy;
    }
}
