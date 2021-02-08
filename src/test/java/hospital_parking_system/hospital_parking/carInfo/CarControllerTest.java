package hospital_parking_system.hospital_parking.carInfo;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.Assert.*;

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

}