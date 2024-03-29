package lib.Files.Text;

import lib.Files.FileTransfer;
import Files.Uploader;
import lib.Sockets.UDPSocketUtils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.SocketAddress;

public class TextFileUploader extends FileTransfer implements Uploader {

    public TextFileUploader(UDPSocketUtils socket, SocketAddress serverAddress) {
        super(socket, serverAddress);
        //System.out.println("client pronto");
    }


    @Override
    public void uploadFile(String fileName) throws IOException {

        byte[] bufferOUT;
        final BufferedReader input = new BufferedReader(new FileReader("share/" + fileName));
        String daSpedire;
        do {
            daSpedire = input.readLine();
            if (daSpedire != null) {
                // predisposizione del messaggio da spedire
                bufferOUT = daSpedire.getBytes();
                // trasmissione del dato al server
//                final DatagramPacket sendPacket = new DatagramPacket(bufferOUT, bufferOUT.length, address);
                socket.send(bufferOUT, address);
            }
        } while (daSpedire != null);

        final byte[] endComunicationSequence = "endoffile".getBytes();
//        final DatagramPacket sendPacket = new DatagramPacket(endComunicationSequence, endComunicationSequence.length, address);
        socket.sendString("endoffile", address);

//        waitForAck();
    }

}
