
import java.util.ArrayList;
import java.util.List;
public class Player {
    
    private String name;
    private int lifebar;
    private int speed;
    private List<Item> inventory;
    private BuildingStructure campus;


    public Player(){
       this.lifebar = 100;
       this.speed = 10;
       this.inventory = new ArrayList<>();
    }

    public void move(String direction){
        boolean success = campus.movePlayer(direction);
        if(success){
            System.out.println("You have already move " + direction + "!" );
            System.out.println(campus.getCurrentRoom().getDescription());
        }else{
            System.out.println("Oops! You cannot move " + direction);
        }
    }

    public void pickUp(String itemName){
        Room currentRoom = campus.getCurrentRoom();
        Item item = currentRoom.removeItem(itemName);
        if(item != null){
            inventory.add(item);
            System.out.println("You pick up the" + itemName);
        }else{
            System.out.println("There is no " + itemName + "here.");
        }
    }

    public void use(Item item){
        if (item instanceof Consumable){
            Consumable c = (Consumable) item;
            if(c.getType().equals("Drink")){
                this.speed += c.getSpeedBoost();
                System.out.println("You have drunk the drink, now you can run faster!");
            }else if (c.getType().equals("Food")){
                this.lifebar += c.getHealAmount();
                System.out.println("You have eaten something, now you are more energetic!");
            }else if (item instanceof Weapon){
                System.out.println("Here is a nice weapon to deal with the zombies!");
            }
            inventory.remove(item);
        }
    }
    
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

    public void attack(Zombie zombie, Weapon weapon) {
    if (weapon.getName().equalsIgnoreCase("Broadcasting equipment")) {
        zombie.takeSpecificDamage(weapon.getDamage(), "ears");
    } else {
        zombie.takeRandomDamage(weapon.getDamage());
    }
}

    public int getLifebar(){
        return lifebar;
    }

    public void setLifebar(int lifebar){
        this.lifebar = lifebar;
    }

    public int getSpeed(){
        return speed;
    }

    public void setSpeed(int speed){
        this.speed = speed;
    }


    public void addToInventory(Item item){
        this.inventory.add(item);
        System.out.println("get the item: " + item.getName());
    }

    public List<Item> getInventory(){
        return inventory;
    }
    
}
