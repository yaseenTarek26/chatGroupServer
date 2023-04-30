package ClientPack;

import ServerConnection.Server;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    private Socket socket;
    private BufferedWriter bufferedWriter;
    private BufferedReader bufferedReader;
    private String userName;
    public Client(Socket socket , String userName){
        this.socket = socket;
        this.userName = userName;
        try {
            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException e) {
            closeEverything(socket, bufferedReader,bufferedWriter);
        }
    }

    public void sendMessage(){
        String clientUserMessage;
        Scanner sc = new Scanner(System.in);
        try {
            bufferedWriter.write(userName);
            bufferedWriter.newLine();
            bufferedWriter.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
while(socket.isConnected()){
clientUserMessage = sc.nextLine();
    try {
        bufferedWriter.write(userName+ " :"+clientUserMessage);
        bufferedWriter.newLine();
        bufferedWriter.flush();
    } catch (IOException e) {
        throw new RuntimeException(e);
    }

}
    }
public void closeEverything(Socket socket, BufferedReader bufferedReader, BufferedWriter bufferedWriter){
    try {
        if(bufferedReader!=null) bufferedReader.close();
        if(bufferedWriter!=null) bufferedWriter.close();
        if(socket!=null)socket.close();
    } catch (IOException e) {
        e.printStackTrace();
    }
}
public void listenToMessage(){

new Thread(){
    @Override
     public void run(){
        while(socket.isConnected()){
            try {
                String groupChatMessage;
                groupChatMessage = bufferedReader.readLine();
                System.out.println(groupChatMessage);
            } catch (IOException e) {
                System.out.println("connection lost.");
            }
        }

    }
}.start();
    }
 public static void main (String[]args){
Scanner sc = new Scanner(System.in);
     System.out.println("enter your userName for the group chat: ");
     String userName = sc.nextLine();
     Socket  socket = null;
     try {
         socket = new Socket("192.168.1.7",1246);
     } catch (IOException e) {
     }
     Client client = new Client(socket,userName);
     client.listenToMessage();
     client.sendMessage();
 }


}
