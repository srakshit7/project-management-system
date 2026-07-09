package com.pms.model;

import java.time.LocalDateTime;

/**
 * Client feedback captured at a given lifecycle stage, per the
 * "client feedback (at each stage)" requirement.
 */
public class Feedback {

    private Long id;
    private DevelopmentTracker.Stage stage;
    private String comment;
    private int rating; // 1 to 5
    private LocalDateTime timestamp = LocalDateTime.now();

    public Feedback() {
    }

    public Feedback(Long id, DevelopmentTracker.Stage stage, String comment, int rating) {
        this.id = id;
        this.stage = stage;
        this.comment = comment;
        this.rating = rating;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public DevelopmentTracker.Stage getStage() {
        return stage;
    }

    public void setStage(DevelopmentTracker.Stage stage) {
        this.stage = stage;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}
