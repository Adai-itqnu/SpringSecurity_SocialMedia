package com.hungdev.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.hungdev.entities.User;

@Repository
public interface UserRepository {
	List<User> findPaged(int pageIndex, int pageSize, int userId);
	Optional<User> findByUsername(String username);
	void save(User user);
    void update(User user);
    void delete(String username);
}