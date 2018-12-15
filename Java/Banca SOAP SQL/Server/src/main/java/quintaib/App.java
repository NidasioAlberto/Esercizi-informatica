package quintaib;

import javax.xml.ws.Endpoint;

public class App {
    public static void main( String[] args ) {
        Endpoint.publish("http://localhost:8888/database", new DatabaseService("217.61.60.117", "AlbertoNidasio", "AlbertoNidasio", "Alberto-07"));
    }
}