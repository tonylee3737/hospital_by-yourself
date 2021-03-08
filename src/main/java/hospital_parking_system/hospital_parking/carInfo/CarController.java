package hospital_parking_system.hospital_parking.carInfo;
import hospital_parking_system.hospital_parking.adminPage.AdminService;
import hospital_parking_system.hospital_parking.member.MemberBean;
import hospital_parking_system.hospital_parking.member.MemberService;
import hospital_parking_system.hospital_parking.member.SessionBean;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
    private final AdminService adminService;
    private final SessionBean sessionBean;



    // 일반 멤버로 로그인 시 실행되는 페이지, 차량 번호 조회 페이지
    @GetMapping("searchCarInfo")
    public String searchCarInfo(Model model, HttpServletRequest request) {
    // 세션을 통해 관리자 멤버의 할인처와 할인시간을 표시한다.
        MemberBean member = sessionBean.getMemberbean();
        if (member == null) {
            return "redirect:/";
        }
        //  할인명 글자 체크
        int text_length = carService.dc_name_length_check(member);
        //차번호 리스트 공백

        int blank_showing_for_carBeans = 4;

        model.addAttribute("blank_showing_for_carBeans", blank_showing_for_carBeans);
        model.addAttribute("text_length", text_length);
        model.addAttribute("member_ClName", member.getClName());
        model.addAttribute("member_show", member);

        Boolean isthis_Mobile = carService.get_Mobile_Or_Web(request.getHeader("user-agent"));

        if(isthis_Mobile==true){
//            return "member/test";
            return "member/m_searchCarInfo";

        }else{
            return "member/searchCarInfo";
        }
    }

    //차량 번호 조회 페이지, 차량 번호가 입력이 되면 POST
    @PostMapping("searchCarInfo")
    public String getCarInfo_Post(@RequestParam(value = "carNumber") String carNumber, Model model, HttpServletRequest request) {
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
        Blank_control blank_control = carService.making_CarList_Blank(carBeans);
        //  할인명 글자 체크
        int text_length = carService.dc_name_length_check(member);

        model.addAttribute("blank_should_not_show", blank_control.getBlank_should_not_show());
        model.addAttribute("blank_showing_for_carBeans", blank_control.getBlank_showing_for_carBeans());
        model.addAttribute("text_length", text_length);
        model.addAttribute("member_ClName", member.getClName());
        model.addAttribute("member_show", member);
        model.addAttribute("carNumber", carNumber);
        model.addAttribute("carBeans", carBeans);
        Boolean isthis_Mobile = carService.get_Mobile_Or_Web(request.getHeader("user-agent"));

        if(isthis_Mobile==true){
             if(carBeans.size() == 0){
                 model.addAttribute("car_List_None", true);
                 return "member/m_searchCarInfo";
             }
//            return "member/m_searchCarInfo";
            return "member/m_searchCarInfo_list";

        }else{
            return "member/searchCarInfo";
        }
    }


//    차량번호조회 페이지에서 번호를 검색한 후, 나열된 리스트 중 하나의 차량번호를 선택하였을 시 뜨는 페이지
    @GetMapping("getCarInfo")
    public String getCarInfo(@RequestParam(required = false, value = "carNumber") String carNumber, //실제 차 넘버
                             @RequestParam(required = false, value = "result") String result,
                             @RequestParam(required = false, value = "carNumber2") String carNumber2, // 검색된 창에 차량 넘버
                             Model model, HttpServletRequest request) throws ParseException, UnsupportedEncodingException {
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

        //차량조회 공백 칸 만들기
        Blank_control blank_control = carService.making_CarList_Blank(carBeansList);



//        경과시간 구하기
        String carEntDyte = carBean.getEntDyTe();
        Car_Ent_Time car_entDyte = carService.get_Car_EntDyte(carEntDyte);

//        할인 리스트
        ControllDiscountCar controllDiscountCar = carService.selectControllDiscountCar(carBean);


        //  할인명 글자 체크
        int text_length = carService.dc_name_length_check(member);
        model.addAttribute("blank_should_not_show", blank_control.getBlank_should_not_show());
        model.addAttribute("blank_showing_for_carBeans", blank_control.getBlank_showing_for_carBeans());
        model.addAttribute("text_length", text_length);
        model.addAttribute("formatDateShow", car_entDyte.getFormatDateShow());
        model.addAttribute("carBeans", carBeansList);
        model.addAttribute("carEnt", car_entDyte.getCarEnt());
        model.addAttribute("member_ClName", member.getClName());
        model.addAttribute("controllDiscountCar", controllDiscountCar);
        model.addAttribute("member", member);
        model.addAttribute("carInfo", carBean);
        model.addAttribute("carNumber", carNumber2);
        model.addAttribute("dcResult", result);
        Boolean isthis_Mobile = carService.get_Mobile_Or_Web(request.getHeader("user-agent"));

        if(isthis_Mobile==true){
            return "member/m_searchCarInfo";

        }else{
            return "member/searchCarInfo";
        }
    }

    @GetMapping("getCarInfo_m")
    public String getCarInfo_m(@RequestParam(required = false, value = "carNumber") String carNumber, //실제 차 넘버
                             @RequestParam(required = false, value = "result") String result,
                             @RequestParam(required = false, value = "carNumber2") String carNumber2, // 검색된 창에 차량 넘버
                             Model model, HttpServletRequest request) throws ParseException, UnsupportedEncodingException {
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

        //차량조회 공백 칸 만들기
        Blank_control blank_control = carService.making_CarList_Blank(carBeansList);



//        경과시간 구하기
        String carEntDyte = carBean.getEntDyTe();
        Car_Ent_Time car_entDyte = carService.get_Car_EntDyte(carEntDyte);

//        할인 리스트
        ControllDiscountCar controllDiscountCar = carService.selectControllDiscountCar(carBean);


        //  할인명 글자 체크
        int text_length = carService.dc_name_length_check(member);
        model.addAttribute("blank_should_not_show", blank_control.getBlank_should_not_show());
        model.addAttribute("blank_showing_for_carBeans", blank_control.getBlank_showing_for_carBeans());
        model.addAttribute("text_length", text_length);
        model.addAttribute("formatDateShow", car_entDyte.getFormatDateShow());
        model.addAttribute("carBeans", carBeansList);
        model.addAttribute("carEnt", car_entDyte.getCarEnt());
        model.addAttribute("member_ClName", member.getClName());
        model.addAttribute("controllDiscountCar", controllDiscountCar);
        model.addAttribute("member", member);
        model.addAttribute("carInfo", carBean);
        model.addAttribute("carNumber", carNumber2);
        model.addAttribute("dcResult", result);
        Boolean isthis_Mobile = carService.get_Mobile_Or_Web(request.getHeader("user-agent"));

        if(isthis_Mobile==true){
            return "member/m_searchCarInfo_info";

        }else{
            return "member/searchCarInfo";
        }
    }


//  차량번호조회 페이지에서 번호를 검색한 후, 나열된 리스트 중 하나의 차량번호를 선택하였을 시 뜨는 페이지 그리고 그 차량의 할인시간을 등록시키는 페이지
    @PostMapping("registerDiscountTime")
    public String discountTime(@RequestParam(value = "discountTime") String time,
                               @RequestParam(value = "idx") String idx,
                               @RequestParam(value = "carNumber") String encarNumber,
                               @RequestParam(value = "searchCarNumber") String searchCarNumber,
                               Model model) throws UnsupportedEncodingException {
        MemberBean memberBean = sessionBean.getMemberbean();
        if(memberBean==null){
            return "redirect:/";
        }

        // 차량 할인 등록하기
        ControllDiscountCar discountCar = carService.set_Dc_Time(time, memberBean, idx);

        
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


        adminService.dateTime_calculate(startDate, endDate);
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
