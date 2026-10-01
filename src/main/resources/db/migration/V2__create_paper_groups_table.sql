CREATE TABLE paper_groups (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    owner_id BIGINT NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL,

    CONSTRAINT fk_paper_groups_owner
        FOREIGN KEY (owner_id)
        REFERENCES users(id)
);