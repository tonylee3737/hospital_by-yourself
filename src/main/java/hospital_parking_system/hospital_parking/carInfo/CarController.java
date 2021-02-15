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

import java.util.List;

@Controller
@RequiredArgsConstructor
public class CarController {

    private final CarService carService;
    private final MemberService memberService;
    private final SessionBean sessionBean;

    @GetMapping("getCarInfo")
    public String getCarInfo(@RequestParam(value = "carNumber") String carNumber, Model model) {
        MemberBean member = sessionBean.getBean();
        if (member == null) {
            return "redirect:/";
        }
        System.out.println(member);
        CarBean car = new CarBean();
        car.setVhlNbr(carNumber);
//        검색된 자동차 리스트 중 한개를 선택하여 차량 정보 출력 함, 이때 쿼리문은 이전과 같은 쿼리문을 사용한다.
        List<CarBean> carBeans = carService.selectCarInfo(car);
        CarBean carBean = carBeans.get(0);
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
}
