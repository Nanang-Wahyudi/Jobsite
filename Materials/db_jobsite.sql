-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: localhost:3306
-- Waktu pembuatan: 26 Jun 2024 pada 11.44
-- Versi server: 8.0.30
-- Versi PHP: 8.1.10

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `db_jobsite`
--

-- --------------------------------------------------------

--
-- Struktur dari tabel `applicant`
--

CREATE TABLE `applicant` (
  `id` varchar(255) NOT NULL,
  `applicant_date` varchar(255) DEFAULT NULL,
  `cv` longblob,
  `status` varchar(255) DEFAULT NULL,
  `job_id` varchar(255) DEFAULT NULL,
  `user_detail_id` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Struktur dari tabel `company`
--

CREATE TABLE `company` (
  `id` varchar(255) NOT NULL,
  `address` varchar(255) DEFAULT NULL,
  `banner` varchar(255) DEFAULT NULL,
  `description` longtext,
  `email` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `picture` varchar(255) DEFAULT NULL,
  `user_id` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Struktur dari tabel `education`
--

CREATE TABLE `education` (
  `id` varchar(255) NOT NULL,
  `avg_score` varchar(255) DEFAULT NULL,
  `major` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Struktur dari tabel `feedback`
--

CREATE TABLE `feedback` (
  `id` varchar(255) NOT NULL,
  `comment` longtext,
  `post_date` varchar(255) DEFAULT NULL,
  `rating` int DEFAULT NULL,
  `applicant_id` varchar(255) DEFAULT NULL,
  `user_detail_id` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Struktur dari tabel `job`
--

CREATE TABLE `job` (
  `id` varchar(255) NOT NULL,
  `description` longtext,
  `is_active` bit(1) DEFAULT NULL,
  `post_date` varchar(255) DEFAULT NULL,
  `qualification` longtext,
  `salary` varchar(255) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `type` varchar(255) DEFAULT NULL,
  `company_id` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Struktur dari tabel `privilege`
--

CREATE TABLE `privilege` (
  `id` varchar(255) NOT NULL,
  `name` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data untuk tabel `privilege`
--

INSERT INTO `privilege` (`id`, `name`) VALUES
('ac5635f4-c5e5-4653-90b5-5c63e5579c67', 'CREATE_ADMIN'),
('9a9cc867-dd0b-4588-b972-ef53e50e95a2', 'CREATE_COMPANY'),
('9fc58942-eeb4-4fb1-9f56-d1b9108d5fb3', 'CREATE_USER'),
('f0147728-1fc1-4892-9ca9-66f90cfe9bac', 'DELETE_ADMIN'),
('f1519da8-9757-4699-a70b-cb140d4ae677', 'DELETE_COMPANY'),
('0bc87f51-6095-421a-a9bc-13e152c30f83', 'DELETE_USER'),
('8d9346d7-6238-471f-8e65-5630add43f4e', 'READ_ADMIN'),
('0ec0be2e-09d4-41ec-9423-99af290d5d2f', 'READ_COMPANY'),
('a23178f2-be2e-4e22-92cf-914a7fe1e22a', 'READ_USER'),
('496bc725-c122-419b-bfa0-0a5c125527bf', 'UPDATE_ADMIN'),
('9f2a7f0c-ceb4-4722-93e1-22bb6bf02124', 'UPDATE_COMPANY'),
('e9c6f7cb-7486-47b8-af83-a202279193f9', 'UPDATE_USER');

-- --------------------------------------------------------

--
-- Struktur dari tabel `role`
--

CREATE TABLE `role` (
  `id` varchar(255) NOT NULL,
  `name` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data untuk tabel `role`
--

INSERT INTO `role` (`id`, `name`) VALUES
('2aa05d8d-0f69-401a-81a9-63058b1438a2', 'ADMIN'),
('12650dc4-a3f8-46ad-917c-479aab3e35cf', 'COMPANY'),
('ac776101-3478-490a-b72d-9725e9eb5641', 'USER');

-- --------------------------------------------------------

--
-- Struktur dari tabel `role_privilege`
--

CREATE TABLE `role_privilege` (
  `role_id` varchar(255) NOT NULL,
  `privilege_id` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data untuk tabel `role_privilege`
--

INSERT INTO `role_privilege` (`role_id`, `privilege_id`) VALUES
('2aa05d8d-0f69-401a-81a9-63058b1438a2', 'ac5635f4-c5e5-4653-90b5-5c63e5579c67'),
('2aa05d8d-0f69-401a-81a9-63058b1438a2', '8d9346d7-6238-471f-8e65-5630add43f4e'),
('2aa05d8d-0f69-401a-81a9-63058b1438a2', '496bc725-c122-419b-bfa0-0a5c125527bf'),
('2aa05d8d-0f69-401a-81a9-63058b1438a2', 'f0147728-1fc1-4892-9ca9-66f90cfe9bac'),
('ac776101-3478-490a-b72d-9725e9eb5641', '9fc58942-eeb4-4fb1-9f56-d1b9108d5fb3'),
('ac776101-3478-490a-b72d-9725e9eb5641', 'a23178f2-be2e-4e22-92cf-914a7fe1e22a'),
('ac776101-3478-490a-b72d-9725e9eb5641', 'e9c6f7cb-7486-47b8-af83-a202279193f9'),
('ac776101-3478-490a-b72d-9725e9eb5641', '0bc87f51-6095-421a-a9bc-13e152c30f83'),
('12650dc4-a3f8-46ad-917c-479aab3e35cf', '9a9cc867-dd0b-4588-b972-ef53e50e95a2'),
('12650dc4-a3f8-46ad-917c-479aab3e35cf', '0ec0be2e-09d4-41ec-9423-99af290d5d2f'),
('12650dc4-a3f8-46ad-917c-479aab3e35cf', '9f2a7f0c-ceb4-4722-93e1-22bb6bf02124'),
('12650dc4-a3f8-46ad-917c-479aab3e35cf', 'f1519da8-9757-4699-a70b-cb140d4ae677');

-- --------------------------------------------------------

--
-- Struktur dari tabel `skill`
--

CREATE TABLE `skill` (
  `id` varchar(255) NOT NULL,
  `name` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Struktur dari tabel `user`
--

CREATE TABLE `user` (
  `id` varchar(255) NOT NULL,
  `password` varchar(255) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Struktur dari tabel `user_detail`
--

CREATE TABLE `user_detail` (
  `id` varchar(255) NOT NULL,
  `address` varchar(255) DEFAULT NULL,
  `description` longtext,
  `email` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `picture` varchar(255) DEFAULT NULL,
  `user_id` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Struktur dari tabel `user_detail_education`
--

CREATE TABLE `user_detail_education` (
  `user_detail_id` varchar(255) NOT NULL,
  `education_id` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Struktur dari tabel `user_detail_skill`
--

CREATE TABLE `user_detail_skill` (
  `user_detail_id` varchar(255) NOT NULL,
  `skill_id` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Struktur dari tabel `user_role`
--

CREATE TABLE `user_role` (
  `user_id` varchar(255) NOT NULL,
  `role_id` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Indexes for dumped tables
--

--
-- Indeks untuk tabel `applicant`
--
ALTER TABLE `applicant`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKt74cl2p3amxj0ukd6ngwdryvl` (`job_id`),
  ADD KEY `FK3dwvg6xoqbsnc5863g6ibrrf1` (`user_detail_id`);

--
-- Indeks untuk tabel `company`
--
ALTER TABLE `company`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UK_bma9lv19ba3yjwf12a34xord3` (`email`),
  ADD UNIQUE KEY `UK_niu8sfil2gxywcru9ah3r4ec5` (`name`),
  ADD KEY `FKdy4v2yb46hefqicjpyj7b7e4s` (`user_id`);

--
-- Indeks untuk tabel `education`
--
ALTER TABLE `education`
  ADD PRIMARY KEY (`id`);

--
-- Indeks untuk tabel `feedback`
--
ALTER TABLE `feedback`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FK6lqtd36rbvet3p038yg4iw12q` (`applicant_id`),
  ADD KEY `FK14wcige3qpdtxfkyvvtqw8owc` (`user_detail_id`);

--
-- Indeks untuk tabel `job`
--
ALTER TABLE `job`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FK5q04favsasq8y70bsei7wv8fc` (`company_id`);

--
-- Indeks untuk tabel `privilege`
--
ALTER TABLE `privilege`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UK_h7iwbdg4ev8mgvmij76881tx8` (`name`);

--
-- Indeks untuk tabel `role`
--
ALTER TABLE `role`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UK_8sewwnpamngi6b1dwaa88askk` (`name`);

--
-- Indeks untuk tabel `role_privilege`
--
ALTER TABLE `role_privilege`
  ADD KEY `FKdkwbrwb5r8h74m1v7dqmhp99c` (`privilege_id`),
  ADD KEY `FKsykrtrdngu5iexmbti7lu9xa` (`role_id`);

--
-- Indeks untuk tabel `skill`
--
ALTER TABLE `skill`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UK_5ljf2l2h4odhtxrsuohlro4ir` (`name`);

--
-- Indeks untuk tabel `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UK_sb8bbouer5wak8vyiiy4pf2bx` (`username`);

--
-- Indeks untuk tabel `user_detail`
--
ALTER TABLE `user_detail`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UK_al4vy84pj6kshsqrsn9kc63sj` (`email`),
  ADD KEY `FKc2fr118twu8aratnm1qop1mn9` (`user_id`);

--
-- Indeks untuk tabel `user_detail_education`
--
ALTER TABLE `user_detail_education`
  ADD KEY `FKahtox3x8dyar60brqkpn8b827` (`education_id`),
  ADD KEY `FKmrya8i4uooma8svve14r5ikkk` (`user_detail_id`);

--
-- Indeks untuk tabel `user_detail_skill`
--
ALTER TABLE `user_detail_skill`
  ADD KEY `FKhcr14wv0cfvap8tja7bg62ero` (`skill_id`),
  ADD KEY `FKmt2f97ksu4q5q2ilijo75o4yn` (`user_detail_id`);

--
-- Indeks untuk tabel `user_role`
--
ALTER TABLE `user_role`
  ADD KEY `FKa68196081fvovjhkek5m97n3y` (`role_id`),
  ADD KEY `FK859n2jvi8ivhui0rl0esws6o` (`user_id`);

--
-- Ketidakleluasaan untuk tabel pelimpahan (Dumped Tables)
--

--
-- Ketidakleluasaan untuk tabel `applicant`
--
ALTER TABLE `applicant`
  ADD CONSTRAINT `FK3dwvg6xoqbsnc5863g6ibrrf1` FOREIGN KEY (`user_detail_id`) REFERENCES `user_detail` (`id`),
  ADD CONSTRAINT `FKt74cl2p3amxj0ukd6ngwdryvl` FOREIGN KEY (`job_id`) REFERENCES `job` (`id`);

--
-- Ketidakleluasaan untuk tabel `company`
--
ALTER TABLE `company`
  ADD CONSTRAINT `FKdy4v2yb46hefqicjpyj7b7e4s` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`);

--
-- Ketidakleluasaan untuk tabel `feedback`
--
ALTER TABLE `feedback`
  ADD CONSTRAINT `FK14wcige3qpdtxfkyvvtqw8owc` FOREIGN KEY (`user_detail_id`) REFERENCES `user_detail` (`id`),
  ADD CONSTRAINT `FK6lqtd36rbvet3p038yg4iw12q` FOREIGN KEY (`applicant_id`) REFERENCES `applicant` (`id`);

--
-- Ketidakleluasaan untuk tabel `job`
--
ALTER TABLE `job`
  ADD CONSTRAINT `FK5q04favsasq8y70bsei7wv8fc` FOREIGN KEY (`company_id`) REFERENCES `company` (`id`);

--
-- Ketidakleluasaan untuk tabel `role_privilege`
--
ALTER TABLE `role_privilege`
  ADD CONSTRAINT `FKdkwbrwb5r8h74m1v7dqmhp99c` FOREIGN KEY (`privilege_id`) REFERENCES `privilege` (`id`),
  ADD CONSTRAINT `FKsykrtrdngu5iexmbti7lu9xa` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`);

--
-- Ketidakleluasaan untuk tabel `user_detail`
--
ALTER TABLE `user_detail`
  ADD CONSTRAINT `FKc2fr118twu8aratnm1qop1mn9` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`);

--
-- Ketidakleluasaan untuk tabel `user_detail_education`
--
ALTER TABLE `user_detail_education`
  ADD CONSTRAINT `FKahtox3x8dyar60brqkpn8b827` FOREIGN KEY (`education_id`) REFERENCES `education` (`id`),
  ADD CONSTRAINT `FKmrya8i4uooma8svve14r5ikkk` FOREIGN KEY (`user_detail_id`) REFERENCES `user_detail` (`id`);

--
-- Ketidakleluasaan untuk tabel `user_detail_skill`
--
ALTER TABLE `user_detail_skill`
  ADD CONSTRAINT `FKhcr14wv0cfvap8tja7bg62ero` FOREIGN KEY (`skill_id`) REFERENCES `skill` (`id`),
  ADD CONSTRAINT `FKmt2f97ksu4q5q2ilijo75o4yn` FOREIGN KEY (`user_detail_id`) REFERENCES `user_detail` (`id`);

--
-- Ketidakleluasaan untuk tabel `user_role`
--
ALTER TABLE `user_role`
  ADD CONSTRAINT `FK859n2jvi8ivhui0rl0esws6o` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  ADD CONSTRAINT `FKa68196081fvovjhkek5m97n3y` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
