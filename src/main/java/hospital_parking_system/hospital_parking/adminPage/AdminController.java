package hospital_parking_system.hospital_parking.adminPage;


import hospital_parking_system.hospital_parking.carInfo.CarBean;
import hospital_parking_system.hospital_parking.carInfo.CarService;
import hospital_parking_system.hospital_parking.carInfo.DiscountedCarInfo;
import hospital_parking_system.hospital_parking.member.MemberBean;
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

    @GetMapping("/adminManaging")
    public String adminManaging(Model model) {
        List<AdminManagerBean> adminManagerBeans = adminService.selectAdminManager();
        model.addAttribute("adminList", adminManagerBeans);
        return "adminManaging";
    }

    @GetMapping("/adminRegister")
    public String adminRegister(Model model) {
        model.addAttribute("memberForm", new MemberForm());
        List<GroupBean> groupBeans = adminService.selectGroupList();
        model.addAttribute("groupList",groupBeans);
        return "adminRegister";
    }

    @PostMapping("/adminRegister")
    public String adminRegister_post(MemberForm form, Model model) {
        MemberBean member = new MemberBean();
        member.setClID(form.getClID());
        member.setClPW(form.getClPW());
        member.setClName(form.getClName());
        member.setClUser(form.getClUser());
        member.setClTel(form.getClTel());
        member.setClEmail(form.getClEmail());
        member.setClDCName1(form.getClDCName1());
        member.setClDCTime1(form.getClDCTime1());
        member.setClDCRate1(form.getClDCRate1());
        member.setClDCName2(form.getClDCName2());
        member.setClDCTime2(form.getClDCTime2());
        member.setClDCRate2(form.getClDCRate2());
        member.setClDCName3(form.getClDCName3());
        member.setClDCTime3(form.getClDCTime3());
        member.setClDCRate3(form.getClDCRate3());
        member.setClDCName4(form.getClDCName4());
        member.setClDCTime4(form.getClDCTime4());
        member.setClDCRate4(form.getClDCRate4());
        member.setClDCName5(form.getClDCName5());
        member.setClDCTime5(form.getClDCTime5());
        member.setClDCRate5(form.getClDCRate5());
        member.setClDCName6(form.getClDCName6());
        member.setClDCTime6(form.getClDCTime6());
        member.setClDCRate6(form.getClDCRate6());
        member.setClMemo(form.getClMemo());
        member.setClDCUse(form.getClDCUse());
        member.setClGrpiDx(form.getClGrpiDx());

        return "adminRegister";
    }

}
