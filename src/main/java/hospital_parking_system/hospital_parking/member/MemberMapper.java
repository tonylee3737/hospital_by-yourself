package hospital_parking_system.hospital_parking.member;


import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface MemberMapper {
    MemberBean selectMember(MemberBean bean);


    MemberBean loginMember(MemberBean bean);

    AdminBean loginAdmin(AdminBean bean);

    void insertMember(MemberBean bean);

    void deleteMember(String ClID);
}
