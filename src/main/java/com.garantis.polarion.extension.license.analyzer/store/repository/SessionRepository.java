package com.garantis.polarion.extension.license.analyzer.store.repository;

import com.garantis.polarion.extension.license.analyzer.store.entities.SessionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SessionRepository extends JpaRepository<SessionEntity, Long>{
}
