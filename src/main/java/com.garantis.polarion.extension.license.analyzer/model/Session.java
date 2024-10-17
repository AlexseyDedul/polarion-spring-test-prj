package com.garantis.polarion.extension.license.analyzer.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Session {
    private Date dateLogin;
    private Date dateLogout;

    public Session(Date login){
        this.dateLogin = login;
    }
}
