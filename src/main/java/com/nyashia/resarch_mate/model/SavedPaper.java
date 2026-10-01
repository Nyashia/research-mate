package com.nyashia.resarch_mate.model;

import jakarta.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "saved_papers")
public class SavedPaper {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String paperId;

    @Column(nullable = false)
    private String title;

    @ManyToOne
    @JoinColumn(name = "group_id", nullable = false)
    private PaperGroup group;

    @Column(nullable = false)
    private Instant savedAt;

    public SavedPaper() {
    }

    public Long getId() {
    return id;
}

public void setId(Long id) {
    this.id = id;
}

public String getPaperId() {
    return paperId;
}

public void setPaperId(String paperId) {
    this.paperId = paperId;
}

public String getTitle() {
    return title;
}

public void setTitle(String title) {
    this.title = title;
}

public PaperGroup getGroup() {
    return group;
}

public void setGroup(PaperGroup group) {
    this.group = group;
}

public Instant getSavedAt() {
    return savedAt;
}

public void setSavedAt(Instant savedAt) {
    this.savedAt = savedAt;
}

}