package com.garantis.polarion.extension.license.analyzer.util;

import com.polarion.core.util.logging.Logger;
import com.polarion.platform.core.PlatformContext;
import com.polarion.platform.security.ISecurityService;
import org.jetbrains.annotations.Nullable;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.security.auth.Subject;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.ForbiddenException;
import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.NotFoundException;
import java.security.PrivilegedAction;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;
import java.util.concurrent.Callable;

public class ApiControllerUtils {
	private static final Logger logger = Logger.getLogger(ApiControllerUtils.class);
	private static final ISecurityService securityService = (ISecurityService) PlatformContext.getPlatform()
			.lookupService(ISecurityService.class);
	
	/**
	 * Converts date string to date object. <br/>
	 * Formats: yyyy-MM-dd HH:mm:ss, yyyy-MM-dd
	 * 
	 * @param date -
	 * @return java.util.Date
	 */
	public static Date convertDate(String date) {
		SimpleDateFormat sdfSimpleDateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat sdfSimpleDate = new SimpleDateFormat("yyyy-MM-dd");
		
		Date newDate = null;
		
		try {
			if(date.contains(" ")) {
				newDate = sdfSimpleDateTime.parse(date);
			}else {
				newDate = sdfSimpleDate.parse(date);
			}
			
			return newDate;
		} catch (ParseException e) {
			throw new IllegalArgumentException(String.format("Wrong date format for date '%s' : %s", date, e.getMessage()));
		}
	}
	
	private ApiControllerUtils() {
		logger.error("This is a utility class and cannot be instantiated");
		throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
	}
	
}
