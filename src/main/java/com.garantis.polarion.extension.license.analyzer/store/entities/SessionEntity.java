package com.garantis.polarion.extension.license.analyzer.store.entities;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "session")
public class SessionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    Long id;

    Date dateLogin;

    Date dateLogout;

    public SessionEntity(Date dateLogin, Date dateLogout) {
        this.dateLogin = dateLogin;
        this.dateLogout = dateLogout;
    }
}
