package edu.indiana.d2i.htrc.bookworm.worksetmetadata;

public class CmdLineException extends Exception {

	public CmdLineException() {
	}

	public CmdLineException(String message) {
		super(message);
	}

	public CmdLineException(Throwable cause) {
		super(cause);
	}

	public CmdLineException(String message, Throwable cause) {
		super(message, cause);
	}

	public CmdLineException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
