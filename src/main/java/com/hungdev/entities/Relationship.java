package com.hungdev.entities;

import java.time.LocalDateTime;

public class Relationship {
	private int followingUserId;
	private int followedUserId;
	private LocalDateTime createdAt;

	public Relationship() {
	}

	public Relationship(int followingUserId, int followedUserId) {
		super();
		this.followingUserId = followingUserId;
		this.followedUserId = followedUserId;
		this.createdAt = LocalDateTime.now();
	}

	public int getFollowingUserId() {
		return followingUserId;
	}

	public void setFollowingUserId(int followingUserId) {
		this.followingUserId = followingUserId;
	}

	public int getFollowedUserId() {
		return followedUserId;
	}

	public void setFollowedUserId(int followedUserId) {
		this.followedUserId = followedUserId;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}
}
