package hospital_parking_system.hospital_parking.adminPage;


import hospital_parking_system.hospital_parking.member.MemberBean;
import hospital_parking_system.hospital_parking.member.MemberMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService{

    final private AdminMapper adminMapper;
    final private MemberMapper memberMapper;

    @Override
    public List<AdminManagerBean> selectAdminManager() {
        return adminMapper.selectAdminManager();
    }

    @Override
    public List<GroupBean> selectGroupList() {
        return adminMapper.selectGroupList();
    }

    @Override
    public GroupBean selectOneGroupFromName(GroupBean bean) {
        return adminMapper.selectOneGroupFromName(bean);
    }

    @Override
    public void insertMember(MemberBean bean) {
        if (memberMapper.loginMember(bean)!=null) {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }else {
            adminMapper.insertMember(bean);

        }
    }

    @Override
    public MemberBean selectOneParking_class(MemberBean bean) {
        return adminMapper.selectOneParking_class(bean);
    }

    @Override
    public void updateMember(MemberBean bean) {
        adminMapper.updateMember(bean);
    }

    @Override
    public void deleteOneManager(MemberBean bean) {
        adminMapper.deleteOneManager(bean);
    }

    @Override
    public void insertGroup(GroupBean groupBean) {
        adminMapper.insertGroup(groupBean);

    }

    @Override
    public void updateGroup(GroupBean groupBean) {
        adminMapper.updateGroup(groupBean);
    }

    @Override
    public GroupBean selectOneGroup(GroupBean groupBean) {
        return adminMapper.selectOneGroup(groupBean);
    }

    @Override
    public void deleteOneGroup(GroupBean groupBean) {
        adminMapper.deleteOneGroup(groupBean);
    }

    @Override
    public MemberBean Procedure_registerManager(MemberBean bean) {
        return adminMapper.Procedure_registerManager(bean);
    }

}
