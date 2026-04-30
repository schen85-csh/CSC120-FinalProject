import java.util.List;
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
            System.out.println("Commands: go [west, east, up, down], take [item], use [item], hide, quit, attack[head, torso, limbs, ears]");
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
        else if (action.equalsIgnoreCase("attack")) {
            handleAttack(argument);
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

    private void handleAttack(String part) {
        Room currentRoom = campus.getCurrentRoom();
        List<Zombie> zombies = currentRoom.getZombies();

        //check if there is zombie in the room
        if (zombies.isEmpty()) {
            System.out.println("You swing your weapon at the empty air. There are no zombies here!");
            return;
        }

        Weapon equippedWeapon = player.getCurrentWeapon();
        //the basic damage value
        int finalDamage = 10;
        String weaponName = "bare hands";

        if (equippedWeapon != null){
            finalDamage = equippedWeapon.getDamage();
            weaponName = equippedWeapon.getName();
            System.out.println(equippedWeapon.getUseFeedback());
        }else{
            System.out.println("You are not holding a weapon! You punch with your bare hands...");
        }

        Zombie target = zombies.get(0);

        //attack zombies' ear when player use the broadcasting equipment
        if (equippedWeapon != null && equippedWeapon.getName().equalsIgnoreCase("Broadcasting equipment")) {
            System.out.println("The high-frequency noise targets the zombie's hearing!");
            target.takeSpecificDamage(finalDamage, "ears"); 
        } else {
            //attack zombies based on player's instruction or randomly
            System.out.println("Dealing damage to "+ target.getName() + "'s " + (part.isEmpty()? "body" : part) + "...");
            if (part == null || part.isEmpty()){
                target.takeRandomDamage(finalDamage);
            } else {
                target.takeSpecificDamage(finalDamage, part);
            }
        }

        System.out.println(">> Damage dealt: " + finalDamage);
        System.out.println(">> "+ target.getName()+"health remaining: "+target.getHealth());

        if (target.isAlive()){
            int zDamage = target.getAttackPower();
            player.takeDamage(zDamage);

            if(!player.isAlive()){
                System.out.println("***********************************");
                System.out.println("GAME OVER! You were eaten by zombies.");
                System.out.println("***********************************");
                this.isRunning = false;
            }
        }else{
            currentRoom.removeZombie(target);
            System.out.println("The area is now safe... for now.");
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
            int valueUsed = 0;
            if (itemToUse instanceof Weapon){
                Weapon w = (Weapon) itemToUse;
                player.setCurrentWeapon(w);
                valueUsed = w.getDamage();
                System.out.println(itemToUse.getUseFeedback());
                System.out.println(">> Weapon Damage: " + valueUsed);
            }else if (itemToUse instanceof Consumable){
                Consumable c = (Consumable) itemToUse;
                valueUsed = c.getHealAmount();
                player.use(itemToUse);
                System.out.println(itemToUse.getUseFeedback());
                System.out.println(">> Lifebar recovered: +" + valueUsed + " | Current Lifebar: " + player.getLifebar()) ;
            }
            
        } else {
            System.out.println("You don't have '" + itemName + "' in your inventory.");
        }
    }

    public static void main(String[] args) {
        GameManager gm = new GameManager();
        gm.start();
    }
}
