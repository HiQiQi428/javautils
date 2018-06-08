package org.luncert.mullog.appender;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Properties;

public final class FileAppender extends StandardAppender {

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
	public void log(int logLevel, String ... fields) throws Exception {
		if (logFile.length() < maxSize) {
			out.close();
			logFile = new File(logFile.getAbsolutePath() + "-" + logFileId);
			logFileId++;
			out = new PrintWriter(logFile);
		}
		out.write(format(logLevel, fields));
		out.flush();
	}

	@Override
	public void finalize() { out.close(); }

}