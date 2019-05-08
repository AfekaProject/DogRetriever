package dtg.dogretriever.View;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import de.hdodenhof.circleimageview.CircleImageView;
import dtg.dogretriever.Model.Dog;
import dtg.dogretriever.Presenter.DogScanListFunctionalityInterface;
import dtg.dogretriever.Presenter.MainActivity;
import dtg.dogretriever.R;

public class DogScanListAdapter extends  RecyclerView.Adapter<DogScanListAdapter.ViewHolder>{
    private ArrayList<Dog> mDogList = new ArrayList<>();
    private Context mContext;
    private DogScanListFunctionalityInterface methodCaller;

    public DogScanListAdapter(ArrayList<Dog> mDogList, Context mContext, DogScanListFunctionalityInterface methodCaller) {
        this.mDogList = mDogList;
        this.mContext = mContext;
        this.methodCaller = methodCaller;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.scan_list_item,parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        //TODO add image implantation

        holder.nameTextView.setText("Name:"+mDogList.get(position).getName());
        holder.colorTextView.setText("Color: "+mDogList.get(position).getColor());
        holder.breedTextView.setText("Breed: "+mDogList.get(position).getBreed());

        holder.parentLayout.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
               // Toast.makeText(mContext, "OwnerId:"+mDogList.get(position).getOwnerId(), Toast.LENGTH_SHORT).show();
                methodCaller.showOwnerInformation(mDogList.get(position).getOwnerId());
                methodCaller.scanDog(mDogList.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDogList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        CircleImageView imageView;
        TextView nameTextView;
        TextView colorTextView;
        TextView breedTextView;
        RelativeLayout parentLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.scan_list_item_image);
            nameTextView = itemView.findViewById(R.id.scan_list_item_name);
            colorTextView = itemView.findViewById(R.id.scan_list_item_color);
            breedTextView = itemView.findViewById(R.id.scan_list_item_breed);
            parentLayout = itemView.findViewById(R.id.scan_list_item_parent_layout);

        }
    }
}