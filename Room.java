import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
public class Room {
    //attributes
    private String name;
    private String description;
    private HashMap<String, Room> exits;
    private List<Item> items;
    private List<Zombie> zombies;

    //constructor
    public Room(String name, String description){
        this.name = name;
        this.description = description;
        this.exits = new HashMap<>();
        this.items = new ArrayList<>();
        this.zombies = new ArrayList<>();
    }

    //getter and setter
    public List<Item> getItems() {
        return this.items;
    }
    
    public void setExit(String direction, Room neighbor){
        exits.put(direction, neighbor);
    }
    
    public Room getExit(String direction){
        return exits.get(direction);
    }

    public void addItem(Item item){
        items.add(item);
    }
    
    public Item removeItem(String itemName) {
        Item foundItem = null;
        for (Item i : items) {
    
            if (i.getName().equalsIgnoreCase(itemName)) {
                foundItem = i;
                break; 
            }
        }
        
        if (foundItem != null) {
            items.remove(foundItem); 
        }
        return foundItem;
    }
    
    public void addZombie(Zombie zombie) {
        this.zombies.add(zombie);
    }
    
    public void removeZombie(Zombie zombie){
        this.zombies.remove(zombie);
    }
    
    public List<Zombie> getZombies() {
        return this.zombies;
    }

    public String getDescription(){
        return "Location: " + "[" + name + "]" + "\n" + description; 
    }
    
    public String getName(){
        return name;
    }
}
