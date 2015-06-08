/*
 * Copyright (c) 2015 Inocybe Technologies, Inc. and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.opendaylight.reservation.tl1.library.message;

/**
 * Thrown exception when a parsing error occurs.
 * @author  Mathieu Lemay
 * @version 1.0.0a
 */
public class TL1ParserException extends Exception{

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    /** Creates a new instance of TL1ParserException */
    public TL1ParserException() {
        super();
    }

    public TL1ParserException(String s)
    {
        super(s);
    }
}
