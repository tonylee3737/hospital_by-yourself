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



//    차량번호조회 페이지에서 번호를 검색한 후, 나열된 리스트 중 하나의 차량번호를 선택하였을 시 뜨는 페이지
    @GetMapping("getCarInfo")
    public String getCarInfo(@RequestParam(required = false, value = "carNumber") String carNumber, //실제 차 넘버
                             @RequestParam(required = false, value = "result") String result,
                             @RequestParam(required = false, value = "carNumber2") String carNumber2, // 검색된 창에 차량 넘버
                             Model model) throws ParseException, UnsupportedEncodingException {
//        test 끝난 후 제거
        MemberBean memberBean = new MemberBean();
        memberBean.setClID("test");
        memberBean.setClPW("1");
        MemberBean memberBean1 = memberService.loginMember(memberBean);
        sessionBean.setMemberbean(memberBean1);
        MemberBean member = sessionBean.getMemberbean();
        String clName = memberBean.getClName();
        carNumber = "1234";
//        test 끝난 후 제거 밑에는 주석 풀기
//        MemberBean member = sessionBean.getMemberbean();
//        String clName = member.getClName();
//        if (member == null) {
//            return "redirect:/";
//        }
//        이까지
        CarBean car = new CarBean();
        car.setVhlNbr(carNumber);
//        검색된 자동차 리스트 중 한개를 선택하여 차량 정보 출력 함, 이때 쿼리문은 이전과 같은 쿼리문을 사용한다.
        List<CarBean> carBeans = carService.selectCarInfo(car);
        CarBean carBean = carBeans.get(0);
        car.setVhlNbr(carNumber2);
        List<CarBean> carBeansList = carService.selectCarInfo(car);
//        경과시간 구하기
        String carEntDyte = carBean.getEntDyTe();
        String year = carEntDyte.substring(0, 4);
        String month = carEntDyte.substring(4, 6);
        String day = carEntDyte.substring(6, 8);
        String hour = carEntDyte.substring(8, 10);
        String min = carEntDyte.substring(10, 12);
        String sec = carEntDyte.substring(12, 14);
        String formatDateShow = year + "-" + month + "-" + day + " " + hour + ":" + min + ":" + sec;
        String formatDate = year + "-" + month + "-" + day + "-" + hour + "-" + min + "-" + sec;
        Calendar getToday = Calendar.getInstance();
        getToday.setTime(new Date());//현재시간
        Date date = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss").parse(formatDate);
        Calendar cmpDate = Calendar.getInstance();
        cmpDate.setTime(date);// 특정날짜
        long diffSec = (getToday.getTimeInMillis() - cmpDate.getTimeInMillis()) / 1000;
        long diffDays = diffSec / (24 * 60 * 60);
        double resDay = (diffSec / (double) (24 * 60 * 60)) - diffDays;
        long diffHour = (long) (resDay * 24);
        double resHour = (resDay * 24) - diffHour;
        long diffMin = (long) (resHour * 60);
        long diffSecond = (diffSec % 60);
        String carEnt = diffDays + "일" + diffHour + "시간" + diffMin + "분" + diffSecond + "초";
        ControllDiscountCar controllDiscountCar = carService.selectControllDiscountCar(carBean);

        model.addAttribute("formatDateShow", formatDateShow);
        model.addAttribute("carBeans", carBeansList);
        model.addAttribute("carEnt", carEnt);
        model.addAttribute("member_ClName", member.getClName());
        model.addAttribute("controllDiscountCar", controllDiscountCar);
        model.addAttribute("member", member);
        model.addAttribute("carInfo", carBean);
        model.addAttribute("carNumber", carNumber2);
        model.addAttribute("dcResult", result);
        return "member/searchCarInfo";
    }
//  차량번호조회 페이지에서 번호를 검색한 후, 나열된 리스트 중 하나의 차량번호를 선택하였을 시 뜨는 페이지 그리고 그 차량의 할인시간을 등록시키는 페이지
    @PostMapping("registerDiscountTime")
    public String discountTime(@RequestParam(value = "discountTime") String time,
                               @RequestParam(value = "idx") String idx,
                               @RequestParam(value = "carNumber") String encarNumber,
                               @RequestParam(value = "searchCarNumber") String searchCarNumber,
                               Model model) throws UnsupportedEncodingException {
        ControllDiscountCar discountCar = new ControllDiscountCar();
        MemberBean memberBean = sessionBean.getMemberbean();
//        if(memberBean==null){
//            return "redirect:/";
//        }
        if (time.equals(memberBean.getClDCName1())) {
            discountCar.setDCName(memberBean.getClDCName1());
            discountCar.setDCTime(memberBean.getClDCTime1());
            discountCar.setDCRate(memberBean.getClDCRate1());
        } else if (time.equals(memberBean.getClDCName2())) {
            discountCar.setDCName(memberBean.getClDCName2());
            discountCar.setDCTime(memberBean.getClDCTime2());
            discountCar.setDCRate(memberBean.getClDCRate2());
        } else if (time.equals(memberBean.getClDCName3())) {
            discountCar.setDCName(memberBean.getClDCName3());
            discountCar.setDCTime(memberBean.getClDCTime3());
            discountCar.setDCRate(memberBean.getClDCRate3());
        } else if (time.equals(memberBean.getClDCName4())) {
            discountCar.setDCName(memberBean.getClDCName4());
            discountCar.setDCTime(memberBean.getClDCTime4());
            discountCar.setDCRate(memberBean.getClDCRate4());
        } else if (time.equals(memberBean.getClDCName5())) {
            discountCar.setDCName(memberBean.getClDCName5());
            discountCar.setDCTime(memberBean.getClDCTime5());
            discountCar.setDCRate(memberBean.getClDCRate5());
        } else if (time.equals(memberBean.getClDCName6())) {
            discountCar.setDCName(memberBean.getClDCName6());
            discountCar.setDCTime(memberBean.getClDCTime6());
            discountCar.setDCRate(memberBean.getClDCRate6());
        }
        discountCar.setVhliDx(idx);
        discountCar.setCliDx(memberBean.getCliDx());
// 쿠폰 사용 유무
        discountCar.setUseDiv("0");
//        - - ?
        discountCar.setActDiv("1");
        carService.Procedure_DiscountCarTime(discountCar);
        String carNumber = URLEncoder.encode(encarNumber, "utf-8");
        return "redirect:/getCarInfo?carNumber=" + carNumber + "&&result=" +discountCar.getResult() + "&&carNumber2=" + searchCarNumber;

    }


    //차량 번호 조회 페이지, 차량번호 입력 번호판
    @GetMapping("searchCarInfo")
    public String searchCarInfo(Model model) {
//        MemberBean member = sessionBean.getMemberbean();
//        if (member == null) {
//            return "redirect:/";
//        }
//테스트 끝난 후 제거
        MemberBean memberBean = new MemberBean();
        memberBean.setClID("test");
        memberBean.setClPW("1");
        MemberBean memberBean1 = memberService.loginMember(memberBean);
        sessionBean.setMemberbean(memberBean1);
        MemberBean member = sessionBean.getMemberbean();
        String clName = memberBean.getClName();

//테스트 끝난 후 제거
//
        model.addAttribute("member_ClName", member.getClName());
        model.addAttribute("member_show", member);
        return "member/searchCarInfo";
    }
//차량 번호 조회 페이지, 차량 번호가 입력이 되면 POST
    @PostMapping("searchCarInfo")
    public String getCarInfo_Post(@RequestParam(value = "carNumber") String carNumber, Model model) {
        MemberBean member = sessionBean.getMemberbean();
//        if (member == null) {
//            return "redirect:/";
//        }
        CarBean car = new CarBean();
        car.setVhlNbr(carNumber);
        List<CarBean> carBeans = carService.selectCarInfo(car);
        if(carBeans.size() == 0) {
            carBeans = null;
        }
        model.addAttribute("member_ClName", member.getClName());
        model.addAttribute("member_show", member);
        model.addAttribute("carNumber", carNumber);
        model.addAttribute("carBeans", carBeans);
        return "member/searchCarInfo";
    }

//등록내역 검색 페이지
    @GetMapping("regSearch")
    public String regSearch(Model model) {
        MemberBean member = sessionBean.getMemberbean();
//        if (member == null) {
//            return "redirect:/";
//        }
        List<DiscountedCarInfo> discountedCarInfos = carService.selectDiscountedCarInfoList();
        model.addAttribute("discountedCarInfo", discountedCarInfos);
        return "member/regSearch";
    }

//    등록내역 검색페이지에서 POST
    @PostMapping("regSearch")
    public String regSearch_post(@RequestParam(value = "carNumber") String carNumber,
                                 @RequestParam(value = "startDate") String startDate,
                                 @RequestParam(value = "endDate") String endDate,
                                 @RequestParam(value = "checkDivUse") String checkDivUse,
                                 Model model) {
        MemberBean member = sessionBean.getMemberbean();
//        if (member == null) {
//            return "redirect:/";
//        }
        String[] start = startDate.split("-");
        String s_year = start[0];
        String s_month = start[1];
        String s_day = start[2];
        String s_date = s_year + s_month + s_day + "000000";
        String[] end = endDate.split("-");
        String e_year = end[0];
        String e_month = end[1];
        String e_day = end[2];
        String e_date = e_year + e_month + e_day + "235959";

        CarBean car = new CarBean();
        car.setVhlNbr(carNumber);
        car.setStartDate(s_date);
        car.setEndDate(e_date);
        car.setUseDiv(checkDivUse);
        String divUse = (checkDivUse.equals("0") ? "미사용" : "사용포함");
        //        List<DiscountedCarInfo> discountedCarInfos = carService.selectDiscountedCarInfo(car);
        List<DiscountedCarInfo> discountedCarInfos = carService.selectDiscountedCarInfoListWithDate_Member(car);
        model.addAttribute("startDate", startDate);
        model.addAttribute("endDate", endDate);
        model.addAttribute("checkDivUse", checkDivUse);
        model.addAttribute("DivUse", divUse);
        model.addAttribute("discountedCarInfo", discountedCarInfos);
        model.addAttribute("carNumber", carNumber);
        return "member/regSearch";
    }

    @GetMapping("logout")
    public String logout() {
        sessionBean.setMemberbean(null);
        return "redirect:/";
    }

}
