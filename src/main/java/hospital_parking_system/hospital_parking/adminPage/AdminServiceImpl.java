package hospital_parking_system.hospital_parking.adminPage;


import hospital_parking_system.hospital_parking.member.MemberBean;
import hospital_parking_system.hospital_parking.member.MemberMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService{

    final private AdminMapper adminMapper;
    final private MemberMapper memberMapper;
//
//    @Autowired
//    AdminServiceImpl(AdminMapper adminMapper) {
//        this.adminMapper = adminMapper;
//
//    }

    @Override
    public List<AdminManagerBean> selectAdminManager() {
        return adminMapper.selectAdminManager();
    }

    @Override
    public List<GroupBean> selectGroupList() {
        return adminMapper.selectGroupList();
    }

    @Override
    public void insertMember(MemberBean bean) {
        if (memberMapper.loginMember(bean)!=null) {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }else {
            adminMapper.insertMember(bean);

        }
    }
}
