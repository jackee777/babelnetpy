package babelnet;

import static java.lang.System.*;

public class Tree_server {


	public static void main(String[] args) throws Exception {
        out.println("start >>>");
        HttpServer server = new HttpServer();
        server.start();
        out.println("<<< end");
	}

}


