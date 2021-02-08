package hospital_parking_system.hospital_parking.member;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class MemberController {

    private MemberService memberService;


    @Autowired
    MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @Autowired
    SessionBean sessionBean;

    @GetMapping("/")
    public String main(Model model) {
        model.addAttribute("loginForm", new loginForm());
        return "main";
    }

    @PostMapping("/info")
    public String main_login(loginForm form, Model model, BindingResult result) {
        MemberBean member = new MemberBean();
        member.setClID(form.getName());
        member.setClPW(form.getPass());

        AdminBean admin = new AdminBean();
        admin.setAdmID(form.getName());
        admin.setAdmPW(form.getPass());

        MemberBean getMember = memberService.loginMember(member);

        if (getMember != null) {
//            return "로그인 후 일반 회원 차량 조회 페이지로 이동";
            return "carNumberSearch";
        } else if (memberService.loginAdmin(admin) != null) {
//            return "로그인 후 관리자 페이지로 이동";
            return "adminController";
        } else {
//            return "로그인 처리 안됨 다시 메인페이지로 이동";
            model.addAttribute("alert", "아이디와 비밀번호를 확인해주세요");
            return "main";
        }
    }


}
