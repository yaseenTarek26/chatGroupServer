package ServerConnection;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
  private  ServerSocket serverSocket ;
public Server(ServerSocket serverSocket){
        this.serverSocket =  serverSocket;
}
public void startServer(){
    try{

        while(!serverSocket.isClosed()){
            Socket socket = serverSocket.accept();
            System.out.println("A new client has been connected");
            clientHandler clientHandler = new clientHandler(socket);
            Thread thread = new Thread(clientHandler);
            thread.start();
        }
    } catch (IOException e) {
        throw new RuntimeException(e);
    }
}
public void closeServerSocket()
{
    try{
        if(serverSocket!=null)
        {serverSocket.close();}
    }catch (IOException e) {throw new RuntimeException(e);}
}

 public static void main(String[]args) throws IOException {
ServerSocket serverSocket = new ServerSocket(1234);
Server server =new Server(serverSocket);
server.startServer();
 }
}
