package hospital_parking_system.hospital_parking.adminPage;


import hospital_parking_system.hospital_parking.carInfo.CarBean;
import hospital_parking_system.hospital_parking.carInfo.CarService;
import hospital_parking_system.hospital_parking.carInfo.ClNameBean;
import hospital_parking_system.hospital_parking.carInfo.DiscountedCarInfo;
import hospital_parking_system.hospital_parking.member.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;
    private final CarService carService;
    private final SessionBean sessionBean;
    private final MemberService memberService;

    //    관리자 페이지, 등록된 할인 차량 리스트 보여주는 페이지,
    @GetMapping("/adminRegSearch")
    public String adminRegSearch(Model model) {
        AdminBean adminBean = sessionBean.getAdminbean();
        if (adminBean == null) {
            return "redirect:/";
        }
        List<DiscountedCarInfo> discountedCarInfos = carService.selectDiscountedCarInfoList();

        List<ClNameBean> clNameBeans = carService.selectClName();

        ClNameBean re_clName = new ClNameBean();
        re_clName.setClName("전체");
        clNameBeans.add(re_clName);
        Collections.reverse(clNameBeans);
        model.addAttribute("clNames", clNameBeans);
        model.addAttribute("discountedCarInfo", discountedCarInfos);
        model.addAttribute("index", 0);
        return "admin/adminRegSearch";
    }

    // 관리자 페이지, 등록된 할인 차량 리스트 중 특정 번호와 날짜와 할인처를 골라 원하는 데이터를 추림.
    @PostMapping("/adminRegSearch")
    public String adminRegSearch_Post(@RequestParam(value = "carNumber") String carNumber,
                                      @RequestParam(value = "startDate") String startDate,
                                      @RequestParam(value = "endDate") String endDate,
                                      @RequestParam(value = "clName") String clName,
                                      @RequestParam(value = "action") String action,
                                      Model model, HttpServletResponse response) throws IOException {
        AdminBean adminBean = sessionBean.getAdminbean();
        if (adminBean == null) {
            return "redirect:/";
        }
//       선택한 날짜 안에 차량 조회 테스트 ( 날짜 포맷 Method)
        String[] dateTime_calculated = adminService.dateTime_calculate(startDate, endDate);
        CarBean car = new CarBean();
        car.setStartDate(dateTime_calculated[0]);
        car.setEndDate(dateTime_calculated[1]);

        car.setVhlNbr(carNumber);
        if (clName.equals("전체")) {
            car.setClName("");
        } else {
            car.setClName(clName);
        }

        //       액셀 다운로드
        List<DiscountedCarInfo> discountedCarInfos = carService.selectDiscountedCarInfoListWithDate(car);
        if (action.equals("액셀")) {
            adminService.excel_down(discountedCarInfos, response);
        }

        List<ClNameBean> clNameBeans = carService.selectClName();
        ClNameBean re_clName = new ClNameBean();
        re_clName.setClName("전체");
        clNameBeans.add(re_clName);
        model.addAttribute("startDate", startDate);
        model.addAttribute("endDate", endDate);
        model.addAttribute("clNames", clNameBeans);
        model.addAttribute("carNumber", carNumber);
        model.addAttribute("return_clName", clName);
        model.addAttribute("discountedCarInfo", discountedCarInfos);
        return "admin/adminRegSearch";
    }

    @GetMapping("/adminRegSearch_edit")
    public String adminRegSearch_edit_get(Model model, @RequestParam(value = "carNumber") String carNumber, AdminRegForm form) {
        CarBean car = new CarBean();
        car.setVhlNbr(carNumber);
        List<DiscountedCarInfo> discountedCarInfos = carService.selectDiscountedCarInfo(car);
        DiscountedCarInfo discountedCarInfo = discountedCarInfos.get(0);
        form.setClName(discountedCarInfo.getClName());
        form.setCarNumber(discountedCarInfo.getCarNumber());
        form.setDCName(discountedCarInfo.getDCName());
        form.setDCTime(discountedCarInfo.getDCTime());
        form.setDCRate(discountedCarInfo.getDCRate());
        form.setUseDiv(discountedCarInfo.getUseDiv());
        form.setDCMemo(discountedCarInfo.getDCMemo());
        form.setDCiDx(discountedCarInfo.getDCiDx());
        form.setVhliDx(discountedCarInfo.getVhliDx());
        form.setCliDx(discountedCarInfo.getCliDx());
        form.setUseDiv(discountedCarInfo.getUseDiv());
        ClNameBean clname = new ClNameBean();
        clname.setClName(discountedCarInfo.getDCName());
        List<ClNameBean> clNameBeans = adminService.selectClNameNotIn(clname);
        model.addAttribute("clNameList", clNameBeans);
        model.addAttribute("form", form);
        model.addAttribute("carNumber", carNumber);
        return "admin/adminRegSearch_edit";
    }

    @PostMapping("/adminRegSearch_edit")
    public String adminRegSearch_edit_post(AdminRegForm form, @RequestParam(value = "h_action") String action,
                                           Model model, RedirectAttributes redirectAttributes) {
        DiscountedCarInfo discountedCarInfo = new DiscountedCarInfo();
        discountedCarInfo.setDCiDx(form.getDCiDx());
        discountedCarInfo.setVhliDx(form.getVhliDx());
        discountedCarInfo.setDCName(form.getDCName());
        discountedCarInfo.setDCRate(form.getDCRate());
        discountedCarInfo.setDCTime(form.getDCTime());
        discountedCarInfo.setDCMemo(form.getDCMemo());
        discountedCarInfo.setCliDx(form.getCliDx());
        discountedCarInfo.setUseDiv(form.getUseDiv());
        String actDiv = (action.equals("수정")) ? "2" : "3";
        discountedCarInfo.setActDiv(actDiv);

        adminService.Procedure_parking_class_discount(discountedCarInfo);
        int result = discountedCarInfo.getResult();
        redirectAttributes.addFlashAttribute("result", result);
        return "redirect:/adminRegSearch";
    }

    //    관리자 페이지 할인 부서 조회 및 등록
    @GetMapping("/adminManaging")
    public String adminManaging(Model model) {
        AdminBean adminBean = sessionBean.getAdminbean();
        if (adminBean == null) {
            return "redirect:/";
        }
        List<AdminManagerBean> adminManagerBeans = adminService.selectAdminManager();
        model.addAttribute("adminList", adminManagerBeans);
        return "admin/adminManaging";
    }


    // 관리자 페이지 할인부서 조회 및 등록 페이지에서 등록된 데이터를 수정.
    @GetMapping("/adminRegister_edit")
    public String adminRegister_edit(@RequestParam(value = "id") String idx, Model model) {
        AdminBean adminBean = sessionBean.getAdminbean();
        if (adminBean == null) {
            return "redirect:/";
        }
        List<GroupBean> groupBeans = adminService.selectGroupList();
        MemberBean memberBean = new MemberBean();
        memberBean.setCliDx(idx);
        MemberBean member = adminService.selectOneParking_class(memberBean);

        //idx를 통해 가져온 멤버의 사항을 MemberForm에 주입시키기.
        MemberForm form = adminService.member_To_Form(member);

        model.addAttribute("memberForm", form);

        model.addAttribute("groupList", groupBeans);
        return "admin/adminRegister_edit";
    }

    // 관리자 페이지 할인부서 조회 및 등록 페이지에서 등록된 데이터를 수정 후 POST
    @PostMapping("/adminRegister_edit")
    public String adminRegister_edit_post(@Valid MemberForm form, BindingResult result, Model model, RedirectAttributes redirectAttributes) {

        AdminBean adminBean = sessionBean.getAdminbean();
        if (adminBean == null) {
            return "redirect:/";
        }

        List<GroupBean> groupBeans = adminService.selectGroupList();
        //수정 중 아이디값과 비밀번호가 값 유효성 검사
        if (result.hasErrors()) {
            model.addAttribute("groupList", groupBeans);
            return "admin/adminRegister_edit";
        }
        // form _ to Member
        MemberBean member = adminService.form_To_Member(form);


        //        수정 중 비밀번호 유효성 검사
        MemberBean memberBean = memberService.loginMember_with_idx_pass(member);
        if (memberBean == null) {
            model.addAttribute("groupList", groupBeans);
            model.addAttribute("alert_password_not_match", "비밀번호가 일치하지 않습니다.");
            return "admin/adminRegister_edit";
        }

        //  수정 중 아이디 유효성 검사
        MemberBean member_id_check = memberService.checkMemberId(member);
        if (member_id_check != null) {
            //아이디가 존재합니다.
            //존재하는 아이디의 idx와 form의 idx가 일치하면 수정 가능.
            if (member_id_check.getCliDx().equals(form.getCliDx())) {
                adminService.Procedure_registerManager(member);
                redirectAttributes.addFlashAttribute("form", "form_edit");
                return "redirect:/adminManaging";
            } else {
                model.addAttribute("exist_id", "중복된 아이디가 존재합니다.");
                return "admin/adminRegister_edit";
            }
        }

        adminService.Procedure_registerManager(member);
        redirectAttributes.addFlashAttribute("form", "form_edit");
        return "redirect:/adminManaging";
    }

    //    관리자 페이지, 할인 부서 조회 및 등록에서 등록하는 페이지
    @GetMapping("/adminRegister")
    public String adminRegister(Model model) {
        AdminBean adminBean = sessionBean.getAdminbean();
        if (adminBean == null) {
            return "redirect:/";
        }
        model.addAttribute("memberForm", new MemberForm());
        List<GroupBean> groupBeans = adminService.selectGroupList();
        model.addAttribute("groupList", groupBeans);
        return "admin/adminRegister";
    }

    // 관리자 페이지, 할인 부서 조회 및 등록 후 POST
    @PostMapping("/adminRegister")
    public String adminRegister_post(@Valid MemberForm form, BindingResult result, Model model, RedirectAttributes redirectAttributes) {
        AdminBean adminBean = sessionBean.getAdminbean();
        if (adminBean == null) {
            return "redirect:/";
        }
        List<GroupBean> groupBeans = adminService.selectGroupList();
        if (result.hasErrors()) {
            model.addAttribute("groupList", groupBeans);
            return "admin/adminRegister";
        }

        MemberBean member = adminService.form_To_Member(form);


        // 아이디 유효성 검사
        MemberBean memberBean = memberService.checkMemberId(member);
        if (memberBean != null) {
            model.addAttribute("alert_exist", "이미 존재하는 관리자입니다.");
            return "admin/adminRegister";
        }
        adminService.Procedure_registerManager(member);
        redirectAttributes.addFlashAttribute("form", "form_regi");
        return "redirect:/adminManaging";
    }

    // 등록된 관리자를 삭제하는 method인데 사용하진 않음//
    @GetMapping("adminDeleteManager")
    public String adminDeleteManager(@RequestParam(value = "id") String idx, Model model) {
        AdminBean adminBean = sessionBean.getAdminbean();
        if (adminBean == null) {
            return "redirect:/";
        }
        MemberBean memberBean = new MemberBean();
        memberBean.setCliDx(idx);
        adminService.deleteOneManager(memberBean);
        return "redirect:/adminManaging";
    }

    //    관리자 페이지 그룹관리 및 등록
    @GetMapping("/adminGroupManaging")
    public String adminGroupList(Model model) {
        AdminBean adminBean = sessionBean.getAdminbean();
        if (adminBean == null) {
            return "redirect:/";
        }
        List<GroupBean> groupBeans = adminService.selectGroupList();
        model.addAttribute("groupList", groupBeans);
        return "admin/adminGroupManaging";
    }

    //    관리자 페이지 그룹관리 및 등록페이지에서 그룹 등록하기.
    @GetMapping("/adminRegister_group")
    public String adminRegister_group(Model model) {
        AdminBean adminBean = sessionBean.getAdminbean();
        if (adminBean == null) {
            return "redirect:/";
        }
        model.addAttribute("adminForm", new AdminForm());
        return "admin/adminRegister_group";
    }

    // 관리자 페이지 그룹 관리 및 등록페이지에서 그룹 등록 후 POST
    @PostMapping("/adminRegister_group")
    public String adminRegister_group_post(@Valid AdminForm form, BindingResult result, Model model, RedirectAttributes redirectAttributes) {
        AdminBean adminBean = sessionBean.getAdminbean();
        if (adminBean == null) {
            return "redirect:/";
        }

        if (result.hasErrors()) {
            return "admin/adminRegister_group";
        }
        GroupBean groupBean = new GroupBean();
        groupBean.setGrpName(form.getGrpName());
        GroupBean groupBean1 = adminService.selectOneGroupFromName(groupBean);
        if (groupBean1 != null) {
            model.addAttribute("alertExistName", "이미 존재하는 그룹입니다.");
            return "admin/adminRegister_group";
        }

        groupBean.setGrpMemo(form.getGrpMemo());
        adminService.insertGroup(groupBean);
        redirectAttributes.addFlashAttribute("form", "form_regi");
        return "redirect:/adminGroupManaging";
    }

    //그룹 관리 및 등록 페이지에서 수정
    @GetMapping("adminRegister_group_edit")
    public String adminRegister_group_edit(@RequestParam(value = "id") String idx, Model model) {
        AdminBean adminBean = sessionBean.getAdminbean();
        if (adminBean == null) {
            return "redirect:/";
        }
        AdminForm form = new AdminForm();
        GroupBean groupBean = new GroupBean();
        groupBean.setGrpiDx(idx);
        GroupBean groupBean1 = adminService.selectOneGroup(groupBean);
        form.setGrpiDx(groupBean1.getGrpiDx());
        form.setGrpName(groupBean1.getGrpName());
        form.setGrpMemo(groupBean1.getGrpMemo());
        model.addAttribute("adminForm", form);
        return "admin/adminRegister_group_edit";
    }

    //그룹 관리 및 등록 페이지에서 수정 후 POST
    @PostMapping("adminRegister_group_edit")
    public String adminRegister_group_edit_post(@Valid AdminForm form, BindingResult result, Model model, RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) {
            return "admin/adminRegister_group_edit";
        }
        GroupBean group = new GroupBean();
        group.setGrpiDx(form.getGrpiDx());
        group.setGrpName(form.getGrpName());
        GroupBean groupBean1 = adminService.selectOneGroupFromName(group);
        if (groupBean1 != null) {
            if (groupBean1.getGrpiDx().equals(form.getGrpiDx())) {
                group.setGrpMemo(form.getGrpMemo());
                adminService.updateGroup(group);
                redirectAttributes.addFlashAttribute("form", "form_edit");
                return "redirect:adminGroupManaging";
            } else {
                model.addAttribute("alertExistName", "이미 존재하는 그룹입니다.");
                return "admin/adminRegister_group_edit";
            }
        }
        group.setGrpMemo(form.getGrpMemo());
        adminService.updateGroup(group);
        redirectAttributes.addFlashAttribute("form", "form_edit");
        return "redirect:adminGroupManaging";
    }

//    @GetMapping("m_userInfo_checking")
//    public String member_info_m_get(Model model) {
//
//        //테스팅 끝나고 수정하기
//
//        // 이까지 수정하기
//        MemberBean memberbean = sessionBean.getMemberbean();
//        LoginForm form = new LoginForm();
//        form.setName(memberbean.getClID());
//        model.addAttribute("loginForm", form);
//        return "member/m_userInfo_checking";
//    }

    @PostMapping("m_userInfo_checking")
    public String member_info_m_post(LoginForm form, Model model) {
        //테스팅 끝나고 수정하기

        // 이까지 수정하기
        MemberBean member = new MemberBean();
        member.setClID(form.getName());
        member.setClPW(form.getPass());
        MemberBean memberBean = memberService.loginMember(member);
        if (memberBean == null) {
            model.addAttribute("alert", "비밀번호를 확인해주세요.");
            return "member/m_userInfo_checking";
        } else {
            M_MemberForm m_member_form = new M_MemberForm();
            m_member_form.setClID(memberBean.getClID());
            m_member_form.setClTel(memberBean.getClTel());
            m_member_form.setClEmail(memberBean.getClEmail());
            m_member_form.setCliDx(memberBean.getCliDx());
            m_member_form.setClMemo(memberBean.getClMemo());
            m_member_form.setClPW(memberBean.getClPW());
            model.addAttribute("form", m_member_form);
            return "member/m_userInfo_edit";
        }
    }

    @PostMapping("m_userInfo_edit")
    public String m_userInfo_edit_post(M_MemberForm m_memberForm, Model model, @RequestParam(required = false, value = "action") String edit_pass, RedirectAttributes redirectAttributes) {


        MemberBean memberBean = adminService.m_memberFrom_To_MemberBean(m_memberForm);
        if (edit_pass != null) {
            model.addAttribute("form", m_memberForm);
            return "member/m_password_edit";
        } else {
            memberService.updateMemberInfo(memberBean);
            redirectAttributes.addFlashAttribute("alert", "정보가 수정되었습니다.");
            return "redirect:m_userInfo_checking";
        }
    }
    @PostMapping("m_password_edit")
    public String m_password_edit_post(M_MemberForm m_memberForm, Model model, RedirectAttributes redirectAttributes) {
        MemberBean member = new MemberBean();
        MemberBean memberBean = adminService.m_memberFrom_To_MemberBean(m_memberForm);
        memberBean.setClPW(m_memberForm.getPass1());
        MemberBean member_exist = memberService.loginMember(memberBean);
        if (member_exist != null) {
            memberBean.setClPW(m_memberForm.getPass2());
            memberService.updateMemberInfo(memberBean);
            redirectAttributes.addFlashAttribute("alert", "비밀번호가 변경되었습니다.");
            return "redirect:/searchCarInfo";
        } else {
            model.addAttribute("alert", "비밀번호를 확인해주세요.");
            model.addAttribute("form", m_memberForm);
            return "member/m_password_edit";
        }
    }
    @GetMapping("m_dc_info")
    public String m_dc_info_get(Model model){
        MemberBean memberbean = sessionBean.getMemberbean();
        if(memberbean==null){
            return "redirect:/";
        }else{
            List<DcInfo> dcInfos = adminService.dcInfo_list(memberbean);
            model.addAttribute("dc_infos", dcInfos);
            return "member/m_dc_info";
        }
    }
    @GetMapping("m_userInfo_checking")
    public String test(Model model){
        model.addAttribute("form", new M_MemberForm());
        return "member/m_userInfo_edit";
    }

    }




    //    그룹 삭제 페이지인데 사용하진 않음.
//    @GetMapping("adminDeleteGroup")
//    public String adminDeleteGroup(@RequestParam(value = "id") String idx) {
//        AdminBean adminBean = sessionBean.getAdminbean();
//        if (adminBean == null) {
//            return "redirect:/";
//        }
//        GroupBean groupBean = new GroupBean();
//        groupBean.setGrpiDx(idx);
//        adminService.deleteOneGroup(groupBean);
//        return "redirect:/adminGroupManaging";
//    }


