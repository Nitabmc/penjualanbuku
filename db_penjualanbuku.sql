/*
 Navicat Premium Data Transfer

 Source Server         : xampp
 Source Server Type    : MySQL
 Source Server Version : 100419
 Source Host           : localhost:3306
 Source Schema         : db_penjualanbuku

 Target Server Type    : MySQL
 Target Server Version : 100419
 File Encoding         : 65001

 Date: 13/01/2023 01:38:07
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for tbl_buku
-- ----------------------------
DROP TABLE IF EXISTS `tbl_buku`;
CREATE TABLE `tbl_buku`  (
  `kode_buku` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `judul_buku` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `pengarang` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `penerbit` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `kode_jenisbuku` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`kode_buku`) USING BTREE,
  INDEX `kode_jenisbuku`(`kode_jenisbuku`) USING BTREE,
  CONSTRAINT `tbl_buku_ibfk_1` FOREIGN KEY (`kode_jenisbuku`) REFERENCES `tbl_jenisbuku` (`kode_jenisbuku`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tbl_buku
-- ----------------------------
INSERT INTO `tbl_buku` VALUES ('BUK-01', 'haland', 'Kimia wow', 'Fiksi', '101');
INSERT INTO `tbl_buku` VALUES ('BUK-02', 'ABjad', 'test 1', 'test 2', '201');

-- ----------------------------
-- Table structure for tbl_jenisbuku
-- ----------------------------
DROP TABLE IF EXISTS `tbl_jenisbuku`;
CREATE TABLE `tbl_jenisbuku`  (
  `kode_jenisbuku` varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `nama_jenisbuku` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  PRIMARY KEY (`kode_jenisbuku`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tbl_jenisbuku
-- ----------------------------
INSERT INTO `tbl_jenisbuku` VALUES ('101', 'Fiksi');
INSERT INTO `tbl_jenisbuku` VALUES ('201', 'Kimia');
INSERT INTO `tbl_jenisbuku` VALUES ('301', 'Matematika');
INSERT INTO `tbl_jenisbuku` VALUES ('test', 'dddd');

-- ----------------------------
-- Table structure for tbl_transaksi
-- ----------------------------
DROP TABLE IF EXISTS `tbl_transaksi`;
CREATE TABLE `tbl_transaksi`  (
  `no_transaksi` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `tanggal` date NOT NULL,
  `nama_pembeli` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `harga` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `dibayar` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `kembali` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `kode_buku` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`no_transaksi`) USING BTREE,
  INDEX `kode_buku`(`kode_buku`) USING BTREE,
  CONSTRAINT `tbl_transaksi_ibfk_1` FOREIGN KEY (`kode_buku`) REFERENCES `tbl_buku` (`kode_buku`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tbl_transaksi
-- ----------------------------
INSERT INTO `tbl_transaksi` VALUES ('T0003', '2023-01-13', 'yanto', '500000', '1000000', '500000', 'BUK-01');

-- ----------------------------
-- Table structure for tbl_user
-- ----------------------------
DROP TABLE IF EXISTS `tbl_user`;
CREATE TABLE `tbl_user`  (
  `username` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `password` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tbl_user
-- ----------------------------
INSERT INTO `tbl_user` VALUES ('admin', 'admin');

SET FOREIGN_KEY_CHECKS = 1;
