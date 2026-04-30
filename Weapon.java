public class Weapon extends Item {
    
    private int damage;

    public Weapon(String name, String description, String type, int damage, String useFeedback){
        super(name, description, type, true, false, useFeedback);
        this.damage = damage;
    }

    public int getDamage(){
        return damage;
    }

}
