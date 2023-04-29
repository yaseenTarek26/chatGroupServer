package ServerConnection;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class clientHandler implements Runnable{
    public static ArrayList<clientHandler>clientHandlers = new ArrayList<>();
    private Socket socket ;
    public BufferedReader bufferedReader;
    public BufferedWriter bufferedWriter;
    public String clientUser;
public clientHandler(Socket socket){
    this.socket = socket;
    try {
    this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
    this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    this.clientUser = bufferedReader.readLine();
    clientHandlers.add(this);
     broadCastMessage("SERVER: "+clientUser+" has Entered chat group!");//broad cast message is a methode that
        // send client text or sever message to all client the server.
    } catch (IOException e) {
      closeEverything(socket, bufferedReader , bufferedWriter);
    }
}

public clientHandler(){

}
    @Override
    public void run() {
    String clientMessage;
    while(socket.isConnected()){
        try {
            clientMessage = bufferedReader.readLine();
            broadCastMessage(clientMessage);
        } catch (IOException e) {
            closeEverything(socket,bufferedReader, bufferedWriter);
            break;
        }
    }


    }
    public void broadCastMessage(String message){
        for(clientHandler clientHandler:clientHandlers){
            if(!clientHandler.clientHandlers.equals(clientHandler)){
                try {
                    clientHandler.bufferedWriter.write(message);
                    clientHandler.bufferedWriter.newLine();
                    clientHandler.bufferedWriter.flush();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }

    }
    public void closeEverything(Socket socket, BufferedReader bufferedReader,BufferedWriter bufferedWriter){
             removeClientHandler();
        try {
            if(bufferedReader!=null) bufferedReader.close();
            if(bufferedWriter!=null) bufferedWriter.close();
        } catch (IOException e) {
           e.printStackTrace();
        }
    }

    public void removeClientHandler(){
    clientHandlers.remove(this);
        broadCastMessage("SERVER: "+clientUser+" has left the chat!");
    }



}
