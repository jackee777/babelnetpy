package server;

public enum Status {
    OK("200 OK"),
    NOT_FOUND("404 Not Found")
    ;

    private final String text;

    private Status(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return this.text;
    }
}
