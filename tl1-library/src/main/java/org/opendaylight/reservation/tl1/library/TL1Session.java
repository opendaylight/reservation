/*
 * Copyright (c) 2015 Inocybe Technologies, Inc. and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.opendaylight.reservation.tl1.library;

import java.io.IOException;
import java.net.ProtocolException;

import org.opendaylight.reservation.tl1.exceptions.FailedCmdException;
import org.opendaylight.reservation.tl1.exceptions.UnsupportedException;
import org.opendaylight.reservation.tl1.library.message.TL1AckMsg;
import org.opendaylight.reservation.tl1.library.message.TL1AutonomousMsg;
import org.opendaylight.reservation.tl1.library.message.TL1InputMsg;
import org.opendaylight.reservation.tl1.library.message.TL1OutputMsg;
import org.opendaylight.reservation.tl1.library.message.TL1OutputParser;
import org.opendaylight.reservation.tl1.library.message.TL1ParserException;
import org.opendaylight.reservation.tl1.library.message.TL1ResponseMsg;
import org.opendaylight.reservation.tl1.library.protocol.IProtocol;
import org.opendaylight.reservation.tl1.transport.ITransport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class is used to open a TL1 session and send and receive message to and
 * from the switch.
 *
 * @author Mathieu Lemay
 * @version 1.0
 */
public class TL1Session implements IProtocol {
    /** SCS Log */
    static private Logger scslog = LoggerFactory.getLogger(TL1Session.class);

    // private Object[] options;

    /** TCP Transport */
    private ITransport transport;

    /** Number of retries when receiving the Bad Acknoledgements */
    public static final int NUMRETRIES = 1;

    /** Switch ID */
    String sid = null;

    /** Creates a new instance of TL1Session */
    public TL1Session() {

    }

    public TL1Session(String sid) {
        // options = new Object[4];
        this.sid = sid;
        // options[0] = sid;
    }

    public TL1Session(String sid, ITransport trans) throws UnsupportedException {
        this.sid = sid;
        setTransport(trans);
        // options = new Object[1];
        // options[0] = sid;
    }

    public TL1Session(String sid, String alarmMonIP) {
        // options = new Object[4];
        this.sid = sid;
        // options[0] = sid;
        // options[3] = alarmMonIP;
    }

    public void flushAll() throws ProtocolException {
    }

    public ITransport getTransport() {
        return transport;
    }

    public void setTransport(ITransport trans) throws UnsupportedException {
        if (trans.getName().trim().equalsIgnoreCase("TCP")
                || trans.getName().trim().equalsIgnoreCase("Virtual")
                || trans.getName().trim().equalsIgnoreCase("SSL")
                || trans.getName().trim().equalsIgnoreCase("Telnet")) {
            transport = trans;
            trans.setProtocol(this);
        } else
            throw new UnsupportedException(
                    "This transport is not supported by this protocol");
    }

    // public Object[] getOptions() {
    // return options;
    // }

    // /**
    // * Sets some options like the switch ID.
    // *
    // * @param obj
    // * array of values of the protocol options
    // */
    // public void setOptions(Object[] opt) {
    // this.sid = (String) opt[0];
    // options = opt;
    // }

    public Object[] recvAll() throws ProtocolException {
        return null;
    }

    public Object recvWait() throws ProtocolException {
        return null;
    }

    public Object recvNoWait() throws ProtocolException {
        return null;
    }

    public String getDesc() {
        return "TL1 Specification v1";
    }

    public String getName() {
        return "TL1";
    }

    /**
     * Opens a connection to the specific host/port
     *
     * @throws FailedCmdException
     *             Exception thrown if command failed
     */
    public void startSession() throws ProtocolException {
        try {
            transport.connect();
        } catch (IOException e) {
            throw new ProtocolException(
                    "TL1Session.startSession: could not connect to switch.\n"
                            + e.getMessage());
        }
    }

    /**
     * Sends out TL1 command to the agent.
     *
     * @param req
     *            Command to send in TL1InputMsg Format
     * @return TL1ResponseMsg
     * @throws FailedCmdException
     *             Exception thrown if command failed
     */
    public Object send(Object req) throws ProtocolException {
        Object msg;
        try {
            if (req instanceof TL1InputMsg) {
                TL1InputMsg request = (TL1InputMsg) req;
                msg = (Object) this.sendCmd(request.toString());
            } else {
                msg = (Object) this.sendCmd((String) req);
            }
            return msg;
        } catch (FailedCmdException e) {
            throw (new ProtocolException(e.getMessage()));
        }
    }

    public Object[] send(Object[] req) throws ProtocolException {
        Object[] msg = new Object[req.length];
        for (int i = 0; i < req.length; i++) {
            msg[i] = send(req[i]);
        }
        return msg;
    }

    /**
     * Sends out TL1 command to the agent.
     *
     * If you are using the Virtual Transport for testing, you will have to
     * disable the CTAG Checking. This is because the CTAG is generated before
     * sending the command, but the CTAG returning from the switch-simulation
     * xml files is fixed and will not match. The affected code below is marked
     * with comments. The SCS will have to be re-compiled and restarted after
     * the ctag checking is disabled. Don't forget to enable it again when
     * you're finished with your testing.
     *
     * @param request
     *            TL1 raw command
     * @return TL1ResponseMsg
     * @throws FailedCmdException
     *             Exception thrown if command failed
     */
    public TL1ResponseMsg sendCmd(String request) throws FailedCmdException {
        String answer;
        TL1OutputMsg msg = null;
        TL1ResponseMsg appendedMsg = null;
        boolean append = false;
        boolean retry = true;
        transport.sendMsg(request);
        // parse the message request to get the ctag out (aways in the fourth
        // section of the command).
        String ctag = request.split(":")[3].split(";")[0];
        scslog.debug("CTAG for request is " + ctag);
        String requested = request;
        while (msg == null) {
            answer = transport.getMsg();

            if (answer == null) {
                throw new FailedCmdException(
                        "Could not get a response to command:\n" + requested);
            }

            try {
                msg = TL1OutputParser.parse(answer);
            } catch (TL1ParserException e) {
                // scslog.debug("\n***********\n");
                scslog.debug("Error parsing message:\n" + e.getMessage());
                // scslog.debug("\n***********\n");
                continue;
            }

            if (msg != null) {
                // CTAG CHECK comment out the ctag check in the if statement
                // below
                if (msg.getType() == TL1OutputMsg.ACK_TYPE
                        && ctag.equals(((TL1AckMsg) msg).getCTAG())) {
                    if (((TL1AckMsg) msg).getAckCode().equals(
                            TL1AckMsg.RETRY_LATER)
                            || ((TL1AckMsg) msg).getAckCode().equals(
                                    TL1AckMsg.NO_ACKNOWLEDGEMENT)
                            || ((TL1AckMsg) msg).getAckCode().equals(
                                    TL1AckMsg.NO_GOOD)) {

                        if (retry) {
                            retry = false;
                            scslog.debug("Got a busy Status. Will try again in 5 second");
                            try {
                                Thread.sleep(5000); // three seconds
                            } catch (InterruptedException e) {
                                // scslog.info(Level.WARNING, e.getMessage(),
                                // e);
                                scslog.info(e.getMessage(), e);
                                break;
                            }
                            transport.sendMsg(request);
                            continue;
                        } else
                            msg = null;
                        break;
                    } else {
                        msg = null;
                        continue;
                    }
                } else if (msg.getType() == TL1OutputMsg.AUTO_TYPE) {
                    scslog.debug("Received Autonomous Msg of type "
                            + ((TL1AutonomousMsg) msg).getAlarmCode()
                            + ". Ignoring message");
                    msg = null;
                    continue;
                } else if (msg.getType() == TL1OutputMsg.PROMPT_TYPE) {
                    scslog.debug("Received prompt - ignoring");
                    msg = null;
                    continue;
                } else if (msg.getType() == TL1OutputMsg.RESP_TYPE) {
                    // CTAG CHECK - comment out if block below
                    if (!ctag.equals(((TL1ResponseMsg) msg).getCTAG())) {
                        msg = null;
                        continue;
                    }

                    else {

                        if (msg.getTermCode() == '>') {
                            if (!append) {
                                appendedMsg = (TL1ResponseMsg) msg;
                                append = true;
                            } else {
                                appendedMsg.append((TL1ResponseMsg) msg);
                            }
                            msg = null;
                            continue;
                        } else if (msg.getTermCode() == ';' && append) {
                            appendedMsg.append((TL1ResponseMsg) msg);
                            append = false;
                            msg = appendedMsg;
                        }
                    }
                }
            } else {
                break;
            }
        }
        if (msg == null) {
            throw (new FailedCmdException(
                    "Could not get response from switch to command:\n"
                            + requested));
        }
        return (TL1ResponseMsg) msg;
    }

    /**
     * Blocked and waits for TL1 autonomous messages (use this in a thread to
     * detect alarms)
     *
     * @return TL1AutonomousMsg
     */
    public TL1AutonomousMsg waitForAutoMsg() {
        TL1OutputMsg msg;
        String rawmsg = null;
        scslog.debug("Waiting for autonomous message");
        try {
            do {
                while (rawmsg == null) {
                    rawmsg = transport.getMsg();
                }
                msg = TL1OutputParser.parse(rawmsg);
                rawmsg = null;
            } while (msg.getType() != TL1OutputMsg.AUTO_TYPE);
            return (TL1AutonomousMsg) msg;
        } catch (TL1ParserException e) {
            return null;
        }
    }

    /**
     * Closes the connection
     */
    public void stopSession() throws ProtocolException {
        scslog.debug("Disconnecting");
        transport.disconnect();
    }

}
