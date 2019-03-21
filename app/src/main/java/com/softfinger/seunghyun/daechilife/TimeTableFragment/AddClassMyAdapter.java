package com.softfinger.seunghyun.daechilife.TimeTableFragment;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
import com.softfinger.seunghyun.daechilife.Dialog.AddClassAlreadyInDialog;
import com.softfinger.seunghyun.daechilife.FirstActivity;
import com.softfinger.seunghyun.daechilife.R;

import java.util.List;

public class AddClassMyAdapter extends RecyclerView.Adapter<AddClassMyAdapter.LectureViewHolder> { //데이터와 아이템에 대한 뷰를 생성하는 역할

    //implements Filterable
    static List<LectureClass> lecture_real;
    private List<LectureClass> lecutre_list;
    Context context;
    static int pos1 = 0;

    AddClassMyAdapter(List<LectureClass> lecture, Context context){
        this.lecture_real = lecture; //get DB
        this.context = context;
        this.lecutre_list = lecture;
    }

    public interface OnItemClickListener { //아이템 클릭
        void onItemClick(String item);
    }

    public class LectureViewHolder extends RecyclerView.ViewHolder{ //모든 서브 뷰를 보유한다.

        TextView lecturenameTV, timeTV, academyTV, addTV, infoTV;
        RelativeLayout lecturelayout;
        LinearLayout deletelayout;
        ImageView deleteselect;

        LectureViewHolder(View itemView){ //전개할 item 정의
            super(itemView);
            lecturenameTV = itemView.findViewById(R.id.addclassmynameTV);
            timeTV = itemView.findViewById(R.id.addclassmytimeTV);
            academyTV = itemView.findViewById(R.id.addclassmyacademyTV);
            infoTV = itemView.findViewById(R.id.addclassmyinfoTV);
            addTV = itemView.findViewById(R.id.addclassmyTV);
            deletelayout = itemView.findViewById(R.id.selectdeletemylayout);
            deleteselect = itemView.findViewById(R.id.deleteselectedaddmy);
            lecturelayout = itemView.findViewById(R.id.addclassmyelementlayout);
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
    public AddClassMyAdapter.LectureViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) { //time when viewholder needs to be initialized
        context = viewGroup.getContext();
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.addclassmyelement, viewGroup, false); //layoutInfalter
        AddClassMyAdapter.LectureViewHolder lvh = new AddClassMyAdapter.LectureViewHolder(v);
        return lvh;
    }

    //untouch -> touchedinmy -> touchedinaddmyadd -> untouch
    @Override
    public void onBindViewHolder(@NonNull AddClassMyAdapter.LectureViewHolder lectureViewHolder, final int position) { //specify contents of each item

        final LectureClass lectureClass = lecutre_list.get(position);
        final RelativeLayout lecturelayout = lectureViewHolder.lecturelayout;
        final TextView nameTV = lectureViewHolder.lecturenameTV;
        final TextView academyTV = lectureViewHolder.academyTV;
        final TextView timeTV = lectureViewHolder.timeTV;
        final TextView infoTV = lectureViewHolder.infoTV;
        final TextView addTV = lectureViewHolder.addTV;
        final LinearLayout deletelayout = lectureViewHolder.deletelayout;
        final ImageView deleteselect = lectureViewHolder.deleteselect;
        final int pos = position;

        /* 항목 데이터 입력 */
        nameTV.setText(lectureClass.getLecturename());
        academyTV.setText(lectureClass.getAcademyname());
        timeTV.setText(lectureClass.getTime());
        infoTV.setText(lectureClass.getSubject()+", "+lectureClass.getAge()+" 대상");


        //편집을 누른 삭제 상태일때 함수처리
        if(AddClassActivity.getDeletego() == 1){

            for (int i = 0; i < lecture_real.size(); i++) {
                //만약 저장되어 있는 강좌라면 그냥 무시하고 넘어감
                if (lecture_real.get(i).getADD_OnMYLIST_TOUCHED() == 1) {
                    lecture_real.get(i).setADD_OnMYLIST_TOUCHED(0);
                }
            }

            lecturelayout.setBackgroundColor(Color.parseColor("#55000000"));

            //layout param
            LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(
                    0,
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    0.12f
            );

            deletelayout.setLayoutParams(param);

            deletelayout.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){

                    if(lectureClass.getADD_OnMYLIST_DELETE() == 0){
                        lecturelayout.setBackgroundColor(Color.parseColor("#958d6e63"));
                        lectureClass.setADD_OnMYLIST_DELETE(1);
                        deleteselect.setBackgroundResource(R.mipmap.check);
                    }
                    else{
                        lecturelayout.setBackgroundColor(Color.parseColor("#55000000"));
                        lectureClass.setADD_OnMYLIST_DELETE(0);
                        deleteselect.setBackgroundResource(R.mipmap.circle);
                    }

                }
            });

            addTV.setText("");

            LinearLayout.LayoutParams param3 = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    0,
                    0f
            );

            addTV.setLayoutParams(param3);

            lecturelayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                }
            });
            //하단에 선택한 것들 취소화 시키는 코드가 요구됨.

        }

        //이 코드들은 편집인 상태에서 처리해야될 함수들
        /* 레이아웃을 누른 경우에 처리해야할 상황들 */
        else {

            //삭제와 관련된 함수 default
            lecturelayout.setBackgroundColor(Color.parseColor("#55000000"));

            LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(
                    0,
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    0f
            );

            deleteselect.setBackgroundResource(R.mipmap.circle);

            deletelayout.setLayoutParams(param);

            deletelayout.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){


                }
            });

            //추가와 관련된 함수들
            lecturelayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    for (int i = 0; i < lecture_real.size(); i++) {
                        //만약 저장되어 있는 강좌라면 그냥 무시하고 넘어감
                        if (lecture_real.get(i).getADD_OnMYLIST_TOUCHED() == 1) {
                            lecture_real.get(i).setADD_OnMYLIST_TOUCHED(0);
                        }
                    }
                    lectureClass.setADD_OnMYLIST_TOUCHED(1);
                    notifyDataSetChanged();
                }
            });

            /* 추가를 누른 경우 처리해야 할 상황들 */
            addTV.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if (event.getAction() == MotionEvent.ACTION_DOWN) {

                        //이미 시간표에 저장되어 있는 강좌라면
                        if (lectureClass.getADD_OnMAINTABLELIST() == 1) {
                            addTV.setBackgroundColor(Color.parseColor("#55000000"));
                            lecturelayout.setBackgroundColor(Color.parseColor("#55000000"));
                            lectureClass.setADD_OnMYLIST_TOUCHED(0);
                            AddClassAlreadyInDialog cdd = new AddClassAlreadyInDialog(AddClassActivity.getActivity());
                            cdd.show();
                            lectureClass.setADD_OnMYLIST_TOUCHED(0); //때는 순간 더이상 레이아웃에 관심을 안가지게끔 함
                        } else if (lectureClass.getADD_OnMYLIST_TOUCHED() == 1) { //레이아웃이 눌려있는 상황이라면

                            TimeTableFragment.deleteLectureTemp(AddClassTimeTableActivity.getTimeTableLayout()); //임시용 테이블 삭제
                            addTV.setBackgroundColor(Color.parseColor("#90000000")); //누른 흔적 색 변화

                            //강의목록에 추가
                            TimeTableFragment.insertLectureToTimeTable(lectureClass, TimeTableFragment.getTimetablelayout());
                            TimeTableFragment.insertLectureToTimeTable(lectureClass, AddClassTimeTableActivity.getTimeTableLayout());
                            TimeTableFragment.insertLectureToDB(lectureClass);
                            lectureClass.setADD_OnMAINTABLELIST(1); //내 시간표 목록에 추가해줘야함.

                        }

                        return true;
                    } else if (event.getAction() == MotionEvent.ACTION_UP) {

                        if (lectureClass.getADD_OnMYLIST_TOUCHED() == 1) { //레이아웃이 눌려진 상황이라면

                            //다시 모든 레이아웃 색들을 제자리로 설정
                            addTV.setBackgroundColor(Color.parseColor("#00000000"));
                            lecturelayout.setBackgroundColor(Color.parseColor("#00000000"));
                            TimeTableFragment.NotifyDataStateChanged(); //내 시간표에 저장된 강좌상태 변경
                            lectureClass.setADD_OnMYLIST_TOUCHED(0); //때는 순간 더이상 레이아웃에 관심을 안가지게끔 함

                        }
                        return true;
                    }
                    return false;
                }
            });

            /* 항목이 한번 눌려진 상황인 경우 */
            //선택된 경우라면 나타나야할 레이아웃은, 색깔 변화 및 임시 시간표 저장 형태가 나타나야 함. 상태를 변화하여 또 다시 생성이 되지 못하도록 함.
            if (lectureClass.getADD_OnMYLIST_TOUCHED() == 1) {

                //추가 및 제거 레이아웃 준비
                lecturelayout.setBackgroundColor(Color.parseColor("#80000000"));
                addTV.setBackgroundColor(Color.parseColor("#00000000"));
                addTV.setText("+ 추가하기");

                //임시 강좌를 보여줌
                TimeTableFragment.deleteLectureTemp(AddClassTimeTableActivity.getTimeTableLayout()); //전의 것을 지우고
                TimeTableFragment.insertLectureTemp(lectureClass, AddClassTimeTableActivity.getTimeTableLayout()); //새로운 것을 추가

                //추가할 수 있도록 준비
                LinearLayout.LayoutParams param2 = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        0,
                        1f
                );

                addTV.setLayoutParams(param2);
            }

            /* 항목이 눌려지지 않은 상화인경우 */
            else if (lectureClass.getADD_OnMYLIST_TOUCHED() == 0) {

                //색깔 및 제거 추가 상황 제거
                lecturelayout.setBackgroundColor(Color.parseColor("#55000000"));
                addTV.setBackgroundColor(Color.parseColor("#55000000"));
                addTV.setText("");

                LinearLayout.LayoutParams param3 = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        0,
                        0f
                );

                addTV.setLayoutParams(param3);

            }
        }

    }

}