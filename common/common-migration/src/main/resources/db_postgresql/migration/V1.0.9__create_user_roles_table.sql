CREATE TABLE user_roles(
    user_id CHAR(36),
    role_id CHAR(36),
    CONSTRAINT user_roles_user FOREIGN KEY (user_id) REFERENCES roles(id),
    CONSTRAINT user_roles_role FOREIGN KEY (role_id) REFERENCES users(id),
    PRIMARY KEY (user_id, role_id)
);