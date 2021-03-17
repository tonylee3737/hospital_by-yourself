package hospital_parking_system.hospital_parking.member;


import java.util.List;

public interface MemberService {


    MemberBean loginMember(MemberBean bean);

    MemberBean loginMember_with_idx_pass(MemberBean bean);

    MemberBean checkMemberId(MemberBean bean);

    AdminBean loginAdmin(AdminBean bean);

    void deleteMember(String ClID);

    void updateMemberInfo(MemberBean bean);

    void updateMemberPass(MemberBean bean);

    List<DcNameInfo> selectAllDcNameFromCliDx(MemberBean memberBean);

    List<DcNameInfo> countDcName(List<DcNameInfo> dcNameInfos);

}
