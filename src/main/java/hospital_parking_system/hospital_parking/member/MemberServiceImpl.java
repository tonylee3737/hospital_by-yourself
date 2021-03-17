package hospital_parking_system.hospital_parking.member;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    final private MemberMapper memberMapper;


    @Override
    public MemberBean loginMember(MemberBean bean) {
        return memberMapper.loginMember(bean);
    }

    @Override
    public MemberBean loginMember_with_idx_pass(MemberBean bean) {
        return memberMapper.loginMember_with_idx_pass(bean);
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

    @Override
    public void updateMemberInfo(MemberBean bean) {
        memberMapper.updateMemberInfo(bean);
    }

    @Override
    public void updateMemberPass(MemberBean bean) {
        memberMapper.updateMemberPass(bean);
    }

    @Override
    public List<DcNameInfo> selectAllDcNameFromCliDx(MemberBean memberBean) {
        return memberMapper.selectAllDcNameFromCliDx(memberBean);
    }

    @Override
    public List<DcNameInfo> countDcName(List<DcNameInfo> dcNameInfos) {
//        List<DcNameInfo> dcNameInfoList = new ArrayList<>();
//        int count =0;
//        for (DcNameInfo dcNameInfo : dcNameInfos) {
//            dcNameInfoList.add(dcNameInfo);
//            dcNameInfoList.contains()
//            if(dcNameInfoList.get(count).getDcName().equals(dcNameInfos.get(count).getDcName()){
//                dcNameInfoList.get(count).setDcNameCount(count+1);
//            }
//            count++;
//        }

        return null;
    }

}
