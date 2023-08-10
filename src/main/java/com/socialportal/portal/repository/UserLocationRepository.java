package com.socialportal.portal.repository;

import com.socialportal.portal.model.geo.UserLocation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserLocationRepository extends JpaRepository <UserLocation, Long> {
}
