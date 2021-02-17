package hospital_parking_system.hospital_parking.carInfo;
import hospital_parking_system.hospital_parking.member.MemberBean;
import hospital_parking_system.hospital_parking.member.MemberService;
import hospital_parking_system.hospital_parking.member.SessionBean;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class CarController {

    private final CarService carService;
    private final MemberService memberService;
    private final SessionBean sessionBean;

    @GetMapping("getCarInfo")
    public String getCarInfo(@RequestParam(value = "carNumber") String carNumber, Model model) throws ParseException, UnsupportedEncodingException {
        MemberBean member = sessionBean.getBean();
        if (member == null) {
            return "redirect:/";
        }
        CarBean car = new CarBean();
        car.setVhlNbr(carNumber);
//        검색된 자동차 리스트 중 한개를 선택하여 차량 정보 출력 함, 이때 쿼리문은 이전과 같은 쿼리문을 사용한다.
        List<CarBean> carBeans = carService.selectCarInfo(car);
        CarBean carBean = carBeans.get(0);
//        경과시간 구하기
        String carEntDyte = carBean.getEntDyTe();
        String year = carEntDyte.substring(0, 4);
        String month = carEntDyte.substring(4, 6);
        String day = carEntDyte.substring(6, 8);
        String hour = carEntDyte.substring(8, 10);
        String min = carEntDyte.substring(10, 12);
        String sec = carEntDyte.substring(12, 14);
        String formatDate = year +"-" + month +"-" + day+"-" +hour+"-" +min+"-" +sec;
        Calendar getToday = Calendar.getInstance();
        getToday.setTime(new Date());//현재시간
        Date date = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss").parse(formatDate);
        Calendar cmpDate = Calendar.getInstance();
        cmpDate.setTime(date);// 특정날짜
        long diffSec = (getToday.getTimeInMillis() - cmpDate.getTimeInMillis()) / 1000;
        long diffDays = diffSec/(24*60*60);
        double resDay = (diffSec/(double)(24 * 60 * 60))-diffDays;
        long diffHour = (long) (resDay*24);
        double resHour = (resDay*24)-diffHour;
        long diffMin = (long) (resHour*60);
        long diffSecond = (diffSec%60);
        String carEnt = diffDays +"일" + diffHour +"시간" + diffMin+"분" +diffSecond +"초";
        ControllDiscountCar controllDiscountCar = carService.selectControllDiscountCar(carBean);
        model.addAttribute("carEnt", carEnt);
        model.addAttribute("controllDiscountCar", controllDiscountCar);
        model.addAttribute("member", member);
        model.addAttribute("carInfo", carBean);
        return "member/searchCarInfo";
    }

    @GetMapping("searchCarInfo")
    public String searchCarInfo(Model model) {
        MemberBean member = sessionBean.getBean();
        if (member == null) {
            return "redirect:/";
        }
        return "member/searchCarInfo";
    }

    @PostMapping("searchCarInfo")
    public String getCarInfo_Post(@RequestParam(value = "carNumber") String carNumber, Model model) {
        MemberBean member = sessionBean.getBean();
        if (member == null) {
            return "redirect:/";
        }
        CarBean car = new CarBean();
        car.setVhlNbr(carNumber);
        List<CarBean> carBeans = carService.selectCarInfo(car);
        model.addAttribute("carNumber", carNumber);
        model.addAttribute("carBeans", carBeans);
        return "member/searchCarInfo";
    }

    @GetMapping("regSearch")
    public String regSearch(Model model) {
        MemberBean member = sessionBean.getBean();
        if (member == null) {
            return "redirect:/";
        }
        List<DiscountedCarInfo> discountedCarInfos = carService.selectDiscountedCarInfoList();
        model.addAttribute("discountedCarInfo", discountedCarInfos);
        return "member/regSearch";
    }

    @PostMapping("regSearch")
    public String regSearch_post(@RequestParam(value = "carNumber") String carNumber, Model model) {
        MemberBean member = sessionBean.getBean();
        if (member == null) {
            return "redirect:/";
        }
        CarBean car = new CarBean();
        car.setVhlNbr(carNumber);
        List<DiscountedCarInfo> discountedCarInfos = carService.selectDiscountedCarInfo(car);
        model.addAttribute("discountedCarInfo", discountedCarInfos);
        return "member/regSearch";
    }

    @GetMapping("logout")
    public String logout(){
        sessionBean.setBean(null);
        return "redirect:/";
    }

    @PostMapping("registerDiscountTime")
    public String discountTime(@RequestParam(value = "discountTime") String time,
                               @RequestParam(value = "idx") String idx,
                               @RequestParam(value = "carNumber") String encarNumber,Model model) throws UnsupportedEncodingException {
        ControllDiscountCar discountCar = new ControllDiscountCar();
        MemberBean memberBean = sessionBean.getBean();
        String discountTime = time.trim();
        String memberDiscountTime = memberBean.getClDCName1().trim();
        if (time.equals(memberBean.getClDCName1())){
            discountCar.setDCName(memberBean.getClDCName1());
            discountCar.setDCTime(memberBean.getClDCTime1());
            discountCar.setDCRate(memberBean.getClDCRate1());
        } else if (time.equals(memberBean.getClDCName2())) {
            discountCar.setDCName(memberBean.getClDCName2());
            discountCar.setDCTime(memberBean.getClDCTime2());
            discountCar.setDCRate(memberBean.getClDCRate2());
        }else if (time.equals(memberBean.getClDCName3())) {
            discountCar.setDCName(memberBean.getClDCName3());
            discountCar.setDCTime(memberBean.getClDCTime3());
            discountCar.setDCRate(memberBean.getClDCRate3());
        }else if (time.equals(memberBean.getClDCName4())) {
            discountCar.setDCName(memberBean.getClDCName4());
            discountCar.setDCTime(memberBean.getClDCTime4());
            discountCar.setDCRate(memberBean.getClDCRate4());
        }else if (time.equals(memberBean.getClDCName5())) {
            discountCar.setDCName(memberBean.getClDCName5());
            discountCar.setDCTime(memberBean.getClDCTime5());
            discountCar.setDCRate(memberBean.getClDCRate5());
        }else if (time.equals(memberBean.getClDCName6())) {
            discountCar.setDCName(memberBean.getClDCName6());
            discountCar.setDCTime(memberBean.getClDCTime6());
            discountCar.setDCRate(memberBean.getClDCRate6());
        }
        discountCar.setVhliDx(idx);
        discountCar.setCliDx(memberBean.getCliDx());
        discountCar.setUseDiv("0");
        discountCar.setActDiv("1");
//        if(carService.selectDiscountCarTime(discountCar)!=null){
//            carService.updateDiscountCarTime(discountCar);
//        }else{
//            carService.insertDiscountCarTime(discountCar);
//        }
        carService.Procedure_DiscountCarTime(discountCar);
        System.out.println(discountCar.getResult());
        String carNumber = URLEncoder.encode(encarNumber, "utf-8");
        return "redirect:/getCarInfo?carNumber="+carNumber;
    }

}
