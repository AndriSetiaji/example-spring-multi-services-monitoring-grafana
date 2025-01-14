package com.ans.gamma.user.persistence.repository;

import com.ans.gamma.user.persistence.domain.User;
import feign.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    // Query untuk mencari berdasarkan id dan name
    @Query("SELECT u FROM User u WHERE u.id = :id AND u.name = :name")
    Optional<User> findByIdAndName(@Param("id") Long id, @Param("name") String name);


    // Query untuk mencari berdasarkan name atau id
//    @Query("SELECT u FROM User u WHERE u.name = :name OR u.id = :id")
//    List<User> findByNameOrId(@Param("name") String name, @Param("id") Long id);
}