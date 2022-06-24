package com.games.pizzaquest.objects;

import java.util.HashMap;

public class NonPlayerCharacter implements PlayerInterface {
    private String name = "";
    Boolean isQuestActive = false;
    private String npcLocation = "";
    private int repToGive = 0;


    private String requiredQuestItemString = "";

    public NonPlayerCharacter(
            String name, String dialog, String npcLocation, String description, String reputation, String questItem
    ) {
        setName(name);
        setDialogue(dialog);
        setNpcLocation(npcLocation);
        setNpcDescription(description);
        int numberOfRep = 0;
        if (!reputation.equals("")) {
            numberOfRep = Integer.parseInt(reputation);
            setRepToGive(numberOfRep);
        }
        setRequiredQuestItemString(questItem);
    }

    public int getRepToGive() {
        return repToGive;
    }

    public void setRepToGive(int repToGive) {
        this.repToGive = repToGive;
    }

    public int processItem(String item) {
        int sendRep = 0;
        if (item.equals(getRequiredQuestItemString())) {
            sendRep = getRepToGive();
            System.out.println("Congratulations you have received " + sendRep + " reputation from " + getName());
        } else {
            System.out.println("You gave the " + item + " to " + getName() + ". Unfortunately he took that item but it not what they were looking for :(");
        }
        return sendRep;
    }


    public String getNpcLocation() {
        return npcLocation;
    }

    public String getRequiredQuestItemString() {
        return requiredQuestItemString;
    }

    public void setRequiredQuestItemString(String requiredQuestItemString) {
        this.requiredQuestItemString = requiredQuestItemString;
    }

    public void setNpcLocation(String npcLocation) {
        this.npcLocation = npcLocation;
    }

    public String getNpcDescription() {
        return npcDescription;
    }

    public void setNpcDescription(String npcDescription) {
        this.npcDescription = npcDescription;
    }

    private String npcDescription = "";

    private final HashMap<String, String> dialogue = new HashMap<String, String>();


    public void setDialogue(String quest) {
        dialogue.put("quest", quest);

    }

    public String giveQuest() {
        return dialogue.get("quest");
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "NonPlayerCharacter{" +
                "name='" + name + '\'' +
                ", isQuestActive=" + isQuestActive +
                ", permanentLocation=" +
                '}';
    }
}
