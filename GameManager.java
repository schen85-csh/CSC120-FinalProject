import java.util.List;
import java.util.Scanner;

public class GameManager {
    // attributes
    private BuildingStructure campus;
    private Player player;
    private boolean isRunning;

    //constructor
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
            processCommand(fullCommand); 
            
        }

        System.out.println("Thanks for playing!");
        input.close();
    }

    /**
     * This method will deal with the command that is given by player
     * @param commandLine
     */
    private void processCommand(String commandLine) {
        // change all into lower case and delete the space
        String line = commandLine.toLowerCase().trim();
        if (line.isEmpty()) return;

        // set up all the commands according to the verbs
        if (line.equals("help")) {
            System.out.println("--- Available Commands ---");
            System.out.println("Basic: go [direction], take [item], hide, quit \n    for command go, players can enter up, down, left, right");
            System.out.println("Use: use [item] \n    for food and drinks, like >> use energy drink");
            System.out.println("Attack: attack [part] \n    players are allowed to use their bare hands to attack some parts(limbs, head, ears, torso) of the zombie. \n    If players did not give a target part, the system will pick a random part to attack.");
            System.out.println("Advanced: use [weapon] to attack [part] \n    players can name the weapon they have and want to use. \n    players can also name the part(limbs, head, ears, torso) of the zombie that they want to attack.");
            return;
        } 
    
        if (line.equals("hide")) {
            player.hide();
            return;
        } 
    
        if (line.equals("quit")) {
            isRunning = false;
            return;
        }

        // set up the complex command (use and attack)
        if (line.startsWith("use") && line.contains("to attack")) {
            // pick the part after "use" and before "to attack"
            String weaponName = line.substring(3, line.indexOf("to attack")).trim();
        
            // pick the part after "to attack"
            int attackIndex = line.indexOf("to attack") + 9;
            String part = "";
            if (line.length() > attackIndex) {
                part = line.substring(attackIndex).trim();
            }
        
            if (weaponName.isEmpty()) {
                System.out.println("What do you want to use to attack?");
            } else {
                handleAttack(weaponName, part);
            }
        }

        // attack [part] or attack
        else if (line.startsWith("attack")) {
            String part = "";
            // if there's a space, there is a [part]
            if (line.contains(" ")) {
                part = line.substring(line.indexOf(" ")).trim();
            }
            handleAttack(null, part); //null means bare hands
        }

        // go [direction]
        else if (line.startsWith("go ")) {
            String direction = line.substring(3).trim();
            player.move(direction);
            checkWinCondition();
        }

        //take [item]
        else if (line.startsWith("take ")) {
            String itemName = line.substring(5).trim();
            player.pickUp(itemName);
        }

        // use [item] 
        else if (line.startsWith("use ")) {
            String itemName = line.substring(4).trim();
            Item item = findItemInInventory(itemName);

            if (item == null) {
                System.out.println("You don't have '" + itemName + "' in your inventory.");
            } 
            else if (item instanceof Consumable) {
                
                Consumable c = (Consumable) item;
                int healAmount = c.getHealAmount();
            
                player.use(item); 
            
                System.out.println(item.getUseFeedback());
                System.out.println(">> HP recovered: +" + healAmount + " | Current HP: " + player.getLifebar());
            } 
            else if (item instanceof Weapon) {
                // give the instruction about how to give correct command
                System.out.println("To use " + item.getName() + " in combat, try: 'use " + item.getName() + " to attack [part]'");
            } 
            else {
                System.out.println("This item cannot be used this way.");
            }
        } 
    
        
        else {
            System.out.println("I don't understand that command. Type 'help' for tips.");
        }
    }
    /**
     * This method will deal with condition that player use weapon to attack.
     * @param weaponName
     * @param part
     */
    private void handleAttack(String weaponName, String part) {
        Room currentRoom = campus.getCurrentRoom();
        List<Zombie> zombies = currentRoom.getZombies();

        // check if there is zombie
        if (zombies.isEmpty()) {
            System.out.println("There are no zombies here to attack!");
            return;
        }
        Zombie target = zombies.get(0);

        
        int finalDamage = 10; // the damage value of bare hands
        Weapon selectedWeapon = null;

        if (weaponName != null && !weaponName.isEmpty()) {
        
            Item item = findItemInInventory(weaponName);
            if (item instanceof Weapon) {
                selectedWeapon = (Weapon) item;
                finalDamage = selectedWeapon.getDamage();
                System.out.println(selectedWeapon.getUseFeedback());
            } else {
                System.out.println("You don't have '" + weaponName + "' or it's not a weapon. Using bare hands!");
            }
        } else {
            System.out.println("You punch with your bare hands...");
        }


        // broadcasting equipment-> hurt ears
        if (selectedWeapon != null && selectedWeapon.getName().equalsIgnoreCase("Broadcasting equipment")) {
            System.out.println("The high-frequency noise targets the zombie's hearing!");
            target.takeSpecificDamage(finalDamage, "ears");
        } 
        
        else {
            if (part == null || part.isEmpty()) {
                target.takeRandomDamage(finalDamage);
            } else {
                target.takeSpecificDamage(finalDamage, part);
            }
        }

        // shows the result
        System.out.println(">> Final Damage: " + finalDamage);
        System.out.println(">> " + target.getName() + " health: " + target.getHealth());

        // zombie attack back and check if zombie or player is dead
        if (target.isAlive()) {
            int zDamage = target.getAttackPower();
            player.takeDamage(zDamage);

            if (!player.isAlive()) {
                System.out.println("***********************************");
                System.out.println("GAME OVER! You were eaten by zombies.");
                System.out.println("***********************************");
                this.isRunning = false;
            }
        } else {
            currentRoom.removeZombie(target);
            System.out.println("The " + target.getName() + " is defeated! The room is safe.");
        }
    }

    /**
     * This method check if the player reach the terminal.
     */
    private void checkWinCondition() {
        // Get the current room name
        String currentRoomName = campus.getCurrentRoom().getName();
        
        // Check if the current room is the Rooftop (case-insensitive)
        if (currentRoomName.equalsIgnoreCase("Rooftop")) {
            System.out.println("\n==================================================");
            System.out.println("🎉 CONGRATULATIONS! YOU MADE IT TO THE ROOFTOP! ");
            System.out.println("==================================================");
            System.out.println("MISSION ACCOMPLISHED! Thanks for playing.");
            
            // Terminate the game loop
            this.isRunning = false;
        }
    }

    /**
     * This method matches the word item with the object item
     * @param itemName
     * @return
     */
    private Item findItemInInventory(String itemName) {
        for (Item i : player.getInventory()) {
            if (i.getName().equalsIgnoreCase(itemName)) {
                return i;
            }
        }
        return null;
    }

    public static void main(String[] args) {
        GameManager gm = new GameManager();
        gm.start();
    }
}
