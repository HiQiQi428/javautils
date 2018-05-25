package org.luncert.Mullog.appender;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Properties;

import org.luncert.Mullog.formatter.Formatter;

public final class FileAppender implements Appender {

	private Formatter formatter;

	private int logFileId = 0;

	private int maxSize = 1024; // kB

	private File logFile;

	private PrintWriter out;

	public FileAppender(Properties props) throws FileNotFoundException {
		this.logFile = new File(props.getProperty("file"));
		out = new PrintWriter(logFile);
	}

	/**
	 * unit = kB; default = 1024kB
	 */
	public void setMaxSize(int maxSize) { this.maxSize = maxSize; }

	@Override
	public void log(int logLevel, String message) throws Exception { output(formatter.format(logLevel, message)); }

	@Override
	public Appender setFormatter(Formatter formatter) {
		this.formatter = formatter;
		return this;
	}

	@Override
	public void finalize() {
		out.close();
	}

	private void output(String data) throws Exception {
		if (beMaxSize()) {
			out.close();
			logFile = new File(logFile.getAbsolutePath() + "-" + logFileId);
			logFileId++;
			out = new PrintWriter(logFile);
		}
		out.write(data);
		out.flush();
	}

	private boolean beMaxSize() {
		if (logFile.length() < maxSize) return false;
		else return true;
	}

}