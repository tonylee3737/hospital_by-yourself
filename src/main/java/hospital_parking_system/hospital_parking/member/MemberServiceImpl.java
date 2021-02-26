package hospital_parking_system.hospital_parking.member;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    final private MemberMapper memberMapper;


    @Override
    public MemberBean loginMember(MemberBean bean) {
        return memberMapper.loginMember(bean);
    }

    @Override
    public MemberBean checkMemberId(MemberBean bean) {
        return memberMapper.checkMemberId(bean);
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
