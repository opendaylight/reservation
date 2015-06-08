/*
 * Copyright (c) 2015 Inocybe Technologies, Inc. and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.opendaylight.reservation.tl1.library.message;

import java.util.StringTokenizer;

/**
 * This objet stores the Time Information contained in the TL1 Autonomous and Response Messages' header.
 * @author  Mathieu Lemay
 * @version 1.0.0a
 */
public class TL1Time {
	/** Time information for TL1 Command */
    private int hour;
	/** Time information for TL1 Command */
    private int minutes;
	/** Time information for TL1 Command */
    private int seconds;
    /** Creates a new instance of TL1Time
     * @param date Time in raw string format
     */
    public TL1Time(String date) {
        StringTokenizer parser=new StringTokenizer(date,":");
        hour=(Integer.parseInt(parser.nextToken()));
        minutes=(Integer.parseInt(parser.nextToken()));
        seconds=(Integer.parseInt(parser.nextToken()));
    }
    /** Returns the hour in the message header
     * @return Hour
     */
    public int getHour(){return hour;}
    /** Returns the minutes in the message header
     * @return Minutes
     */
    public int getMinutes(){return minutes;}
    /** Returns the seconds in the message header
     * @return Seconds
     */
    public int getSeconds(){return seconds;}
}

