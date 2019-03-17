package server;

import static server.Constant.*;

import java.io.IOException;
import java.io.InputStream;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

public class HttpHeader {

    private HttpMethod method;
    private String path;
    private final String headerText;
    private Map<String, String> messageHeaders = new HashMap<>();

    public HttpHeader(InputStream in) throws IOException {
        StringBuilder header = new StringBuilder();

        header.append(this.readRequestLine(in))
              .append(this.readMessageLine(in));

        this.headerText =  header.toString();
    }

    private String readRequestLine(InputStream in) throws IOException {
        String requestLine = IOUtil.readLine(in);

        String[] tmp = requestLine.split(" ");
        this.method = HttpMethod.valueOf(tmp[0].toUpperCase());
        this.path = URLDecoder.decode(tmp[1], "UTF-8");

        return requestLine + CRLF;
    }

    private StringBuilder readMessageLine(InputStream in) throws IOException {
        StringBuilder sb = new StringBuilder();

        String messageLine = IOUtil.readLine(in);

        while (messageLine != null && !messageLine.isEmpty()) {
            this.putMessageLine(messageLine);

            sb.append(messageLine + CRLF);
            messageLine = IOUtil.readLine(in);
        }

        return sb;
    }

    private void putMessageLine(String messageLine) {
        String[] tmp = messageLine.split(":");
        String key = tmp[0].trim();
        String value = tmp[1].trim();
        this.messageHeaders.put(key, value);
    }

    public String getText() {
        return this.headerText;
    }

    public int getContentLength() {
        return Integer.parseInt(this.messageHeaders.getOrDefault("Content-Length", "0"));
    }

    public boolean isChunkedTransfer() {
        return this.messageHeaders.getOrDefault("Transfer-Encoding", "-").equals("chunked");
    }

    public String getPath() {
        return this.path;
    }

    public boolean isGetMethod() {
        return this.method == HttpMethod.GET;
    }
}
