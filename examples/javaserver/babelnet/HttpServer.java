package babelnet;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UncheckedIOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import server.ContentType;
import server.EmptyRequestException;
import server.HttpHeader;
import server.HttpRequest;
import server.HttpResponse;
import server.Status;

public class HttpServer {

    private ExecutorService service = Executors.newCachedThreadPool();
    private static final int PORT = 1000; //待ち受けポート番号

    public void start() {
        try (ServerSocket server = new ServerSocket(PORT)) {
            while (true) {
                this.serverProcess(server);
            }
        } catch (Exception e) {
            e.printStackTrace(System.err);
        }
    }

    private void serverProcess(ServerSocket server) throws IOException {
        Socket socket = server.accept();
        Make_word_tree wt = new Make_word_tree();

        this.service.execute(() -> {
            try (
                InputStream in = socket.getInputStream();
                OutputStream out = socket.getOutputStream();
                ) {

                HttpRequest request = new HttpRequest(in);

                HttpHeader header = request.getHeader();

                String url_path = "/files"+header.getPath();
                String target_function = url_path.split("\\?")[0];

                System.out.println(header.getText());
                System.out.println(url_path);
                System.out.println(target_function);
                System.out.println(target_function.equals("/files/getSynsetIds"));

                if (header.isGetMethod()) {
                    File file = new File(".", target_function);
                    if (file.exists() && file.isFile()) {
                    	if(target_function.equals("/files/getSynsetIds")) {
                    		System.out.println("search");
                    		BabelInfo bi = readHeaderPath(url_path);
                    		wt.getSynsetIds(bi.lemma, bi.searchLang);
                    	}
                        this.respondLocalFile(file, out);
                    } else {
                        this.respondNotFoundError(out);
                    }
                } else {
                    this.respondOk(out);
                }
            } catch (EmptyRequestException e) {
                // ignore
            } catch (IOException e) {
                throw new UncheckedIOException(e);
            } finally {
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private BabelInfo readHeaderPath(String path) {
    	BabelInfo bi = new BabelInfo();
    	String[] data = path.split("\\?");
    	for(String info: data[1].split("&")) {
    		String[] path_info = info.split("=");
    		if(path_info[0].equals("lemma")) {
    			bi.lemma = path_info[1];
    		}
    		else if(path_info[0].equals("lang")) {
    			bi.lang = path_info[1];
    		}
    		else if(path_info[0].equals("searchLang")) {
    			bi.searchLang = path_info[1];
    		}
    		else if(path_info[0].equals("targetLang")) {
    			bi.targetLang = path_info[1];
    		}
    		else if(path_info[0].equals("synsetid")) {
    			bi.synsetid = path_info[1];
    		}
    		else if(path_info[0].equals("pos")) {
    			bi.pos = path_info[1];
    		}
    		else if(path_info[0].equals("source")) {
    			bi.source = path_info[1];
    		}
    		else if(path_info[0].equals("key")) {
    			bi.key = path_info[1];
    		}
    	}

    	return bi;
    }

    private void respondNotFoundError(OutputStream out) throws IOException {
        HttpResponse response = new HttpResponse(Status.NOT_FOUND);
        response.addHeader("Content-Type", ContentType.TEXT_PLAIN);
        response.setBody("404 Not Found");
        response.writeTo(out);
    }

    private void respondLocalFile(File file, OutputStream out) throws IOException {
        HttpResponse response = new HttpResponse(Status.OK);
        response.setBody(file);
        response.writeTo(out);
    }

    private void respondOk(OutputStream out) throws IOException {
        HttpResponse response = new HttpResponse(Status.OK);
        response.writeTo(out);
    }
}
