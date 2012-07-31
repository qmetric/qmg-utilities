// Copyright (c) 2010, QMetric Group Limited. All rights reserved.

package com.qmetric.utilities.assembler;

/**
 * Assembler pattern specification which abstracts clients from any Dozer mapping implementation. Also, having an assembler implementations for each
 * module in our hierarchy reduces naming conflicts between spring beans in different modules.
 * <p/>
 * http://java.sun.com/blueprints/corej2eepatterns/Patterns/TransferObjectAssembler.html
 * <p/>
 * See Dozer documentation for exact mapping capabilities of the assembler. http://dozer.sourceforge.net/documentation/about.html
 */
public interface BeanAssembler
{
    /**
     * Create new destination class bean from source object.
     *
     * @param source Source object.
     * @param destinationClass Destination class.
     * @param <T> Destination class type.
     * @return Destination object.
     */
    <T> T assemble(final Object source, final Class<T> destinationClass);

    /**
     * Assemble existing destination object from source object.
     *
     * @param source Source object.
     * @param destination Destination object.
     */
    void assemble(final Object source, final Object destination);
}