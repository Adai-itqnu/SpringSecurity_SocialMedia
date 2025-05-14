package com.hungdev.repositories;

import java.util.List;

public interface FollowRepository {
	void followUser(int followerId, int followingId);
	void unfollowUser(int followerId, int followingId);
    boolean isFollowing(int followerId, int followingId);
    List<Integer> getFollowingIds(int userId);
}
