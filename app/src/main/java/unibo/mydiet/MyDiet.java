package unibo.mydiet;


import unibo.mydiet.view.MainFrame;

/**
 * The main application loader.
 */
public final class MyDiet {
    private MyDiet() {

    }

    /**
     * Starts the login view.
     *
     * @param args The arguments passed to the application.
     */
    public static void main(final String[] args) {
        new MainFrame();
    }
}
