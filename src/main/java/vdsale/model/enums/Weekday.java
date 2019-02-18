package vdsale.model.enums;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

public enum Weekday {

    SUNDAY("Domingo", Calendar.SUNDAY),
    MONDAY("Segunda", Calendar.MONDAY),
    TUESDAY("Terça", Calendar.TUESDAY),
    WEDNESDAY("Quarta", Calendar.WEDNESDAY),
    THURSDAY("Quinta", Calendar.THURSDAY),
    FRIDAY("Sexta", Calendar.FRIDAY),
    SATURDAY("Sábado", Calendar.SATURDAY)

    ;

    private String name;
    private int code;

    Weekday(String name, int code){
        this.name = name;
        this.code = code;
    }

    public static Weekday valueOf(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int weekday = calendar.get(Calendar.DAY_OF_WEEK);
        return Arrays.stream(Weekday.values()).filter(day -> day.getCode() == weekday).findFirst().orElse(null);
    }

    public String getName() {
        return name;
    }

    public int getCode() {
        return code;
    }
}
