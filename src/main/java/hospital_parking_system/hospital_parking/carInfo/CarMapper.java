package hospital_parking_system.hospital_parking.carInfo;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface CarMapper {
    List<CarBean> selectCarInfo(CarBean bean);


    List<DiscountedCarInfo> selectDiscountedCarInfo(CarBean bean);
}
