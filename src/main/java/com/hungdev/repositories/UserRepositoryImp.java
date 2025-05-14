package com.hungdev.repositories;

import com.hungdev.entities.User;
import com.hungdev.entities.UserRole;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepositoryImp implements UserRepository {

	private final DataSource dataSource;

	@Autowired
	public UserRepositoryImp(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	@Override
	public List<User> findPaged(int pageIndex, int pageSize, int userId) {
		List<User> users = new ArrayList<>();
		String sql = "SELECT * FROM users WHERE id <> ? LIMIT ? OFFSET ?";
		int offset = pageIndex * pageSize;

		try (Connection conn = dataSource.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setInt(1, userId);
			pstmt.setInt(2, pageSize);
			pstmt.setInt(3, offset);
			try (ResultSet rs = pstmt.executeQuery()) {
				while (rs.next()) {
					users.add(mapUser(rs));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return users;
	}

	@Override
	public Optional<User> findByUsername(String username) {
		String sql = "SELECT * FROM users WHERE username = ?";
		try (Connection conn = dataSource.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setString(1, username);
			try (ResultSet rs = pstmt.executeQuery()) {
				if (rs.next()) {
					return Optional.of(mapUser(rs));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return Optional.empty();
	}

	@Override
	public void save(User user) {
		String sql = "INSERT INTO users (username, password, role) VALUES (?, ?, ?)";
		try (Connection conn = dataSource.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setString(1, user.getUsername());
			pstmt.setString(2, user.getPassword());
			pstmt.setString(3, user.getRole().name());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void update(User user) {
		String sql = "UPDATE users SET password = ?, role = ? WHERE username = ?";
		try (Connection conn = dataSource.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setString(1, user.getPassword());
			pstmt.setString(2, user.getRole().name());
			pstmt.setString(3, user.getUsername());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void delete(String username) {
		String sql = "DELETE FROM users WHERE username = ?";
		try (Connection conn = dataSource.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setString(1, username);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private User mapUser(ResultSet rs) throws SQLException {
		return new User(rs.getInt("id"), rs.getString("username"), rs.getString("password"),
				UserRole.valueOf(rs.getString("role")));
	}

}
