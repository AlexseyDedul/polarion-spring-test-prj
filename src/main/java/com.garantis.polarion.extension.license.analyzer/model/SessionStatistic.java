package com.garantis.polarion.extension.license.analyzer.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SessionStatistic {
    private int current;
    private Date sessionDate;
}
