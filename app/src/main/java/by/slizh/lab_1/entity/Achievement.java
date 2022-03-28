package by.slizh.lab_1.entity;

public enum Achievement {
    BEGINNER(
            "Beginner",
            "Score 3"
    ),
    JUNIOR("Junior",
            "Score 6");

    private String title;
    private String description;

    Achievement(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }
}
