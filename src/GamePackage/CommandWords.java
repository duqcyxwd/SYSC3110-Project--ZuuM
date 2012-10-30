package GamePackage;
/**
 * This class is part of the "World of Zuul" application. 
 * "World of Zuul" is a very simple, text based adventure game.  
 ** 
 * This class holds an enumeration of all command words known to the game.
 * It is used to recognise commands as they are typed in.
 *
 * @author Bruno Colantonio, Nishant Bhasin, Mohamed Ahmed, Yongquinchuan Du 
 * @version Oct 23rd, 2012
 */

public class CommandWords
{
    // a constant array that holds all valid command words
    private static final String[] validCommands = {
        "go", "quit", "help", "look", "pick", 
        "inventory", "undo", "redo" 
    };

    /**
     * Constructor - initialize the command words.
     */
    public CommandWords(){
        // nothing to do at the moment...
    }

    /**
     * Check whether a given String is a valid command word. 
     * @return true if a given string is a valid command,
     * false if it isn't.
     */
    public boolean isCommand(String aString){
        for(int i = 0; i < validCommands.length; i++) {
            if(validCommands[i].equals(aString)) //iterates through validCommands and if a value equals sString return true
                return true;
        }
        // if we get here, the string was not found in the commands
        return false;
    }

    public String getWords() {
        String s = "";
        for(String command : validCommands)
            s+= "-" + command + ": \n";
        return s;
    }
}
