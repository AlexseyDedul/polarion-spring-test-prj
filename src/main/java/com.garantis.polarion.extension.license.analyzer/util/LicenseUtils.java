package com.garantis.polarion.extension.license.analyzer.util;

import com.garantis.polarion.license.AccessModification;
import com.garantis.polarion.license.License;
import com.garantis.polarion.license.LicenseManager;
import com.garantis.polarion.license.Status;
import com.polarion.core.util.exceptions.UserFriendlyRuntimeException;
import com.polarion.core.util.logging.Logger;
import com.polarion.platform.core.PlatformContext;
import com.polarion.platform.security.ISecurityService;

import javax.ws.rs.ForbiddenException;

public class LicenseUtils {
	private static final Logger logger = Logger.getLogger(LicenseUtils.class);
	private static ISecurityService securityService = (ISecurityService)PlatformContext.getPlatform().lookupService(ISecurityService.class);
	
	private static LicenseManager licenseManager;

	private static int serverFullAccessUsersCount = 0;
	private static int serverLimitedAccessUsersCount = 0;
	
	/**
	 * Server users full access role name
	 */
	private final static String fullAccessRole = "garantis_la_full";
	
	/**
	 * Server users limited access role name
	 */
	private final static String limitedAccessRole = "garantis_la_limited";
	
	/**
	 * Return License instance.
	 * 
	 * @return License
	 */
	public static License getLicense() {
		
		serverFullAccessUsersCount = securityService.getUsersForGlobalRole(LicenseUtils.fullAccessRole).size();

		serverLimitedAccessUsersCount = securityService.getUsersForGlobalRole(LicenseUtils.limitedAccessRole).size();
		
		if(LicenseUtils.licenseManager == null) {
			LicenseUtils.licenseManager = new LicenseManager("GARANTIS License analyzer", serverFullAccessUsersCount, serverLimitedAccessUsersCount, "licenseanalyzer_lic.json");
		}
		
		License lic = LicenseUtils.licenseManager.getLicense();
		lic.setGlobalRole(LicenseUtils.fullAccessRole);
		lic.setTrialRole(LicenseUtils.limitedAccessRole);
		
		return lic;
	}
	
	public static int getServerFullAccessUsersCount() {
		return securityService.getUsersForGlobalRole(LicenseUtils.fullAccessRole).size();
	}

	public static int getServerLimitedAccessUsersCount() {
		return securityService.getUsersForGlobalRole(LicenseUtils.limitedAccessRole).size();
	}

	/**
	 * Check license status.
	 */
	public static void checkLicenseAccess() {
		String access = checkUserAccess();
		
		if(AccessModification.NOT_VIEW.name().equals(access)) {
			getLicense().setStatus(Status.INVALID, "You don`t have permission to use this method.");
			logger.error("You don`t have permission to use this method.");
			throw new ForbiddenException("You don`t have permission to use this method.");
		}else {
			getLicense().getLicenseStatus();
		}
		
		String checkStatus = getLicense().getStatus().getStatus();
		if(checkStatus.equals(Status.INVALID)) {
			String message = getLicense().getStatus().getMessage();
			logger.error(message);
			throw new ForbiddenException(message);
		}
	}
	
	public static void checkUserAccessWrapper() {
		try {
			LicenseUtils.checkLicenseAccess();
		}catch(ForbiddenException e) {
			throw new UserFriendlyRuntimeException(e.getMessage());
		}
	}
	
	/**
	 * Check current user access.
	 * 
	 * @return access status
	 */
	public static String checkUserAccess() {
		String loginId = securityService.getCurrentUserCredentials("").getLoginId();
		AccessModification am = LicenseUtils.getLicense().getPermissionForCurrentUser(securityService.getRolesForUser(loginId));
		return am.name();
	}
	
	private LicenseUtils() {throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");};
}
