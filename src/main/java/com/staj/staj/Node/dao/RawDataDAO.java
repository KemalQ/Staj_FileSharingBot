package com.staj.staj.Node.dao;

import com.staj.staj.Node.entity.RawData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RawDataDAO extends JpaRepository<RawData, Long> {
}
