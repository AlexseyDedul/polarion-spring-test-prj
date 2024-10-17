package com.garantis.polarion.extension.license.analyzer.util;

import java.io.File;

public class ExtensionConfiguration {
	private static final String USERS_BINARY_SASSION_BACKUP_FILE = "user_session_history.history";
	private static final String BINARY_STATISTICS_BACKUP_FILE = "statistics.history";
	private static final String LICENSE_LOGS_FOLDER = "main";
	
	public static String getBinaryUsersBackupPath() {
		return new StringBuilder()
				.append(PolarionUtils.getPolarionPathToDataFolder())
				.append(File.separator)
				.append("license-analyzer-backup")
				.append(File.separator)
				.append(ExtensionConfiguration.USERS_BINARY_SASSION_BACKUP_FILE)
				.toString();
	}
	
	public static String getBinaryStatisticsBackupPath() {
		return new StringBuilder()
				.append(PolarionUtils.getPolarionPathToDataFolder())
				.append(File.separator)
				.append("license-analyzer-backup")
				.append(File.separator)
				.append(ExtensionConfiguration.BINARY_STATISTICS_BACKUP_FILE)
				.toString();
	}
	
	public static String getPathToLogs() {
		return new StringBuilder()
				.append(PolarionUtils.getPolarionPathToDataFolder())
				.append(File.separator)
				.append("logs")
				.append(File.separator)
				.append(ExtensionConfiguration.LICENSE_LOGS_FOLDER)
				.toString();
	}
	
	/**
	 * Gets true if directories are exist.
	 * 
	 * @param filePath
	 * @return true if dirs exist or false.
	 */
	public static boolean isFolderStructureExist(String filePath) {
		File file = new File(filePath);
        
        File parentDir = file.getParentFile();
        if(parentDir != null && !parentDir.exists()) {
        	boolean dirCreated = parentDir.mkdirs();
        	if(!dirCreated) {
        		return false;
        	}
        }
        return true;
	}
	
}
