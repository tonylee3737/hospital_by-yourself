package hospital_parking_system.hospital_parking.member;


import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.lang.reflect.Member;
import java.util.List;

@Repository
@Mapper
public interface MemberMapper {

    MemberBean loginMember(MemberBean bean);

    AdminBean loginAdmin(AdminBean bean);

//    이하 사용하지 않음
    MemberBean checkMemberId(MemberBean bean);

    MemberBean loginMember_with_idx_pass(MemberBean bean);

    void deleteMember(String ClID);

    void updateMemberInfo(MemberBean bean);

    void updateMemberPass(MemberBean bean);

    List<DcNameInfo> selectAllDcNameFromCliDx(MemberBean memberBean);
}
