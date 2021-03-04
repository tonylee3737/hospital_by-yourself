package hospital_parking_system.hospital_parking.adminPage;

import hospital_parking_system.hospital_parking.carInfo.DiscountedCarInfo;
import hospital_parking_system.hospital_parking.member.MemberBean;

import javax.persistence.Tuple;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.util.List;

public interface AdminService {
    List<AdminManagerBean> selectAdminManager();

    List<GroupBean> selectGroupList();

    GroupBean selectOneGroupFromName(GroupBean bean);

    void insertMember(MemberBean bean);

    MemberBean selectOneParking_class(MemberBean bean);

    void updateMember(MemberBean bean);

    void deleteOneManager(MemberBean bean);

    void insertGroup(GroupBean groupBean);

    void updateGroup(GroupBean groupBean);

    GroupBean selectOneGroup(GroupBean groupBean);

    void deleteOneGroup(GroupBean groupBean);

    MemberBean Procedure_registerManager(MemberBean bean);

//    member to Form
    MemberForm member_To_Form(MemberBean member);

//    form to Member
    MemberBean form_To_Member(MemberForm form);


//  날짜 format 변경하기
    String[] dateTime_calculate(String startDate, String endDate);

//    액셀 다운로드 Method
    void excel_down(List<DiscountedCarInfo> discountedCarInfos, HttpServletResponse response) throws UnsupportedEncodingException;
}
