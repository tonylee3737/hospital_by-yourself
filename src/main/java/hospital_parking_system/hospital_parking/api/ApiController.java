package hospital_parking_system.hospital_parking.api;


import hospital_parking_system.hospital_parking.adminPage.AdminMapper;
import hospital_parking_system.hospital_parking.member.MemberBean;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RequiredArgsConstructor
@RestController
public class ApiController {

    final private AdminMapper adminMapper;

    @PostMapping("/api/test")
    public MemberBean2 ApiTest(@RequestBody MemberBean memberBean) {
        String name = "gwanho";
//        return new MemberBean2(name);
        return new MemberBean2(name);
    }

    @Data
    static class MemberBean2 {
        private String name;
        private String height;

        public MemberBean2(String name) {
            this.name = name;
            this.height = "181";
        }
    }

    @GetMapping("/api/test2")
    public String ApiTest2(@RequestBody String) {
        return "hello world";
    }
}
