import java.io.Console;

public class Password {

    public void passwordExample() {
        Console console = System.console();
        if (console == null) {
            System.exit(0);
        }
        char passwordArray[] = console.readPassword("Enter your secret password: ");
        console.printf("Password entered was: "+ new String(passwordArray)+"\n");
    }
    
    public static void main(String[] args) {
        new Password().passwordExample();
    }
}
