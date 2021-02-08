package hospital_parking_system.hospital_parking.carInfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class CarController {

    private CarService carService;

    @Autowired
    CarController(CarService carService) {
        this.carService = carService;
    }

    @GetMapping("getCarInfo")
    public String getCarInfo() {
        return "carNumberSearch";
    }

    @PostMapping("getCarInfo")
    public String getCarInfo_Post(@RequestParam(value = "carNumber") String carNumber, Model model) {
        CarBean car = new CarBean();
        car.setVhlNbr(carNumber);
        List<CarBean> carBeans = carService.selectCarInfo(car);
        model.addAttribute("carBeans", carBeans.get(0));
        return "carNumberSearch";
    }
}
