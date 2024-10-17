package com.garantis.polarion.extension.license.analyzer.util;

import com.polarion.platform.internal.LicenseSecurityManager;

/**
 * Provides polarion license security instance.
 */
public class LicenseSecurityProxy {
	private final LicenseSecurityManager licenseManager = LicenseSecurityManager.getLicenseManager();
	
	protected LicenseSecurityProxy() {};
	
	public static LicenseSecurityProxy getInstance() {
		return SingletoneHolder.INSTANCE;
	}
	
	public LicenseSecurityManager getManager() {
		return this.licenseManager;
	}
	
	private static class SingletoneHolder {
		public static final LicenseSecurityProxy INSTANCE = new LicenseSecurityProxy();
	}
}
