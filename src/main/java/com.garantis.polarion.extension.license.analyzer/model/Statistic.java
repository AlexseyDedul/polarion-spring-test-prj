package com.garantis.polarion.extension.license.analyzer.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.LinkedList;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Statistic {
    private PolarionLicenseType licenseType;
    private PolarionLicenseKind licenseKind;
    private int peak;
    private int limit;
    private LinkedList<SessionStatistic> sessions;

    public SessionStatistic getLastSessions() {
        return sessions.getFirst();
    }

    public Statistic getStatisticWithFirstSession() {
        Statistic copyStat = new Statistic();
        copyStat.setLicenseType(this.getLicenseType());
        copyStat.setLicenseKind(this.getLicenseKind());
        copyStat.setLimit(this.getLimit());
        copyStat.setPeak(this.getPeak());

        if(this.getSessions().size() > 0) {
            LinkedList<SessionStatistic> copy = new LinkedList<>();
            copy.add(this.getLastSessions());
            copyStat.setSessions(copy);
        }else {
            copyStat.setSessions(new LinkedList<>());
        }

        return copyStat;
    }
}
