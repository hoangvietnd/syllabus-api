-- 1. Create subjects table with full audit fields
CREATE TABLE subjects (
  id BIGSERIAL PRIMARY KEY,
  name VARCHAR(255) NOT NULL UNIQUE,
  description TEXT,
  created_by BIGINT NOT NULL REFERENCES users(id),
  updated_by BIGINT REFERENCES users(id),
  created_at TIMESTAMPTZ DEFAULT NOW(),
  updated_at TIMESTAMPTZ DEFAULT NOW()
);

-- 2. Enhance courses table
-- Add foreign key to subjects
ALTER TABLE courses
ADD COLUMN subject_id BIGINT REFERENCES subjects(id);

-- Add updated_by for audit trail
ALTER TABLE courses
ADD COLUMN updated_by BIGINT REFERENCES users(id);

-- 3. Enhance materials table with full audit fields
-- Add created_by for audit trail
ALTER TABLE materials
ADD COLUMN created_by BIGINT REFERENCES users(id);

-- Add updated_by for audit trail
ALTER TABLE materials
ADD COLUMN updated_by BIGINT REFERENCES users(id);

-- Add updated_at for audit trail
ALTER TABLE materials
ADD COLUMN updated_at TIMESTAMPTZ DEFAULT NOW();
