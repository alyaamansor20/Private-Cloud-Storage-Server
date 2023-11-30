package com.example.privatecloudstorageserver;
import java.io.*;
import java.net.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/**
 * Defines the type of Communication
 * Server to Proxy
 * Server to Server
 */
enum CommunicationType {
    Proxy,
    CallServer,
    ListenToServer
}

/**
 * Manage network Communication
 */
public class NetworkManager {
    // Used to accept new connection
    public static ServerSocket mHost;
    // Save Online servers
    public static ArrayList<Connect> mServer;
    // Used to wait the information sent from proxy to be extracted
    public static boolean mInformationExtracted;
    // Port number we listen on
    public static final int mPortNumber = 4455;
    // Holds proxy information
    Connect mProxy;
    /**
     * Constructor for NetworkManager class
     */
    public NetworkManager(Proxy proxy){
        mProxy = proxy;
        mInformationExtracted = false;
        mServer=new ArrayList<Connect>();
        try{
            // Start Listening on Port 4455 with max load 100
            mHost = new ServerSocket(mPortNumber, 100, InetAddress.getByName("0.0.0.0"));
        }catch (IOException e)
        {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }

    }

    /**
     * Connect to Proxy then connect to online servers
     */
    public void StartServer()  {
        try{
            // Create thread to connect to the Proxy
            ThreadManager ProxyThread = new ThreadManager(CommunicationType.Proxy, mProxy);
            ProxyThread.start();

            // Pool for listening to other servers that are trying to connect with us
            int MAX_POOL = 4;
            ExecutorService pool = Executors.newFixedThreadPool(MAX_POOL);
            for(int i = 0; i < MAX_POOL; i++){
                pool.execute(new ThreadManager(CommunicationType.ListenToServer));
            }

            // Wait until information extracted from Proxy
            while(!mInformationExtracted);

            // Create a thread to connect to online servers
            for(Connect s : NetworkManager.mServer) {
                ThreadManager ServerThread = new ThreadManager(CommunicationType.CallServer, s);
                ServerThread.start();
            }

        }catch (IOException e)
        {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }

}

/**
 * Handles Multi-Threading in Network Communication
 */
class ThreadManager extends Thread {
    private Connect mConnect;
    private Socket mSocket;
    private PrintWriter mWriter;
    private BufferedReader mReader;


    private CommunicationType mCommunicationType;

    public ThreadManager( CommunicationType communicationType,Connect connect) throws IOException {
        // Connect with Proxy
        mConnect = connect;
        mCommunicationType = communicationType;
    }
    public ThreadManager(CommunicationType communicationType) throws IOException {
        mCommunicationType = communicationType;
    }

    /**
     * Start Connection
     * @throws IOException
     */
    private void InitCommunication() {
        try {
            mSocket = new Socket(mConnect.getIpAddress(), mConnect.getPortNumber());
            InitReaderWriter();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Initialize Reader/Writer Variables
     * @throws IOException
     */
    private void InitReaderWriter() {
        try{
            OutputStream output = mSocket.getOutputStream();
            mWriter = new PrintWriter(output, true);
            InputStream input = mSocket.getInputStream();
            mReader = new BufferedReader(new InputStreamReader(input));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Run the Thread -- Must Call start() Function Never Call run()
     */
    @Override
    public void run() {
        if(mCommunicationType == CommunicationType.ListenToServer){
            ListenToServer();
        }
        else{
            InitCommunication();
            if(mCommunicationType == CommunicationType.Proxy) CallProxy();
            else if(mCommunicationType == CommunicationType.CallServer) CallServer();
        }
    }

    /**
     * Start Communicating with Proxy and Extract Info about other online Servers and Keep the connection ALIVE
     */
    private void CallProxy(){
        HostManager hostManager=new HostManager();
        try{
            // Authenticate with the proxy [Server ID, Proxy ID, Proxy Password]
            mWriter.print(hostManager.getUid()+",");
            mWriter.print(NetworkManager.mPortNumber +",");
            mWriter.print(mConnect.getId()+",");
            mWriter.println(((Proxy)mConnect).getPasswordProxy());

            Connect server=new Server();
            int i = 0;
            Thread.sleep(100);
            // Read Line by Line data sent from Proxy
            while(mReader.ready())
            {
                // Extract data
                String [] tokens=mReader.readLine().split(",");
                server.setId(tokens[0]);
                server.setIpAddress(tokens[1]);
                server.setPortNumber(Integer.parseInt(tokens[2]));

                // Add new server to the list
                NetworkManager.mServer.add(server);
                i++;
            }

            NetworkManager.mInformationExtracted = true;

            // Keep Connection alive
            while (true)
            {
                mWriter.println("ALIVE");
                mWriter.flush();
            }
        }
        /**
         * @throws IOException if stream to file cannot be written to or closed
         */
        catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Connect to already online servers
     */
    private void CallServer() {
        try{
            System.out.println("SERVER CONNECTED !!!\n\n\n\n\n\n\n");
            System.out.println("IP: " + mSocket.getInetAddress().toString());
            System.out.println("PORT: " + mSocket.getPort());

            mSocket.close();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Listen for the Newly online servers to connect
     */
    private void ListenToServer() {
        try {
            // Waiting for the newly online server to connect
            mSocket = NetworkManager.mHost.accept();
            InitReaderWriter();
            System.out.println("SERVER CONNECTED !!!");
            System.out.println("IP: " + mSocket.getInetAddress().toString());
            System.out.println("PORT: " + mSocket.getPort());

            mSocket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}