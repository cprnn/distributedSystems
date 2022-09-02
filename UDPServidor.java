import java.net.*;
import java.io.*;

public class UDPServidor {
    public static void main(String args[]) throws Exception {
        DatagramSocket s = new DatagramSocket(6789);
        System.out.println("\n\n*** Servidor aguardando request***");

        while (true) {
            byte[] buffer = new byte[1000];

            DatagramPacket req = new DatagramPacket(buffer, buffer.length);
            s.receive(req);
            System.out.println("*** Request recebido de: " + req.getAddress() + ":" + req.getPort() + "***");

            new Handler(req, s);
        }
    }
}

class Handler implements Runnable {
    DatagramSocket socket;
    DatagramPacket pkt;

    Handler(DatagramPacket pkt, DatagramSocket socket) {
        this.pkt = pkt;
        this.socket = socket;
        new Thread(this).start();
        System.out.println("A thread foi criada!");
    }

    @Override
    public void run() {
        try {
            String invertedString = new StringBuilder(new String(pkt.getData(), pkt.getOffset(), pkt.getLength(), "UTF8")).reverse().toString();
            DatagramPacket resp = new DatagramPacket(invertedString.getBytes(), invertedString.length(), pkt.getAddress(), pkt.getPort());

            socket.send(resp);

        } catch (SocketException e) {
            System.out.println("Erro de socket: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("Erro envio/recepção pacote: " + e.getMessage());
//        } finally {      //não é necessário finalizar o socket
//            if (socket != null) socket.close();
        }

    }
}


