public class Consumable extends Item {
    
    private String type;
    private int healAmount;
    private int speedBoost;

    public Consumable(String name, String description, String type, int healAmount, int speedBoost){
        super(name, description, true, false);
        this.type = type;
        this.healAmount = healAmount;
        this.speedBoost = speedBoost;
    }
}
