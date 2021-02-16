package hospital_parking_system.hospital_parking.carInfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarServiceImpl implements CarService {

    private CarMapper carMapper;

    @Autowired
    CarServiceImpl(CarMapper carMapper) {
        this.carMapper = carMapper;
    }

    @Override
    public List<CarBean> selectCarInfo(CarBean bean) {
        return carMapper.selectCarInfo(bean);
    }


    @Override
    public List<DiscountedCarInfo> selectDiscountedCarInfo(CarBean bean) {
        return carMapper.selectDiscountedCarInfo(bean);
    }

    @Override
    public List<DiscountedCarInfo> selectDiscountedCarInfoList() {
        return carMapper.selectDiscountedCarInfoList();
    }

    @Override
    public ControllDiscountCar selectControllDiscountCar(CarBean bean) {
        return carMapper.selectControllDiscountCar(bean);
    }

    @Override
    public void insertDiscountCarTime(ControllDiscountCar discountCar) {
        carMapper.insertDiscountCarTime(discountCar);
    }

    @Override
    public ControllDiscountCar selectDiscountCarTime(ControllDiscountCar discountCar) {
        return carMapper.selectDiscountCarTime(discountCar);
    }

    @Override
    public void updateDiscountCarTime(ControllDiscountCar discountCar) {
        carMapper.updateDiscountCarTime(discountCar);
    }


}
