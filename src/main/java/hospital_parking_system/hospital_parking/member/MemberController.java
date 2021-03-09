package hospital_parking_system.hospital_parking.member;


import hospital_parking_system.hospital_parking.carInfo.CarService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final SessionBean sessionBean;
    private final CarService carService;
//메인페이지 첫 화면 페이지
    @GetMapping("")
    public String main(Model model, HttpServletRequest request) {
        model.addAttribute("loginForm", new loginForm());
        Boolean mobile_or_web = carService.get_Mobile_Or_Web(request.getHeader("user-agent"));
        if(mobile_or_web==true){
            return "m_main";
        }else{
            return "main";
        }
        }


//메인페이지 첫 화면 페이지, 로그인화면 POST
    @PostMapping("")
    public String main_login(loginForm form, Model model, HttpServletRequest request) {
//      로그인 폼을 받아와서 데이터를 끄낸다, 아이디와 비밀번호를 각 빈에 저장 후 데이터가 있는지 확인한다.
        MemberBean member = new MemberBean();
        member.setClID(form.getName());
        member.setClPW(form.getPass());

        AdminBean admin = new AdminBean();
        admin.setAdmID(form.getName());
        admin.setAdmPW(form.getPass());

        if (memberService.loginMember(member) != null) {
//          처음, 데이터가 널이 아닐 경우, 로그인 허용한다. 이왕 동시에 세션에 로그인 후 얻은 데이터값을 저장한다.
            MemberBean memberBean = memberService.loginMember(member);
            sessionBean.setMemberbean(memberBean);

            return "redirect:/searchCarInfo";

        } else if (memberService.loginAdmin(admin) != null) {
//            만약 첫번째 검사 시, 널 값이라면 admin 검사 로그인을 실시한다. 위와 같은 로직으로 admin 페이지로 로그인을 허용한다.
//            세션값도 저장한다.
            AdminBean adminBean = memberService.loginAdmin(admin);
            sessionBean.setAdminbean(adminBean);
            return "redirect:/adminRegSearch";
        } else {
//          만약 로그인 시 아이디와 비밀번호에 이상이 있다면 에러 메세지를 발생시킨다.
            model.addAttribute("alert", "아이디와 비밀번호를 확인해주세요.");
            Boolean mobile_or_web = carService.get_Mobile_Or_Web(request.getHeader("user-agent"));
            if(mobile_or_web==true){
                return "m_main";
            }else{
                return "main";
            }
        }
    }


}
