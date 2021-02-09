package hospital_parking_system.hospital_parking.carInfo;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class DiscountedCarInfo {
    private String CarNumber;
    private String DCName;
    private String ClName;
    private String DCTime;
    private String DCRate;
    private String InsDayTime;
    private String ClDCUse;
}