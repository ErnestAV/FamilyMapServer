package data_generators;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import model.Event;
import model.Person;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DataGenerator {
    private ArrayList<Person> allFamilyTree;
    private String userName;
    private EventGenerator eventG;
    private Random randomizer = new Random();
    private ArrayList<Event> eventsList = new ArrayList<Event>();

    //----FUNCTIONS TO GENERATE GENERATIONS-----//
    public DataGeneration generateAllTheGenerations(Person person, int numOfGenerations) {
        userName = person.getAssociatedUsername();
        eventG = new EventGenerator(userName);
        nuevoArbolFamiliar(person, numOfGenerations);
        return new DataGeneration(allFamilyTree, eventG.getEventArrayList());
    }

    public void nuevoArbolFamiliar(Person personaRaiz, int generaciones) {
        allFamilyTree = new ArrayList<>();
        allFamilyTree.add(personaRaiz);
        int currentYear = 2000;
        eventG.birthEvent(personaRaiz, currentYear);
        generateParents(personaRaiz, generaciones - 1, currentYear);
    }

    //-----FUNCTION TO GENERATE LIFE EVENTS FOR MOTHERS AND FATHERS-----//
    private void generateLifeEvents(Person mama, Person papa, int currentYear) {
        eventG.birthEvent(mama, currentYear);
        eventG.birthEvent(papa, currentYear);
        eventG.marriageEvent(papa, mama, currentYear);
        eventG.deathEvent(papa, currentYear);
        eventG.deathEvent(mama, currentYear);

        int randomLifeEvent = randomizer.nextInt(4);

        if (currentYear < 1920){
            eventG.deathEvent(papa, currentYear);
            eventG.deathEvent(mama, currentYear);
        }
        else if (randomLifeEvent == 0){
            eventG.makeRandomEvent(mama, currentYear);
        }
        else if (randomLifeEvent == 1){
            eventG.makeRandomEvent(papa, currentYear);
        }
        else if (randomLifeEvent == 2){
            eventG.makeRandomEvent(papa, currentYear);
        }
        else {
            eventG.makeRandomEvent(mama, currentYear);
        }
    }

    //-----FUNCTIONS TO GENERATE FATHER AND MOTHER-----//

    private void generateParents(Person person, int generation, int currentYear) {
        int generationalGap = 30;
        currentYear = currentYear - generationalGap - randomizer.nextInt(10);

        Person mother = new Person();
        Person father = makePapa(person, mother.getPersonID());
        mother = makeMama(mother, father);

        person.setFatherID(father.getPersonID());
        person.setMotherID(mother.getPersonID());

        generateLifeEvents(mother, father, currentYear);

        allFamilyTree.add(mother);
        allFamilyTree.add(father);

        if (generation != 0) {
            generateParents(mother, generation - 1, currentYear);
            generateParents(father, generation - 1, currentYear);
        }
    }

    private Person makePapa(Person kid, String spouse) {
        Person papa = new Person();
        papa.setFirstName(generateMaleName());
        papa.setGender("m");
        papa.setSpouseID(spouse);
        papa.setAssociatedUsername(userName);

        if (kid.getGender().equals("m")) {
            papa.setLastName(kid.getLastName());
        }
        else {
            papa.setLastName(generateLastName());
        }

        return papa;
    }

    private Person makeMama(Person mama, Person esposo) {
        mama.setFirstName(generateFemaleName());
        mama.setLastName(esposo.getLastName());
        mama.setGender("f");
        mama.setSpouseID(esposo.getPersonID());
        mama.setAssociatedUsername(userName);

        return mama;
    }

    //-----NAME GENERATION-----//

    public String generateMaleName() {
        try {
            FileReader fileReader = new FileReader(new File("json/mnames.json"));
            JsonObject objetoRaiz = JsonParser.parseReader(fileReader).getAsJsonObject();
            JsonArray maleNamesArray = objetoRaiz.getAsJsonArray("data");

            int index = randomizer.nextInt(maleNamesArray.size());
            String maleName = maleNamesArray.get(index).toString();
            maleName = maleName.substring(1, maleName.length() - 1);
            return maleName;

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return "Error";
    }

    public String generateFemaleName() {
        try {
            FileReader fileReader = new FileReader(new File("json/fnames.json"));
            JsonObject objetoRaiz = JsonParser.parseReader(fileReader).getAsJsonObject();
            JsonArray femaleNamesArray = objetoRaiz.getAsJsonArray("data");

            int index = randomizer.nextInt(femaleNamesArray.size());
            String femaleName = femaleNamesArray.get(index).toString();
            femaleName = femaleName.substring(1, femaleName.length() - 1);
            return femaleName;

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return "Error";
    }

    public String generateLastName() {
        try {
            FileReader fileReader = new FileReader(new File("json/snames.json"));
            JsonObject objetoRaiz = JsonParser.parseReader(fileReader).getAsJsonObject();
            JsonArray lastNamesArray = objetoRaiz.getAsJsonArray("data");

            int index = randomizer.nextInt(lastNamesArray.size());
            String lastName = lastNamesArray.get(index).toString();
            lastName = lastName.substring(1, lastName.length() - 1);
            return lastName;

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return "Error";
    }


}