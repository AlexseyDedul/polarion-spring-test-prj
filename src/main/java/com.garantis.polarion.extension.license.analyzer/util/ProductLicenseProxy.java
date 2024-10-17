package com.garantis.polarion.extension.license.analyzer.util;

import com.garantis.polarion.extension.license.analyzer.model.PolarionLicenseType;
import com.polarion.platform.internal.OldProductLicense;

import java.util.Date;

/**
 * Provides polarion license product instance.
 */
public class ProductLicenseProxy {
	private final OldProductLicense productLicense = (OldProductLicense) LicenseSecurityProxy.getInstance().getManager().getProductLicense();

	protected ProductLicenseProxy() {}
	
	public static ProductLicenseProxy getInstance() {
		return SingletonHolder.INSTANCE;
	}
	
	public int getNamedUsersForLicense(PolarionLicenseType type) {
		return this.productLicense.getNamedUsersForLicense(PolarionLicenseType.getById(type.getId()));
	}
	
	public int getConcurrentUsersForLicense(PolarionLicenseType type) {
		return this.productLicense.getConcurrentUsersForLicense(PolarionLicenseType.getById(type.getId()));
	}
	
	public Date getDateCreated() {
		return this.productLicense.getDateCreated();
	}
	
	public boolean hasProductLicense() {
		return this.productLicense != null;
	}
	
	private static class SingletonHolder {
		public static final ProductLicenseProxy INSTANCE = new ProductLicenseProxy();
	}
}
