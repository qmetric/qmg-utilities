// Copyright (c) 2011, QMetric Group Limited. All rights reserved.

package com.qmetric.utilities.assembler;

import com.qmetric.utilities.assembler.BeanAssembler;
import com.qmetric.utilities.assembler.BeanAssemblerImpl;
import org.dozer.Mapper;
import org.junit.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public final class BeanAssemblerImplTest
{
    @Test
    public void assembleDestinationClass()
    {
        final Mapper mockMapper = mock(Mapper.class);
        final Object mockSource = mock(Object.class);

        final BeanAssembler beanAssembler = new BeanAssemblerImpl(mockMapper);
        beanAssembler.assemble(mockSource, Class.class);

        verify(mockMapper).map(mockSource, Class.class);
    }

    @Test
    public void assembleDestinationObject()
    {
        final Mapper mockMapper = mock(Mapper.class);
        final Object mockSource = mock(Object.class);
        final Object mockDestination = mock(Object.class);

        final BeanAssembler beanAssembler = new BeanAssemblerImpl(mockMapper);
        beanAssembler.assemble(mockSource, mockDestination);

        verify(mockMapper).map(mockSource, mockDestination);
    }
}