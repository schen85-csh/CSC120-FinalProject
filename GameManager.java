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
            processCommand(fullCommand); 
            
        }

        System.out.println("Thanks for playing!");
        input.close();
    }

    private void processCommand(String commandLine) {
        String line = commandLine.toLowerCase().trim();

        if (line.equals("help")) {
            System.out.println("Commands: go [dir], take [item], use [item], attack [part]");
            System.out.println("Advanced: use [weapon] to attack [part]");
        } 
        else if (line.equals("hide")) {
            player.hide();
        } 
        else if (line.equals("quit")) {
            isRunning = false;
        } 
        // 优先处理长指令
        else if (line.startsWith("use") && line.contains("to attack")) {
            String weaponName = line.substring(3, line.indexOf("to attack")).trim();
            int attackIndex = line.indexOf("to attack") + 9;
            String part = (line.length() > attackIndex) ? line.substring(attackIndex).trim() : "";
        
            if (weaponName.isEmpty()) {
                System.out.println("What do you want to use to attack?");
            } else {
                handleAttack(weaponName, part);
            }
        }
        else if (line.startsWith("go ")) {
            player.move(line.substring(3).trim());
            checkWinCondition();
        }
        else if (line.startsWith("take ")) {
            player.pickUp(line.substring(5).trim());
        }
        else if (line.startsWith("attack")) {
            // 改进点：安全获取部位
            String part = line.contains(" ") ? line.substring(line.indexOf(" ")).trim() : "";
            handleAttack(null, part);
        }
        else if (line.startsWith("use ")) {
            String itemName = line.substring(4).trim();
            Item item = findItemInInventory(itemName);

            if (item == null) {
                System.out.println("You don't have '" + itemName + "' in your inventory.");
            } else if (item instanceof Consumable) {
                player.use(item); 
                System.out.println(item.getUseFeedback());
                System.out.println(">> Lifebar: " + player.getLifebar());
            } else if (item instanceof Weapon) {
                System.out.println("To use " + itemName + ", try: 'use " + itemName + " to attack [part]'");
            } else {
                System.out.println("You can't use this item like that.");
            }
        } 
        else {
            System.out.println("I don't understand that command.");
        }
    }

    private void handleAttack(String weaponName, String part) {
        Room currentRoom = campus.getCurrentRoom();
        List<Zombie> zombies = currentRoom.getZombies();

        // 1. 检查是否有丧尸
        if (zombies.isEmpty()) {
            System.out.println("There are no zombies here to attack!");
            return;
        }
        Zombie target = zombies.get(0);

        // 2. 确定伤害和武器反馈
        int finalDamage = 10; // 默认空手伤害
        Weapon selectedWeapon = null;

        if (weaponName != null && !weaponName.isEmpty()) {
            // 在背包里找指定的武器
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

        // 3. 执行攻击逻辑
        // 特殊处理：广播设备自动打耳朵
        if (selectedWeapon != null && selectedWeapon.getName().equalsIgnoreCase("Broadcasting equipment")) {
            System.out.println("The high-frequency noise targets the zombie's hearing!");
            target.takeSpecificDamage(finalDamage, "ears");
        } 
        // 普通处理：指定部位或随机
        else {
            if (part == null || part.isEmpty()) {
                target.takeRandomDamage(finalDamage);
            } else {
                target.takeSpecificDamage(finalDamage, part);
            }
        }

        // 4. 显示伤害结果 (注意：具体的伤害值建议在 Zombie 类里打印，这里保持同步)
        System.out.println(">> Final Damage: " + finalDamage);
        System.out.println(">> " + target.getName() + " health: " + target.getHealth());

        // 5. 丧尸反击与死亡检查
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
