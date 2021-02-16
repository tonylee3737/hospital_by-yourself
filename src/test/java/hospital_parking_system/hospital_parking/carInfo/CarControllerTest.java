package hospital_parking_system.hospital_parking.carInfo;

import hospital_parking_system.hospital_parking.adminPage.AdminService;
import hospital_parking_system.hospital_parking.member.MemberBean;
import hospital_parking_system.hospital_parking.member.MemberService;
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

    @Autowired
    MemberService memberService;

    @Autowired
    AdminService adminService;
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
      }
      @Test
       public void 컨트롤할인차량() {
       //given
          CarBean car = new CarBean();
          //when
          car.setVHIdx("34");
          ControllDiscountCar controllDiscountCar = carService.selectControllDiscountCar(car);

       //then
          Assertions.assertThat(controllDiscountCar.getVhliDx()).isEqualTo("34");
       }
       @Test//셀렉트 옵션에서 가져온 값과 멤버에 있는 값을 비교 -> 해당 rate, time을 control빈에 대입 -> insert, update 쿼리 날리기.
        public void discountTime시간비교() {
        //given
           MemberBean member = new MemberBean();
           member.setClID("tony");
           member.setClPW("1");
           MemberBean memberBean = memberService.loginMember(member);

        //when
           String clDCName1 = memberBean.getClDCName1();
           String time = "1시간할인";

        //then
           Assertions.assertThat(clDCName1).isEqualTo(time);
        }
        @Test //
         public void 할인등록하기() {
         //given
            ControllDiscountCar discount = new ControllDiscountCar();
            discount.setVhliDx("35");
            discount.setDCName("1시간할인");
            discount.setDCRate("10");
            discount.setDCTime("60");
            discount.setCliDx("1");
            discount.setUseDiv("0");
            carService.insertDiscountCarTime(discount);
            CarBean car = new CarBean();

            //when
            car.setVHIdx("35");
            ControllDiscountCar discountCar = carService.selectControllDiscountCar(car);

            //then
            Assertions.assertThat(discountCar.getVhliDx()).isEqualTo(discount.getVhliDx());
         }
         @Test
          public void 등록되었다면업데이트하기() {
          //given
             ControllDiscountCar car = new ControllDiscountCar();
             car.setVhliDx("34");
             car.setDCName("test시간할인");
             car.setUseDiv("0");
             car.setDCRate("10");
             car.setDCTime("10");
             ControllDiscountCar discountCar = carService.selectDiscountCarTime(car);
             //when
             if(discountCar!=null){
                 carService.updateDiscountCarTime(car);
             }else{
                 carService.insertDiscountCarTime(car);
             }
             ControllDiscountCar discountCar1 = carService.selectDiscountCarTime(car);
             //then
             Assertions.assertThat(discountCar1.getDCName()).isEqualTo("test시간할인");
          }
}