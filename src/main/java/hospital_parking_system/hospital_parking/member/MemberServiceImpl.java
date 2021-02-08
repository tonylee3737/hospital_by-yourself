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
    public void insertMember(MemberBean bean) {
        if (memberMapper.selectMember(bean)!=null) {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }else {
            memberMapper.insertMember(bean);
        }
    }

    @Override
    public MemberBean selectMember(MemberBean bean) {
        return memberMapper.selectMember(bean);
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
