public class testRun {
    public static void main(String[] args){
        ModelInterface model = new Client();
        ControllerInterface controller = new Controller(model);
    }
}
