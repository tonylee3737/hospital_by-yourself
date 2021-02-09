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

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class CarController {

    private final CarService carService;
    private final MemberService memberService;
    private final SessionBean sessionBean;

    @GetMapping("getCarInfo")
    public String getCarInfo(@RequestParam(value = "carNumber") String carNumber, Model model) {
        CarBean car = new CarBean();
        car.setVhlNbr(carNumber);
        List<CarBean> carBeans = carService.selectCarInfo(car);
        CarBean carBean = carBeans.get(0);
        MemberBean member = sessionBean.getBean();
        model.addAttribute("member", member);
        model.addAttribute("carInfo", carBean);
        return "carNumberSearch";
    }

    @PostMapping("getCarInfo")
    public String getCarInfo_Post(@RequestParam(value = "carNumber") String carNumber, Model model, HttpServletRequest request) {
        CarBean car = new CarBean();
        car.setVhlNbr(carNumber);
        List<CarBean> carBeans = carService.selectCarInfo(car);
        model.addAttribute("carNumber", carNumber);
        model.addAttribute("carBeans", carBeans);
        return "carNumberSearch";
    }

    @GetMapping("regSearch")
    public String regSearch(Model model) {
        return "regSearch";
    }

    @PostMapping("regSearch")
    public String regSearch_post(@RequestParam(value = "carNumber") String carNumber, Model model) {
        CarBean car = new CarBean();
        car.setVhlNbr(carNumber);
        List<DiscountedCarInfo> discountedCarInfos = carService.selectDiscountedCarInfo(car);
        model.addAttribute("discountedCarInfo", discountedCarInfos);
        return "regSearch";
    }
}
