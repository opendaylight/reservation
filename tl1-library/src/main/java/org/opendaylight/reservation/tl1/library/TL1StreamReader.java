/*
 * Copyright (c) 2015 Inocybe Technologies, Inc. and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.opendaylight.reservation.tl1.library;

import java.io.IOException;
import java.util.List;

import org.opendaylight.reservation.tl1.transport.AbstractStreamReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A StreamReader for the TL1 Protocol. It used message delimters to separate
 * the responses.
 *
 *
 */
public class TL1StreamReader extends AbstractStreamReader {

    Logger logger = LoggerFactory.getLogger(TL1StreamReader.class);

    /** Characters to look for that flag the end of a message */
    private List<Character> msgdelimiters;

    public TL1StreamReader(List<Character> delims) {
        this.msgdelimiters = delims;
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Runnable#run()
     */
    public void run() {
        logger.debug("Starting TL1 Stream Reader");
        try {
            int c;

            while (((c = inStream.read()) != -1) && (!end)) {

                // scslog.debug("inRead - Data: " + c + " as a char is: " +
                // (char)c);

                if ((c > VALIDSTART && c < VALIDSTOP) || c == LF || c == CR) {

                    buffer.append((char) c);
                    // logger.debug("Buffer: " + buffer);

                    for (int i = 0; i < msgdelimiters.size(); i++) {
                        if ((char) c == msgdelimiters.get(i)) {
                            // if (buffer.toString().indexOf("REPT EVT SESSION")
                            // == -1) {

                            logger.debug("Message Received adding to List");
                            commands.addLast((String) buffer.toString());
                            buffer = new StringBuilder();
                            break;
                            // }
                        }
                    }
                }
            }
        } catch (IOException e) {
            logger.debug("Connection Lost!" + e.getMessage());
        }
        logger.debug("Connection Terminated");

    }

}
