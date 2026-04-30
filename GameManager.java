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
        // 1. 预处理：统一转小写并去首尾空格
        String line = commandLine.toLowerCase().trim();
        if (line.isEmpty()) return;

        // 2. 处理单单词指令
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

        // 3. 处理组合指令：use [weapon] to attack [part]
        if (line.startsWith("use") && line.contains("to attack")) {
         // 提取 "use " 之后到 " to attack" 之前的部分
            String weaponName = line.substring(3, line.indexOf("to attack")).trim();
        
            // 提取 "to attack " 之后的部分
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

        // 4. 处理单纯的攻击：attack [part] 或 attack
        else if (line.startsWith("attack")) {
            String part = "";
            // 如果输入中包含空格，说明后面跟着部位名
            if (line.contains(" ")) {
                part = line.substring(line.indexOf(" ")).trim();
            }
            handleAttack(null, part); // 第一个参数为 null 表示空手
        }

        // 5. 处理移动：go [direction]
        else if (line.startsWith("go ")) {
            String direction = line.substring(3).trim();
            player.move(direction);
            checkWinCondition();
        }

        // 6. 处理拾取：take [item]
        else if (line.startsWith("take ")) {
            String itemName = line.substring(5).trim();
            player.pickUp(itemName);
        }

        // 7. 处理单纯的使用：use [item] (针对 bandage/food 等)
        else if (line.startsWith("use ")) {
            String itemName = line.substring(4).trim();
            Item item = findItemInInventory(itemName);

            if (item == null) {
                System.out.println("You don't have '" + itemName + "' in your inventory.");
            } 
            else if (item instanceof Consumable) {
                // 执行恢复逻辑
                Consumable c = (Consumable) item;
                int healAmount = c.getHealAmount();
            
                player.use(item); // 调用 Player 的使用逻辑处理数值
            
                System.out.println(item.getUseFeedback());
                System.out.println(">> HP recovered: +" + healAmount + " | Current HP: " + player.getLifebar());
            } 
            else if (item instanceof Weapon) {
                // 引导玩家使用组合指令
                System.out.println("To use " + item.getName() + " in combat, try: 'use " + item.getName() + " to attack [part]'");
            } 
            else {
                System.out.println("This item cannot be used this way.");
            }
        } 
    
        // 8. 兜底逻辑
        else {
            System.out.println("I don't understand that command. Type 'help' for tips.");
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
