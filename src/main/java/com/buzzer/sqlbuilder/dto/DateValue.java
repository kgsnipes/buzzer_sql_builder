package com.buzzer.sqlbuilder.dto;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateValue extends BuzzerBean {

    private Date date;
    private SimpleDateFormat format;

    public DateValue(Date date, SimpleDateFormat format) {
        this.date = date;
        this.format = format;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public SimpleDateFormat getFormat() {
        return format;
    }

    public void setFormat(SimpleDateFormat format) {
        this.format = format;
    }

    public String getFormattedDate()
    {
        return format.format(this.date);
    }

    public static DateValue create(Date date,SimpleDateFormat format)
    {
        return new DateValue(date,format);
    }
}
