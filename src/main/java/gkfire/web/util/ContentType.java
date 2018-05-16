package gkfire.web.util;

public enum ContentType {
    DOC("application/vnd.openxmlformats-officedocument.wordprocessingml.document"),
    DOCX("application/wsword"),
    JPG("image/jpeg"),
    PNG("image/png"),
    BMP("image/bmp"),
    PDF("application/pdf");

    private final String description;

    private ContentType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return this.description;
    }

    public static ContentType getByDescription(String description) {
        for (ContentType c : values()) {
            if (c.description.contentEquals(description)) {
                return c;
            }
        }
        return null;
    }
}
