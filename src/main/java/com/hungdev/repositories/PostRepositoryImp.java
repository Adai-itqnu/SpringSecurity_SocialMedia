/*
 * package com.hungdev.repositories;
 * 
 * public class PostRepositoryImp {
 * 
 * }
 */
package com.hungdev.repositories;

import com.hungdev.entities.Post;
import com.hungdev.entities.PostStatus;
import com.hungdev.entities.UserRole;

import java.time.LocalDateTime;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class PostRepositoryImp implements PostRepository {

	private final DataSource dataSource;

	@Autowired
	public PostRepositoryImp(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	
	@Override
	public List<Post> findAll() {
	    List<Post> posts = new ArrayList<>();
	    String sql = "SELECT * FROM posts ORDER BY created_at DESC";

	    try (Connection conn = dataSource.getConnection();
	         PreparedStatement pstmt = conn.prepareStatement(sql);
	         ResultSet rs = pstmt.executeQuery()) {

	        while (rs.next()) {
	            posts.add(mapPost(rs));
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return posts;
	}

	@Override
	public List<Post> findPagedNewestByFollowings(int userId, int pageIndex, int pageSize) {
		List<Post> posts = new ArrayList<>();
		String sql = "SELECT * FROM posts WHERE user_id IN (SELECT following_id FROM follows WHERE follower_id = ?) AND status <> 'DRAFTED' ORDER BY created_at DESC LIMIT ? OFFSET ?";
		int offset = pageIndex * pageSize;

		try (Connection conn = dataSource.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setInt(1, userId);
			pstmt.setInt(2, pageSize);
			pstmt.setInt(3, offset);
			try (ResultSet rs = pstmt.executeQuery()) {
				while (rs.next()) {
					posts.add(mapPost(rs));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return posts;
	}

	@Override
	public List<Post> findAllByUserId(int userId) {
		List<Post> posts = new ArrayList<>();
		String sql = "SELECT * FROM posts WHERE user_id = ? ORDER BY created_at DESC";

		try (Connection conn = dataSource.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setInt(1, userId);
			try (ResultSet rs = pstmt.executeQuery()) {
				while (rs.next()) {
					posts.add(mapPost(rs));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return posts;
	}

	@Override
	public Optional<Post> findById(int id) {
		String sql = "SELECT * FROM posts WHERE id = ?";
		try (Connection conn = dataSource.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setInt(1, id);
			try (ResultSet rs = pstmt.executeQuery()) {
				if (rs.next()) {
					return Optional.of(mapPost(rs));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return Optional.empty();
	}

	@Override
	public void add(Post post) {
		String sql = "INSERT INTO posts (user_id, title, body, status, created_at) VALUES (?, ?, ?, ?, NOW())";
		try (Connection conn = dataSource.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setInt(1, post.getUserId());
			pstmt.setString(2, post.getTitle());
			pstmt.setString(3, post.getBody());
			pstmt.setString(4, post.getStatus().name());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void update(Post post) {
		String sql = "UPDATE posts SET title = ?, body = ?, status = ? WHERE id = ?";
		try (Connection conn = dataSource.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setString(1, post.getTitle());
			pstmt.setString(2, post.getBody());
			pstmt.setString(3, post.getStatus().name());
			pstmt.setInt(4, post.getId());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void delete(int id) {
		String sql = "DELETE FROM posts WHERE id = ?";
		try (Connection conn = dataSource.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setInt(1, id);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private Post mapPost(ResultSet rs) throws SQLException {
		String statusStr = rs.getString("status");
		PostStatus status = (statusStr != null && !statusStr.isEmpty())
				? PostStatus.valueOf(statusStr.trim().toUpperCase())
				: PostStatus.DRAFTED;
		return new Post(rs.getInt("id"), rs.getString("title"), rs.getString("body"), rs.getInt("user_id"), status,
				rs.getTimestamp("created_at") != null ? rs.getTimestamp("created_at").toLocalDateTime()
						: LocalDateTime.now());
	}

	public List<Post> search(UserRole role, String query, int currentUserId) {
		String finalQuery = "SELECT p.* FROM posts p " + "JOIN users u ON p.user_id = u.id "
				+ "LEFT JOIN follows f ON f.follower_id = ? AND f.following_id = u.id "
				+ "WHERE f.follower_id IS NOT NULL AND (p.title LIKE ? OR u.username LIKE ?)";
		
		if (role == UserRole.ROLE_USER) {
			finalQuery += " AND p.status <> 'DRAFTED'";
		}

		List<Post> posts = new ArrayList<>();
		try (Connection conn = dataSource.getConnection(); PreparedStatement stmt = conn.prepareStatement(finalQuery)) {

			String likeQuery = "%" + query + "%";
			stmt.setInt(1, currentUserId); // f.follower_id
			stmt.setString(2, likeQuery); // p.title
			stmt.setString(3, likeQuery); // u.username
			stmt.setString(4, likeQuery); // fallback: match username

			try (ResultSet rs = stmt.executeQuery()) {
				while (rs.next()) {
					posts.add(mapPost(rs));
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return posts;
	}

}