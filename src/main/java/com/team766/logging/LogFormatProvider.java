package com.team766.logging;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class LogFormatProvider {
	private ArrayList<String> m_formatStrings;

	public LogFormatProvider() {
		m_formatStrings = new ArrayList<String>();
	}

	public synchronized String getFormatString(int index) {
		return m_formatStrings.get(index);
	}

	public synchronized List<String> listFormatStrings() {
		return Collections.unmodifiableList(m_formatStrings);
	}

	protected synchronized int addFormatString(String format) {
		m_formatStrings.add(format);
		return m_formatStrings.size() - 1;
	}
}
