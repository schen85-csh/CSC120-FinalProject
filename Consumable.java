public class Consumable extends Item {
    //attributes
    private String type;
    private int healAmount;
    //constructor
    public Consumable(String name, String description, String type, int healAmount, String useFeedback){
        super(name, description, type, true, false, useFeedback);
        this.healAmount = healAmount;
    }

    //getter
    public int getHealAmount(){
        return this.healAmount;
    }

}
