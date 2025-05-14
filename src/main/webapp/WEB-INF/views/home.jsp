<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.List"%>
<%@ page import="com.hungdev.entities.Post"%>
<%@ page import="com.hungdev.entities.User"%>

<!DOCTYPE html>
<html lang="vi">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Simple Social Network</title>
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css"
	rel="stylesheet">
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap-icons/font/bootstrap-icons.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/homepage.css">

</head>
<body>
	<nav class="navbar navbar-expand-lg navbar-dark">
		<div class="container">
			<a class="navbar-brand" href="<%=request.getContextPath()%>/home">
				Simple Social Network</a>
			<form action="<%=request.getContextPath()%>/home" method="get"
				class="d-flex search-bar">
				<input type="text" name="keyword" class="form-control me-2"
					placeholder="Tìm kiếm..." required>
				<button type="submit" class="btn btn-light btn-hover">🔍</button>
			</form>
			<a href="<%=request.getContextPath()%>/auth/logout"
				class="btn btn-danger btn-sm btn-hover"> <i
				class="bi bi-box-arrow-right"></i> Đăng xuất
			</a>
		</div>
	</nav>
	<div class="container mt-5 pt-5">
		<div class="row">
			<div class="col-md-3">
				<div class="sidebar">
					<h4 class="text-primary">Người dùng</h4>
					<div class="list-group">
						<%
						List<User> userList = (List<User>) request.getAttribute("users");
						List<Integer> followingIds = (List<Integer>) request.getAttribute("followingIds");
						if (userList != null) {
							for (User user : userList) {
						%>
						<div
							class="list-group-item d-flex align-items-center justify-content-between">
							<form
								action="<%=(followingIds.contains(user.getId())) ? "/simple-social-network/follow/unfollow/"
		: "/simple-social-network/follow/"%>"
								method="post">
								<input name="userId" value="<%=(user.getId())%>" hidden /> <span><%=(user.getUsername())%></span>
								<button type="submit"
									class="btn btn-sm <%=(followingIds.contains(user.getId())) ? "btn-outline-danger" : "btn-outline-primary"%> btn-hover">
									<i
										class="bi <%=(followingIds.contains(user.getId())) ? "bi-person-dash" : "bi-person-plus"%>"></i>
								</button>
							</form>
						</div>
						<%
						}
						}
						%>
					</div>
				</div>
			</div>
			<div class="col-md-6 feed">
				<div class="card mb-4 post-card">
					<h5 class="card-header bg-primary text-white">Đăng bài mới</h5>
					<div class="card-body">
						<form action="<%=request.getContextPath()%>/post/create"
							method="post">
							<div class="mb-3">
								<input type="text" class="form-control" name="title"
									placeholder="Tiêu đề" required>
							</div>
							<div class="mb-3">
								<textarea class="form-control" name="content" rows="3"
									placeholder="Nội dung" required></textarea>
							</div>
							<button class="btn btn-success btn-hover">Đăng bài</button>
						</form>
					</div>
				</div>
				<%
				List<Post> postList = (List<Post>) request.getAttribute("posts");
				if (postList != null) {
					for (Post post : postList) {
						String authorName = "hungdev";
						for (User user : userList) {
					if (user.getId() == post.getUserId()) {
						authorName = user.getUsername();
						break;
					}
						}
				%>
				<div class="card post-card mb-3">
					<div class="card-body">
						<div class="d-flex align-items-center mb-2">
							<img src="<%=request.getContextPath()%>/resources/images/th.jpg"
								class="avatar me-2" alt="Avatar"> <span
								class="me-2 text-primary"><%=authorName%></span> <small
								class="text-muted ms-2"><%=post.getCreatedAt()%></small>
						</div>
						<h5 class="card-title text-dark"><%=post.getTitle()%></h5>
						<p class="card-text p-3 bg-light rounded shadow-sm"
							style="font-size: 1.1rem; font-weight: 500; line-height: 1.6;">
							<%=post.getBody()%>
						</p>
						<div class="d-flex justify-content-between">
							<button class="btn btn-outline-primary btn-sm btn-hover">
								<i class="bi bi-hand-thumbs-up"></i> Thích
							</button>
							<button class="btn btn-outline-secondary btn-sm btn-hover">
								<i class="bi bi-chat-dots"></i> Bình luận
							</button>
						</div>
					</div>
				</div>

				<%
				}
				}
				%>
			</div>
		</div>
	</div>
</body>
</html>