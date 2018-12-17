package dtg.dogretriever;

import java.util.ArrayList;

import dtg.dogretriever.Model.Dog;

public class DebugUtils {

    public static ArrayList<Dog> createDogArrayList (){
        ArrayList<Dog> dogsList = new ArrayList<>();
        dogsList.add(new Dog("000001","Luka","Pitbull","White",Dog.EnumSize.SMALL,"Insane"));
        dogsList.add(new Dog("000002","Nala","Labrador","White",Dog.EnumSize.LARGE,"Fat"));
        dogsList.add(new Dog("3","kc","Pincher","Brown",Dog.EnumSize.TINY,"Good girl"));
        return dogsList;
    }




}
