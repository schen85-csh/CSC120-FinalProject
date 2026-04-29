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

    public void start() {
        Scanner input = new Scanner(System.in);
        System.out.println("--- Welcome to the Zombie School Survival ---");
        System.out.println("Goal: Reach the Rooftop. Watch out for zombies!");
        System.out.println("Type 'help' to see commands.");
        System.out.println("----------------------------------------------");

        
        System.out.println(campus.getCurrentRoom().getDescription());

        while (isRunning) {
            System.out.print("\n> ");
            String fullCommand = input.nextLine().trim();
            if (fullCommand.isEmpty()) continue;

            String[] parts = fullCommand.split(" ", 2);
            String action = parts[0].toLowerCase();
            String argument = (parts.length > 1) ? parts[1] : "";

            processCommand(action, argument);
        }

        System.out.println("Thanks for playing!");
        input.close();
    }

    private void processCommand(String action, String argument) {
        switch (action) {
            case "help":
                System.out.println("Commands: go [direction], take [item], use [item], hide, quit");
                break;
            case "go":
                player.move(argument);
                break;
            case "take":
                player.pickUp(argument);
                break;
            case "use":
                useItemFromInventory(argument);
                break;
            case "hide":
                player.hide();
                break;
            case "quit":
                isRunning = false;
                break;
            default:
                System.out.println("I don't understand that command.");
        }
    }

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
