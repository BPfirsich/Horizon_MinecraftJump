package benTho.horizonMinecraftJump;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.ByteBuffer;
import java.util.concurrent.BlockingDeque;

public class ServerPackages {

    // ====================================
    // TCP
    // ====================================

    public static void tcp_Disconnect(DataOutputStream out) throws IOException {
        System.out.println("Request Package to server: Disconnect");

        ByteBuffer buffer = ByteBuffer.allocate(8);
        buffer.putInt(0); // Header
        buffer.putInt(0); // Payload
        out.write(buffer.array());
    }

    public static int tcp_GetClientID(BlockingDeque<ByteBuffer> in, DataOutputStream out) throws IOException {
        System.out.println("Request Package to server: ClientID");

        ByteBuffer buffer = ByteBuffer.allocate(8);
        buffer.putInt(1);
        buffer.putInt(0);
        out.write(buffer.array());

        try {
            ByteBuffer response = in.take(); // Blocks until is reads something
            if (response.limit() == 0) return -1; // On disconnect or anything like that
            return response.getInt();

        } catch (InterruptedException e) {
            return -1;
        }
    }

    public static int tcp_createRoom(BlockingDeque<ByteBuffer> in, DataOutputStream out) throws IOException {
        System.out.println("Request Package to server: CreateRoom");

        ByteBuffer buffer = ByteBuffer.allocate(8);
        buffer.putInt(2);
        buffer.putInt(0);
        out.write(buffer.array());

        try {
            ByteBuffer response = in.take(); // Blocks until is reads something
            if (response.limit() == 0) return -1; // On disconnect or anything like that
            return response.getInt();

        } catch (InterruptedException e) {
            return -1;
        }
    }

    public static int tcp_joinRoom(BlockingDeque<ByteBuffer> in, DataOutputStream out, int roomID) throws IOException {
        System.out.println("Request Package to server: JoinRoom");

        ByteBuffer buffer = ByteBuffer.allocate(12);
        buffer.putInt(3);
        buffer.putInt(4);
        buffer.putInt(roomID);
        out.write(buffer.array());

        try {
            ByteBuffer response = in.take(); // Blocks until is reads something
            if (response.limit() == 0) return -1; // On disconnect or anything like that
            return response.getInt();

        } catch (InterruptedException e) {
            return -1;
        }
    }

    public static int tcp_getRoomNumber(BlockingDeque<ByteBuffer> in, DataOutputStream out) throws IOException {
        System.out.println("Request Package to server: getRoomNumber");

        ByteBuffer buffer = ByteBuffer.allocate(8);
        buffer.putInt(4);
        buffer.putInt(0);
        out.write(buffer.array());

        try {
            ByteBuffer response = in.take(); // Blocks until is reads something
            if (response.limit() == 0) return -1; // On disconnect or anything like that
            return response.getInt();

        } catch (InterruptedException e) {
            return -1;
        }
    }

    public static void tcp_leaveRoom(DataOutputStream out) throws IOException {
        System.out.println("Request Package to server: LeaveRoom");

        ByteBuffer buffer = ByteBuffer.allocate(8);
        buffer.putInt(5);
        buffer.putInt(0);
        out.write(buffer.array());
    }

    public static int tcp_getServerVersion(BlockingDeque<ByteBuffer> in, DataOutputStream out) throws IOException {
        System.out.println("Request Package to server: GetServerVersion");

        ByteBuffer buffer = ByteBuffer.allocate(8);
        buffer.putInt(100);
        buffer.putInt(0);
        out.write(buffer.array());

        try {
            ByteBuffer response = in.take(); // Blocks until is reads something
            if (response.limit() == 0) return -1; // On disconnect or anything like that
            return response.getInt();

        } catch (InterruptedException e) {
            return -1;
        }
    }

    public static int tcp_GetServerTickSpeedMs(BlockingDeque<ByteBuffer> in, DataOutputStream out) throws IOException {
        System.out.println("Request Package to server: TickSpeed");

        ByteBuffer buffer = ByteBuffer.allocate(8);
        buffer.putInt(101);
        buffer.putInt(0);
        out.write(buffer.array());

        try {
            ByteBuffer response = in.take(); // Blocks until is reads something
            if (response.limit() == 0) return -1; // On disconnect or anything like that
            return response.getInt();

        } catch (InterruptedException e) {
            return -1;
        }
    }

    public static int tcp_GetPingMs(BlockingDeque<ByteBuffer> in, DataOutputStream out) throws IOException {
        System.out.println("Request Package to server: Ping");

        long startTime = System.currentTimeMillis();
        ByteBuffer buffer = ByteBuffer.allocate(8);
        buffer.putInt(102);
        buffer.putInt(0);
        out.write(buffer.array());

        try {
            ByteBuffer response = in.take(); // Blocks until is reads something
            if (response.limit() == 0) return -1; // On disconnect or anything like that
            // Just do nothing with the return. Just contains a bool of "True"
            return (int)(System.currentTimeMillis() - startTime);

        } catch (InterruptedException e) {
            return -1;
        }
    }

    // ====================================
    // UDP
    // ====================================

    public static void udp_sendPlayerUpdate(DatagramSocket udpSocket, InetAddress address, int clientID, int roomID, float x, float y) throws IOException {
        ByteBuffer buffer = ByteBuffer.allocate(3*4 + 2*4);

        buffer.putInt(0);
        buffer.putInt(clientID);
        buffer.putInt(roomID);

        buffer.putFloat(x);
        buffer.putFloat(y);

        byte[] data = buffer.array();

        DatagramPacket packet = new DatagramPacket(
                data,
                data.length,
                address,
                ServerConnector.PORT
        );
        udpSocket.send(packet);
    }

    public static void udp_sendAliveSignal(DatagramSocket udpSocket, InetAddress address) throws IOException {
        ByteBuffer buffer = ByteBuffer.allocate(4);
        buffer.putInt(1);

        byte[] data = buffer.array();
        DatagramPacket packet = new DatagramPacket(
                data,
                data.length,
                address,
                ServerConnector.PORT
        );
        udpSocket.send(packet);
    }

}
