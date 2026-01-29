package benTho.horizonMinecraftJump;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;

public class ServerConnector {

    public static final int MY_SERVER_VERSION = 1;
    public static final int PORT = 56565;

    // Server Data
    private int _currentClientID = -1;
    private volatile int _currentRoomID = -1;
    private int _serverVersion = -1;
    private volatile int _serverTickMs = -1;
    private volatile int _serverPing = -1;

    // Sockets
    private DatagramSocket udpSocket = null;

    private Socket tcpSocket = null;
    private DataInputStream tcpIn = null;
    private DataOutputStream tcpOut = null;

    private ServerLoop _serverLoop = null;

    // Status values
    private volatile boolean _isConnected = false; // volatile = all threads see the current state
    private InetAddress _currentServerAddress = null;
    private String _currentWorld = "__";

    public boolean isConnected() {
        return _isConnected;
    }

    public int getPing() {
        return _serverPing;
    }

    public int getServerTickMs() {
        return _serverTickMs;
    }

    public boolean TryConnection(InetAddress serverAddress, Main mainRef) {
        if (_isConnected) {
            System.err.println("Cant connect to server while being already connected!");
            return false;
        }

        try {
            // Setup UDP
            udpSocket = new DatagramSocket();

            // Setup TCP
            tcpSocket = new Socket(serverAddress, PORT);
            tcpIn = new DataInputStream(tcpSocket.getInputStream());
            tcpOut = new DataOutputStream(tcpSocket.getOutputStream());

            _currentServerAddress = serverAddress;
            _isConnected = true;

            _serverLoop = new ServerLoop(tcpIn, udpSocket, _currentServerAddress, mainRef);
            _serverLoop.startServerLoop_tcp();

            if (!GatherServerInfo()) {
                return false; // The function already handling disconnection etc.
            }
            _serverPing = calculatePing();
            System.out.println("Server Ping: " + _serverPing);

            _serverLoop.startServerLoop_udp(_currentClientID);

            System.out.println("Successfully connected to \"" + serverAddress.toString() + "\"");
            return true;

        } catch (IOException e) {
            System.err.println("Connection to Server failed!");
            e.printStackTrace();
            _isConnected = false;
            return false;
        }
    }

    public void Disconnect() {
        System.out.println("Disconnecting from Server...");

        if (_isConnected) {
            try {
                _serverLoop.stopServerLoop();

                ServerPackages.tcp_Disconnect(tcpOut);
                udpSocket.close();
                tcpSocket.close();
            } catch (IOException e) { }
        }

        // Reset everything
        _isConnected = false;

        _currentClientID = -1;
        _currentRoomID = -1;
        _serverVersion = -1;
        _serverTickMs = -1;
        _serverPing = -1;

        udpSocket = null;
        tcpSocket = null;
        tcpIn = null;
        tcpOut = null;

        _currentServerAddress = null;
        _serverLoop = null;
    }

    // If ServerVersion and Client Version doesn't match -> cancel connection, and false
    public boolean GatherServerInfo() {
        if (!_isConnected) return false;

        try {
            _serverVersion = ServerPackages.tcp_getServerVersion(_serverLoop.responseQueue, tcpOut);
            if (_serverVersion != MY_SERVER_VERSION) {
                System.err.println("Server and client version doesn't match!");
                Disconnect();
                return false;
            }
            _serverTickMs = ServerPackages.tcp_GetServerTickSpeedMs(_serverLoop.responseQueue, tcpOut);
            _currentClientID = ServerPackages.tcp_GetClientID(_serverLoop.responseQueue, tcpOut);

        } catch (IOException e) {
            System.err.println("Server connection lost!");
            Disconnect();
        }

        return true;
    }

    public int CreateRoom() {
        if (!_isConnected) return -1;

        try {
            _currentRoomID = ServerPackages.tcp_createRoom(_serverLoop.responseQueue, tcpOut);
            return _currentRoomID;

        } catch (IOException e) {
            System.err.println("Server connection lost!");
            Disconnect();
            return -1;
        }
    }

    public int getCurrentRoomID() {
        if (!_isConnected) return -1;

        try {
            _currentRoomID = ServerPackages.tcp_getRoomNumber(_serverLoop.responseQueue, tcpOut);
            return _currentRoomID;

        } catch (IOException e) {
            System.err.println("Server connection lost!");
            Disconnect();
            return -1;
        }
    }

    public int joinRoom(int roomID) {
        if (!_isConnected) return -1;

        try {
            return ServerPackages.tcp_joinRoom(_serverLoop.responseQueue, tcpOut, roomID);

        } catch (IOException e) {
            System.err.println("Server connection lost!");
            Disconnect();
            return -1;
        }
    }

    public void leaveRoom() {
        if (!_isConnected) return;

        try {
            ServerPackages.tcp_leaveRoom(tcpOut);

        } catch (IOException e) {
            System.err.println("Server connection lost!");
            Disconnect();
        }
    }

    public void sendPlayerPos(float x, float y) {
        if (!_isConnected) return;

        try {
            ServerPackages.udp_sendPlayerUpdate(
                    udpSocket,
                    _currentServerAddress,
                    _currentClientID,
                    _currentRoomID,
                    x, y
            );

        } catch (IOException e) {
            System.err.println("Server connection lost!");
            Disconnect();
        }
    }

    public String getCurrentWorld(int targetRoomID) {
        if (!_isConnected) return "__";

        try {
            return ServerPackages.tcp_GetCurrentWorld(_serverLoop.responseQueue, tcpOut, targetRoomID);

        } catch (IOException e) {
            System.err.println("Server connection lost!");
            Disconnect();
            return "__";
        }
    }

    public void changeWorld(int roomID, String targetWorld) {
        if (!_isConnected) return;

        try {
            ServerPackages.tcp_changeWorld(tcpOut, roomID, targetWorld);

        } catch (IOException e) {
            System.err.println("Server connection lost!");
            Disconnect();
        }
    }

    public void syncArrow(float x, float y, float vx, float vy) {
        if (!_isConnected) return;

        try {
            ServerPackages.udp_sendArrowShot(udpSocket, _currentServerAddress, _currentClientID, _currentRoomID,
                    x, y, vx, vy);

        } catch (IOException e) {
            System.err.println("Server connection lost!");
            Disconnect();
        }
    }

    // Currently just meant to be called once on connection. Bad design but yeah...
    private int calculatePing() {
        if (!_isConnected) return -1;

        try {
            int sum = 0;
            final int measureTimes = 5;

            for (int i = 0; i < measureTimes; i++) {
                sum += ServerPackages.tcp_GetPingMs(_serverLoop.responseQueue, tcpOut);
            }
            return sum / measureTimes;

        } catch (IOException e) {
            System.err.println("Server connection lost!");
            Disconnect();
            return -1;
        }
    }

}
