package com.nounou.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nounou.constants.EnumExceptions;
import com.nounou.constants.EnumLogType;
import com.nounou.entities.Logs;
import com.nounou.interfacesRepositories.IRepoLoggers;

@Component
public class LoggerService { // NOPMD by jonathan on 21/07/2019 12:48
	
	@Autowired
	private IRepoLoggers _repoLogger;	
	
	public void warn(final String p_className, 
			final EnumExceptions p_exceptionType,
			final String p_description,
			final String p_methodName) {
		
		final Logs log = new Logs(EnumLogType.WARN,
				p_className,
				p_exceptionType,
				p_description,
				p_methodName);
		this._repoLogger.save(log);
	}
	
	public void info(final String p_className, 
			final EnumExceptions p_exceptionType,
			final String p_description,
			final String p_methodName) {
		
		final Logs log = new Logs(EnumLogType.INFO,
				p_className,
				p_exceptionType,
				p_description,
				p_methodName);
		this._repoLogger.save(log);
	}
	
	public void debug(final String p_className, 
			final EnumExceptions p_exceptionType,
			final String p_description,
			final String p_methodName) {
		
		final Logs log = new Logs(EnumLogType.DEBUG,
				p_className,
				p_exceptionType,
				p_description,
				p_methodName);
		this._repoLogger.save(log);
	}
	
	public void error(final String p_className, 
			final EnumExceptions p_exceptionType,
			final String p_description,
			final String p_methodName) {
		
		final Logs log = new Logs(EnumLogType.ERROR,
				p_className,
				p_exceptionType,
				p_description,
				p_methodName);
		this._repoLogger.save(log);
	}
}