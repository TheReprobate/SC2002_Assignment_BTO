package btosystem.cont;

import btosystem.utils.InputHandler;

public abstract class Controller {
    public void execute() {
        while (true) {
            try {
                System.out.println(getMenu());
                int input = InputHandler.getIntIndexInput("Select an option: ");
                if(process(input) == -1) return;
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }
    }
    protected abstract int process(int input) throws Exception;
    protected abstract String getMenu();
}
