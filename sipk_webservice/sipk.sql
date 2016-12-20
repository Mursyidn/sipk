-- phpMyAdmin SQL Dump
-- version 4.5.1
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: Nov 05, 2016 at 01:16 AM
-- Server version: 10.1.13-MariaDB
-- PHP Version: 5.6.21

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `sipk`
--

-- --------------------------------------------------------

--
-- Table structure for table `admin`
--

CREATE TABLE `admin` (
  `kode_admin` int(4) NOT NULL,
  `nik_admin` int(10) NOT NULL,
  `nama_admin` varchar(50) NOT NULL,
  `username` varchar(10) NOT NULL,
  `password` varchar(255) NOT NULL,
  `kategori` varchar(20) NOT NULL,
  `kode_perusahaan` varchar(30) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `admin`
--

INSERT INTO `admin` (`kode_admin`, `nik_admin`, `nama_admin`, `username`, `password`, `kategori`, `kode_perusahaan`) VALUES
(13, 1232, 'superadmin', 'superadmin', '$2y$10$H7q6XwQ5puoMqDDRIyc3w.uWnxhEtNJIz0SEP2k8FQ6ZdDb.Tx89O', 'Admin', 'Semua'),
(15, 1123, 'Admin MSP', 'adminmsp', '$2y$10$QOH6vQhava0omidhNNw2eul0AjgxmrBiJ4dL8F18alpC6Ea3M/cKK', 'Admin', 'MSP');

-- --------------------------------------------------------

--
-- Table structure for table `divisi`
--

CREATE TABLE `divisi` (
  `kode_divisi` varchar(10) NOT NULL,
  `kode_perusahaan` varchar(10) NOT NULL,
  `nama_divisi` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `divisi`
--

INSERT INTO `divisi` (`kode_divisi`, `kode_perusahaan`, `nama_divisi`) VALUES
('AA', 'MICRO', 'AA'),
('AD', 'MSP', 'AD'),
('AN', 'MICRO', 'AN'),
('bb', 'MICRO', 'bb'),
('ITM', 'MSP', 'ITM');

-- --------------------------------------------------------

--
-- Table structure for table `gaji`
--

CREATE TABLE `gaji` (
  `kode_gaji` int(10) NOT NULL,
  `kode_karyawan` int(10) NOT NULL,
  `kode_tunjangan` int(10) NOT NULL,
  `kode_upah_pokok` int(10) NOT NULL,
  `periode` varchar(20) NOT NULL,
  `periode_kehadiran` varchar(25) NOT NULL,
  `h_kerja` int(2) NOT NULL,
  `h_cuti` int(2) NOT NULL,
  `h_sakit` int(2) NOT NULL,
  `h_izin_p` int(2) NOT NULL,
  `h_tanpa_k` int(2) NOT NULL,
  `jam_minus` float NOT NULL,
  `kekurangan_jam_kerja` float NOT NULL,
  `potongan_pinjaman` int(15) DEFAULT '0',
  `ket_potongan` int(2) DEFAULT NULL,
  `potongan_jam_kerja` int(15) NOT NULL,
  `upah_pokok` int(15) NOT NULL,
  `t_makan` int(15) NOT NULL,
  `t_transport` int(15) NOT NULL,
  `t_bpjs_kesehatan` int(15) NOT NULL,
  `t_bpjs_ketenagakerjaan` int(15) NOT NULL,
  `t_pajak` int(15) NOT NULL,
  `t_keahlian` int(15) NOT NULL,
  `t_jabatan` int(15) NOT NULL,
  `t_produktivitas` int(15) NOT NULL,
  `t_disiplin` int(15) NOT NULL,
  `tot_t_lembur` int(15) DEFAULT '0',
  `tot_gaji` int(15) NOT NULL,
  `jumlah_pengeluaran` int(15) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `gaji`
--

INSERT INTO `gaji` (`kode_gaji`, `kode_karyawan`, `kode_tunjangan`, `kode_upah_pokok`, `periode`, `periode_kehadiran`, `h_kerja`, `h_cuti`, `h_sakit`, `h_izin_p`, `h_tanpa_k`, `jam_minus`, `kekurangan_jam_kerja`, `potongan_pinjaman`, `ket_potongan`, `potongan_jam_kerja`, `upah_pokok`, `t_makan`, `t_transport`, `t_bpjs_kesehatan`, `t_bpjs_ketenagakerjaan`, `t_pajak`, `t_keahlian`, `t_jabatan`, `t_produktivitas`, `t_disiplin`, `tot_t_lembur`, `tot_gaji`, `jumlah_pengeluaran`) VALUES
(8, 15, 2, 2, 'Oktober 2016', '01/10/2016 - 31/10/2016', 22, 0, 0, 1, 1, 5, 21, 250000, 3, 242775, 2000000, 200000, 200000, 10000, 0, 0, 0, 0, 0, 20000, 0, 1927225, 1937225),
(9, 16, 3, 3, 'Oktober 2016', '01/10/2016 - 31/10/2016', 22, 1, 1, 0, 0, 2, 2, 100000, 1, 34682, 3000000, 400000, 200000, 10000, 10000, 10000, 20000, 10000, 10000, 20000, 108035, 3633353, 3663353),
(10, 18, 4, 4, 'Oktober 2016', '01/10/2016 - 31/10/2016', 22, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2000000, 220000, 220000, 0, 0, 0, 0, 0, 0, 0, 37341, 2477341, 2477341);

-- --------------------------------------------------------

--
-- Table structure for table `histori_job_karyawan`
--

CREATE TABLE `histori_job_karyawan` (
  `kode_histori_job_karyawan` int(10) NOT NULL,
  `kode_gaji` int(10) NOT NULL,
  `kode_job` varchar(10) NOT NULL,
  `kode_job_karyawan` int(10) NOT NULL,
  `kode_karyawan` int(10) NOT NULL,
  `periode` varchar(20) NOT NULL,
  `nama_job` varchar(50) NOT NULL,
  `bobot` int(3) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `histori_job_karyawan`
--

INSERT INTO `histori_job_karyawan` (`kode_histori_job_karyawan`, `kode_gaji`, `kode_job`, `kode_job_karyawan`, `kode_karyawan`, `periode`, `nama_job`, `bobot`) VALUES
(1, 5, 'Antiran', 1, 18, 'September 2016', 'Anrian BNI', 50),
(2, 8, 'Antiran', 2, 15, 'Oktober 2016', 'Anrian BNI', 50),
(3, 8, 'A1', 4, 15, 'Oktober 2016', 'SKK Migas', 50),
(4, 9, 'A1', 3, 16, 'Oktober 2016', 'SKK Migas', 100),
(5, 10, 'Antiran', 1, 18, 'Oktober 2016', 'Anrian BNI', 50);

-- --------------------------------------------------------

--
-- Table structure for table `job`
--

CREATE TABLE `job` (
  `kode_job` varchar(10) NOT NULL,
  `kode_perusahaan` varchar(10) NOT NULL,
  `no_kontrak` varchar(30) NOT NULL,
  `client` varchar(100) NOT NULL,
  `nama_job` varchar(50) NOT NULL,
  `tgl_awal` char(10) NOT NULL,
  `tgl_akhir` char(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `job`
--

INSERT INTO `job` (`kode_job`, `kode_perusahaan`, `no_kontrak`, `client`, `nama_job`, `tgl_awal`, `tgl_akhir`) VALUES
('A1', 'MICRO', '12345', 'SKK', 'SKK Migas', '01/10/2016', '30/09/2016'),
('Antiran', 'MICRO', '54321', 'BNI', 'Anrian BNI', '18/06/2016', '05/10/2016');

-- --------------------------------------------------------

--
-- Table structure for table `job_karyawan`
--

CREATE TABLE `job_karyawan` (
  `kode_job_karyawan` int(10) NOT NULL,
  `kode_karyawan` int(10) NOT NULL,
  `kode_job` varchar(10) NOT NULL,
  `bobot` int(3) NOT NULL,
  `status_job_karyawan` varchar(15) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `job_karyawan`
--

INSERT INTO `job_karyawan` (`kode_job_karyawan`, `kode_karyawan`, `kode_job`, `bobot`, `status_job_karyawan`) VALUES
(1, 18, 'Antiran', 50, 'Aktif'),
(2, 15, 'Antiran', 50, 'Aktif'),
(3, 16, 'A1', 100, 'Aktif'),
(4, 15, 'A1', 50, 'Aktif');

-- --------------------------------------------------------

--
-- Table structure for table `karyawan`
--

CREATE TABLE `karyawan` (
  `kode_karyawan` int(10) NOT NULL,
  `nik` varchar(10) NOT NULL,
  `nama_karyawan` varchar(50) NOT NULL,
  `alamat` text NOT NULL,
  `tempat_lahir` varchar(50) NOT NULL,
  `tanggal_lahir` char(10) NOT NULL,
  `status_kawin` varchar(15) NOT NULL,
  `jenis_kelamin` varchar(10) NOT NULL,
  `kode_perusahaan` varchar(10) NOT NULL,
  `kode_divisi` varchar(10) NOT NULL,
  `jabatan` varchar(50) DEFAULT NULL,
  `bank` varchar(30) DEFAULT NULL,
  `norek` varchar(20) DEFAULT NULL,
  `metode_pembayaran` varchar(10) NOT NULL,
  `status_karyawan` varchar(15) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `karyawan`
--

INSERT INTO `karyawan` (`kode_karyawan`, `nik`, `nama_karyawan`, `alamat`, `tempat_lahir`, `tanggal_lahir`, `status_kawin`, `jenis_kelamin`, `kode_perusahaan`, `kode_divisi`, `jabatan`, `bank`, `norek`, `metode_pembayaran`, `status_karyawan`) VALUES
(15, '123', 'Ridwan', 'bekasi', 'bekasi', '05/10/2016', 'Belum Kawin', 'Laki-laki', 'MSP', 'AD', '', 'BRI', '123123', 'Transfer', 'Tetap'),
(16, '123123', 'Naufal', 'bekasi', 'nakndlaw', '04/10/2016', 'Belum Kawin', 'Laki-laki', 'MSP', 'ITM', '', '', '', 'Tunai', 'Tetap'),
(17, '113', 'mursyid', 'bandung', 'jakarta', '04/10/1995', 'Belum Kawin', 'Laki-laki', 'MICRO', 'AN', 'ITM', 'BNI', '12144442', 'Transfer', 'Outsourcing'),
(18, '12', 'ahmad', 'bandung', 'jakarta', '04/10/2016', 'Sudah Kawin', 'Perempuan', 'MICRO', 'AN', '', '', '', 'Tunai', 'Kontrak');

-- --------------------------------------------------------

--
-- Table structure for table `perusahaan`
--

CREATE TABLE `perusahaan` (
  `kode_perusahaan` varchar(10) NOT NULL,
  `nama_perusahaan` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `perusahaan`
--

INSERT INTO `perusahaan` (`kode_perusahaan`, `nama_perusahaan`) VALUES
('MICRO', 'Micro'),
('MSP', 'MSP');

-- --------------------------------------------------------

--
-- Table structure for table `thr`
--

CREATE TABLE `thr` (
  `kode_thr` int(10) NOT NULL,
  `kode_karyawan` int(10) NOT NULL,
  `periode` varchar(10) NOT NULL,
  `thr` int(15) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `thr`
--

INSERT INTO `thr` (`kode_thr`, `kode_karyawan`, `periode`, `thr`) VALUES
(1, 17, '04/10/2016', 300000),
(2, 15, '07/09/2016', 200000),
(3, 18, '04/10/2016', 300000);

-- --------------------------------------------------------

--
-- Table structure for table `tunjangan`
--

CREATE TABLE `tunjangan` (
  `kode_tunjangan` int(10) NOT NULL,
  `kode_karyawan` int(10) NOT NULL,
  `t_makan` int(15) NOT NULL,
  `t_transport` int(15) NOT NULL,
  `t_bpjs_kesehatan` int(15) DEFAULT '0',
  `t_bpjs_ketenagakerjaan` int(15) DEFAULT '0',
  `t_pajak` int(15) DEFAULT '0',
  `t_keahlian` int(15) DEFAULT '0',
  `t_jabatan` int(15) DEFAULT '0',
  `t_produktivitas` int(15) DEFAULT '0',
  `t_disiplin` int(15) DEFAULT '0',
  `status_tunjangan` varchar(15) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `tunjangan`
--

INSERT INTO `tunjangan` (`kode_tunjangan`, `kode_karyawan`, `t_makan`, `t_transport`, `t_bpjs_kesehatan`, `t_bpjs_ketenagakerjaan`, `t_pajak`, `t_keahlian`, `t_jabatan`, `t_produktivitas`, `t_disiplin`, `status_tunjangan`) VALUES
(1, 17, 10000, 10000, 0, 0, 0, 0, 50000, 50000, 50000, 'Aktif'),
(2, 15, 10000, 10000, 10000, 0, 0, 0, 0, 0, 20000, 'Aktif'),
(3, 16, 20000, 10000, 10000, 10000, 10000, 20000, 10000, 10000, 20000, 'Aktif'),
(4, 18, 10000, 10000, 0, 0, 0, 0, 0, 0, 0, 'Aktif');

-- --------------------------------------------------------

--
-- Table structure for table `tunjangan_lembur`
--

CREATE TABLE `tunjangan_lembur` (
  `kode_tunjangan_lembur` int(10) NOT NULL,
  `kode_karyawan` int(10) NOT NULL,
  `kode_tunjangan` int(10) NOT NULL,
  `kode_upah_pokok` int(10) NOT NULL,
  `tgl_lembur` char(10) DEFAULT NULL,
  `periode_gajian` varchar(20) NOT NULL,
  `kategori` varchar(40) NOT NULL,
  `durasi` float NOT NULL,
  `upah_perjam` int(15) DEFAULT NULL,
  `t_makan` int(15) DEFAULT '0',
  `t_transport` int(15) DEFAULT '0',
  `t_lembur` int(15) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `tunjangan_lembur`
--

INSERT INTO `tunjangan_lembur` (`kode_tunjangan_lembur`, `kode_karyawan`, `kode_tunjangan`, `kode_upah_pokok`, `tgl_lembur`, `periode_gajian`, `kategori`, `durasi`, `upah_perjam`, `t_makan`, `t_transport`, `t_lembur`) VALUES
(1, 17, 1, 1, '02/10/2016', 'Oktober 2016', 'Hari Sabtu', 1, 5780, 10000, NULL, 18671),
(2, 18, 4, 4, '05/10/2016', 'Oktober 2016', 'Hari Sabtu', 1, 11561, 10000, 10000, 37341),
(3, 17, 1, 1, '04/10/2016', 'Oktober 2016', 'Hari Libur Nasional', 1, 5780, 10000, NULL, 21561),
(4, 16, 3, 3, '04/10/2016', 'Oktober 2016', 'Hari Biasa', 3, 17341, 20000, 10000, 108035),
(5, 17, 1, 1, '28/10/2016', 'Nopember 2016', 'Hari Biasa', 4, 5780, 10000, NULL, 44682),
(6, 17, 1, 1, '15/12/2016', 'Desember 2016', 'Hari Biasa', 5, 5780, 10000, 10000, 63353);

-- --------------------------------------------------------

--
-- Table structure for table `upah_pokok`
--

CREATE TABLE `upah_pokok` (
  `kode_upah_pokok` int(10) NOT NULL,
  `kode_karyawan` int(10) NOT NULL,
  `upah_pokok` int(15) NOT NULL,
  `status_upah_pokok` varchar(15) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `upah_pokok`
--

INSERT INTO `upah_pokok` (`kode_upah_pokok`, `kode_karyawan`, `upah_pokok`, `status_upah_pokok`) VALUES
(1, 17, 1000000, 'Aktif'),
(2, 15, 2000000, 'Aktif'),
(3, 16, 3000000, 'Aktif'),
(4, 18, 2000000, 'Aktif');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `admin`
--
ALTER TABLE `admin`
  ADD PRIMARY KEY (`kode_admin`);

--
-- Indexes for table `divisi`
--
ALTER TABLE `divisi`
  ADD PRIMARY KEY (`kode_divisi`);

--
-- Indexes for table `gaji`
--
ALTER TABLE `gaji`
  ADD PRIMARY KEY (`kode_gaji`);

--
-- Indexes for table `histori_job_karyawan`
--
ALTER TABLE `histori_job_karyawan`
  ADD PRIMARY KEY (`kode_histori_job_karyawan`);

--
-- Indexes for table `job`
--
ALTER TABLE `job`
  ADD PRIMARY KEY (`kode_job`);

--
-- Indexes for table `job_karyawan`
--
ALTER TABLE `job_karyawan`
  ADD PRIMARY KEY (`kode_job_karyawan`);

--
-- Indexes for table `karyawan`
--
ALTER TABLE `karyawan`
  ADD PRIMARY KEY (`kode_karyawan`);

--
-- Indexes for table `perusahaan`
--
ALTER TABLE `perusahaan`
  ADD PRIMARY KEY (`kode_perusahaan`);

--
-- Indexes for table `thr`
--
ALTER TABLE `thr`
  ADD PRIMARY KEY (`kode_thr`);

--
-- Indexes for table `tunjangan`
--
ALTER TABLE `tunjangan`
  ADD PRIMARY KEY (`kode_tunjangan`);

--
-- Indexes for table `tunjangan_lembur`
--
ALTER TABLE `tunjangan_lembur`
  ADD PRIMARY KEY (`kode_tunjangan_lembur`);

--
-- Indexes for table `upah_pokok`
--
ALTER TABLE `upah_pokok`
  ADD PRIMARY KEY (`kode_upah_pokok`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `admin`
--
ALTER TABLE `admin`
  MODIFY `kode_admin` int(4) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=16;
--
-- AUTO_INCREMENT for table `gaji`
--
ALTER TABLE `gaji`
  MODIFY `kode_gaji` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;
--
-- AUTO_INCREMENT for table `histori_job_karyawan`
--
ALTER TABLE `histori_job_karyawan`
  MODIFY `kode_histori_job_karyawan` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;
--
-- AUTO_INCREMENT for table `job_karyawan`
--
ALTER TABLE `job_karyawan`
  MODIFY `kode_job_karyawan` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;
--
-- AUTO_INCREMENT for table `karyawan`
--
ALTER TABLE `karyawan`
  MODIFY `kode_karyawan` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=19;
--
-- AUTO_INCREMENT for table `thr`
--
ALTER TABLE `thr`
  MODIFY `kode_thr` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;
--
-- AUTO_INCREMENT for table `tunjangan`
--
ALTER TABLE `tunjangan`
  MODIFY `kode_tunjangan` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;
--
-- AUTO_INCREMENT for table `tunjangan_lembur`
--
ALTER TABLE `tunjangan_lembur`
  MODIFY `kode_tunjangan_lembur` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;
--
-- AUTO_INCREMENT for table `upah_pokok`
--
ALTER TABLE `upah_pokok`
  MODIFY `kode_upah_pokok` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
