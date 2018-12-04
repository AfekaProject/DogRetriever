package dtg.dogretriever.Presenter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

import dtg.dogretriever.Model.Dog;
import dtg.dogretriever.R;
import dtg.dogretriever.View.DogCardAdapter;

public class ScannerActivity extends AppCompatActivity {

    private ArrayList<Dog> dogArrayList;
    private DogCardAdapter dogCardAdapter;
    private ListView dogList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanner);
        initListToShow();
        dogList = findViewById(R.id.scanner_scannedList);
        dogCardAdapter = new DogCardAdapter(getBaseContext(),dogArrayList);
        dogList.setAdapter(dogCardAdapter);

    }

    private void initListToShow() {
        dogArrayList = new ArrayList<>();
        dogArrayList.add(new Dog(1,"Luka","Pitbull","White","Small","Insane"));
        dogArrayList.add(new Dog(2,"Nala","Labrador","White","Fat","Fat"));
        dogArrayList.add(new Dog(3,"kc","Pincher","Brown","Tiny","Good girl"));
    }
}
