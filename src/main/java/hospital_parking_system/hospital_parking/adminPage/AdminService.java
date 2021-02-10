package hospital_parking_system.hospital_parking.adminPage;

import hospital_parking_system.hospital_parking.member.MemberBean;

import java.util.List;

public interface AdminService {
    List<AdminManagerBean> selectAdminManager();

    List<GroupBean> selectGroupList();

    void insertMember(MemberBean bean);
}
