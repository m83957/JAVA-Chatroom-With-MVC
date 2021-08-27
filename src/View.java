import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class View implements ActionListener,MessageObserver{
    private ModelInterface model;
    private ControllerInterface controller;

    private JFrame frame;
    private JPanel textPane;
    private JPanel messagePane;
    private JPanel LoginPane;
    private JLabel Labelname = new JLabel("Username");
    private JLabel Labelmsg = new JLabel("Message");
    private JLabel LabelIp = new JLabel("Ip");
    private JButton sendBTN;
    private JButton loginBTN;
    private JTextField Fieldusername;
    private JTextField Fieldmessage;
    private JTextField FieldIp;
    private JTextArea textArea;
    public View(ModelInterface model,ControllerInterface controller){
        this.model = model;
        this.controller = controller;
        model.registerObserver((MessageObserver)this);
    }
    public void createChatbox(){
        frame = new JFrame("ChatRoom With MVC Pattern");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500,500);
        frame.setLayout(new FlowLayout());

        textPane = new JPanel();
        messagePane = new JPanel();
        LoginPane = new JPanel();
        LoginPane.setLayout(new BoxLayout(LoginPane, BoxLayout.Y_AXIS));
        textPane.setBackground(Color.gray);

        textArea = new JTextArea(22,30);
        textArea.setLineWrap(true);
        
        Fieldusername = new JTextField("User");
        Fieldmessage = new JTextField(30);
        Fieldmessage.setText("Message");
        FieldIp = new JTextField("127.0.0.1");
        sendBTN = new JButton("Send");
        loginBTN = new JButton("Login");
        sendBTN.setEnabled(false);

        JPanel cornerPanel = new JPanel();
        cornerPanel.add(new JLabel("ChatRoom"));

        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        
        sendBTN.addActionListener(this);
        loginBTN.addActionListener(this);

        textPane.add(scrollPane);
        LoginPane.add(Labelname);
        LoginPane.add(Fieldusername);
        LoginPane.add(LabelIp);
        LoginPane.add(FieldIp);
        LoginPane.add(loginBTN);
        messagePane.add(Labelmsg);
        messagePane.add(Fieldmessage);
        messagePane.add(sendBTN);  

        frame.getContentPane().add(LoginPane,BorderLayout.WEST);
        frame.add(textPane);
        frame.getContentPane().add(messagePane,BorderLayout.SOUTH);

        frame.setVisible(true);
    }
    //////Controller
    public void disableLoginBTN(){
        loginBTN.setEnabled(false);
    }
    public void enableLoginBTN(){
        loginBTN.setEnabled(true);
    }
    public void disableSendBTN(){
        sendBTN.setEnabled(false);
    }
    public void enableSendBTN(){
        sendBTN.setEnabled(true);
    }
    public void ClientMessage(String message){
        //textArea.append(message + "\n");
    }
    //////Controller
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == sendBTN){
            System.out.println("sendBTN");
            String message = Fieldmessage.getText();
            if(message!=null){
                controller.sendMessage(message);
                Fieldmessage.setText("");
            }
        }else if(e.getSource() == loginBTN){
            System.out.println("loginBTN");
            String username = Fieldusername.getText();
            String Ip = FieldIp.getText();
            controller.setNetwork(Ip, username);
        }
    }
    @Override
    public void updateServerText() {
        String text = model.getServerText();
        textArea.append(text + "\n");
    }
}