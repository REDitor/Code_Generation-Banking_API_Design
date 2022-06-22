package io.swagger.repository;

import io.swagger.model.entity.Role;
import io.swagger.model.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    @Query(value = "SELECT * FROM Users u "+
            "LEFT JOIN USER_ROLES ur ON ur.USER_USER_ID = u.USER_ID " +
            "WHERE ur.roles = '1'", nativeQuery = true)
    Page<User> getAllCustomers(Pageable page);

    @Query(value = "SELECT * FROM Users u "+
            "LEFT JOIN USER_ROLES ur ON ur.USER_USER_ID = u.user_Id " +
            "WHERE ur.roles = '0' AND (u.FIRST_NAME LIKE '%'||:firstName||'%' OR u.LAST_NAME LIKE '%'||:lastName||'%')", nativeQuery = true)
    Page<User> getAllEmployeesByName(Pageable page, @Param("firstName") String firstName, @Param("lastName") String lastName);

    @Query(value = "SELECT * FROM Users u "+
            "LEFT JOIN USER_ROLES ur ON ur.USER_USER_ID = u.user_Id " +
            "WHERE ur.roles = '0'", nativeQuery = true)
    Page<User> getAllEmployees(Pageable page);

    @Query(value = "SELECT * FROM Users u " +
            "LEFT JOIN USER_ROLES ur ON ur.USER_USER_ID = u.user_Id " +
            "WHERE ur.roles = '1' and u.user_Id= :userId ", nativeQuery = true)
    User getOneCustomer(@Param("userId") UUID userId);

    @Query(value = "SELECT * FROM Users u " +
            "LEFT JOIN USER_ROLES ur ON ur.USER_USER_ID = u.user_Id " +
            "WHERE ur.roles = '0' and u.user_Id= :userId ", nativeQuery = true)
    User getOneEmployee(@Param("userId") UUID userId);

    Page<User> getAllByFirstNameIsLikeOrLastNameIsLike(Pageable page, String firstName, String lastName);
    Page<User> getAllByFirstNameOrLastNameAndAccount_Empty(Pageable page, String firstName, String lastName);

    User findByUsername(String username);

    User findUserByUserId(UUID id);

    Page<User> getAllByAccount_Empty(Pageable page);

    User findUserByRolesAndUserId(Role role, UUID UserId);

    User findByUserIdAndRolesIs( UUID UserId, Role role);

}