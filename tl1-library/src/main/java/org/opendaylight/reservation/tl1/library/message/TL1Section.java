/*
 * Copyright (c) 2015 Inocybe Technologies, Inc. and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.opendaylight.reservation.tl1.library.message;

/**
 * TL1 Section of payload data
 *
 * @author Mathieu Lemay
 * @version 1.0.0a
 */

public class TL1Section {
	/** Field TL1Fields in this Section */
	private TL1Field[] fields;

	/**
	 * Creates a new instance of TL1Section
	 *
	 */
	public TL1Section() {

	}

	public TL1Section(String section) {

		if (section.equals("")) {
			fields = null;
			//fields[0] = new TL1Field(section);
		}
		else {
			// Remove quotes
			if (section.charAt(0) == '"'
					&& section.charAt(section.length() - 1) == '"')
				section = section.substring(1, section.length() - 1);

			String[] splitSection = section.split(",");
			fields = new TL1Field[splitSection.length];
			for (int i = 0; i < splitSection.length; i++) {
				fields[i] = new TL1Field(splitSection[i]);
			}
		}
	}

	public void add(TL1Field field) {

	}

	/**
	 * get the differents field from that section in an array form
	 *
	 * @return TL1Field array of data
	 */
	public TL1Field[] getFields() {
		return fields;
	}

	/**
	 * Get a specific field
	 *
	 * @param index
	 *            index of the field to get
	 * @return TL1Field to get
	 */
	public TL1Field getField(int index) {
		if(fields == null) {
			return null;
		}
		return fields[index];
	}

	/**
	 * Convert a TL1Section to a String
	 */
	public String toString() {
		StringBuffer s = new StringBuffer();
		for (int i = 0; i < fields.length; i++) {
			s.append(fields[i].toString());
			if (i < fields.length - 1)
				s.append(",");
		}
		return s.toString();
	}

	public void setFields(TL1Field[] fields) {
		this.fields = fields;
	}
}
