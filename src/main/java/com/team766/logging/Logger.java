package com.team766.logging;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.EnumMap;

import com.team766.library.CircularBuffer;

public class Logger {
	private static final int MAX_NUM_RECENT_ENTRIES = 500;
	
	private static EnumMap<Category, Logger> m_loggers = new EnumMap<Category, Logger>(Category.class);
	private static LogWriter m_logWriter = null;
	private CircularBuffer<LogEntry> m_recentEntries = new CircularBuffer<LogEntry>(MAX_NUM_RECENT_ENTRIES);
	
	static {
		for (Category category : Category.values()) {
			m_loggers.put(category, new Logger(category));
		}
	}
	
	public static Logger get(Category category) {
		return m_loggers.get(category);
	}
	
	private final Category m_category;
	
	private Logger(Category category) {
		m_category = category;
	}
	
	public Collection<LogEntry> recentEntries() {
		return Collections.unmodifiableCollection(m_recentEntries);
	}

	public void logData(Severity severity, String format, Object... args) {
		m_recentEntries.add(new RawLogEntry(severity, new Date(), m_category, String.format(format, args)));
		LogEntry logEntry = LogWriter.instance.log(severity, m_category, format, args);
		if (logEntry != null) {
			m_recentEntries.add(logEntry);
		}
	}
	
	public void logRaw(Severity severity, String message) {
		LogEntry logEntry = LogWriter.instance.logRaw(severity, m_category, message);
		if (logEntry != null) {
			m_recentEntries.add(logEntry);
		}
	}
}
