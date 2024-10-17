package com.garantis.polarion.extension.license.analyzer.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;

import java.util.LinkedList;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserSessionHistory {

    private String userId;
    private PolarionLicenseType licenseType;
    private PolarionLicenseKind licenseKind;
    private LinkedList<Session> sessionHistory;

    public UserSessionHistory(@NotNull String userId, PolarionLicenseType licenseType, PolarionLicenseKind kind) {
        this.userId = userId;
        this.licenseType = licenseType;
        this.licenseKind = kind;
        this.sessionHistory = new LinkedList<>();
    }

    /**
     * Last login session for the user.
     */
    public Session getLastSession() {
        return sessionHistory.size() > 0 ? sessionHistory.getFirst() : null;
    }


    public UserSessionHistory getUserWithFirstSession() {
        UserSessionHistory copyUser = new UserSessionHistory();
        copyUser.setUserId(this.getUserId());
        copyUser.setLicenseType(this.getLicenseType());
        copyUser.setLicenseKind(this.getLicenseKind());

        if(this.getSessionHistory().size() > 0) {
            LinkedList<Session> sessionHistoryCopy = new LinkedList<>();
            sessionHistoryCopy.add(this.getLastSession());
            copyUser.setSessionHistory(sessionHistoryCopy);
        }else {
            copyUser.setSessionHistory(new LinkedList<>());
        }

        return copyUser;
    }
}
