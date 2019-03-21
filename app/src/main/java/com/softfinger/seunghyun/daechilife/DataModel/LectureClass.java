package com.softfinger.seunghyun.daechilife.DataModel;

public class LectureClass {

    //int로 저장하는 방법은 좋은 방법이 아님. 관심목록에 있으면서, 내 시간표에 저장될 수 있음.
    //따라서 변수를 6개 선언하여 각 변수의 값을 받아서 눌린 상태인지 아닌지를 확인. 검색결과 목록에 출력됨을 나타내는 변수, 관심목록에 나타남, 관심목록에서 눌림...

    String lecturename, subject, age, time, academyname, teachername;

    //이 정보들은 USERDB에서 가져와서 비교하여 입력해놓아야 할 정보들임
    /* 수업 추가와 관련된 변수 */
    int ADD_OnSearchLIST = 0; //검색결과 목록에 출력되어 있는지 확인하는 변수

    /* 추가 - 미리 설정해놔야 하는 변수들 */
    int ADD_OnMYLIST = 0; //내 관심 목록에 출력되어 있는지 확인하는 변수
    int ADD_OnMYLIST_TOUCHED = 0; //내 관심 목록에서 선택되어 있는지 확인하는 변수
    int ADD_OnMYLIST_DELETE = 0;
    int ADD_OnMAINTABLELIST = 0; //내 시간표에 등재되어 있는지 확인하는 변수
    int ADD_OnMAINTABLELIST_TOUCHED = 0; //내 시간표에 등재되어 눌려있는지 확인하는 변수
    int ADD_OnMAINTABLELIST_DELETE = 0;

    public LectureClass(String teachername, String academyname){
        this.teachername = teachername;
        this.academyname = academyname;
        setDefault();
    }

    public void setDefault(){
        ADD_OnSearchLIST = 0;
        ADD_OnMYLIST = 0;
        ADD_OnMYLIST_TOUCHED = 0;
        ADD_OnMAINTABLELIST = 0;
        ADD_OnMAINTABLELIST_TOUCHED = 0;
    }

    public void setLecturename(String lecturename){
        this.lecturename = lecturename;
    }

    public void setSubject(String subject){
        this.subject = subject;
    }

    public void setAge(String age){
        this.age = age;
    }

    public void setTime(String time){
        this.time = time;
    }

    public void setAcademyname(String academyname){
        this.academyname = academyname;
    }

    public void setTeachername(String teachername) {
        this.teachername = teachername;
    }

    public String getSubject() {
        return subject;
    }

    public String getAge() {
        return age;
    }

    public String getTime() {
        return time;
    }

    public String getAcademyname() {
        return academyname;
    }

    public String getTeachername() {
        return teachername;
    }

    public String getLecturename() {

        return lecturename;
    }

    public int getADD_OnSearchLIST() {
        return ADD_OnSearchLIST;
    }

    public int getADD_OnMYLIST() {
        return ADD_OnMYLIST;
    }

    public int getADD_OnMYLIST_TOUCHED() {
        return ADD_OnMYLIST_TOUCHED;
    }

    public int getADD_OnMAINTABLELIST() {
        return ADD_OnMAINTABLELIST;
    }

    public int getADD_OnMAINTABLELIST_TOUCHED() {
        return ADD_OnMAINTABLELIST_TOUCHED;
    }

    public void setADD_OnSearchLIST(int ADD_OnSearchLIST) {
        this.ADD_OnSearchLIST = ADD_OnSearchLIST;
    }

    public void setADD_OnMYLIST(int ADD_OnMYLIST) {
        this.ADD_OnMYLIST = ADD_OnMYLIST;
    }

    public void setADD_OnMYLIST_TOUCHED(int ADD_OnMYLIST_TOUCHED) {
        this.ADD_OnMYLIST_TOUCHED = ADD_OnMYLIST_TOUCHED;
    }

    public void setADD_OnMAINTABLELIST(int ADD_OnMAINTABLELIST) {
        this.ADD_OnMAINTABLELIST = ADD_OnMAINTABLELIST;
    }

    public void setADD_OnMAINTABLELIST_TOUCHED(int ADD_OnMAINTABLELIST_TOUCHED) {
        this.ADD_OnMAINTABLELIST_TOUCHED = ADD_OnMAINTABLELIST_TOUCHED;
    }

    public int getADD_OnMAINTABLELIST_DELETE() {
        return ADD_OnMAINTABLELIST_DELETE;
    }

    public void setADD_OnMAINTABLELIST_DELETE(int ADD_OnMAINTABLELIST_DELETE) {
        this.ADD_OnMAINTABLELIST_DELETE = ADD_OnMAINTABLELIST_DELETE;
    }


    public int getADD_OnMYLIST_DELETE() {
        return ADD_OnMYLIST_DELETE;
    }

    public void setADD_OnMYLIST_DELETE(int ADD_OnMYLIST_DELETE) {
        this.ADD_OnMYLIST_DELETE = ADD_OnMYLIST_DELETE;
    }
}
