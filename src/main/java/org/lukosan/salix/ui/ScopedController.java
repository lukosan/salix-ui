package org.lukosan.salix.ui;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.lukosan.salix.SalixScopeRegistry;
import org.lukosan.salix.SalixScoped;
import org.lukosan.salix.SalixService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;

public abstract class ScopedController {

	private static final Log logger = LogFactory.getLog(ScopedController.class);
	
	@Autowired
	protected SalixScopeRegistry salixScopeRegistry;
	@Autowired(required=false)
	protected PermissionEvaluator permissionEvaluator;
	@Autowired
	protected SalixService salixService;

	protected <T> List<T> filter(Authentication authentication, List<T> list) {
		if(null == permissionEvaluator)
			return list;
		return list.stream().filter(s -> permissionEvaluator.hasPermission(authentication, ((SalixScoped) s).getScope(), "SALIX_USER")).collect(Collectors.toList());
	}

	private static final String[] patterns = { "ISO_DATE_TIME", "yyyy-MM-dd HH:mm:ss", "yyyyMMdd HHmmss" };
	private static final Map<String, DateTimeFormatter> formatters;
	
	static {
		formatters = new HashMap<String, DateTimeFormatter>();
		formatters.put(patterns[0], DateTimeFormatter.ISO_DATE_TIME);
		for(int ix = 1; ix < patterns.length; ix++)
			formatters.put(patterns[ix], DateTimeFormatter.ofPattern(patterns[ix]));
	}

	protected static LocalDateTime parse(String dt, String pattern) {
		if(!formatters.containsKey(pattern))
			formatters.put(pattern, DateTimeFormatter.ofPattern(pattern));
		return LocalDateTime.parse(dt, formatters.get(pattern));
	}
	
	protected static LocalDateTime parseLocalDateTime(String dt) {
		for(String pattern : patterns) {
			try {
				LocalDateTime t = parse(dt, pattern);
				return t;
			} catch(DateTimeParseException e) {
				if(logger.isDebugEnabled())
					logger.debug(e);
			}
		}
		return null;
	}

}
