package ptit;

import java.sql.Date;

import lombok.Data;

@Data
public class Guest {
    private String idCard;
    private String name;
    private Date dob;
}
