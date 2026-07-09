package com.pms.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Tracks a project through its development lifecycle.
 * Stage order: REQUIREMENT -> FEASIBILITY -> DESIGN -> TESTING -> DEPLOYMENT -> MAINTENANCE
 */
public class DevelopmentTracker {

    /** Lifecycle stages, matching the note: requirement feasibility, testing, deployment, maintenance. */
    public enum Stage {
        REQUIREMENT,
        FEASIBILITY,
        DESIGN,
        TESTING,
        DEPLOYMENT,
        MAINTENANCE
    }

    private Stage currentStage = Stage.REQUIREMENT;
    private List<String> history = new ArrayList<>();

    public DevelopmentTracker() {
        history.add(logEntry(Stage.REQUIREMENT));
    }

    /** Moves to the next lifecycle stage, if one exists. Returns false if already at the final stage. */
    public boolean advanceStage() {
        Stage[] stages = Stage.values();
        int next = currentStage.ordinal() + 1;
        if (next >= stages.length) {
            return false;
        }
        currentStage = stages[next];
        history.add(logEntry(currentStage));
        return true;
    }

    private String logEntry(Stage stage) {
        return stage + " @ " + LocalDateTime.now();
    }

    public Stage getCurrentStage() {
        return currentStage;
    }

    public void setCurrentStage(Stage currentStage) {
        this.currentStage = currentStage;
    }

    public List<String> getHistory() {
        return history;
    }

    public void setHistory(List<String> history) {
        this.history = history;
    }
}
