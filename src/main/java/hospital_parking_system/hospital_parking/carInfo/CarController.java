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
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class CarController {

    private final CarService carService;
    private final MemberService memberService;
    private final SessionBean sessionBean;



// 일반 멤버로 로그인 시 실행되는 페이지, 차량 번호 조회 페이지
    @GetMapping("searchCarInfo")
    public String searchCarInfo(Model model) {
//        세션을 통해 관리자 멤버의 할인처와 할인시간을 표시한다.
        MemberBean member = sessionBean.getMemberbean();
        if (member == null) {
            return "redirect:/";
        }
        //  할인명 글자 체크
        List length_Check = new ArrayList();
        length_Check.add(member.getClDCName1());
        length_Check.add(member.getClDCName2());
        length_Check.add(member.getClDCName3());
        length_Check.add(member.getClDCName4());
        length_Check.add(member.getClDCName5());
        length_Check.add(member.getClDCName6());
        int max_length = 0;
        int text_length=0;
        for(Object li : length_Check){
            int length = li.toString().getBytes(StandardCharsets.UTF_8).length;
            if(max_length < length){
                max_length = length;
            }
        }
        if(max_length<=15){
            text_length=15;
        }else if(max_length <=25){
            text_length=25;
        }else{
            text_length=30;
        }
        int blank_showing_for_carBeans = 4;
        model.addAttribute("blank_showing_for_carBeans", blank_showing_for_carBeans);
        model.addAttribute("text_length", text_length);
        model.addAttribute("member_ClName", member.getClName());
        model.addAttribute("member_show", member);
        return "member/searchCarInfo";
    }

    //차량 번호 조회 페이지, 차량 번호가 입력이 되면 POST
    @PostMapping("searchCarInfo")
    public String getCarInfo_Post(@RequestParam(value = "carNumber") String carNumber, Model model) {
//        parameter값으로 carNumber를 받고 받아온 차량번호를 통하여 차량을 조회한다.
        MemberBean member = sessionBean.getMemberbean();
        if (member == null) {
            return "redirect:/";
        }
        CarBean car = new CarBean();
        car.setVhlNbr(carNumber);
//        차량 조회
        List<CarBean> carBeans = carService.selectCarInfo(car);

//        차량조회 공백 칸 만들기
        String blank_should_not_show = null;
        int blank_showing_for_carBeans=0;

        if(carBeans.size() == 0) {
            carBeans = null;
            blank_showing_for_carBeans = 4;
        }else{
            blank_showing_for_carBeans = 4-(carBeans.size());
            if(blank_showing_for_carBeans==0){
                blank_should_not_show = "do not";
            }
        }
        //  할인명 글자 체크
        List length_Check = new ArrayList();
        length_Check.add(member.getClDCName1());
        length_Check.add(member.getClDCName2());
        length_Check.add(member.getClDCName3());
        length_Check.add(member.getClDCName4());
        length_Check.add(member.getClDCName5());
        length_Check.add(member.getClDCName6());
        int max_length = 0;
        int text_length=0;
        for(Object li : length_Check){
            int length = li.toString().getBytes(StandardCharsets.UTF_8).length;
            if(max_length < length){
                max_length = length;
            }
        }
        if(max_length<=15){
            text_length=15;
        }else if(max_length <=25){
            text_length=25;
        }else{
            text_length=30;
        }


        model.addAttribute("blank_should_not_show", blank_should_not_show);
        model.addAttribute("blank_showing_for_carBeans", blank_showing_for_carBeans);
        model.addAttribute("text_length", text_length);
        model.addAttribute("member_ClName", member.getClName());
        model.addAttribute("member_show", member);
        model.addAttribute("carNumber", carNumber);
        model.addAttribute("carBeans", carBeans);
        return "member/searchCarInfo";
    }


//    차량번호조회 페이지에서 번호를 검색한 후, 나열된 리스트 중 하나의 차량번호를 선택하였을 시 뜨는 페이지
    @GetMapping("getCarInfo")
    public String getCarInfo(@RequestParam(required = false, value = "carNumber") String carNumber, //실제 차 넘버
                             @RequestParam(required = false, value = "result") String result,
                             @RequestParam(required = false, value = "carNumber2") String carNumber2, // 검색된 창에 차량 넘버
                             Model model) throws ParseException, UnsupportedEncodingException {
//      1.  조회 후 나열된 차량번호 중 택1을 하였을 시 구현되는 페이지, 이때 parameter값으로 carNumber(클릭한 차량), carNumber2(검색된 차량)
//      2.  조회 후 할인등록 시 할인시간을 택하고 submit이 실행되면 이때 parameter값으로 result도 얻는다. 할인권이 등록인지, 수정인지 알기 위함.
        MemberBean member = sessionBean.getMemberbean();
        String clName = member.getClName();
        if (member == null) {
            return "redirect:/";
        }
//        이까지
        CarBean car = new CarBean();
//        검색된 차량 넘버 중 하나를 선택한 full Number
        car.setVhlNbr(carNumber);
        List<CarBean> carBeans = carService.selectCarInfo(car);
        CarBean carBean = carBeans.get(0);
//      검색창에 입력된 부분적인 car Number
        car.setVhlNbr(carNumber2);
        List<CarBean> carBeansList = carService.selectCarInfo(car);

        //        차량조회 공백 칸 만들기
        String blank_should_not_show = null;
        int blank_showing_for_carBeans=0;

        if(carBeans.size() == 0) {
            carBeans = null;
            blank_showing_for_carBeans = 4;
        }else{
            blank_showing_for_carBeans = 4-(carBeansList.size());
            if(blank_showing_for_carBeans==0){
                blank_should_not_show = "do not";
            }
        }


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


        //  할인명 글자 체크
        List length_Check = new ArrayList();
        length_Check.add(member.getClDCName1());
        length_Check.add(member.getClDCName2());
        length_Check.add(member.getClDCName3());
        length_Check.add(member.getClDCName4());
        length_Check.add(member.getClDCName5());
        length_Check.add(member.getClDCName6());
        int max_length = 0;
        int text_length=0;
        for(Object li : length_Check){
            int length = li.toString().getBytes(StandardCharsets.UTF_8).length;
            if(max_length < length){
                max_length = length;
            }
        }
        if(max_length<=15){
            text_length=15;
        }else if(max_length <=25){
            text_length=25;
        }else{
            text_length=30;
        }

        model.addAttribute("blank_should_not_show", blank_should_not_show);
        model.addAttribute("blank_showing_for_carBeans", blank_showing_for_carBeans);
        model.addAttribute("text_length", text_length);
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
        if(memberBean==null){
            return "redirect:/";
        }
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

//등록내역 검색 페이지
    @GetMapping("regSearch")
    public String regSearch(Model model) {
        MemberBean member = sessionBean.getMemberbean();
        if (member == null) {
            return "redirect:/";
        }
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
        if (member == null) {
            return "redirect:/";
        }
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
