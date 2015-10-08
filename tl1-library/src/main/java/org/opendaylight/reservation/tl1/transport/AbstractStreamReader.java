/*
 * Copyright (c) 2015 Inocybe Technologies, Inc. and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.opendaylight.reservation.tl1.transport;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.LinkedList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Abstract base class for a StreamReader. Extending classes must also implement the
 * Runnable interface
 *
 * @author Scott Campbell (CRC)
 *
 */
public abstract class AbstractStreamReader implements Runnable {
	/** Logger */
	static protected Logger logger = LoggerFactory.getLogger(AbstractStreamReader.class);

	/** Int value for Carriage Return */
	protected static final int CR = 13;

	/** Int value for Line Feed */
	protected static final int LF = 10;

	/** Int value for valid character start */
	protected static final int VALIDSTART = 31;

	/** Int value for valid character stop */
	protected static final int VALIDSTOP = 127;

	/** Input Stream */
	protected BufferedReader inStream = null;

	/** PrintWriter to write to the stream transport */
	protected PrintWriter outPrint;

	/** String Buffer to input Data */
	protected StringBuilder buffer = null;

	/** Die variable will kill the reader thread when set to true */
	protected boolean end = false;

	/** LinkedList Containing the response strings */
	public LinkedList<String> commands = null;

	/**
	 * Construct a Stream Reader giving it the stream
	 */
	public AbstractStreamReader()
	{
		buffer = new StringBuilder();
		commands = new LinkedList<String>();
	}

	public void setIOStreams(InputStream inStream, PrintWriter outStream) {
		this.inStream = new BufferedReader(new InputStreamReader(inStream));
		this.outPrint = outStream;
	}

	/**
	 * @param end the end to set
	 */
	public void setEnd(boolean end) {
		this.end = end;
	}

	public void flushCommands() {
		commands = new LinkedList<String>();
		logger.info("Response list flushed");
	}
}
