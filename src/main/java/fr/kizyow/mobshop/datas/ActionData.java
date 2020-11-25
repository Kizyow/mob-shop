package fr.kizyow.mobshop.datas;

public enum ActionData {

    NONE("none"),
    SELL_BUTCHER("sell_butcher"),
    SELL_SHOP("sell_shop"),
    PREVIOUS_PAGE("previous_page"),
    NEXT_PAGE("next_page"),
    CONFIRM("confirm"),
    REFUSE("refuse"),
    CLOSE("close");

    private final String name;

    ActionData(String name){
        this.name = name;
    }

    public String getName(){
        return name;
    }

    public static ActionData getAction(String name){

        for(ActionData actionData : values()){
            if(actionData.getName().equalsIgnoreCase(name)){
                return actionData;
            }

        }

        return NONE;

    }

}
