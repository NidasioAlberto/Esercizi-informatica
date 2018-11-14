package quintaib;

import java.io.*;
import java.net.*;

public class LineClient {

    public static final int PORT = 4445;
    public static final int N_BYTES = 30;

    public static void main(String[] args) throws IOException {

        // creazione della socket datagram con un timeout di 30s
        DatagramSocket socket = new DatagramSocket();
        socket.setSoTimeout(30000);
        System.out.println("LineClient: started");
        System.out.println("Socket: " + socket);

        // creazione e invio del pacchetto di richiesta
        byte[] buf = new byte[256];
        InetAddress addr;
        if (args.length == 0)
            addr = InetAddress.getByName(null);
        else
            addr = InetAddress.getByName(args[0]);

        // for (int i=0; i<200; i++){

        for (int i = 0; i < 5; i++) {
            //MODIFICA 2-a
            //richiedo al client n byte
            buf = Integer.toString(N_BYTES).getBytes();
            DatagramPacket packet = new DatagramPacket(buf, buf.length, addr, PORT);
            socket.send(packet);
            System.out.println("Request sent to " + addr);

            // pulizia del buffer
            buf = new byte[N_BYTES];
            for (int j = 0; j < buf.length; j++)
                buf[j] = ' ';

            // ricezione e stampa della risposta
            packet = new DatagramPacket(buf, buf.length);
            socket.receive(packet);
            // sospensiva solo per i millisecondi indicati, dopodich� solleva una
            // SocketException
            String received = new String(packet.getData());
            System.out.println("Line got: " + received);
        }

        System.out.println("LineClient: closing...");
        socket.close();
    }
}