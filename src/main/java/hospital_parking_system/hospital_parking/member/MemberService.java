package hospital_parking_system.hospital_parking.member;


public interface MemberService {


    MemberBean loginMember(MemberBean bean);

    AdminBean loginAdmin(AdminBean bean);

    void deleteMember(String ClID);


}
