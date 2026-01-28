package benTho.horizonMinecraftJump;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;

public class ServerLoop {

    // Queue for answers from the Server. (Header 0)
    public BlockingDeque<ByteBuffer> responseQueue = new LinkedBlockingDeque<>();

    private DataInputStream tcpIn = null;
    private DatagramSocket udpSocket = null;
    private InetAddress address = null;

    private Thread tcpThread = null;
    private Thread udpThread = null;
    private Thread udpKeepAliveThread = null;

    private volatile boolean tcpListenerRunning = false;
    private volatile boolean udpListenerRunning = false;

    public ServerLoop(DataInputStream tcpIn, DatagramSocket udpSocket, InetAddress address) {
        this.tcpIn = tcpIn;
        this.udpSocket = udpSocket;
        this.address = address;
    }

    public void startServerLoop_tcp() {
        System.out.println("Starting Server-listener-loop");

        tcpThread = new Thread(this::tcpLoop);

        tcpListenerRunning = true;
        tcpThread.start();
    }

    // Because for UDP we need a clientID, and we can only get this if the tcp tunnel is already setup
    public void startServerLoop_udp(int clientID) {
        System.out.println("Starting Server-listener-loop");

        udpKeepAliveThread = new Thread(this::udpKeepAlive);
        udpThread = new Thread(() -> this.udpLoop(clientID));

        udpListenerRunning = true;
        udpKeepAliveThread.start();
        udpThread.start();
    }


    // Stops TPC und UDP
    public void stopServerLoop() {
        System.out.println("Stopping Server-listeners");
        tcpListenerRunning = false;
        udpListenerRunning = false;

        try {
            if(tcpThread != null) tcpThread.interrupt();
            if(udpKeepAliveThread != null) udpKeepAliveThread.interrupt();
            if(udpThread != null) udpThread.interrupt();
        } catch (Exception e) {
            // Doesnt matter at this point
        }

        udpKeepAliveThread = null;
        udpThread = null;
        tcpThread = null;
    }

    private void connectionErrorHappened() {
        System.err.println("Connection to Server lost!");

        // If any package still waiting for a package, kick it out with that
        responseQueue.offer(ByteBuffer.allocate(0));


    }

    private void tcpLoop() {
        System.out.println("TCP Thread Running...");

        while(tcpListenerRunning) {
            try {
                byte[] headerBuffer = new byte[8];
                tcpIn.readFully(headerBuffer);
                ByteBuffer hByteBuffer = ByteBuffer.wrap(headerBuffer, 0, headerBuffer.length);
                hByteBuffer.order(ByteOrder.BIG_ENDIAN); // So that java reads the data correctly (said ChatGPT :P)

                int header = hByteBuffer.getInt();
                int payload = hByteBuffer.getInt();

                byte[] dataBuffer = new byte[payload];
                tcpIn.readFully(dataBuffer);
                ByteBuffer dByteBuffer = ByteBuffer.wrap(dataBuffer, 0, dataBuffer.length);
                dByteBuffer.order(ByteOrder.BIG_ENDIAN); // So that java reads the data correctly (said ChatGPT :P)

                processTcpPackage(header, dByteBuffer);

            } catch (IOException e) {
                connectionErrorHappened();
                break;
            }
        }

        System.out.println("TCP Thread Stopped...");
    }

    private static final int TCPHEADER_RESPONSE = 0;

    private void processTcpPackage(int header, ByteBuffer data) {
        System.out.println("Received Package from server with header: " + header);

        switch (header) {
            case TCPHEADER_RESPONSE -> {
                try {
                    responseQueue.put(data); // Blocks until it can write into it
                } catch (InterruptedException e) { }
            }
        }
    }

    private void udpLoop(int clientID) {
        System.out.println("UDP Thread Running...");

        // Send ClientData to Server to create a "tunnel"


        // Start the loop

        System.out.println("UDP Thread Stopped...");
    }

    private void udpKeepAlive() {
        System.out.println("KeepAlive Thread Running...");

        // Send a alive signal every 20_000ms
        while(udpListenerRunning) {
            try {
                ServerPackages.udp_sendAliveSignal(udpSocket, address);
            } catch (IOException e) {
                connectionErrorHappened();
                break;
            }

            try {
                Thread.sleep(20000);
            } catch (InterruptedException e) {
                // Just chill about it :P
            }
        }

        System.out.println("KeepAlive Thread Stopped...");
    }


}
