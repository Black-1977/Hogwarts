-- liquibase formated sql

-- changeset Black77:1
CREATE INDEX student_name_index ON student (name);

-- changeset Black77:2
CREATE INDEX faculty_name_and_color_index ON faculty (name, color);
