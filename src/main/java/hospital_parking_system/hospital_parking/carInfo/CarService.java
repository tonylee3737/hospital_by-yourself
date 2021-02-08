package hospital_parking_system.hospital_parking.carInfo;

import java.util.List;

public interface CarService {
    List<CarBean> selectCarInfo(CarBean bean);
}
