public class Weapon extends Item {
    
    private int damage;
    //constructor
    public Weapon(String name, String description, String type, int damage, String useFeedback){
        super(name, description, type, true, false, useFeedback);
        this.damage = damage;
    }
    //getter
    public int getDamage(){
        return damage;
    }

}
