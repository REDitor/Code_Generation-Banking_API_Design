package io.swagger.repository;

import io.swagger.model.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    @Query(value = "SELECT * FROM User u "+
            "LEFT JOIN USER_ROLES ur ON ur.USER_USER_ID = u.user_Id " +
            "WHERE ur.roles = '1'", nativeQuery = true)
    Page<User> getAllCustomers(Pageable page);

    @Query(value = "SELECT * FROM User u "+
            "LEFT JOIN USER_ROLES ur ON ur.USER_USER_ID = u.user_Id " +
            "WHERE ur.roles = '0'", nativeQuery = true)
    Page<User> getAllEmployees(Pageable page);

    @Query(value = "SELECT * FROM User u " +
            "LEFT JOIN USER_ROLES ur ON ur.USER_USER_ID = u.user_Id " +
            "WHERE ur.roles = '1' and u.user_Id= :userId ", nativeQuery = true)
    User getOne(@Param("userId") UUID userId);

    @Query(value = "SELECT * FROM User u " +
            "LEFT JOIN USER_ROLES ur ON ur.USER_USER_ID = u.user_Id " +
            "WHERE ur.roles = '0' and u.user_Id= :userId ", nativeQuery = true)
    User getOneEmployee(@Param("userId") UUID userId);

    User findByUsername(String username);


}