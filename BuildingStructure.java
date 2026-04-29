import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
public class BuildingStructure{

    private Map<String, Room> rooms;
    private Room currentRoom;
    private Room startRoom;

   public BuildingStructure(){
        this.rooms = new HashMap<>();
        this.loadRooms("roomDescription.txt");
        this.loadItems("items.txt");
        this.setUpExits();
   }

   /**
    * 
    */
   private void loadRooms(String fileName){
    try {
        File file = new File(fileName);
        Scanner scanner = new Scanner(file);

        while(scanner.hasNextLine()){
            //read the lines in the text
            String line = scanner.nextLine();

            // spilt each line into two parts by using the divisor ":"
            String[] parts = line.split(":");

            //check if there are two parts for each line
            if(parts.length >= 2){

                //delete the space 
                String name = parts[0].trim();
                String description = parts[1].trim();

                //create a new room using the information we get above
                Room newRoom = new Room(name, description);
                rooms.put(name, newRoom);
            }
        }
        scanner.close();
        System.out.println("Finish loading the map!" + rooms.size() + "rooms have been created!");
    } catch (Exception e) {
        System.err.println("Error: Cannot find the map!" + fileName);
    }
   }

  
private void loadItems(String fileName) {
    try {
        File file = new File(fileName);
        Scanner scanner = new Scanner(file);

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
        
            String[] parts = line.split(":");

            if (parts.length >= 6) {
                String roomName = parts[0].trim();
                String itemName = parts[1].trim();
                String itemDesc = parts[2].trim();
                String type = parts[3].trim();
                int value = Integer.parseInt(parts[4].trim()); // 将数值转为整数
                boolean canHide = Boolean.parseBoolean(parts[5].trim()); // 转为布尔值

                
                Room targetRoom = rooms.get(roomName);

                if (targetRoom != null) {
                    if (type.equalsIgnoreCase("Weapon")) {
                        targetRoom.addItem(new Weapon(itemName, itemDesc, value));
                    } else if (type.equalsIgnoreCase("Food") || type.equalsIgnoreCase("Drink")) {
                        
                        int heal = type.equalsIgnoreCase("Food") ? value : 0;
                        int speed = type.equalsIgnoreCase("Drink") ? value : 0;
                        targetRoom.addItem(new Consumable(itemName, itemDesc, type, heal, speed));
                    } else {
                        
                        targetRoom.addItem(new Item(itemName, itemDesc, true, canHide));
                    }
                }
            }
        }
        scanner.close();
        System.out.println("Items loaded successfully!");
    } catch (Exception e) {
        System.err.println("Error loading items: " + e.getMessage());
    }
}

   private void setUpExits(){
        //pick the rooms from the variable "rooms"
        Room library = rooms.get("Library");
        Room peRoom = rooms.get("PERoom");
        Room storeroom = rooms.get("Storeroom");
        Room medicalRoom = rooms.get("MedicalRoom");
        Room scienceRoom = rooms.get("ScienceRoom");
        Room broadcastRoom = rooms.get("BroadcastRoom");
        Room rooftop = rooms.get("Rooftop");

        //connect the rooms together
        //connect library with the peRoom
        if (library != null && peRoom != null) {
            library.setExit("west", peRoom);
            peRoom.setExit("east", library);
        }
        //connect library and storeroom
        if (library != null && storeroom != null) {
            library.setExit("east", storeroom);
            storeroom.setExit("west", library);
        }
        //connect storeroom with medical room
        //medical room is on the second floor and there is a stair connect storeroom with the medical room
        if (storeroom != null && medicalRoom != null) {
            storeroom.setExit("up", medicalRoom);
            medicalRoom.setExit("down", storeroom);
        }
        //connect broadcast room and dmedical room
        if (medicalRoom != null && broadcastRoom != null){
            medicalRoom.setExit("west", broadcastRoom);
            broadcastRoom.setExit("east", medicalRoom);
        }

        //connect broadcast room and science classroom
        if (broadcastRoom != null && scienceRoom != null){
            broadcastRoom.setExit("west", scienceRoom);
            scienceRoom.setExit("east", broadcastRoom);
        }

        //connect science class room and the roof
        //The roof is on the third floor and there is a stair in the science classroom that will lead players to the roof.
        if (scienceRoom != null && rooftop != null){
            scienceRoom.setExit("up", rooftop);
            rooftop.setExit("down", scienceRoom);
        }
        this.currentRoom = library;
   }

   public Room getCurrentRoom(){
        return currentRoom;
   }

   public boolean movePlayer(String direction){
        Room nextRoom = currentRoom.getExit(direction);
        if (nextRoom != null) {
            this.currentRoom = nextRoom;
            return true;
        } else {
            return false;
        }
   }
   
}