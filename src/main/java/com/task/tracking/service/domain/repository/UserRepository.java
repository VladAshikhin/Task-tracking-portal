package com.task.tracking.service.domain.repository;

import com.task.tracking.service.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    List<User> findAll();

    User findById(Integer id);

    User getUserByLogin(String login);

    @Query("SELECT COUNT(u) FROM User u")
    Integer getNumberOfRegisteredUsers();

    List<User> findByRole(String role);

    @Query("SELECT u FROM User u WHERE active = true")
    List<User> findActive();

}