package com.hungdev.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.hungdev.entities.Post;
import com.hungdev.entities.UserRole;

@Repository
public interface PostRepository {
	List<Post> findPagedNewestByFollowings(int userId, int pageIndex, int pageSize);
	List<Post> findAllByUserId(int userId);
	Optional<Post> findById(int id);
	void add(Post post);
	void update(Post post);
	void delete(int id);
	List<Post> search(UserRole role, String query, int currentUserId);
	List<Post> findAll();

}
