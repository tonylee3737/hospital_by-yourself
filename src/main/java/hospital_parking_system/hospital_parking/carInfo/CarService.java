package hospital_parking_system.hospital_parking.carInfo;

import java.util.List;

public interface CarService {
    List<CarBean> selectCarInfo(CarBean bean);

    List<DiscountedCarInfo> selectDiscountedCarInfo(CarBean bean);

    List<DiscountedCarInfo> selectDiscountedCarInfoList();

    ControllDiscountCar selectControllDiscountCar(CarBean bean);

    void insertDiscountCarTime(ControllDiscountCar discountCar);

    ControllDiscountCar selectDiscountCarTime(ControllDiscountCar discountCar);

    void updateDiscountCarTime(ControllDiscountCar discountCar);
}
