package dtg.dogretriever.View;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import dtg.dogretriever.Model.Dog;
import dtg.dogretriever.Model.FirebaseAdapter;
import dtg.dogretriever.R;

public class DogsListAdapter extends BaseAdapter {
    private ArrayList<Dog> dogsList;
    private ImageView deleteDog;
    private Context context;
    private LayoutInflater inflater;
    private FirebaseAdapter firebaseAdapter;

    public DogsListAdapter(ArrayList<Dog> dogs, Context context){
        this.dogsList = dogs;
        this.context = context;
        firebaseAdapter = FirebaseAdapter.getInstanceOfFireBaseAdapter();
    }

    @Override
    public int getCount() {
        return dogsList.size();
    }


    @Override
    public Object getItem(int i) {
        return dogsList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        inflater = (LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );

        if (view == null) {
            view = inflater.inflate(R.layout.profile_dog_layout,
                    viewGroup, false);
        }
        TextView textViewDogName = view.findViewById(R.id.profile_dog_List_Name);
        deleteDog = view.findViewById(R.id.profile_delete_dog);
        CircleImageView dogImg = view.findViewById(R.id.profile_dog_img);

        textViewDogName.setText(dogsList.get(i).getName());
        Picasso.get()
                .load(dogsList.get(i).getmImageUrl())
                .placeholder(R.drawable.asset21h)
                .fit()
                .error(R.drawable.asset21h)
                .into(dogImg);

        deleteDog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseAdapter.removeDogFromDataBase(dogsList.get(i));
                dogsList.remove(i);
                notifyDataSetChanged();
            }
        });
        return view;
    }
}
