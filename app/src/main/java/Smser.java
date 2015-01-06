import java.util.Calendar;

/**
 * Created by teddy on 2014/12/31.
 */
public class Smser {
    private Calendar SmsInTime;
    private Calendar SmsOutTime;
    private int DiscountDay;
    private int SmsYear;
    private int SmsDay;
    private int LeaveDay;

    public void Smser()
    {

    }
    public void setSmsInTime(Calendar userSmsInTime){
        SmsInTime = userSmsInTime;
    }
    public Calendar getSmsInTime(){
        return SmsInTime;
    }
    public Calendar getSmsOutTime(){
        SmsOutTime.add(Calendar.DAY_OF_YEAR,SmsYear);
        SmsOutTime.add(Calendar.DAY_OF_YEAR,SmsDay);
        return SmsOutTime;
    }
    public void setSmsLifeDay(int userYear,int userDay){
        if(userYear >= 0 && userDay >= 0)
        {
            SmsYear = userYear;
            SmsDay = userDay;
        }
    }
    public void getLeaveDay(){

    }

}
