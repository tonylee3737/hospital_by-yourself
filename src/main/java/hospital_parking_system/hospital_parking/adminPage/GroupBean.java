package hospital_parking_system.hospital_parking.adminPage;

import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

@Getter @Setter
public class GroupBean {
    private String GrpiDx;

    private String GrpName;
    private String GrpMemo;
}
