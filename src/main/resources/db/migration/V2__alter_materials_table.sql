ALTER TABLE materials DROP CONSTRAINT IF EXISTS materials_lesson_id_fkey;

ALTER TABLE materials DROP COLUMN IF EXISTS lesson_id;
ALTER TABLE materials DROP COLUMN IF EXISTS url_or_path;
ALTER TABLE materials DROP COLUMN IF EXISTS type;
ALTER TABLE materials DROP COLUMN IF EXISTS size_bytes;
ALTER TABLE materials DROP COLUMN IF EXISTS mime_type;

ALTER TABLE materials ADD COLUMN course_id BIGINT NOT NULL;
ALTER TABLE materials ADD COLUMN description TEXT;
ALTER TABLE materials ADD COLUMN file_path VARCHAR(255) NOT NULL;
ALTER TABLE materials ADD COLUMN file_type VARCHAR(100);

ALTER TABLE materials ADD CONSTRAINT fk_materials_course FOREIGN KEY (course_id) REFERENCES courses(id);
