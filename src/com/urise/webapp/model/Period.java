package com.urise.webapp.model;

import java.time.LocalDate;
import java.util.Objects;

public class Period {
    private final LocalDate startDate;
    private final LocalDate endDate;
    private final String title;
    private final String description;

    public Period(LocalDate startDate, LocalDate endDate, String title, String description) {
        Objects.requireNonNull(startDate, "startDate must not de null");
        Objects.requireNonNull(endDate, "endDate must not de null");
        Objects.requireNonNull(title, "title must not de null");
        this.startDate = startDate;
        this.endDate = endDate;
        this.title = title;
        this.description = description;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Period period = (Period) o;
        return startDate.equals(period.startDate) && endDate.equals(period.endDate) && title.equals(period.title) && Objects.equals(description, period.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(startDate, endDate, title, description);
    }

    @Override
    public String toString() {
        return "startDate = " + startDate + ", endDate = " + endDate + ", title = "
                + title + "\n, description = " + description;
    }
}