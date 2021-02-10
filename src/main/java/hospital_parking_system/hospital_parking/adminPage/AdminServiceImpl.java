package hospital_parking_system.hospital_parking.adminPage;


import hospital_parking_system.hospital_parking.member.MemberBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminServiceImpl implements AdminService{

    private AdminMapper adminMapper;

    @Autowired
    AdminServiceImpl(AdminMapper adminMapper) {
        this.adminMapper = adminMapper;

    }

    @Override
    public List<AdminManagerBean> selectAdminManager() {
        return adminMapper.selectAdminManager();
    }

    @Override
    public List<GroupBean> selectGroupList() {
        return adminMapper.selectGroupList();
    }

    @Override
    public void insertMember(MemberBean bean) {}
}
