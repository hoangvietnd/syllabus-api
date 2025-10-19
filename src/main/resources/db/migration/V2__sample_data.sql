-- Tạo user admin mặc định
insert into users (email, password_hash, full_name, role, created_at)
values ('admin@example.com',
        '$2a$10$E0NRz6J8Jli9hJzMLm/AveY1V.1F6sylnCnMCU5Vx9B6nT6Uykfbe', -- bcrypt giả định
        'Administrator',
        'ADMIN',
        now());

-- Tạo user teacher
insert into users (email, password_hash, full_name, role, created_at)
values ('teacher@example.com',
        '$2a$10$7Q9Zf7sQ4Z8Zf7sQ4Z8Zf7sQ4Z8Zf7sQ4Z8Zf7sQ4Z8Zf7sQ4Z8Zf7',
        'John Teacher',
        'TEACHER',
        now());

-- Tạo user student
insert into users (email, password_hash, full_name, role, created_at)
values ('student@example.com',
        '$2a$10$7Q9Zf7sQ4Z8Zf7sQ4Z8Zf7sQ4Z8Zf7sQ4Z8Zf7sQ4Z8Zf7sQ4Z8Zf7',
        'Jane Student',
        'STUDENT',
        now());

-- Tạo khóa học mẫu
insert into courses (title, description, tags, version, created_by, created_at, updated_at)
values ('Lập trình Java cơ bản',
        'Khóa học nhập môn Java cho người mới bắt đầu',
        '["java","backend","beginner"]',
        1,
        1,
        now(),
        now());

-- Tạo module cho khóa học
insert into modules (course_id, title, order_index, summary, created_at, updated_at)
values (1, 'Giới thiệu về Java', 1, 'Tổng quan về ngôn ngữ Java và môi trường phát triển', now(), now());

-- Tạo lesson cho module
insert into lessons (module_id, title, content, order_index, duration_minutes, created_at, updated_at)
values (1, 'Cài đặt JDK và IDE', 'Hướng dẫn cài đặt JDK và IntelliJ IDEA', 1, 30, now(), now());

-- Tạo material cho lesson
insert into materials (lesson_id, type, url_or_path, name, size_bytes, mime_type, created_at)
values (1, 'PDF', '/uploads/java-setup.pdf', 'Hướng dẫn cài đặt Java', 102400, 'application/pdf', now());