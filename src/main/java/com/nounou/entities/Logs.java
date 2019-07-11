package com.nounou.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.nounou.constants.EnumExceptions;
import com.nounou.constants.EnumLogType;

/**
 * 
 * @author jonathan
 *
 */
@Entity
@Table(name = "logs")
public class Logs { 

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	private String className;
	private String exceptionType;
	private String description;
	private String methodName;
	private String logType;
	
	public EnumLogType getLogType() {
		return EnumLogType.valueOf(logType);
	}
	public void setLogType(final EnumLogType p_logType) {
		this.logType = p_logType.toString();
	}
	
	// Default constructor
	public Logs() {
		
	}
	
	// Surcharged constructor
	public Logs(final EnumLogType p_logType,
					final String p_className,
					final EnumExceptions p_exceptionType,
					final String p_description,
					final String p_methodName) {
		this.logType = p_logType.toString();
		this.className = p_className;
		this.exceptionType = p_exceptionType.toString();
		this.description = p_description;
		this.methodName = p_methodName;
	}
	public int getId() {
		return id;
	}
	public void setId(final int id) {
		this.id = id;
	}
	public String getClassName() {
		return className;
	}
	public void setClassName(final String p_className) {
		this.className = p_className;
	}
	public EnumExceptions getExceptionType() {
		return EnumExceptions.valueOf(exceptionType);
	}
	public void setExceptionType(final EnumExceptions exceptionType) {
		this.exceptionType = exceptionType.toString();
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(final String description) {
		this.description = description;
	}
	public String getMethodName() {
		return methodName;
	}
	public void setMethodName(final String p_methodName) {
		this.methodName = p_methodName;
	}
	
}
