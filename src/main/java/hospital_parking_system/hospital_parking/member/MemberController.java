package hospital_parking_system.hospital_parking.member;


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


    @GetMapping("")
    public String main(Model model) {
        model.addAttribute("loginForm", new loginForm());
        return "main";
//        MemberBean memberbean = new MemberBean();
//        memberbean.setClID("tony");
//        memberbean.setClPW("1");
//        sessionBean.setMemberbean(memberbean);
//        return "member/searchCarInfo";
    }

    @PostMapping("")
    public String main_login(loginForm form, Model model, BindingResult result, HttpServletRequest request) {
        MemberBean member = new MemberBean();
        member.setClID(form.getName());
        member.setClPW(form.getPass());

        AdminBean admin = new AdminBean();
        admin.setAdmID(form.getName());
        admin.setAdmPW(form.getPass());

        if (memberService.loginMember(member) != null) {
            System.out.println("null");
//            return "로그인 후 일반 회원 차량 조회 페이지로 이동";
            MemberBean memberBean = memberService.loginMember(member);
            sessionBean.setMemberbean(memberBean);
            return "member/searchCarInfo";
        } else if (memberService.loginAdmin(admin) != null) {
//            return "로그인 후 관리자 페이지로 이동";
            AdminBean adminBean = memberService.loginAdmin(admin);
            sessionBean.setAdminbean(adminBean);
            return "redirect:/adminRegSearch";
        } else {
//            return "로그인 처리 안됨 다시 메인페이지로 이동";
            model.addAttribute("alert", "아이디와 비밀번호를 확인해주세요.");
            return "main";
        }
    }


}
