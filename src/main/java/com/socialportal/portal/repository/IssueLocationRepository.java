package com.socialportal.portal.repository;

import com.socialportal.portal.model.geo.IssueLocation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IssueLocationRepository extends JpaRepository<IssueLocation, Long> {
    List<IssueLocation> findAllByLatitudeBetweenAndLongitudeBetween(Double minLatitude, Double maxLatitude,
                                                                    Double minLongitude, Double maxLongitude);
}
