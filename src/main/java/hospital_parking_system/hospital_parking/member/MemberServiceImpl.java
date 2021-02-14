package hospital_parking_system.hospital_parking.member;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;

@Service
public class MemberServiceImpl implements MemberService {

    private MemberMapper memberMapper;

    @Autowired
    MemberServiceImpl(MemberMapper memberMapper) {
        this.memberMapper = memberMapper;
    }


    @Override
    public MemberBean loginMember(MemberBean bean) {
        return memberMapper.loginMember(bean);
    }

    @Override
    public AdminBean loginAdmin(AdminBean bean) {
        return memberMapper.loginAdmin(bean);
    }

    @Override
    public void deleteMember(String ClID) {
        memberMapper.deleteMember(ClID);
    }

}
