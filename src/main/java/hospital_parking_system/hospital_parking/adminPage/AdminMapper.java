package hospital_parking_system.hospital_parking.adminPage;

import hospital_parking_system.hospital_parking.member.MemberBean;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface AdminMapper {
    List<AdminManagerBean> selectAdminManager();

    List<GroupBean> selectGroupList();

    void insertMember(MemberBean bean);
}
