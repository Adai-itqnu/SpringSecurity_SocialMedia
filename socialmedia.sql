-- phpMyAdmin SQL Dump
-- version 5.2.2
-- https://www.phpmyadmin.net/
--
-- Host: localhost:3306
-- Generation Time: May 09, 2025 at 01:37 PM
-- Server version: 8.4.3
-- PHP Version: 8.3.16

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `socialmedia`
--

-- --------------------------------------------------------

--
-- Table structure for table `follows`
--

CREATE TABLE `follows` (
  `follower_id` int NOT NULL,
  `following_id` int NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `follows`
--

INSERT INTO `follows` (`follower_id`, `following_id`, `created_at`) VALUES
(1, 1, '2025-03-19 08:33:04'),
(2, 1, '2025-04-20 07:49:52'),
(2, 5, '2025-04-20 07:49:22');

-- --------------------------------------------------------

--
-- Table structure for table `posts`
--

CREATE TABLE `posts` (
  `id` int NOT NULL,
  `title` varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
  `body` text COLLATE utf8mb4_general_ci NOT NULL,
  `user_id` int NOT NULL,
  `status` enum('DRAFTED','POSTED') COLLATE utf8mb4_general_ci NOT NULL DEFAULT 'DRAFTED',
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `posts`
--

INSERT INTO `posts` (`id`, `title`, `body`, `user_id`, `status`, `created_at`) VALUES
(1, 'Chào mừng đến với Social Network!', 'Chúng tôi rất vui mừng khi bạn tham gia. Hãy chia sẻ những khoảnh khắc tuyệt vời cùng mọi người!', 2, 'POSTED', '2025-03-19 08:32:46'),
(2, 'Lời nhắc nhở mỗi ngày', 'Hãy nhớ dành thời gian cho bản thân. Sức khỏe và tinh thần luôn là điều quan trọng nhất!', 1, 'POSTED', '2025-03-19 08:32:46'),
(3, 'Mẹo nhỏ giúp tập trung', 'Tắt thông báo điện thoại và đặt ra khung giờ làm việc không bị gián đoạn. Bạn sẽ thấy năng suất hơn!', 2, 'POSTED', '2025-03-19 08:32:46'),
(4, 'Hôm nay bạn đã cười chưa?', 'Dù bận rộn thế nào, đừng quên nở nụ cười. Một nụ cười có thể thay đổi cả ngày của bạn đấy!', 2, 'POSTED', '2025-03-19 08:32:46'),
(5, 'Sách hay nên đọc', 'Nếu bạn đang tìm sách, tôi khuyên bạn nên thử đọc \"7 Thói quen của người thành đạt\" – rất bổ ích!', 2, 'POSTED', '2025-03-19 08:32:46'),
(6, 'Quote hay', '“Hành trình ngàn dặm bắt đầu từ một bước chân.” – Lao Tzu', 2, 'DRAFTED', '2025-03-19 08:32:46'),
(7, 'Du lịch Đà Lạt', 'Bài viết chia sẻ kinh nghiệm du lịch Đà Lạt 3 ngày 2 đêm.', 3, 'POSTED', '2025-04-20 08:01:45'),
(8, 'Học lập trình Java', 'Hướng dẫn học lập trình Java cho người mới bắt đầu.', 3, 'POSTED', '2025-04-20 08:01:45'),
(9, 'Ẩm thực miền Trung', 'Khám phá những món ăn đặc sắc miền Trung Việt Nam.', 3, 'DRAFTED', '2025-04-20 08:01:45'),
(10, 'Review sách hay', 'Giới thiệu một số cuốn sách nên đọc trong năm nay.', 3, 'POSTED', '2025-04-20 08:01:45'),
(11, 'Kỹ năng mềm cho sinh viên', 'Tầm quan trọng của kỹ năng mềm trong môi trường đại học.', 3, 'DRAFTED', '2025-04-20 08:01:45'),
(12, 'Hành trình khám phá Tây Bắc', 'Chia sẻ kinh nghiệm đi phượt Tây Bắc mùa lúa chín.', 4, 'POSTED', '2025-04-20 08:03:43'),
(13, 'Lập trình Web cơ bản', 'Giới thiệu các bước xây dựng một website đơn giản bằng HTML, CSS.', 4, 'POSTED', '2025-04-20 08:03:43'),
(14, 'Tự học tiếng Anh giao tiếp', 'Các mẹo đơn giản giúp bạn luyện tiếng Anh mỗi ngày.', 4, 'POSTED', '2025-04-20 08:03:43'),
(15, 'Sống xanh mỗi ngày', 'Gợi ý những hành động nhỏ giúp bảo vệ môi trường.', 4, 'POSTED', '2025-04-20 08:03:43'),
(16, 'Phân biệt AI và Machine Learning', 'Giải thích ngắn gọn sự khác nhau giữa trí tuệ nhân tạo và học máy.', 4, 'POSTED', '2025-04-20 08:03:43'),
(20, 'bet88', 'Quà tặng cột sống', 1, 'POSTED', '2025-04-26 16:04:12'),
(21, 'aydo', '123', 1, 'POSTED', '2025-05-06 14:12:40');

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `id` int NOT NULL,
  `username` varchar(50) COLLATE utf8mb4_general_ci NOT NULL,
  `password` varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
  `role` enum('ROLE_ADMIN','ROLE_USER') COLLATE utf8mb4_general_ci NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`id`, `username`, `password`, `role`, `created_at`) VALUES
(1, 'hungdev', '$2a$10$YRV.XpuGOVNgNKwOK/G5ieniDj0NHDsv8k9SBrRgrtbjgsKUov3B6', 'ROLE_ADMIN', '2025-03-19 08:28:54'),
(2, 'caubelauga', '$2a$10$ZYmp6NRHdYnv/RURBCF66eumfJ4EyHOtTFbR7XgqAMy06kz6c.3c.', 'ROLE_USER', '2025-03-19 08:29:18'),
(3, 'danh', '$2a$10$FKy4kPCyfbVSf7JuhwailOpPEZQ1x2k2NVnaVn.eLQeZYHkWfiJqS', 'ROLE_USER', '2025-03-19 08:47:19'),
(4, 'hongnhung', '$2a$10$NQA4RtDRKuXIo7h4IFv0n.oM/.fjSC7sxYc/rdn9wDeVpcCRkNzxu', 'ROLE_USER', '2025-03-19 09:02:03'),
(5, 'huynh', '$2a$10$DjmahA55GGgXaaQMpcaP/OG3SF2lPjWkfydJJUhPm3/GewFA.jdN2', 'ROLE_USER', '2025-03-19 09:02:10');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `follows`
--
ALTER TABLE `follows`
  ADD PRIMARY KEY (`follower_id`,`following_id`),
  ADD KEY `followed_user_id` (`following_id`);

--
-- Indexes for table `posts`
--
ALTER TABLE `posts`
  ADD PRIMARY KEY (`id`),
  ADD KEY `user_id` (`user_id`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `username` (`username`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `posts`
--
ALTER TABLE `posts`
  MODIFY `id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=22;

--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `follows`
--
ALTER TABLE `follows`
  ADD CONSTRAINT `follows_ibfk_1` FOREIGN KEY (`follower_id`) REFERENCES `users` (`id`) ON DELETE CASCADE,
  ADD CONSTRAINT `follows_ibfk_2` FOREIGN KEY (`following_id`) REFERENCES `users` (`id`) ON DELETE CASCADE;

--
-- Constraints for table `posts`
--
ALTER TABLE `posts`
  ADD CONSTRAINT `posts_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
