import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Zombie {
    // attributes
    private String name;
    private int health;
    private int attackPower;
    private Map<String, Double> bodyParts; 
    //constructor
    public Zombie(String name, int health, int attackPower) {
        this.name = name;
        this.health = health;
        this.attackPower = attackPower;
        this.bodyParts = new HashMap<>();
        
       //list the zombie's body part and give them multiplier value
        bodyParts.put("head", 2.0);   
        bodyParts.put("torso", 1.0);  
        bodyParts.put("limbs", 0.7);  
        bodyParts.put("ears", 1.5);   
    }


    /**
     * this method deal with the attack commands with a specific body part.
     * It calculate the final damage and give the information.
     * @param baseDamage
     * @param part
     */
    public void takeSpecificDamage(int baseDamage, String part) {
        double multiplier = bodyParts.getOrDefault(part.toLowerCase(), 1.0);
        int finalDamage = (int) (baseDamage * multiplier);
        this.health -= finalDamage;

        if (this.health<0){
            this.health = 0;
        }
        
        System.out.println(name + "'s' " + part + " is attacked! Cause a " + finalDamage + " points damage");
        checkStatus();
        System.out.println(">> Damage dealt: " + finalDamage);
        System.out.println(">> " + name + " health remaining: " + health);
    }

    /**
     * this method will pick a random body part of zombie to attack
     * @param baseDamage
     */
    public void takeRandomDamage(int baseDamage) {
        //list two limbs here, because limbs has the smallest multilpier. And I want to reduce the possibility for the player to get head and torso.
        String[] parts = {"head", "torso", "limbs", "limbs"}; 
        Random rand = new Random();
        String randomPart = parts[rand.nextInt(parts.length)];
        
        //call takeSpecificDamage
        takeSpecificDamage(baseDamage, randomPart);
    }

    /**
     * this method checks if the zombie is still alive and gives some hint information.
     */
    private void checkStatus() {
        if (health <= 0) {
            System.out.println(name + " falls! You made it!");
        } else {
            System.out.println(name + " still has " + health + " bloods!");
        }
    }

    //getters
    public int getAttackPower(){
        return attackPower;
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