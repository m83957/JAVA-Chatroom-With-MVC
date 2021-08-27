public class Controller implements ControllerInterface{
    private View view;
    private ModelInterface model;
    public Controller(ModelInterface model){
        this.model = model;
        view = new View(model,this);
        view.createChatbox();
        view.disableSendBTN();
    }
    @Override
    public void sendMessage(String message) {
        model.setMessage(message);
        view.ClientMessage(message);
    }
    @Override
    public void setNetwork(String IP, String username) {
        model.setUpNetworking(IP,username);
        model.ClientThreadStart();
        view.disableLoginBTN();
        view.enableSendBTN();
    }
}
