package btosystem.cont;

import btosystem.utils.InputHandler;

public abstract class Controller {
    public void execute() {
        while (true) {
            try {
                if(!load()) return;
                System.out.println(display());
                int input = InputHandler.getIntIndexInput("Select an option: ");
                if(process(input) == -1) return;
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            System.out.println("=".repeat(150));
        }
    }
    protected abstract boolean load() throws Exception;
    protected abstract String display();
    protected abstract int process(int input) throws Exception;
}
