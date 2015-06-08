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

public class EngineException extends Exception {

    private static final long serialVersionUID = -5669367817669690129L;

    public EngineException() {
    }

    public EngineException(String msg) {
        super(msg);
    }
}
