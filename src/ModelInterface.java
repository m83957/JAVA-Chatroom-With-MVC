public interface ModelInterface {
    String getServerText();
    void setUpNetworking(String IP, String username);
    void ClientThreadStart();
    void setMessage(String Msg);
    void registerObserver(MessageObserver obs);
    void removeObserver(MessageObserver obs);

}
