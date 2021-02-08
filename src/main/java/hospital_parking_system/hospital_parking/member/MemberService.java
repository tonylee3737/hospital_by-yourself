package hospital_parking_system.hospital_parking.member;


public interface MemberService {

    void insertMember(MemberBean bean);

    MemberBean selectMember(MemberBean bean);

    MemberBean loginMember(MemberBean bean);

    AdminBean loginAdmin(AdminBean bean);

    void deleteMember(String ClID);


}
