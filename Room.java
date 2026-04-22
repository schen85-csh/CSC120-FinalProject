import java.util.HashMap;
import java.util.List;
public class Room {
    private String name;
    private String description;
    private HashMap<String, Room> exits;
    private List<Item> items;
    private List<Zombie> zombies;

    public void setExit(String direction, Room neighbor){
        exits.put(direction, neighbor);
    }

    public Room getExit(String direction){
        return exits.get(direction);
    }

    public void addItem(Item item){
        items.add(item);
    }

    public Item removeItem(String itemName){
        for (Item i:items){
            if(i.getName().contains(itemName)){
                items.remove(i);
                return i;
            }
        }
        return null;
    }

    public String getDescription(){
        return "Location: " + name + "\n" + description; 
    }
}
