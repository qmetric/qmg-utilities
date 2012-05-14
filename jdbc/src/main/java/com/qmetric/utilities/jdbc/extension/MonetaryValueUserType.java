package com.qmetric.utilities.jdbc.extension;

import com.qmetric.utilities.money.MonetaryValue;
import org.hibernate.HibernateException;
import org.hibernate.usertype.EnhancedUserType;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

public class MonetaryValueUserType implements EnhancedUserType
{
    @Override public int[] sqlTypes()
    {
        return new int[] {Types.NUMERIC};
    }

    @Override public Class returnedClass()
    {
        return MonetaryValue.class;
    }

    @Override public boolean equals(final Object x, final Object y) throws HibernateException
    {
        if (x == y)
        {
            return true;
        }

        if (x == null || y == null)
        {
            return false;
        }

        MonetaryValue mvx = (MonetaryValue) x;
        MonetaryValue mvy = (MonetaryValue) y;

        return mvx.equals(mvy);
    }

    @Override public int hashCode(final Object x) throws HibernateException
    {
        return x.hashCode();
    }

    @Override public Object nullSafeGet(final ResultSet resultSet, final String[] names, final Object owner) throws HibernateException, SQLException
    {
        return nullSafeGet(resultSet, names[0]);
    }

    public Object nullSafeGet(ResultSet resultSet, String string) throws SQLException
    {
        final BigDecimal monetaryValue = (BigDecimal) org.hibernate.type.StandardBasicTypes.BIG_DECIMAL.nullSafeGet(resultSet, string);

        if (monetaryValue == null)
        {
            return null;
        }

        return new MonetaryValue(monetaryValue);
    }

    @Override public void nullSafeSet(final PreparedStatement preparedStatement, final Object value, final int index)
            throws HibernateException, SQLException
    {
        if (value == null)
        {
            org.hibernate.type.StandardBasicTypes.BIG_DECIMAL.nullSafeSet(preparedStatement, null, index);
        }
        else
        {
            org.hibernate.type.StandardBasicTypes.BIG_DECIMAL.nullSafeSet(preparedStatement, ((MonetaryValue) value).asBigDecimal(), index);
        }
    }

    @Override public Object deepCopy(final Object value) throws HibernateException
    {
        if (value == null)
        {
            return null;
        }

        return new MonetaryValue(((MonetaryValue) value).asBigDecimal());
    }

    @Override public boolean isMutable()
    {
        return false;
    }

    @Override public Serializable disassemble(final Object value) throws HibernateException
    {
        return (Serializable) value;
    }

    @Override public Object assemble(final Serializable cached, final Object owner) throws HibernateException
    {
        return cached;
    }

    @Override public Object replace(final Object original, final Object target, final Object owner) throws HibernateException
    {
        return original;
    }

    @Override public String objectToSQLString(Object object)
    {
        throw new UnsupportedOperationException();
    }

    @Override public String toXMLString(Object object)
    {
        return object.toString();
    }

    @Override public Object fromXMLString(String string)
    {
        return new MonetaryValue(string);
    }
}
