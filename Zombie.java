import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Zombie {
    private String name;
    private int health;
    private Map<String, Double> bodyParts; 
    public Zombie(String name, int health) {
        this.name = name;
        this.health = health;
        this.bodyParts = new HashMap<>();
        
       
        bodyParts.put("head", 2.0);   
        bodyParts.put("torso", 1.0);  
        bodyParts.put("limbs", 0.7);  
        bodyParts.put("ears", 1.5);   
    }

    
    public void takeSpecificDamage(int baseDamage, String part) {
        double multiplier = bodyParts.getOrDefault(part.toLowerCase(), 1.0);
        int finalDamage = (int) (baseDamage * multiplier);
        this.health -= finalDamage;
        
        System.out.println(name + "'s' " + part + " is attacked! Cause a " + finalDamage + " points damage");
        checkStatus();
        System.out.println(">> Damage dealt: " + finalDamage);
        System.out.println(">> " + name + " health remaining: " + health);
    }

    
    public void takeRandomDamage(int baseDamage) {
        String[] parts = {"head", "torso", "limbs", "limbs"}; // limbs 出现两次增加概率
        Random rand = new Random();
        String randomPart = parts[rand.nextInt(parts.length)];
        
    
        takeSpecificDamage(baseDamage, randomPart);
    }

    private void checkStatus() {
        if (health <= 0) {
            System.out.println(name + " falls! You made it!");
        } else {
            System.out.println(name + " still has" + health + " bloods!");
        }
    }

    public String getName(){
        return name;
    }

    public int getHealth() {
         return health; 
    }

    public boolean isAlive() {
         return health > 0; 
    }
}