package hospital_parking_system.hospital_parking.carInfo;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
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
    @Test
     public void 할인차량리스트조회테스트() {
     //given
        List<DiscountedCarInfo> discountedCarInfos = carService.selectDiscountedCarInfoList();
    //when
        String carNumber = discountedCarInfos.get(0).getCarNumber();
    //then
        Assertions.assertThat(carNumber).isEqualTo("10다1234");

     }
     @Test
      public void 시간계산하기() {
      //given
         CarBean car = new CarBean();
         car.setVhlNbr("1");
         List<CarBean> carBeans = carService.selectCarInfo(car);
         CarBean carBean = carBeans.get(0);
         import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

         public class Main {
             public static void main(String[] args) {
//        현재시간 가져오기
                 String carDate = "201901011010";

                 String year = carDate.substring(0, 4);
                 String month = carDate.substring(4, 6);
                 String day = carDate.substring(6, 8);
                 String hour = carDate.substring(8, 10);
                 String min = carDate.substring(10, 12);

//        입차시간
                 Date date = null;
                 DateFormat df = new SimpleDateFormat("yyyy-MM-dd-HH-mm");
                 Calendar entryTime = Calendar.getInstance();

                 try{
                     date = df.parse(year +"-"+ month +"-"+ day +"-"+ hour +"-"+ min);
                 }catch(ParseException e){
                     e.printStackTrace();
                 }
                 entryTime.setTime(date);

                 Calendar currentTime = Calendar.getInstance();
                 currentTime.setTime(new Date());

                 System.out.println("입차시간:" + df.format(entryTime.getTime()));
                 System.out.println("현재시간:" + df.format(currentTime.getTime()));

                 currentTime.add(Calendar.YEAR, -entryTime.get(Calendar.YEAR));
                 currentTime.add(Calendar.MONTH,-entryTime.get(Calendar.MONTH));
                 currentTime.add(Calendar.DATE, -entryTime.get(Calendar.DATE));
                 currentTime.add(Calendar.HOUR, -entryTime.get(Calendar.HOUR));
                 currentTime.add(Calendar.MINUTE,-entryTime.get(Calendar.MINUTE));
                 System.out.println("after:" + df.format(currentTime.getTime()));

             }
         }

         //when
         String entDyTe = carBean.getEntDyTe();
         String year = entDyTe.substring(0, 4);
         String month = entDyTe.substring(4, 6);
         String day = entDyTe.substring(6, 8);
         String hour = entDyTe.substring(8, 10);
         String min = entDyTe.substring(10, 12);
         //then
         Assertions.assertThat(year).isEqualTo("2019");
         Assertions.assertThat(month).isEqualTo("01");
         Assertions.assertThat(day).isEqualTo("15");
         Assertions.assertThat(hour).isEqualTo("10");
         Assertions.assertThat(min).isEqualTo("10");

      }
}