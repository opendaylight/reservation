/*
 * This file was created in partnership by the Communications Research
 * Centre Canada, The i2cat Foundation in Barcelona Spain,
 * Inocybe Technologies inc. and University of Ottawa.
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
 * Created on Oct 12, 2004
 */

package org.opendaylight.reservation.tl1.library.protocol;


import java.net.ProtocolException;

import org.opendaylight.reservation.tl1.exceptions.UnsupportedException;
import org.opendaylight.reservation.tl1.transport.ITransport;

/**
 * This interface must be implementated by all the new protocols used by the implementations.
 * @author inocult
 *
 *
 * @version 1.0
 */
public interface IProtocol {
    /** This method returns the name of this protocol
     * @return String Name of the protocol
     */
    public String getName();

    /** This method returns the detailed information about this protocol *
     * @return String Description of the protocol
     */
    public String getDesc();

    /** This method returns the transport used by the protocol
     * @return Transport Transport tused by this protocol
     */
    public ITransport getTransport();

    public void setTransport(ITransport trans) throws UnsupportedException;

    /** Insures the transport is prepared/connected to send data and flushes old data. */
    public void startSession() throws ProtocolException;

    /** Stops the transport or disconnects and flushes the buffers */
    public void stopSession() throws ProtocolException;

    /** Sends a command object to the protocol *
     * @param command Object
     */
    public  Object send(Object command) throws ProtocolException;

    /** Sends a list of command objects  to the protocol *
     * @param commandList Object[]
     */
    public Object[] send(Object[] commandList) throws ProtocolException;

    /** Receives an object will return null if there are no objects in the transport queue *
     * @return Object Message received from transport
     * 							null if no objects in the queue
     */
    public Object recvNoWait() throws ProtocolException;

    /** Waits for an object's reception *
     * @return Object Message received from transport
     */
    public Object recvWait() throws ProtocolException;

    /** Receives all objects contained in transport queue *
     * @return Object[] Messages received from transport queue
     */
    public Object[] recvAll() throws ProtocolException;

    /** Flushes the transport queue */
    public void flushAll() throws ProtocolException;
}
