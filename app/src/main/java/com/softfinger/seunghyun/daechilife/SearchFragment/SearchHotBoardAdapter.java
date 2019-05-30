package com.softfinger.seunghyun.daechilife.SearchFragment;

import android.content.Context;
import android.graphics.Color;
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
import com.softfinger.seunghyun.daechilife.HomeFragment.DLHomeChartAdapter;
import com.softfinger.seunghyun.daechilife.R;

import java.util.List;

public class SearchHotBoardAdapter extends RecyclerView.Adapter<SearchHotBoardAdapter.TeacherViewHolder> { //데이터와 아이템에 대한 뷰를 생성하는 역할
    //implements Filterable
    static List<String> teachers_real;
    private List<String> teachers_list;
    Context context;
    static int pos1 = 0;

    SearchHotBoardAdapter(List<String> teachers, Context context){
        this.teachers_real = teachers; //get DB
        this.context = context;
        this.teachers_list = teachers;
    }

    public interface OnItemClickListener { //아이템 클릭
        void onItemClick(String item);
    }

    public class TeacherViewHolder extends RecyclerView.ViewHolder{ //모든 서브 뷰를 보유한다.

        TextView searchkeyword, number, up;
        ImageView updown;
        RelativeLayout elementlayout;

        TeacherViewHolder(View itemView){ //전개할 item 정의

            super(itemView);
            searchkeyword = itemView.findViewById(R.id.sfkeyword);
            number = itemView.findViewById(R.id.sfnumber);
            up = itemView.findViewById(R.id.sfupnum);
            updown = itemView.findViewById(R.id.sfupdown);
            elementlayout = itemView.findViewById(R.id.searchfamouselementlayout);

        }

        public void bind(final String item, final SearchHotBoardAdapter.OnItemClickListener listener) { ;
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    listener.onItemClick(item);
                }
            });
        }
    }

    @Override
    public int getItemCount() { //number of items presented in data
        if(teachers_list == null){
            return 0;
        };
        return teachers_list.size();
    }

    @Override
    public SearchHotBoardAdapter.TeacherViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) { //time when viewholder needs to be initialized
        context = viewGroup.getContext();
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.searchfamouselement, viewGroup, false); //layoutInfalter
        SearchHotBoardAdapter.TeacherViewHolder lvh = new SearchHotBoardAdapter.TeacherViewHolder(v);
        return lvh;
    }

    //need to change code here
    @Override
    public void onBindViewHolder(@NonNull SearchHotBoardAdapter.TeacherViewHolder teacherViewHolder, final int position) { //specify contents of each item

        final String element = teachers_list.get(position);
        final TextView keyword = teacherViewHolder.searchkeyword;
        final TextView number = teacherViewHolder.number;
        final RelativeLayout layout = teacherViewHolder.elementlayout;
        final TextView up = teacherViewHolder.up;
        final ImageView updown = teacherViewHolder.updown;
        final int pos = position;

        if(pos % 2 == 1){
            updown.setImageResource(R.mipmap.downb);
            up.setText("2");
            up.setTextColor(Color.parseColor("#3399FF"));
        }
        else{
            updown.setImageResource(R.mipmap.up);
            up.setText("1");
            up.setTextColor(Color.parseColor("#CC6000"));
        }
        number.setText(Integer.toString(pos + 1));
        keyword.setText(element);

    }

}
