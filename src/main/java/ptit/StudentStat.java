package ptit;

import java.util.ArrayList;

import lombok.Data;

@Data
public class StudentStat {
    private Student student;
    private ArrayList<ServiceStat> ss;
}
