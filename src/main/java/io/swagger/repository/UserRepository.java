package io.swagger.repository;

import io.swagger.model.entity.Role;
import io.swagger.model.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    Page<User> findAllByRolesContaining(Pageable page, Role role);

    User findUserByUserIdAndRolesContaining(UUID userId, Role role);
    Page<User> getAllByFirstNameIsLikeOrLastNameIsLikeAndRolesContaining(Pageable page, String firstName, String lastName, Role role);


    Page<User> getAllByAccount_EmptyAndRolesContaining(Pageable page, Role role);
    Page<User> getAllByFirstNameOrLastNameAndAccount_EmptyAndRolesContaining(Pageable page, String firstName, String lastName, Role role);

    User findByUsername(String username);
    User findUserByUserId(UUID id);




}