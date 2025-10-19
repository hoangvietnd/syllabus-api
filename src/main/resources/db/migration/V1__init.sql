-- Tạo bảng users
create table users (
  id bigserial primary key,
  email varchar(255) unique not null,
  password_hash varchar(255) not null,
  full_name varchar(255),
  role varchar(20) not null,
  created_at timestamptz default now()
);

-- Tạo bảng courses
create table courses (
  id bigserial primary key,
  title varchar(255) not null,
  description text,
  tags jsonb,
  version int default 1,
  created_by bigint references users(id),
  created_at timestamptz default now(),
  updated_at timestamptz default now()
);

-- Tạo bảng modules
create table modules (
  id bigserial primary key,
  course_id bigint not null references courses(id) on delete cascade,
  title varchar(255) not null,
  order_index int not null,
  summary text,
  created_at timestamptz default now(),
  updated_at timestamptz default now()
);

-- Tạo bảng lessons
create table lessons (
  id bigserial primary key,
  module_id bigint not null references modules(id) on delete cascade,
  title varchar(255) not null,
  content text,
  order_index int not null,
  duration_minutes int,
  created_at timestamptz default now(),
  updated_at timestamptz default now()
);

-- Tạo bảng materials
create table materials (
  id bigserial primary key,
  lesson_id bigint not null references lessons(id) on delete cascade,
  type varchar(20) not null,
  url_or_path varchar(1024) not null,
  name varchar(255) not null,
  size_bytes bigint,
  mime_type varchar(255),
  created_at timestamptz default now()
);

-- Tạo bảng audit_logs
create table audit_logs (
  id bigserial primary key,
  actor_id bigint references users(id),
  action varchar(100) not null,
  entity_type varchar(100) not null,
  entity_id bigint not null,
  payload jsonb,
  created_at timestamptz default now()
);

-- Indexes
create index idx_courses_title on courses(title);
create index idx_modules_course_order on modules(course_id, order_index);
create index idx_lessons_module_order on lessons(module_id, order_index);
create index idx_audit_entity on audit_logs(entity_type, entity_id);
create index idx_audit_actor on audit_logs(actor_id);