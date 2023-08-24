package com.socialportal.portal.repository;

import com.socialportal.portal.model.user.UserImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
public interface UserImageRepository extends JpaRepository<UserImage, Long> {
    @Query(value = "SELECT * from user_image WHERE owner_id = ?1", nativeQuery = true)
    Optional<UserImage> findByOwnerId(Long id);
}
