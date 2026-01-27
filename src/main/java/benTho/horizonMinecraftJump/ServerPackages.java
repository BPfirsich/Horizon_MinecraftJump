package benTho.horizonMinecraftJump;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.ByteBuffer;

public class ServerPackages {

    // ====================================
    // TCP
    // ====================================

    public static void tcp_Disconnect(DataInputStream in, DataOutputStream out) throws IOException {
        out.writeInt(0);
        out.writeInt(0);
    }

    public static int tcp_GetClientID(DataInputStream in, DataOutputStream out) throws IOException {
        out.writeInt(1);
        out.writeInt(0);

        return in.readInt();
    }

    public static int tcp_createRoom(DataInputStream in, DataOutputStream out) throws IOException {
        out.writeInt(2);
        out.writeInt(0);

        return in.readInt();
    }

    public static void tcp_joinRoom(DataInputStream in, DataOutputStream out, int roomID) throws IOException {
        out.writeInt(3);
        out.writeInt(4);

        out.writeInt(roomID);
    }

    public static int tcp_getRoomNumber(DataInputStream in, DataOutputStream out) throws IOException {
        out.writeInt(4);
        out.writeInt(0);

        return in.readInt();
    }

    public static void tcp_leaveRoom(DataInputStream in, DataOutputStream out) throws IOException {
        out.writeInt(5);
        out.writeInt(0);
    }

    public static int tcp_getServerVersion(DataInputStream in, DataOutputStream out) throws IOException {
        out.writeInt(100);
        out.writeInt(0);

        return in.readInt();
    }

    public static int tcp_GetServerTickSpeedMs(DataInputStream in, DataOutputStream out) throws IOException {
        out.writeInt(101);
        out.writeInt(0);

        return in.readInt();
    }

    public static int tcp_GetPingMs(DataInputStream in, DataOutputStream out) throws IOException {
        long startTime = System.currentTimeMillis();
        out.writeInt(102);
        out.writeInt(0);

        boolean b = in.readBoolean();
        return (int)(System.currentTimeMillis() - startTime);
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

}
