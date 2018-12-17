package quintaib;

import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;

public class App {
    public static void main( String[] args ) throws Exception {
        URL wsdlURL = new URL("http://localhost:8888/database?wsdl");
		QName qname = new QName("http://quintaib/", "DatabaseServiceService"); 
		
        Service service = Service.create(wsdlURL, qname);
        
        DatabaseServiceInterface databaseService = service.getPort(DatabaseServiceInterface.class);

        System.out.println(databaseService.login("Alberto", "Alberto").toString());
    }
}
