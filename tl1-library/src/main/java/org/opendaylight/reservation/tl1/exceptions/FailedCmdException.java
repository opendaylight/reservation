/*
 * This file was created in partnership by the Communications Research
 * Centre Canada, The University of Ottawa and The i2cat Foundation in
 * Barcelona Spain.
 *
 * The contents of this file are subject to the Apache License 2.0 (the
 * "License"). You may not use this file except in compliance with the
 * License. You may obtain a copy of the License at
 * http://phi.badlab.crc.ca/uclp/Apache2LICENSE-2.0.txt.
 * Software distributed under the License is distributed on an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied. See
 * the License for the specific language governing rights and limitations
 * under the License.
 */
 /*
 * FailedCmdException.java
 *
 * Created on May 9, 2003, 12:33 PM
 */

package org.opendaylight.reservation.tl1.exceptions;

/** Exception sent when a command has failed in the wrapper
 *
 * <p><b>Organization:</b><br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
 * Communications Research Centre and University of Ottawa - Copyright (c) 2003
 * @author Mathieu Lemay
 * @author Research Technologist Communications Research Centre
 * @version 1.0
 */
public class FailedCmdException extends EngineException
{
    private static final long serialVersionUID = 1L;


    /**
     * Creates a new instance of <code>FailedCmdException</code> without detail message.
     */
    public FailedCmdException()
    {
    	super();
    }


    /**
     * Constructs an instance of <code>FailedCmdException</code> with the specified detail message.
     * @param msg the detail message.
     */
    public FailedCmdException(String msg)
    {
        super(msg);
    }
}
