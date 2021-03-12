package hospital_parking_system.hospital_parking.adminPage;

import hospital_parking_system.hospital_parking.carInfo.ClNameBean;
import hospital_parking_system.hospital_parking.carInfo.DiscountedCarInfo;
import hospital_parking_system.hospital_parking.member.MemberBean;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface AdminMapper {
    List<AdminManagerBean> selectAdminManager();

    List<GroupBean> selectGroupList();

    GroupBean selectOneGroup(GroupBean groupBean);

    void deleteOneGroup(GroupBean groupBean);

    GroupBean selectOneGroupFromName(GroupBean bean);
    void insertMember(MemberBean bean);

    MemberBean selectOneParking_class(MemberBean bean);

    void updateMember(MemberBean bean);

    void deleteOneManager(MemberBean bean);

    void insertGroup(GroupBean groupBean);

    void updateGroup(GroupBean groupBean);

    List<ClNameBean> selectClNameNotIn(ClNameBean clNameBean);

    MemberBean Procedure_registerManager(MemberBean bean);

    DiscountedCarInfo Procedure_parking_class_discount(DiscountedCarInfo discountedCarInfo);
}
