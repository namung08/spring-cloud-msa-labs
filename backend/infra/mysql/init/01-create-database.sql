drop database if exists `user_service_db`;
drop database if exists `product_service_db`;
drop database if exists `order_service_db`;

create database if not exists `user_service_db` character set utf8mb4 collate utf8mb4_unicode_ci;
create database if not exists `product_service_db` character set utf8mb4 collate utf8mb4_unicode_ci;
create database if not exists `order_service_db` character set utf8mb4 collate utf8mb4_unicode_ci;
