import java.util.Scanner;

public class GameManager {
    private BuildingStructure campus;
    private Player player;
    private boolean isRunning;

    public GameManager() {
        
        this.campus = new BuildingStructure();
        this.player = new Player(campus); 
        this.isRunning = true;
    }
    /**
     * print the welcome message
     * print the description about the library(where the players starts)
     * start the game loop: if the attribute isRunning is true, the game will keep going
     * use split to divide player's words into movements and direction
     */
    public void start() {
        //print the welcome message
        Scanner input = new Scanner(System.in);
        System.out.println("--- Welcome to the Zombie School Survival ---");
        System.out.println("Goal: Reach the Rooftop. Watch out for zombies!");
        System.out.println("Type 'help' to see commands.");
        System.out.println("----------------------------------------------");

        //print the description about the library(where the players starts)
        System.out.println(campus.getCurrentRoom().getDescription());

        //start the game loop: if the attribute isRunning is true, the game will keep going
        while (isRunning) {
            System.out.print("\n> ");
            String fullCommand = input.nextLine().trim();
            if (fullCommand.isEmpty()) continue;

            //use split to divide player's words into action and argument
            String[] parts = fullCommand.split(" ", 2);

            //pick the first part and change it into lower case
            String action = parts[0].toLowerCase();
            
            //set a String variable
            String argument;
            if (parts.length > 1) {
                argument = parts[1];
            } else {
                argument = "";
            }

            //call processCommand 
            processCommand(action, argument);
        }

        System.out.println("Thanks for playing!");
        input.close();
    }

    private void processCommand(String action, String argument) {
        //use ignore case here to make the process more convinient
        if (action.equalsIgnoreCase("help")) {
            System.out.println("Commands: go [direction], take [item], use [item], hide, quit");
        } 
        else if (action.equalsIgnoreCase("go")) {
            player.move(argument);
            checkWinCondition();
        } 
        else if (action.equalsIgnoreCase("take")) {
            player.pickUp(argument);
        } 
        else if (action.equalsIgnoreCase("use")) {
            useItemFromInventory(argument);
        } 
        else if (action.equalsIgnoreCase("hide")) {
            player.hide();
        } 
        else if (action.equalsIgnoreCase("quit")) {
            isRunning = false;
        } 
        else {
            System.out.println("I don't understand that command.");
        }
    }

   
    private void checkWinCondition() {
        // Get the current room name
        String currentRoomName = campus.getCurrentRoom().getName();
        
        // Check if the current room is the Rooftop (case-insensitive)
        if (currentRoomName.equalsIgnoreCase("Rooftop")) {
            System.out.println("\n==================================================");
            System.out.println("🎉 CONGRATULATIONS! YOU MADE IT TO THE ROOFTOP! ");
            System.out.println("==================================================");
            System.out.println("The cold night air hits your face as you push open the door.");
            System.out.println("In the distance, the spotlight of a rescue helicopter");
            System.out.println("cuts through the dark sky and begins its descent.");
            System.out.println("You take one last look at the silent, infested campus...");
            System.out.println("You have survived the nightmare!");
            System.out.println("==================================================");
            System.out.println("MISSION ACCOMPLISHED! Thanks for playing.");
            
            // Terminate the game loop
            this.isRunning = false;
        }
    }

    //pick player's words and match the word item with the object item
    private void useItemFromInventory(String itemName) {
        Item itemToUse = null;
        for (Item i : player.getInventory()) {
            if (i.getName().equalsIgnoreCase(itemName)) {
                itemToUse = i;
                break;
            }
        }
        if (itemToUse != null) {
            player.use(itemToUse);
        } else {
            System.out.println("You don't have '" + itemName + "' in your inventory.");
        }
    }

    public static void main(String[] args) {
        GameManager gm = new GameManager();
        gm.start();
    }
}
