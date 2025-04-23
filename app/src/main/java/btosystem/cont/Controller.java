package btosystem.cont;

import btosystem.utils.InputHandler;

/**
 * An abstract base controller class that defines the core execution flow for
 * command-line interface controllers. Implements the main control loop and
 * delegates specific operations to subclass implementations.
 */
public abstract class Controller {

    /**
     * Executes the main controller loop.
     */
    public void execute() {
        while (true) {
            try {
                if(!load()) return;
            } catch (Exception e) {
                System.out.println(e.getMessage());
                return;
            }
            try {
                System.out.println(display());
                int input = InputHandler.getIntIndexInput("Select an option: ");
                if (process(input) == -1) {
                    return;
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            System.out.println("=".repeat(150));
        }
    }

    /**
     * Loads or initializes controller data.
     *
     * @return true if loading succeeded and execution should continue,
     *         false to terminate the controller
     * @throws Exception if an error occurs during loading
     */
    protected abstract boolean load() throws Exception;

    /**
     * Generates the display output for the current state.
     *
     * @return the string to be displayed to the user
     */
    protected abstract String display();

    /**
     * Processes user input and executes corresponding actions.
     *
     * @param input the user's selection
     *
     * @return 0 to continue execution, -1 to terminate the controller
     * @throws Exception if an error occurs during processing
     */
    protected abstract int process(int input) throws Exception;
}
