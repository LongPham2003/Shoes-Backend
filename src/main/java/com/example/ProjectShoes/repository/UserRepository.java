package com.example.ProjectShoes.repository;

import com.example.ProjectShoes.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Query("""
                SELECT u FROM User u
                WHERE (:keyword IS NULL 
                   OR u.userName LIKE %:keyword%
                   OR u.name LIKE %:keyword%
                   OR u.email LIKE %:keyword%
                   OR u.phoneNumber LIKE %:keyword%)
            """)
    Page<User> searchByKeyword(@Param("keyword") String keyword, Pageable pageable);

    boolean existsByEmail(String email);
    boolean existsByUserName(String username);
    boolean existsByPhoneNumber(String phoneNumber);
}


