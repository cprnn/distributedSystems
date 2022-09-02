import java.net.*;
import java.io.*;

public class UDPCliente {

    public static void main(String args[]) {
        DatagramSocket s = null;
        try {
            String server = "localhost";
            int port = 6789;
            String msg = "Oi, eu sou um pacote de teste!";
            
            if (args.length > 0) server = args[0];
            if (args.length > 1) port = Integer.parseInt(args[1]);
            if (args.length > 2) msg = args[2];
            byte[] m = msg.getBytes();
            InetAddress serv = InetAddress.getByName(server);

            DatagramSocket socket = new DatagramSocket();
            System.out.println("* Socket criado na porta: " + socket.getLocalPort());
            DatagramPacket req = new DatagramPacket(m, msg.length(), serv, port);
            socket.send(req);
            System.out.println("* Datagrama enviado...: " + msg);
            
            byte[] buffer = new byte[1000];
            DatagramPacket resp = new DatagramPacket(buffer, buffer.length);
            socket.setSoTimeout(10000);
            socket.receive(resp);

            System.out.println("* Resposta do servidor: " + 
                    new String(resp.getData(),0,resp.getLength()));
            
        } catch (SocketException e) {
            System.out.println("! Erro socket: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("! Erro envio/recepcao do pacote: " + e.getMessage());
//        }  finally {
//            if (s != null) s.close();
        }     
    }
    
}
