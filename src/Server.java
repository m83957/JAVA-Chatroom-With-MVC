import java.io.*;
import java.net.*;
import java.util.*;

public class Server{
    ArrayList<PrintWriter> clientOutPutStream;

    public class ServerThreadStart implements Runnable {
        BufferedReader reader;
        Socket socket;
        public ServerThreadStart(Socket clientSocket){
            try{
                this.socket = clientSocket;
                InputStreamReader isReader = new InputStreamReader(socket.getInputStream());
                reader = new BufferedReader(isReader);
            } catch (Exception ex){
                ex.printStackTrace();
            }
        }
        @Override
        public void run() {
            String message;
            try{
                while((message = reader.readLine()) != null){
                    System.out.println(message);
                    tellEveryone(message);
                }
            
            } catch (Exception ex){
                ex.printStackTrace();
            }
        }
        
    }
    public void go(){
        clientOutPutStream = new ArrayList<>();
        try{
            ServerSocket serverSocket = new ServerSocket(5000);
            while(true){
                Socket clientSocket = serverSocket.accept();
                PrintWriter writer = new PrintWriter(clientSocket.getOutputStream());
                clientOutPutStream.add(writer);

                Thread t = new Thread(new ServerThreadStart(clientSocket));
                t.start();
                System.out.println("get a Connection");
            }
        } catch (Exception ex){
            ex.printStackTrace();
        }
    }
    public void tellEveryone(String message){
        Iterator<PrintWriter> iterator = clientOutPutStream.iterator();
        while(iterator.hasNext()){
            try{
                PrintWriter writer = (PrintWriter)iterator.next();
                writer.println(message);
                writer.flush();
            } catch (Exception ex){
                ex.printStackTrace();
            }
        }
    }
    public static void main(String[] args){
        new Server().go();
    }
}
