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
 * Created on Dec 7, 2003
 *
 */

package org.opendaylight.reservation.tl1.transport;

import java.io.IOException;

import org.opendaylight.reservation.tl1.library.protocol.IProtocol;


/**An interface to describe transport types used to send/receive data from the switch
 *
 * This class handles all the SSL Transport details to be able to send/receive
 * data from the switch.
 * <p>
 * Known Limitations: N/A
 * <p>
 * <b>History</b> <br>
 * <p>
 * 2006/04: Change in the transport to be integrated in the engine. <br>
 * Inocybe Technologies - Copyright &copy 2006 <br>
 * <p>
 * 2003/08: Initial file creation. <br>
 * Communications Research Centre and University of Ottawa - Copyright &copy 2003 <br>
 * <p>
 * @author Mathieu Lemay - Research Technologist, Communications Research Centre
 * @version 2.0
 */

public interface ITransport
{
		/** Timeout value in a 500ms unit */
		public static final int TIMEOUT=80;

		/** Default Connection Port */
		public static final int DEFAULTPORT=1099;

		/** Number of ms for basic unit */
		public static final int HALFSECOND=500;

		/**
	     * This method returns the name of this Transport
	     * @return This is the name of the Transport
	     */
	    public String getName();

	    /**
	     * This method returns the Protocol associated with this Transport instance
	     * @return This is the Protocol instance
	     */

	    public IProtocol getProtocol();

	    /**
	     * This method sets the Protocol associated with this Transport instance
	     * @param  prot This is the Protocol instance
	     */

	    public void setProtocol(IProtocol prot);

	    /**
	     * This method returns the detailed information about this Transport
	     * @return This is the description of the the transport with all the necessary information.
	     */
	    public String getDesc();

		/** Connects to the TL1 Device
		 * @throws IOException Throws IOException if it couldn't connection to host.. This can
		 * be either an UnknownHostException or a IO Read/Write Exception
		 */
		public void connect() throws IOException;

		/** Sends a raw string to the device
		 * @param rawInput Sends String to send
	     * @throws IOException
		 */
		public void sendMsg(String rawInput);

		/** Receives a raw string from the device
		 * @return String rawOutput
		 * @throws IOException
		 */
		public String getMsg();

		/** Disconnects from the switch */
		public void disconnect();

		/** Set the Stream reader */
		public void setStreamReader(AbstractStreamReader streamReader);

		/** get the stream reader */
		public AbstractStreamReader getStreamReader();


		/**
		 * This enables other components to set transport options.
		 * @param opts array of options
		 */
//		public void setOptions(Hashtable<Object,Object> opts);

		/** get the options
		 * @return array of options that have been set on the transport
		 */
//		public Hashtable<Object,Object> getOptions();

}

