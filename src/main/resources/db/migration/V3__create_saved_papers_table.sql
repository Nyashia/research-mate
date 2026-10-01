CREATE TABLE saved_papers (
    id BIGSERIAL PRIMARY KEY,
    paper_id VARCHAR(255) NOT NULL,
    title VARCHAR(500) NOT NULL,
    group_id BIGINT NOT NULL,
    saved_at TIMESTAMP WITH TIME ZONE NOT NULL,

    CONSTRAINT fk_saved_papers_group
        FOREIGN KEY (group_id)
        REFERENCES paper_groups(id)
);