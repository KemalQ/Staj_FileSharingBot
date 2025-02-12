package com.staj.staj.common_jpa.dao;

import com.staj.staj.common_jpa.entity.AppDocument;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppDocumentDAO extends JpaRepository<AppDocument, Long> {
}
