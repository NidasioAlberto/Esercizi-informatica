package quintaib;

import java.io.*;
import java.net.*;
import java.util.*;

public class LineServer {

    public static final int PORT = 4445; // porta al di fuori del range 1-1024 !
    public static final String FILE = "Dante.txt";
    DatagramSocket socket;
    static BufferedReader in = null;
    static boolean moreLines = true;

    public LineServer() {

        try {
            // creazione della socket datagram
            socket = new DatagramSocket(PORT);
            System.out.println("Socket: " + socket);
        } catch (SocketException e) {
            System.err.println("Unable to create the socket");
        }

        try {
            // associazione di uno stream di input al file da cui estrarre le linee
            in = new BufferedReader(new FileReader(FILE));
            System.out.println("File " + FILE + " opened");

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Could not open line file, serving time instead.");
        }
    }

    public static void main(String[] args) throws IOException {

        System.out.println("LineServer: started");
        LineServer server = new LineServer();

        while (moreLines) {
            try {
                byte[] buf = new byte[256];

                // ricezione della richiesta
                DatagramPacket packet = new DatagramPacket(buf, buf.length);
                server.socket.receive(packet);

                //MODIFICA 2-a
                System.out.println("Requested " + new String(packet.getData()) + " bytes");

                // preparazione della linea da inviare
                String dString = null;
                if (in == null)
                    dString = new Date().toString();
                else
                    dString = getNextLine(new Integer(new String(packet.getData())));
                if (dString != null) {
                    buf = dString.getBytes();

                    // "impacchettamento" e invio della risposta
                    InetAddress address = packet.getAddress();
                    int port = packet.getPort();
                    packet = new DatagramPacket(buf, buf.length, address, port);
                    server.socket.send(packet);
                    System.out.println("Sending packet to " + address + ", " + port);
                }

            } catch (IOException e) {
                e.printStackTrace();
                moreLines = false;
            }

        }

        System.out.println("No more lines. Goodbye.");
        System.out.println("LineServer: closing...");
        server.socket.close();
    }

    static String getNextLine(int nBytes) {
        char[] buf = new char[nBytes];
        String returnValue;
        try {
            if(in.read(buf) < 0) {
                in.close();
                moreLines = false;
                returnValue = null;
                // returnValue = "No more lines. Goodbye.";
            } else {
                returnValue = buf.toString();
            }
        } catch (IOException e) {
            returnValue = "IOException occurred.";
        }
        return returnValue;
    }
}