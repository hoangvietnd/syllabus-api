-- Insert users
INSERT INTO users (email, password_hash, full_name, role, created_at)
VALUES 
    ('admin@example.com', '$2a$10$trvRVoyzvc.TJU.FOsTAJes0CyO06J/luaZmXYDg2c.CAARtKo9hC', 'Administrator', 'ADMIN', now()),
    ('instructor1@example.com', '$2a$10$trvRVoyzvc.TJU.FOsTAJes0CyO06J/luaZmXYDg2c.CAARtKo9hC', 'John Teacher', 'TEACHER', now()),
    ('student1@example.com', '$2a$10$trvRVoyzvc.TJU.FOsTAJes0CyO06J/luaZmXYDg2c.CAARtKo9hC', 'Alice Student', 'STUDENT', now());

-- Insert courses
INSERT INTO courses (title, description, tags, created_by, created_at, updated_at)
VALUES 
    ('Introduction to Programming', 'Learn the basics of programming with Python', '["python", "programming", "beginner"]'::jsonb, 1, now(), now()),
    ('Web Development Fundamentals', 'Master HTML, CSS and JavaScript', '["web", "html", "css", "javascript"]'::jsonb, 1, now(), now()),
    ('Data Structures and Algorithms', 'Essential computer science concepts', '["algorithms", "data-structures", "computer-science"]'::jsonb, 1, now(), now());

-- Insert modules
INSERT INTO modules (course_id, title, order_index, summary, created_at, updated_at)
VALUES 
    (1, 'Getting Started with Python', 1, 'Setup and basic concepts', now(), now()),
    (1, 'Variables and Data Types', 2, 'Understanding Python data types', now(), now()),
    (1, 'Control Flow', 3, 'Conditionals and loops', now(), now()),
    (2, 'HTML Basics', 1, 'Structure of web pages', now(), now()),
    (2, 'CSS Styling', 2, 'Making web pages beautiful', now(), now()),
    (2, 'JavaScript Fundamentals', 3, 'Adding interactivity', now(), now()),
    (3, 'Arrays and Lists', 1, 'Basic data structures', now(), now()),
    (3, 'Sorting Algorithms', 2, 'Common sorting techniques', now(), now()),
    (3, 'Graph Theory', 3, 'Understanding graphs', now(), now());

-- Insert lessons
INSERT INTO lessons (module_id, title, content, order_index, duration_minutes, created_at, updated_at)
VALUES 
    (1, 'Installing Python', 'Step by step guide to install Python...', 1, 30, now(), now()),
    (1, 'Your First Program', 'Writing Hello World program...', 2, 45, now(), now()),
    (1, 'Using Python IDLE', 'Introduction to Python development environment...', 3, 60, now(), now()),
    (4, 'HTML Document Structure', 'Understanding DOCTYPE, HTML, HEAD, BODY tags...', 1, 45, now(), now()),
    (4, 'Common HTML Elements', 'Learning about p, div, span tags...', 2, 60, now(), now()),
    (4, 'Forms and Input Elements', 'Creating interactive web forms...', 3, 75, now(), now()),
    (7, 'Introduction to Arrays', 'Understanding array data structure...', 1, 60, now(), now()),
    (7, 'Array Operations', 'Common operations on arrays...', 2, 45, now(), now()),
    (7, 'Array Complexity', 'Time and space complexity of array operations...', 3, 60, now(), now());

-- Insert materials
INSERT INTO materials (lesson_id, type, url_or_path, name, size_bytes, mime_type, created_at)
VALUES 
    (1, 'PDF', '/materials/python-installation-guide.pdf', 'Python Installation Guide', 1024000, 'application/pdf', now()),
    (1, 'VIDEO', '/materials/python-setup-video.mp4', 'Python Setup Tutorial', 15000000, 'video/mp4', now()),
    (1, 'CODE', '/materials/hello-world.py', 'Hello World Example', 1024, 'text/x-python', now()),
    (4, 'PDF', '/materials/html-basics.pdf', 'HTML Basics Guide', 2048000, 'application/pdf', now()),
    (4, 'VIDEO', '/materials/html-tutorial.mp4', 'HTML Tutorial', 20000000, 'video/mp4', now()),
    (4, 'CODE', '/materials/index.html', 'Sample HTML Page', 2048, 'text/html', now()),
    (7, 'PDF', '/materials/array-basics.pdf', 'Array Fundamentals', 1536000, 'application/pdf', now()),
    (7, 'VIDEO', '/materials/array-visualization.mp4', 'Array Operations Visualization', 25000000, 'video/mp4', now()),
    (7, 'CODE', '/materials/array-examples.py', 'Array Examples', 4096, 'text/x-python', now());

-- Insert audit logs
INSERT INTO audit_logs (actor_id, action, entity_type, entity_id, payload, created_at)
VALUES 
    (1, 'CREATE', 'COURSE', 1, '{"title": "Introduction to Programming"}', now()),
    (1, 'CREATE', 'COURSE', 2, '{"title": "Web Development Fundamentals"}', now()),
    (1, 'CREATE', 'COURSE', 3, '{"title": "Data Structures and Algorithms"}', now());