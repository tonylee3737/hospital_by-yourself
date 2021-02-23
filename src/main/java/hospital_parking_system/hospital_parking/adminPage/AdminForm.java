package hospital_parking_system.hospital_parking.adminPage;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter @Setter
public class AdminForm {
    private String GrpiDx;
    @NotEmpty(message = "그룹명은 필수록 입력하셔야 됩니다.")
    private String GrpName;
    private String GrpMemo;
}
