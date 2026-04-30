public class Item {
    private String name;
    private String description;
    private boolean canHide;
    private boolean canPick;
    private String type;
    private String useFeedback;

    public Item(String name, String description, String type, boolean canPick, boolean canHide, String useFeedback){
        this.name = name;
        this.description = description;
        this.type = type;
        this.canPick = canPick;
        this.canHide = canHide;
        this.useFeedback = useFeedback;
    }
    
    public String getUseFeedback(){
        return useFeedback;
    }

    public String getType(){
        return this.type;
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

