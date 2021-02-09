package hospital_parking_system.hospital_parking.adminPage;


import hospital_parking_system.hospital_parking.carInfo.CarBean;
import hospital_parking_system.hospital_parking.carInfo.CarService;
import hospital_parking_system.hospital_parking.carInfo.DiscountedCarInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;
    private final CarService carService;

    @GetMapping("/adminRegSearch")
    public String adminRegSearch(Model model) {
        return "adminRegSearch";
    }

    @PostMapping("/adminRegSearch")
    public String adminRegSearch_Post(@RequestParam(value = "carNumber") String carNumber, Model model) {
        CarBean car = new CarBean();
        car.setVhlNbr(carNumber);
        List<DiscountedCarInfo> discountedCarInfos = carService.selectDiscountedCarInfo(car);
        model.addAttribute("discountedCarInfo", discountedCarInfos);
        return "adminRegSearch";
    }
}
