package com.softfinger.seunghyun.daechilife.TimeTableFragment;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.softfinger.seunghyun.daechilife.DataModel.LectureClass;
import com.softfinger.seunghyun.daechilife.R;

import java.util.List;

public class AddClassTimeTableAdapter extends RecyclerView.Adapter<AddClassTimeTableAdapter.LectureViewHolder> { //데이터와 아이템에 대한 뷰를 생성하는 역할
    //implements Filterable
    static List<LectureClass> lecture_real;
    private List<LectureClass> lecutre_list;
    Context context;
    static int pos1 = 0;

    AddClassTimeTableAdapter(List<LectureClass> lecture, Context context){
        this.lecture_real = lecture; //get DB
        this.context = context;
        this.lecutre_list = lecture;
    }

    public interface OnItemClickListener { //아이템 클릭
        void onItemClick(String item);
    }

    public class LectureViewHolder extends RecyclerView.ViewHolder{ //모든 서브 뷰를 보유한다.

        TextView lecturenameTV, timeTV, academyTV, infoTV;
        RelativeLayout lecturelayout;
        LinearLayout delete;
        ImageView selectdelete;

        LectureViewHolder(View itemView){ //전개할 item 정의
            super(itemView);
            lecturenameTV = itemView.findViewById(R.id.addclasstimetablenameTV);
            timeTV = itemView.findViewById(R.id.addclasstimetabletimeTV);
            academyTV = itemView.findViewById(R.id.addclasstimetableacademyTV);
            infoTV = itemView.findViewById(R.id.addclasstimetableinfoTV);
            delete = itemView.findViewById(R.id.selectdeletetimetablelayout);
            lecturelayout = itemView.findViewById(R.id.addclasstimeelementlayout);
            selectdelete = itemView.findViewById(R.id.deleteselectedaddtimetable);
        }

        public void bind(final String item, final TimeCellAdapter.OnItemClickListener listener) { ;
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    listener.onItemClick(item);
                }
            });
        }
    }

    @Override
    public int getItemCount() { //number of items presented in data
        if(lecutre_list == null){
            return 0;
        };
        return lecutre_list.size();
    }

    @Override
    public AddClassTimeTableAdapter.LectureViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) { //time when viewholder needs to be initialized
        context = viewGroup.getContext();
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.addclasstimetableelement, viewGroup, false); //layoutInfalter
        AddClassTimeTableAdapter.LectureViewHolder lvh = new AddClassTimeTableAdapter.LectureViewHolder(v);
        return lvh;
    }

    //need to change code here
    @Override
    public void onBindViewHolder(@NonNull AddClassTimeTableAdapter.LectureViewHolder lectureViewHolder, final int position) { //specify contents of each item

        final LectureClass lectureClass = lecutre_list.get(position);
        final RelativeLayout lecturelayout = lectureViewHolder.lecturelayout;
        final TextView nameTV = lectureViewHolder.lecturenameTV;
        final TextView academyTV = lectureViewHolder.academyTV;
        final TextView timeTV = lectureViewHolder.timeTV;
        final TextView infoTV = lectureViewHolder.infoTV;
        final LinearLayout delete = lectureViewHolder.delete;
        final ImageView selectdelete = lectureViewHolder.selectdelete;
        final int pos = position;

        nameTV.setText(lectureClass.getLecturename());
        academyTV.setText(lectureClass.getAcademyname());
        timeTV.setText(lectureClass.getTime());
        infoTV.setText(lectureClass.getSubject()+", "+lectureClass.getAge()+" 대상");

        //편집을 누른 상태에 표시하는 레이아웃
        if(AddClassActivity.getDeletego() == 1){

            LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(
                    0,
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    0.12f
            );

            delete.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){

                    if(lectureClass.getADD_OnMAINTABLELIST_DELETE() == 0){
                        lecturelayout.setBackgroundColor(Color.parseColor("#958d6e63"));
                        lectureClass.setADD_OnMAINTABLELIST_DELETE(1);
                        selectdelete.setBackgroundResource(R.mipmap.check);
                    }
                    else{
                        lecturelayout.setBackgroundColor(Color.parseColor("#55000000"));
                        lectureClass.setADD_OnMAINTABLELIST_DELETE(0);
                        selectdelete.setBackgroundResource(R.mipmap.circle);
                    }

                }
            });

            delete.setLayoutParams(param);

        }

        //편집을 누른 상태가 아니라면.
        else{

            lecturelayout.setBackgroundColor(Color.parseColor("#55000000"));

            LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(
                    0,
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    0f
            );
            selectdelete.setBackgroundResource(R.mipmap.circle);

            delete.setLayoutParams(param);

            delete.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){


                }
            });
        }


    }

}