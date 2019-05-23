package com.softfinger.seunghyun.daechilife.HomeFragment;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.softfinger.seunghyun.daechilife.DataModel.TeacherElement;
import com.softfinger.seunghyun.daechilife.R;

import java.util.List;

public class DLEnterAdapter extends RecyclerView.Adapter<DLEnterAdapter.EnterViewHolder> { //데이터와 아이템에 대한 뷰를 생성하는 역할
    //implements Filterable
    static List<String> schools;
    private List<Integer> image;
    Context context;

    DLEnterAdapter(List<String> school, List<Integer> image, Context context){
        this.schools = school; //get DB
        this.context = context;
        this.image = image;
    }

    public interface OnItemClickListener { //아이템 클릭
        void onItemClick(String item);
    }

    public class EnterViewHolder extends RecyclerView.ViewHolder{ //모든 서브 뷰를 보유한다.
        TextView schoolname;
        LinearLayout schoolimage;

        EnterViewHolder(View itemView){ //전개할 item 정의

            super(itemView);
            schoolimage = itemView.findViewById(R.id.enterschoolimage);
            schoolname = itemView.findViewById(R.id.enterschoolname);

        }

        public void bind(final String item, final DLEnterAdapter.OnItemClickListener listener) { ;
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    listener.onItemClick(item);
                }
            });
        }
    }

    @Override
    public int getItemCount() { //number of items presented in data
        if(schools == null){
            return 0;
        };
        return schools.size();
    }

    @Override
    public DLEnterAdapter.EnterViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) { //time when viewholder needs to be initialized
        context = viewGroup.getContext();
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.daehakelement, viewGroup, false); //layoutInfalter
        DLEnterAdapter.EnterViewHolder lvh = new DLEnterAdapter.EnterViewHolder(v);
        return lvh;
    }

    //need to change code here
    @Override
    public void onBindViewHolder(@NonNull DLEnterAdapter.EnterViewHolder enterViewHolder, final int position) { //specify contents of each item

        final String schoolname = schools.get(position);
        final Integer schoolimage = image.get(position);
        final TextView name = enterViewHolder.schoolname;
        final LinearLayout image = enterViewHolder.schoolimage;
        final int pos = position;

        name.setText("2019년 " + schoolname + " 입학전형");
        image.setBackgroundResource(schoolimage);


    }

}