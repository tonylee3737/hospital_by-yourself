package hospital_parking_system.hospital_parking.member;


import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

@SessionScope
@Component
@Getter @Setter
public class SessionBean {
    private MemberBean memberbean;
    private AdminBean adminbean;
}
