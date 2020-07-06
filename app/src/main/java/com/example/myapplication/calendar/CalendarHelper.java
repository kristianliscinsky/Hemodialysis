package com.example.myapplication.calendar;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import androidx.preference.PreferenceManager;

import java.util.Calendar;

public class CalendarHelper {
    private Context context;
    private final int SECONDS = 0;
    private Calendar final_previous_HD = Calendar.getInstance();
    private Calendar final_next_HD = Calendar.getInstance();

    public CalendarHelper(Context context) {
        this.context = context;
    }

    public Calendar getFinal_previous_HD() {
        return final_previous_HD;
    }

    public Calendar getFinal_next_HD() {
        return final_next_HD;
    }

    /**
     * Shift calendar to previous week + 1
     */
    private void resetCalendarPrev(Calendar cal) {
        cal.add(Calendar.DATE, -8);
    }

    /**
     *  Shift calendar to next week + 1
     */
    private void resetCalendarNext(Calendar cal) {
        cal.add(Calendar.DATE, 8);
    }

    private void setPrevAndNextDate(DialysisDate dialysisDate) {
        Calendar actDate = Calendar.getInstance();
        Calendar previous_HD = Calendar.getInstance();
        Calendar next_HD = Calendar.getInstance();

        previous_HD.set(Calendar.HOUR_OF_DAY, dialysisDate.getHour());
        previous_HD.set(Calendar.MINUTE, dialysisDate.getMinute());
        previous_HD.set(Calendar.SECOND, dialysisDate.getSecond());

        next_HD.set(Calendar.HOUR_OF_DAY, dialysisDate.getHour());
        next_HD.set(Calendar.MINUTE, dialysisDate.getMinute());
        next_HD.set(Calendar.SECOND, dialysisDate.getSecond());

        if(actDate.get(Calendar.DAY_OF_WEEK) > dialysisDate.getDayOfWeek()) {
            int dayDiff = dialysisDate.getDayOfWeek() - actDate.get(Calendar.DAY_OF_WEEK);
            previous_HD.add(Calendar.DATE, dayDiff);
            next_HD.add(Calendar.DATE, dayDiff + 7);

            if (!previous_HD.before(final_previous_HD)) {
                final_previous_HD.setTime(previous_HD.getTime());
            }

            if (next_HD.before(final_next_HD)) {
                final_next_HD.setTime(next_HD.getTime());
            }
        } else if (actDate.get(Calendar.DAY_OF_WEEK) < dialysisDate.getDayOfWeek()) {
            int dayDiff = dialysisDate.getDayOfWeek() - actDate.get(Calendar.DAY_OF_WEEK);
            previous_HD.add(Calendar.DATE, dayDiff - 7);
            next_HD.add(Calendar.DATE, dayDiff);

            if (!previous_HD.before(final_previous_HD)) {
                final_previous_HD.setTime(previous_HD.getTime());
            }

            if (next_HD.before(final_next_HD)) {
                final_next_HD.setTime(next_HD.getTime());
            }
        } else if (actDate.get(Calendar.DAY_OF_WEEK) == dialysisDate.getDayOfWeek()) {
            Calendar hemodialysis = Calendar.getInstance();
            hemodialysis.set(Calendar.DATE, actDate.get(Calendar.DATE));
            hemodialysis.set(Calendar.HOUR_OF_DAY, dialysisDate.getHour());
            hemodialysis.set(Calendar.MINUTE, dialysisDate.getMinute());
            hemodialysis.set(Calendar.SECOND, dialysisDate.getSecond());

            if (hemodialysis.after(actDate)) {
                //next is already set
                previous_HD.add(Calendar.DATE, -7);
            } else {
                next_HD.add(Calendar.DATE, 7);
            }

            if (!previous_HD.before(final_previous_HD)) {
                final_previous_HD.setTime(previous_HD.getTime());
            }

            if (next_HD.before(final_next_HD)) {
                final_next_HD.setTime(next_HD.getTime());
            }
        }
    }

    public void findPrevAndNextHD() {
        resetCalendarPrev(final_previous_HD);
        resetCalendarNext(final_next_HD);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this.context);
        String numberOfHDPerWeek = sharedPreferences.getString("number_of_hemodialysis_perWeek", "1");
        int hdPerWeek = Integer.parseInt(numberOfHDPerWeek);

        if (hdPerWeek == 1) {
            //default value is Monday 00:00
            int dayOfWeek1 = Integer.parseInt(sharedPreferences.getString("hemodialysis1_day", "2"));
            //int time1 = Integer.parseInt(sharedPreferences.getString("hemodialysis1_time", "1"));
            String f = sharedPreferences.getString("hemodialysis1_time", "1");
            Log.e("ddddd", f);
            int time1 = Integer.parseInt(f);

            ParseHDTime parseHDTime1 = new ParseHDTime(time1);
            Log.e("dayofWeek: ", String.valueOf(dayOfWeek1) + " " + parseHDTime1.getHour() + ": " + parseHDTime1.minute);
            DialysisDate dialysisDate1 = new DialysisDate(dayOfWeek1, parseHDTime1.getHour(), parseHDTime1.getMinute(), SECONDS);
            setPrevAndNextDate(dialysisDate1);

        } else if (hdPerWeek == 2) {
            //default value is Monday 00:00
            int dayOfWeek1 = Integer.parseInt(sharedPreferences.getString("hemodialysis1_day", "2"));
            int time1 = Integer.parseInt(sharedPreferences.getString("hemodialysis1_time", "1"));

            ParseHDTime parseHDTime1 = new ParseHDTime(time1);
            DialysisDate dialysisDate1 = new DialysisDate(dayOfWeek1, parseHDTime1.getHour(), parseHDTime1.getMinute(), SECONDS);
            setPrevAndNextDate(dialysisDate1);

            //default value is Wednesday 00:00
            int dayOfWeek2 = Integer.parseInt(sharedPreferences.getString("hemodialysis2_day", "4"));
            int time2 = Integer.parseInt(sharedPreferences.getString("hemodialysis2_time", "1"));

            ParseHDTime parseHDTime2 = new ParseHDTime(time2);
            DialysisDate dialysisDate2 = new DialysisDate(dayOfWeek2, parseHDTime2.getHour(), parseHDTime2.getMinute(), SECONDS);
            setPrevAndNextDate(dialysisDate2);

        } else if (hdPerWeek == 3) {
            //default value is Monday 00:00
            int dayOfWeek1 = Integer.parseInt(sharedPreferences.getString("hemodialysis1_day", "2"));
            int time1 = Integer.parseInt(sharedPreferences.getString("hemodialysis1_time", "1"));

            ParseHDTime parseHDTime1 = new ParseHDTime(time1);
            DialysisDate dialysisDate1 = new DialysisDate(dayOfWeek1, parseHDTime1.getHour(), parseHDTime1.getMinute(), SECONDS);
            setPrevAndNextDate(dialysisDate1);

            //default value is Wednesday 00:00
            int dayOfWeek2 = Integer.parseInt(sharedPreferences.getString("hemodialysis2_day", "4"));
            int time2 = Integer.parseInt(sharedPreferences.getString("hemodialysis2_time", "1"));

            ParseHDTime parseHDTime2 = new ParseHDTime(time2);
            DialysisDate dialysisDate2 = new DialysisDate(dayOfWeek2, parseHDTime2.getHour(), parseHDTime2.getMinute(), SECONDS);
            setPrevAndNextDate(dialysisDate2);

            //default value is Friday 00:00
            int dayOfWeek3 = Integer.parseInt(sharedPreferences.getString("hemodialysis3_day", "6"));
            int time3 = Integer.parseInt(sharedPreferences.getString("hemodialysis3_time", "1"));

            ParseHDTime parseHDTime3 = new ParseHDTime(time3);
            DialysisDate dialysisDate3 = new DialysisDate(dayOfWeek3, parseHDTime3.getHour(), parseHDTime3.getMinute(), SECONDS);
            setPrevAndNextDate(dialysisDate3);
        }
        Toast.makeText(this.context,"numberOfHDperWeek: " + numberOfHDPerWeek, Toast.LENGTH_LONG).show();
        Log.e("prev: ", getFinal_previous_HD().getTime().toString());
        Log.e("next: ", getFinal_next_HD().getTime().toString());
    }

    private class ParseHDTime {
        private int hour;
        private int minute;

        private ParseHDTime(int parse) {
            parseTime(parse);
        }

        private int getHour() {
            return hour;
        }

        private int getMinute() {
            return minute;
        }

        private void parseTime(int parse) {
            Log.e("parse: ", String.valueOf(parse));
            switch (parse) {
                case 1:
                    this.hour = 0;
                    this.minute = 0;
                    break;
                case 2:
                    this.hour = 0;
                    this.minute = 30;
                    break;
                case 3:
                    this.hour = 1;
                    this.minute = 0;
                    break;
                case 4:
                    this.hour = 1;
                    this.minute = 30;
                    break;
                case 5:
                    this.hour = 2;
                    this.minute = 0;
                    break;
                case 6:
                    this.hour = 2;
                    this.minute = 30;
                    break;
                case 7:
                    this.hour = 3;
                    this.minute = 0;
                    break;
                case 8:
                    this.hour = 3;
                    this.minute = 30;
                    break;
                case 9:
                    this.hour = 4;
                    this.minute = 0;
                    break;
                case 10:
                    this.hour = 4;
                    this.minute = 30;
                    break;
                case 11:
                    this.hour = 5;
                    this.minute = 0;
                    break;
                case 12:
                    this.hour = 5;
                    this.minute = 30;
                    break;
                case 13:
                    this.hour = 6;
                    this.minute = 0;
                    break;
                case 14:
                    this.hour = 6;
                    this.minute = 30;
                    break;
                case 15:
                    this.hour = 7;
                    this.minute = 0;
                    break;
                case 16:
                    this.hour = 7;
                    this.minute = 30;
                    break;
                case 17:
                    this.hour = 8;
                    this.minute = 0;
                    break;
                case 18:
                    this.hour = 8;
                    this.minute = 30;
                    break;
                case 19:
                    this.hour = 9;
                    this.minute = 0;
                    break;
                case 20:
                    this.hour = 9;
                    this.minute = 30;
                    break;
                case 21:
                    this.hour = 10;
                    this.minute = 0;
                    break;
                case 22:
                    this.hour = 10;
                    this.minute = 30;
                    break;
                case 23:
                    this.hour = 11;
                    this.minute = 0;
                    break;
                case 24:
                    this.hour = 11;
                    this.minute = 30;
                    break;
                case 25:
                    this.hour = 12;
                    this.minute = 0;
                    break;
                case 26:
                    this.hour = 12;
                    this.minute = 30;
                    break;
                case 27:
                    this.hour = 13;
                    this.minute = 0;
                    break;
                case 28:
                    this.hour = 13;
                    this.minute = 30;
                    break;
                case 29:
                    this.hour = 14;
                    this.minute = 0;
                    break;
                case 30:
                    this.hour = 14;
                    this.minute = 30;
                    break;
                case 31:
                    this.hour = 15;
                    this.minute = 0;
                    break;
                case 32:
                    this.hour = 15;
                    this.minute = 30;
                    break;
                case 33:
                    this.hour = 16;
                    this.minute = 0;
                    break;
                case 34:
                    this.hour = 16;
                    this.minute = 30;
                    break;
                case 35:
                    this.hour = 17;
                    this.minute = 0;
                    break;
                case 36:
                    this.hour = 17;
                    this.minute = 30;
                    break;
                case 37:
                    this.hour = 18;
                    this.minute = 0;
                    break;
                case 38:
                    this.hour = 18;
                    this.minute = 30;
                    break;
                case 39:
                    this.hour = 19;
                    this.minute = 0;
                    break;
                case 40:
                    this.hour = 19;
                    this.minute = 30;
                    break;
                case 41:
                    this.hour = 20;
                    this.minute = 0;
                    break;
                case 42:
                    this.hour = 20;
                    this.minute = 30;
                    break;
                case 43:
                    this.hour = 21;
                    this.minute = 0;
                    break;
                case 44:
                    this.hour = 21;
                    this.minute = 30;
                    break;
                case 45:
                    this.hour = 22;
                    this.minute = 0;
                    break;
                case 46:
                    this.hour = 22;
                    this.minute = 30;
                    break;
                case 47:
                    this.hour = 23;
                    this.minute = 0;
                    break;
                case 48:
                    this.hour = 23;
                    this.minute = 30;
                    break;
            }
        }
    }
}