public class Item {
    private String name;
    private String description;
    private boolean canHide;
    private boolean canPick;

    public Item(String name, String description, boolean canPick, boolean canHide){
        this.name = name;
        this.description = description;
        this.canPick = canPick;
        this.canHide = canHide;
    }

    public String getName(){
        return this.name;
    }

    public boolean isCanPick(){
        return canPick;
    }

    public boolean isCanHide(){
        return canHide;
    }

    public String getDescription(){
        return description;
    }

}

