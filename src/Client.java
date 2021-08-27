
import java.io.*;
import java.net.*;
import java.util.ArrayList;
public class Client implements ModelInterface{
    private ArrayList<MessageObserver> MessageObserver = new ArrayList<>();
    private String username;
    private String serverMessage;
    private Socket socket;
    private BufferedReader reader;
    private PrintWriter writer;

    @Override
    public String getServerText() {
        return serverMessage;
    }

    @Override
    public void setUpNetworking(String IP, String username){
        this.username = username;
        try{
            socket = new Socket(IP,5000);
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer = new PrintWriter(socket.getOutputStream());
            
        } catch (IOException ex){
            ex.printStackTrace();
        }     
    }
    @Override
    public void ClientThreadStart(){
        Thread readerThread = new Thread(new Runnable() {
            @Override
            public void run(){
                String messageIN;
                try{
                    while((messageIN = reader.readLine()) != null){
                        System.out.println("Got " + messageIN);
                        serverMessage = messageIN;
                        notifyServerObservers();
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
        readerThread.start();
    }
    @Override
    public void setMessage(String Msg) {
        try{
            String message = "[" + username + "] " + Msg;
            writer.println(message);
            writer.flush();
        } catch (Exception ex){
            ex.printStackTrace();
        }
    }
    @Override
    public void registerObserver(MessageObserver obs) {
        MessageObserver.add(obs);
    }
    @Override
    public void removeObserver(MessageObserver obs) {
        int i = MessageObserver.indexOf(obs);
        if(i>=0){
            MessageObserver.remove(obs);
        }
    }
    public void notifyServerObservers(){
        for(int i = 0; i < MessageObserver.size(); i++){
            MessageObserver observer = (MessageObserver)MessageObserver.get(i);
            observer.updateServerText();
        }
    }
    

}
