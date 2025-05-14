<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="com.hungdev.entities.Post" %>

<html>
<head>
    <title>Admin</title>
    <style>
        body { font-family: sans-serif; margin: 30px; background: #f5f5f5; }
        table { width: 100%; border-collapse: collapse; background: white; }
        th, td { padding: 10px; border: 1px solid #ccc; }
        th { background: #007bff; color: #fff; }
        tr:nth-child(even) { background: #f0f0f0; }
    </style>
</head>
<body>
    <h2><a href="${pageContext.request.contextPath}/home">Homepage</a></h2>
    <%
        List<Post> posts = (List<Post>) request.getAttribute("posts");
        if (posts != null && !posts.isEmpty()) {
    %>
    <table>
        <tr>
            <th>ID</th><th>Title</th><th>Body</th><th>User</th><th>Status</th><th>Created</th>
        </tr>
        <%
            for (Post post : posts) {
        %>
        <tr>
            <td><%= post.getId() %></td>
            <td><%= post.getTitle() %></td>
            <td><%= post.getBody() %></td>
            <td><%= post.getUserId() %></td>
            <td><%= post.getStatus() %></td>
            <td><%= post.getCreatedAt() %></td>
        </tr>
        <%
            }
        %>
    </table>
    <% } else { %>
        <p>No posts found.</p>
    <% } %>
</body>
</html>
 