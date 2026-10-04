ALTER TABLE saved_papers
ADD CONSTRAINT uk_saved_papers_paper_group
UNIQUE (paper_id, group_id);