package org.luncert.mullog.appender;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Paths;
import java.util.Properties;

import org.luncert.mullog.Mullog;

public final class FileAppender extends StandardAppender {

	private int logFileId = 0;

	private int maxSize = 1024; // kB

	private File logFile;

	private PrintWriter out;

	public FileAppender(Properties props) throws IOException {
		super(props);
		String filePath = props.getProperty("file");
		if (filePath.startsWith(".")) logFile = Paths.get(Mullog.getConfigPath().getParent().toString(), filePath).toFile();
		else logFile = new File(filePath);
		// System.out.println(logFile.getPath());
		if (!logFile.exists()) logFile.createNewFile();
		if (props.contains("maxSize")) maxSize = Integer.valueOf(props.getProperty("maxSize"));
		out = new PrintWriter(logFile);
	}
	
	@Override
	public void finalize() { out.close(); }

	@Override
	protected void output(String data) throws Exception {
		if (logFile.length() >= maxSize) {
			out.close();
			logFile = new File(logFile.getAbsolutePath() + "." + logFileId);
			logFileId++;
			out = new PrintWriter(logFile);
		}
		out.append(data);
		out.append('\n');
		out.flush();
	}

}