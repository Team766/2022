package com.team766.hal.wpilib;

import com.ctre.phoenix.ErrorCode;
import com.team766.hal.SpeedControllerCommandFailedException;
import com.team766.logging.LoggerExceptionUtils;

class BaseCTRESpeedController {

	protected static final int TIMEOUT_MS = 20;

	protected static enum ExceptionTarget {
		THROW,
		LOG,
	}

	protected static void errorCodeToException(ExceptionTarget throwEx, ErrorCode err) {
		if (err == ErrorCode.OK) {
			return;
		}
		var ex = new SpeedControllerCommandFailedException(err.toString());
		switch (throwEx) {
			case THROW:
				throw ex;
			case LOG:
				LoggerExceptionUtils.logException(ex);
				break;
		}
	}

}
