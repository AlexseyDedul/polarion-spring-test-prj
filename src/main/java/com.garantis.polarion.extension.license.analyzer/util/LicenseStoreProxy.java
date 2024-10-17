package com.garantis.polarion.extension.license.analyzer.util;

import com.polarion.core.util.logging.Logger;
import com.polarion.platform.internal.LicenseStore;
import com.polarion.platform.internal.UsersFile;

import java.util.concurrent.locks.Lock;
import java.util.function.BiConsumer;
import java.util.function.BiPredicate;
import java.util.function.Consumer;

/**
 * Provides polarion license store instance.
 */
public class LicenseStoreProxy {
	private static final Logger logger = Logger.getLogger(LicenseStoreProxy.class);
	private final LicenseStore licenseStore = LicenseSecurityProxy.getInstance().getManager().getLicenseStore();

	protected LicenseStoreProxy() {
	}

	public static LicenseStoreProxy getInstance() {
		return SingletonHolder.INSTANCE;
	}

	public boolean doInLicenseStore(Consumer<LicenseStoreProxy> consumer) {
		return this.doInLicenseStore((store, usersFile) -> {
			consumer.accept(store);
			return true;
		}, false);
	}

	public boolean doInLicenseStore(BiConsumer<LicenseStoreProxy, UsersFile> consumer) {
		return this.doInLicenseStore((store, usersFile) -> {

			consumer.accept(store, usersFile);
			return true;
		}, false);
	}

	public boolean doInLicenseStore(BiPredicate<LicenseStoreProxy, UsersFile> predicate, boolean save) {
		Lock lock = this.licenseStore.getExclusiveLock();
		lock.lock();

		try {
			UsersFile usersFile = this.licenseStore.loadUsersFile();
			boolean result = predicate.test(this, usersFile);
			if (result && save) {
				this.licenseStore.saveUsersFile(usersFile);
			}

			return result;
		} catch (Exception e) {
			String msg = String.format("Unable to process %s in %s: %s", this.getClass().getSimpleName(),
					this.licenseStore.getUsersFileLocation(), e.getLocalizedMessage());
			logger.error(msg, e);
		} finally {
			lock.unlock();
		}

		return false;
	}

	private static class SingletonHolder {
		public static final LicenseStoreProxy INSTANCE = new LicenseStoreProxy();
	}
}
