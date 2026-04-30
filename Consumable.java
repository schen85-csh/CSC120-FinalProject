public class Consumable extends Item {
    
    private String type;
    private int healAmount;

    public Consumable(String name, String description, String type, int healAmount, String useFeedback){
        super(name, description, type, true, false, useFeedback);
        this.healAmount = healAmount;
    }


    public int getHealAmount(){
        return this.healAmount;
    }

}
