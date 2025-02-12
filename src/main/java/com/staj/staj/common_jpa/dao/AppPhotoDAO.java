package com.staj.staj.common_jpa.dao;

import com.staj.staj.common_jpa.entity.AppPhoto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppPhotoDAO extends JpaRepository<AppPhoto, Long> {
}
