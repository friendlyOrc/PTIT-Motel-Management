package ptit;

import java.util.ArrayList;

import lombok.Data;

@Data
public class ServiceStat {
    private int month;
    private Service service;
    private ArrayList<StudentService> ss;
    private int count;
    private Float total;
}
