package server;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Stream;

public enum ContentType {
    TEXT_PLAIN("text/plain", "txt"),
    TEXT_HTML("text/html", "html,htm"),
    TEXT_CSS("text/css", "css"),
    TEXT_XML("text/xml", "xml"),
    APPLICATION_JAVASCRIPT("application/javascript", "js"),
    APPLICATION_JSON("application/json", "json"),
    IMAGE_JPEG("image/jpeg", "jpg,jpeg"),
    IMAGE_PNG("image/png", "png"),
    IMAGE_GIF("image/gif", "gif"),
    ;

    private static final Map<String, ContentType> EXTENSION_CONTENT_TYPE_MAP = new HashMap<>();

    static {
        Stream.of(ContentType.values())
              .forEach(contentType -> {
                  contentType.extensions.forEach(extension -> {
                      EXTENSION_CONTENT_TYPE_MAP.put(extension.toUpperCase(), contentType);
                  });
              });
    }

    private final String text;
    private final Set<String> extensions = new HashSet<>();

    private ContentType(String text, String extensions) {
        this.text = text;
        this.extensions.addAll(Arrays.asList(extensions.split(",")));
    }

    @Override
    public String toString() {
        return this.text;
    }

    public static ContentType toContentType(String extension) {
        Objects.requireNonNull(extension);
        return EXTENSION_CONTENT_TYPE_MAP.getOrDefault(extension.toUpperCase(), TEXT_PLAIN);
    };
}
