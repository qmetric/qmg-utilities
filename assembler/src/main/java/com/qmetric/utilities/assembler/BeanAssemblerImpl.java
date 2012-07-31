// Copyright (c) 2010, QMetric Group Limited. All rights reserved.

package com.qmetric.utilities.assembler;

import com.qmetric.utilities.assembler.BeanAssembler;
import org.dozer.Mapper;

public class BeanAssemblerImpl implements BeanAssembler
{
    private Mapper dozerBeanMapper;

    public BeanAssemblerImpl(final Mapper dozerBeanMapper)
    {
        this.dozerBeanMapper = dozerBeanMapper;
    }

    @Override public <T> T assemble(final Object source, final Class<T> destinationClass)
    {
        return dozerBeanMapper.map(source, destinationClass);
    }

    @Override public void assemble(final Object source, final Object destination)
    {
        dozerBeanMapper.map(source, destination);
    }
}