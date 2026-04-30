
import java.util.ArrayList;
import java.util.List;
public class Player {
    //attributes
    private String name;
    private int lifebar;
    private List<Item> inventory;
    private BuildingStructure campus;
    private Weapon currentWeapon;

    //constructor
    public Player(BuildingStructure campus){
       this.campus = campus;
       this.lifebar = 100;
       this.inventory = new ArrayList<>();
    }
    /**
     * This method deals with the player's lifebar after player is attacked by the zombie and prints the information.
     * @param amount
     */
    public void takeDamage(int amount){
        this.lifebar -= amount;
        if (this.lifebar < 0) {
            this.lifebar = 0;
        }
        System.out.println("OHCH! The zombie attacks you ! You lost " + amount + " lifebar.");
        System.out.println("Current Lifebar: " + this.lifebar);
    }

    
    /**
     * This method allows players to move around and also prints information as hints.
     * @param direction
     */
    public void move(String direction){
        boolean success = campus.movePlayer(direction);
        if(success){
            Room currentRoom = campus.getCurrentRoom();
            System.out.println("You move " + direction + " successfully!" );
            System.out.println(campus.getCurrentRoom().getDescription());
            List<Zombie> zombies = currentRoom.getZombies();
            if(! zombies.isEmpty()){
                System.out.println("\n[!] WARNING: You are not alone...");
                for(Zombie z:zombies){
                    System.out.println(">> A "+z.getName()+ " is wandering here!");
                }
            }else{
                System.out.println("\nThe room seems quiet... for now.");
            }
        }else{
            System.out.println("Oops! You cannot move " + direction);
        }
    }

    /**
     * This mathod check if there's the item in the room and print information to tell player that if they successfully pick up the item.
     * if the player can pick the item, the item will be added into the inventory.
     * @param itemName
     */
    public void pickUp(String itemName){
        Room currentRoom = campus.getCurrentRoom();
        Item item = currentRoom.removeItem(itemName);
        if(item != null){
            inventory.add(item);
            System.out.println("You pick up the " + item.getName());
        }else{
            System.out.println("There is no " + itemName + " here.");
        }
    }

    /**
     * This method check if the item is consumable and then modify player's lifebar.
     * @param item
     */
    public void use(Item item){
        if (item instanceof Consumable){
            Consumable c = (Consumable) item;
            this.lifebar += c.getHealAmount();
            inventory.remove(item);
        }
    }

    /**
     * This method check the current room to see if there's somewhere to hide and prints information to tell player if they hide successfully.
     */
    public void hide(){
        Room currentRoom = campus.getCurrentRoom();
        boolean canHideHere = false;
        for (Item i : currentRoom.getItems()){
            if (i.isCanHide()){
                canHideHere = true;
                break;
            }
        }
        if (canHideHere){
            System.out.println("You can hide behide shelter. Then the zombies cannot see you!");
        }else{
            System.out.println("It is a open space! You have no where to hide!");
        }
    }

    //getter and setter
    public boolean isAlive(){
        return this.lifebar > 0;
    }
    public void setCurrentWeapon(Weapon weapon){
        this.currentWeapon = weapon;
    }

    public Weapon getCurrentWeapon(){
        return this.currentWeapon;
    }

    public int getLifebar(){
        return lifebar;
    }

    public void setLifebar(int lifebar){
        this.lifebar = lifebar;
    }

    public void addToInventory(Item item){
        this.inventory.add(item);
        System.out.println("get the item: " + item.getName());
    }

    public List<Item> getInventory(){
        return inventory;
    }
    
}
