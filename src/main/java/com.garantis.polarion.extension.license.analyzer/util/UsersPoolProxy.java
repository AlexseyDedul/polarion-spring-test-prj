package com.garantis.polarion.extension.license.analyzer.util;

import com.garantis.polarion.extension.license.analyzer.model.PolarionLicenseKind;
import com.garantis.polarion.extension.license.analyzer.model.PolarionLicenseType;
import com.polarion.platform.internal.*;
import com.polarion.platform.internal.IUsersPool.IConcurrentLicenseStatistics;
import com.polarion.platform.internal.IUsersPool.ILicenseStatistics;
import com.polarion.platform.internal.IUsersPool.IUser;

import java.util.Collection;
import java.util.List;

public class UsersPoolProxy {
	private final IUsersPool usersPool = LicenseSecurityManager.getLicenseManager().getUsersPoolOrNull();
	private final UsersPoolDataStore usersPoolDataStore = LicenseSecurityManager.getLicenseManager().getDataStore();

	protected UsersPoolProxy() {
	};

	public static UsersPoolProxy getInstance() {
		return SingletoneHolder.INSTANCE;
	}

	public boolean hasUsersPool() {
		return this.usersPool != null;
	}

	public Collection<IUser> getUsers() {
		return this.usersPool.getUsers();
	}

	public String getUsersFilePropertyName(PolarionLicenseType type, PolarionLicenseKind kind) {
		return this.usersPool.getUsersFilePropertyName(PolarionLicenseType.getById(type.getId()), kind.isConcurrent());
	}

	public LicenseType getAssignedUserLicense(String userId) {
		return this.usersPool.getAssignedUserLicenseWithGroup(userId);
	}

	public boolean isAssignedUserLicense(String userId) {
		return this.usersPool.isAssignedUserConcurrent(userId);
	}

	public PolarionLicenseType getDefaultConcurrentUserLicense() {
		return PolarionLicenseType.getByOriginal(this.usersPool.getDefaultConcurrentUserLicense());
	}

	public int getConcurrentLicensePeak(PolarionLicenseType type) {
		return this.usersPool.getConcurrentLicensePeak(type.getId());
	}

	public int getFreeNamedSlotsCount(PolarionLicenseType type) {
		SimpleMap<LicenseType, NamedLicenseStatistics> namedLicenseStatisticsSimpleMap = usersPoolDataStore.getNamedLicenseStatisticsMap();
		LicenseType licenseType = PolarionLicenseType.getById(type.getId());
		LicenseType currentObj = null;
		for(LicenseType namedLicenseType: namedLicenseStatisticsSimpleMap.keySet()){
			if(namedLicenseType.getId().equals(licenseType.getId())){
				currentObj = namedLicenseType;
			}
		}
		return ((NamedLicenseStatistics)namedLicenseStatisticsSimpleMap.get(currentObj).orElseThrow()).getFreeSlots();
	}

	public static String getUserId(IUser user) {
		return user.getName();
	}

	public boolean isAssignedUserConcurrent(String userId) {
		return this.usersPool.isAssignedUserConcurrent(userId);
	}

	private abstract static class LicenseStatisticsProxy<L extends ILicenseStatistics> {
		protected L statistic;

		public LicenseStatisticsProxy(L statistic) {
			this.statistic = statistic;
		}

		public LicenseType getLicense() {
			return this.statistic.getLicense();
		}

		public PolarionLicenseType getPolarionLicense() {
			return PolarionLicenseType.getByOriginal(this.getLicense());
		}

		public int getFreeSlots() {
			return this.statistic.getFreeSlots();
		}

		public int getConfiguredSlots() {
			return this.statistic.getConfiguredSlots();
		}
	}

	public static class NamedLicenseStatisticsProxy extends LicenseStatisticsProxy<ILicenseStatistics> {
		private NamedLicenseStatisticsProxy(ILicenseStatistics statistic) {
			super(statistic);
		}
	}

	public static class ConcurrentLicenseStatisticsProxy extends LicenseStatisticsProxy<IConcurrentLicenseStatistics> {
		private ConcurrentLicenseStatisticsProxy(IConcurrentLicenseStatistics statistic) {
			super(statistic);
		}

		public int getFreeSlots() {
			return ((IConcurrentLicenseStatistics) this.statistic).getFreeSlots();
		}

		public int getConfiguredSlots() {
			return ((IConcurrentLicenseStatistics) this.statistic).getConfiguredSlots();
		}
	}

	public List<ConcurrentLicenseStatisticsProxy> getConcurrentUserStatistics() {
		return this.usersPool.getConcurrentUserStatistics().stream().map(type -> {
			return new ConcurrentLicenseStatisticsProxy(type);
		}).toList();
	}

	public List<NamedLicenseStatisticsProxy> getNamedUserStatistics() {
		return this.usersPool.getNamedUserStatistics().stream().map(type -> {
			return new NamedLicenseStatisticsProxy(type);
		}).toList();
	}

	private static class SingletoneHolder {
		public static final UsersPoolProxy INSTANCE = new UsersPoolProxy();
	}
}
