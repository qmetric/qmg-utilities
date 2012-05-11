package com.qmetric.utilities.jdbc.extension;

import org.hibernate.HibernateException;
import org.hibernate.usertype.EnhancedUserType;
import org.joda.time.DateTime;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;

public class JodaDateTimeUserType implements EnhancedUserType, Serializable
{
    private static final int[] SQL_TYPES = new int[] {org.hibernate.type.StandardBasicTypes.TIMESTAMP.sqlType()};

    public int[] sqlTypes()
    {
        return Arrays.copyOf(SQL_TYPES, SQL_TYPES.length);
    }

    public Class returnedClass()
    {
        return DateTime.class;
    }

    public boolean equals(Object x, Object y) throws HibernateException
    {
        if (x == y)
        {
            return true;
        }
        if (x == null || y == null)
        {
            return false;
        }

        DateTime dtx = (DateTime) x;
        DateTime dty = (DateTime) y;

        return dtx.equals(dty);
    }

    public int hashCode(Object object) throws HibernateException
    {
        return object.hashCode();
    }

    public Object nullSafeGet(ResultSet resultSet, String[] strings, Object object) throws HibernateException, SQLException
    {
        return nullSafeGet(resultSet, strings[0]);
    }

    public Object nullSafeGet(ResultSet resultSet, String string) throws SQLException
    {
        Object timestamp = org.hibernate.type.StandardBasicTypes.TIMESTAMP.nullSafeGet(resultSet, string);
        if (timestamp == null)
        {
            return null;
        }

        return new DateTime(timestamp);
    }

    public void nullSafeSet(PreparedStatement preparedStatement, Object value, int index) throws HibernateException, SQLException
    {
        if (value == null)
        {
            org.hibernate.type.StandardBasicTypes.TIMESTAMP.nullSafeSet(preparedStatement, null, index);
        }
        else
        {
            org.hibernate.type.StandardBasicTypes.TIMESTAMP.nullSafeSet(preparedStatement, ((DateTime) value).toDate(), index);
        }
    }

    public Object deepCopy(Object value) throws HibernateException
    {
        return value;
    }

    public boolean isMutable()
    {
        return false;
    }

    public Serializable disassemble(Object value) throws HibernateException
    {
        return (Serializable) value;
    }

    public Object assemble(Serializable cached, Object value) throws HibernateException
    {
        return cached;
    }

    public Object replace(Object original, Object target, Object owner) throws HibernateException
    {
        return original;
    }

    public String objectToSQLString(Object object)
    {
        throw new UnsupportedOperationException();
    }

    public String toXMLString(Object object)
    {
        return object.toString();
    }

    public Object fromXMLString(String string)
    {
        return new DateTime(string);
    }
}
