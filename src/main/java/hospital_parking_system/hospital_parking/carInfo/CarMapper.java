package hospital_parking_system.hospital_parking.carInfo;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface CarMapper {
    List<CarBean> selectCarInfo(CarBean bean);

    List<DiscountedCarInfo> selectDiscountedCarInfoList();

    List<DiscountedCarInfo> selectDiscountedCarInfo(CarBean bean);

    ControllDiscountCar selectControllDiscountCar(CarBean bean);

    void insertDiscountCarTime(ControllDiscountCar discountCar);

    ControllDiscountCar selectDiscountCarTime(ControllDiscountCar discountCar);

    void updateDiscountCarTime(ControllDiscountCar discountCar);

    ControllDiscountCar Procedure_DiscountCarTime(ControllDiscountCar discountCar);

    List<DiscountedCarInfo> selectDiscountedCarInfoListWithDate(CarBean bean);

    List<DiscountedCarInfo> selectDiscountedCarInfoListWithDate_Member(CarBean bean);

    List<ClNameBean> selectClNameFromClidx();
}
