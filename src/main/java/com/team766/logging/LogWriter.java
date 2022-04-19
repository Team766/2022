package com.team766.logging;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import com.team766.config.ConfigFileReader;
import com.team766.library.LossyPriorityQueue;

public class LogWriter extends LogFormatProvider {
	private static final int QUEUE_SIZE = 50;
	
	public static LogWriter instance = null;

	public static String logFilePathBase = null;
	
	private static class LogUncaughtException implements Thread.UncaughtExceptionHandler {
		public void uncaughtException(Thread t, Throwable e) {
			e.printStackTrace();

			if (instance != null) {
				StringWriter sw = new StringWriter();
				PrintWriter pw = new PrintWriter(sw);
				pw.print("Uncaught exception: ");
				e.printStackTrace(pw);
				pw.flush();
				String str = sw.toString();
				try {
					instance.logRaw(Severity.ERROR, Category.JAVA_EXCEPTION, str);
				} catch (Exception exc) {
					exc.printStackTrace();
				}

				while (true) {
					try {
						instance.close();
						break;
					} catch (IOException e1) {
						e1.printStackTrace();
					} catch (InterruptedException e1) {
						e1.printStackTrace();
					}
				}
			}
		}
	}

	static {
		try {
			logFilePathBase = ConfigFileReader.getInstance().getString("logFilePath").get();
			new File(logFilePathBase).mkdirs();
			final String timestamp = new SimpleDateFormat("yyyyMMdd'T'HHmmss").format(new Date());
			final String logFilePath = new File(logFilePathBase, timestamp).getAbsolutePath();
			instance = new LogWriter(logFilePath);
			Logger.get(Category.CONFIGURATION).logRaw(Severity.INFO, "Logging to " + logFilePath);
		} catch (Exception e) {
			e.printStackTrace();
			try {
				instance = new LogWriter(OutputStream.nullOutputStream());
				LoggerExceptionUtils.logException(e);
				Logger.get(Category.CONFIGURATION).logRaw(Severity.ERROR, "Config file is not available. Logs will only be in-memory and will be lost when the robot is turned off.");
			} catch (IOException ex) {
				ex.printStackTrace();
				throw new RuntimeException(ex);
			}
		}

		Thread.setDefaultUncaughtExceptionHandler(new LogUncaughtException());
	}
	
	private LossyPriorityQueue<LogEntry> m_entriesQueue;
	
	private static Thread m_workerThread;
	private boolean m_running = true;
	
	private HashMap<String, Integer> m_formatStringIndices = new HashMap<String, Integer>();
	
	private OutputStream m_fileStream;
	private ObjectOutputStream m_objectStream;
	
	private Severity m_minSeverity = Severity.INFO;
	
	public LogWriter(String filename) throws IOException {
		this(new FileOutputStream(filename));
	}

	public LogWriter(OutputStream fileStream) throws IOException {
		m_entriesQueue = new LossyPriorityQueue<LogEntry>(QUEUE_SIZE, new LogEntryComparator());
		m_fileStream = fileStream;
		m_objectStream = new ObjectOutputStream(m_fileStream);
		m_workerThread = new Thread(new Runnable() {
			public void run() {
				while (true) {
					LogEntry entry;
					try {
						entry = m_entriesQueue.poll();
					} catch (InterruptedException e) {
						System.out.println("Logger thread received interruption");
						continue;
					}
					if (entry instanceof CloseLogPseudoEntry) {
						return;
					}
					entry.write(m_objectStream);
				}
			}
		});
		m_workerThread.start();
	}

	public synchronized void close() throws IOException, InterruptedException {
		m_running = false;
		m_entriesQueue.add(new CloseLogPseudoEntry());

		m_entriesQueue.waitForEmpty();
		m_workerThread.join();

		m_objectStream.flush();
		m_fileStream.flush();

		m_objectStream.close();
		m_fileStream.close();
	}
	
	public void setSeverityFilter(Severity threshold) {
		m_minSeverity = threshold;
	}
	
	public synchronized LogEntry log(Severity severity, Category category, String format, Object... args) {
		if (severity.compareTo(m_minSeverity) < 0) {
			return null;
		}
		if (!m_running) {
			System.out.println("Log message during shutdown: " + String.format(format, args));
			return null;
		}
		Integer index = m_formatStringIndices.get(format);
		LogEntry logEntry;
		if (index == null) {
			m_formatStringIndices.put(format, addFormatString(format));
			if (m_formatStringIndices.size() % 100 == 0) {
				System.out.println("You're logging a lot of unique messages. Please switch to using logRaw()");
			}
			logEntry = new LogEntryWithFormat(severity, new Date(), category, format, args);
		} else {
			logEntry = new FormattedLogEntry(severity, new Date(), category, index, args);
		}
		m_entriesQueue.add(logEntry);
		return logEntry;
	}
	
	public synchronized LogEntry logRaw(Severity severity, Category category, String message) {
		if (severity.compareTo(m_minSeverity) < 0) {
			return null;
		}
		if (!m_running) {
			System.out.println("Log message during shutdown: " + message);
			return null;
		}
		var logEntry = new RawLogEntry(severity, new Date(), category, message);
		m_entriesQueue.add(logEntry);
		return logEntry;
	}
}
