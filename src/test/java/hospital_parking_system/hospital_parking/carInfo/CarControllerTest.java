package hospital_parking_system.hospital_parking.carInfo;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
//차량번호를 통해 히스토리 테이블에 있는 데이터를 가져온다, 그 테이블에 vhidx를 이용해서 discount table을 조회한다.그리고 조회한 내용을 가져온다.

//SELECT * FROM parking_class_discount AS a INNER JOIN parking_vehicle_history AS b ON b.VHidx = a.Vhlidx  WHERE CarNumber="10다5491"

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class CarControllerTest {
    @Autowired
    CarService carService;

    @Test
    public void 차량번호테스트() {

        //given
        CarBean car = new CarBean();
        car.setVhlNbr("5491");

        //when
        List<CarBean> carBeans = carService.selectCarInfo(car);
        //then
        Assertions.assertThat(carBeans.get(0).getVhlNbr()).isEqualTo("10다5491");
    }

     @Test
      public void 히스토리차량넘버idx로조회() {
      //given
         CarBean car = new CarBean();
         car.setVhlNbr("1234");
         List<DiscountedCarInfo> discountedCarInfos = carService.selectDiscountedCarInfo(car);
         //then
         Assertions.assertThat(discountedCarInfos.get(0).getDCTime()).isEqualTo("60");
    }
}