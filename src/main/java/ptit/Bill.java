package ptit;

import java.util.ArrayList;

import lombok.Data;

@Data
public class Bill {
    private int month;
    private Student student;
    private ArrayList<StudentService> ss;
    private ArrayList<MonthlyTicket> mt;
    private float total;
}
