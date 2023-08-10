package com.socialportal.portal.repository;

import com.socialportal.portal.model.geo.IssueLocation;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;

public interface IssueLocationRepository extends JpaRepository<IssueLocation, Long> {
   /* @Query("SELECT il FROM IssueLocation il WHERE il.latitude BETWEEN :minLatitude AND :maxLatitude " +
            "AND il.longitude BETWEEN :minLongitude AND :maxLongitude")
    List<IssueLocation> findIssueLocationsByLatitudeAndLongitudeRange(
            @Param("minLatitude") Double minLatitude,
            @Param("maxLatitude") Double maxLatitude,
            @Param("minLongitude") Double minLongitude,
            @Param("maxLongitude") Double maxLongitude
    );

    */
    List<IssueLocation> findAllByLatitudeBetweenAndLongitudeBetween(Double minLatitude, Double maxLatitude,
                                                                    Double minLongitude, Double maxLongitude);
}
