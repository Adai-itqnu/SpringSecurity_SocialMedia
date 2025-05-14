package com.hungdev.repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class FollowRepositoryImp implements FollowRepository {
	@Autowired
	private DataSource dataSource;

	@Override
	public void followUser(int followerId, int followingId) {
		String query = "INSERT INTO follows (follower_id, following_id) VALUES (?, ?)";
		try (Connection conn = dataSource.getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
			stmt.setInt(1, followerId);
			stmt.setInt(2, followingId);
			stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void unfollowUser(int followerId, int followingId) {
		String sql = "DELETE FROM follows WHERE follower_id = ? AND following_id = ?";
		try (Connection conn = dataSource.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setInt(1, followerId);
			stmt.setInt(2, followingId);
			stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean isFollowing(int followerId, int followingId) {
		String sql = "SELECT COUNT(*) FROM follows WHERE follower_id = ? AND following_id = ?";
		try (Connection conn = dataSource.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setInt(1, followerId);
			stmt.setInt(2, followingId);
			ResultSet rs = stmt.executeQuery();
			if (rs.next())
				return rs.getInt(1) > 0;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public List<Integer> getFollowingIds(int userId) {
		List<Integer> followingIds = new ArrayList();
		String sql = "SELECT following_id FROM follows WHERE follower_id = ?";

		try (Connection conn = dataSource.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setInt(1, userId);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				followingIds.add(rs.getInt("following_id"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return followingIds;
	}

}
