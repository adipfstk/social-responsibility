package com.socialportal.portal.repository;

import com.socialportal.portal.model.issues.IssueImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface IssueImageRepository extends JpaRepository<IssueImage, Long> {
   @Query(value = "SELECT * from issue_image WHERE owner_id = ?1", nativeQuery = true)
   Optional<List<IssueImage>> findAllByOwnerId(Long ownerId);
}
