package com.team766.logging;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

public class LogReader extends LogFormatProvider {

	private FileInputStream m_fileStream;
	private ObjectInputStream m_objectStream;

	public LogReader(String filename) throws IOException {
		m_fileStream = new FileInputStream(filename);
		m_objectStream = new ObjectInputStream(m_fileStream);
	}
	
	public LogEntry readNext() throws IOException {
		LogEntry entry = LogEntry.deserialize(m_objectStream);
		if (entry instanceof LogEntryWithFormat) {
			addFormatString(((LogEntryWithFormat)entry).getFormat());
		}
		return entry;
	}
}
