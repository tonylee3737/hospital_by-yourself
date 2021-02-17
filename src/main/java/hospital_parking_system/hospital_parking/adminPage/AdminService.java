package hospital_parking_system.hospital_parking.adminPage;

import hospital_parking_system.hospital_parking.member.MemberBean;

import java.util.List;

public interface AdminService {
    List<AdminManagerBean> selectAdminManager();

    List<GroupBean> selectGroupList();

    void insertMember(MemberBean bean);

    MemberBean selectOneParking_class(MemberBean bean);

    void updateMember(MemberBean bean);

    void deleteOneManager(MemberBean bean);

    void insertGroup(GroupBean groupBean);

    GroupBean selectOneGroup(GroupBean groupBean);

    void deleteOneGroup(GroupBean groupBean);

    MemberBean Procedure_registerManager(MemberBean bean);
}
