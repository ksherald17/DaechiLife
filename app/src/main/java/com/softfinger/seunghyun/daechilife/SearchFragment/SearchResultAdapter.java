package com.softfinger.seunghyun.daechilife.SearchFragment;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.softfinger.seunghyun.daechilife.DataModel.BoardHotElement;
import com.softfinger.seunghyun.daechilife.DataModel.LectureClass;
import com.softfinger.seunghyun.daechilife.DataModel.TeacherElement;
import com.softfinger.seunghyun.daechilife.HomeFragment.DLBoardHotElementAdapter;
import com.softfinger.seunghyun.daechilife.HomeFragment.DLBoardIkMyungAdapter;
import com.softfinger.seunghyun.daechilife.HomeFragment.DLHomeChartAdapter;
import com.softfinger.seunghyun.daechilife.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

public class SearchResultAdapter extends RecyclerView.Adapter<SearchResultAdapter.LectureViewHolder> {

    static List<TeacherElement> lectures_real;
    private List<TeacherElement> lectures_list;
    Context context;

    SearchResultAdapter(List<TeacherElement> lectures, Context context){
        this.lectures_real = lectures; //get DB
        this.context = context;
        this.lectures_list = lectures;
    }

    public interface OnItemClickListener { //아이템 클릭
        void onItemClick(TeacherElement item);
    }

    public class LectureViewHolder extends RecyclerView.ViewHolder{ //모든 서브 뷰를 보유한다.

        TextView teachername, notice, teacherdescription, teacherwatch, morelecture, review;
        RecyclerView classRV;
        LectureViewHolder(View itemView){ //전개할 item 정의

            super(itemView);
            teachername = itemView.findViewById(R.id.teachersearchresult);
            notice = itemView.findViewById(R.id.teachernotice);
            teacherwatch = itemView.findViewById(R.id.teacherwatch);
            teacherdescription = itemView.findViewById(R.id.teacherdescription);
            morelecture = itemView.findViewById(R.id.seemorelecture);
            review = itemView.findViewById(R.id.seereview);
            classRV = itemView.findViewById(R.id.searchresultclassRV);

        }

        public void bind(final TeacherElement item, final DLHomeChartAdapter.OnItemClickListener listener) { ;
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    listener.onItemClick(item);
                }
            });
        }
    }

    @Override
    public int getItemCount() { //number of items presented in data
        if(lectures_real == null){
            return 0;
        };
        return lectures_real.size();
    }

    @Override
    public SearchResultAdapter.LectureViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) { //time when viewholder needs to be initialized
        context = viewGroup.getContext();
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.searchresult_teacher, viewGroup, false); //layoutInfalter
        SearchResultAdapter.LectureViewHolder lvh = new SearchResultAdapter.LectureViewHolder(v);
        return lvh;
    }

    //need to change code here
    @Override
    public void onBindViewHolder(@NonNull SearchResultAdapter.LectureViewHolder lectureViewHolder, final int position) { //specify contents of each item

        final TeacherElement result = lectures_list.get(position);
        final TextView teachername = lectureViewHolder.teachername;
        final TextView notice = lectureViewHolder.notice;
        final TextView teacherdescription = lectureViewHolder.teacherdescription;
        final TextView teacherwatch = lectureViewHolder.teacherwatch;
        final TextView morelecture = lectureViewHolder.morelecture;
        final TextView seereview = lectureViewHolder.review;
        final RecyclerView classRV = lectureViewHolder.classRV;
        final int pos = position;

        teachername.setText(result.getTeachername() + " 선생님");

        classRV.setHasFixedSize(true); //recyclerView has fixed size
        LinearLayoutManager llm = new LinearLayoutManager(context); //리스트를 생성하는 역할을 한다.
        classRV.setLayoutManager(llm);
        InResultClassAdapter searchResultAdapter = new InResultClassAdapter(result.getLectureClassess(), context);
        classRV.setAdapter(searchResultAdapter);

        notice.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

                if(notice.getText().toString().equals("알림 ON")){
                    notice.setText("알림 해제");
                }

                else {
                    notice.setText("알림 ON");
                }

                notifyDataSetChanged();
            }
        });

        teacherdescription.setText(result.getDescription());
        teacherwatch.setText("조회수 " + Integer.toString(result.getWatch())+ "회");

        morelecture.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){



            }
        });

        seereview.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){



            }
        });


    }

}