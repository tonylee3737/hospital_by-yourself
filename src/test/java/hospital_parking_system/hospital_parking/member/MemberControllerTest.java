package hospital_parking_system.hospital_parking.member;

import hospital_parking_system.hospital_parking.adminPage.AdminService;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Member;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;


//회원가입, 중복가입 처리 불가 구현
//로그인, 일반 로그인 시 아이디가 없으면 admin DB로 다시 확인, 없으면 알림.
//차량 번호 조회하기

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class MemberControllerTest {


    @Autowired
    MemberService memberService;

    @Autowired
    AdminService adminService;

    @Test
    @Rollback(value = false)
    public void 회원가입테스트() {
        //given
//        MemberBean bean = new MemberBean();
//        bean.setClID("gwanho");
//        bean.setClPW("gwanho");

        //when
//        adminService.insertMember(bean);

//        MemberBean memberBean = memberService.loginMember(bean);
        memberService.deleteMember("teset1");
        memberService.deleteMember("test123");
        memberService.deleteMember("test123123");
        memberService.deleteMember("123123");
        memberService.deleteMember("sadf");
        memberService.deleteMember("asdfsdf");
        //then
//        Assertions.assertThat(bean.getClID()).isEqualTo(memberBean.getClID());
    }

    @Test(expected = IllegalStateException.class)
    public void 중복회원테스트() {
        //given
        MemberBean bean1 = new MemberBean();
        MemberBean bean2 = new MemberBean();
        bean1.setClID("tony");
        bean1.setClPW("tony");
        bean2.setClID("tony");
        bean2.setClPW("tony");

        //when
        adminService.insertMember(bean1);
        adminService.insertMember(bean2);

        //then
        fail("에외가 발생해야한다.");
    }

    @Test
    public void 로그인테스트() {
        //given
        MemberBean member = new MemberBean();
        member.setClID("test");
        member.setClPW("1");
        AdminBean adminMember = new AdminBean();
        adminMember.setAdmID("admin");
        adminMember.setAdmPW("1");

        //when
        MemberBean memberBean = memberService.loginMember(member);
        AdminBean adminBean = memberService.loginAdmin(adminMember);
        //then
        Assertions.assertThat(memberBean.getClID()).isEqualTo("test");
        Assertions.assertThat(adminBean.getAdmID()).isEqualTo("admin");

    }

    @Test
    @Rollback(value = false)
    public void updateMemberTest() {
        //Given
        MemberBean member = new MemberBean();
        member.setClID("test");
        member.setClPW("1");
        member.setCliDx("95");
        member.setClEmail("tony@gmail.com");
        member.setClTel("0");
        member.setClMemo("없음");
        memberService.updateMemberInfo(member);
        //When
        MemberBean memberBean = memberService.loginMember(member);
        //Then
        Assertions.assertThat(memberBean.getClPW()).isEqualTo(member.getClPW());
    }

    @Test
    public void selectDcName() {
        //Given
        MemberBean memberBean = new MemberBean();
        memberBean.setClID("test");
        memberBean.setClPW("1");
        MemberBean memberBean1 = memberService.loginMember(memberBean);
        List<DcNameInfo> o_dcNameInfos = memberService.selectAllDcNameFromCliDx(memberBean1);
        List<DcNameInfo> n_dcNameInfos = new ArrayList<>();
        //When
//        int[] count = new int[3];
//        String[] dcName_list = {"관호특별할인", "장애인할인용", "아이트로특별할인"};
//
//        for (DcNameInfo dcNameInfo : o_dcNameInfos) {
//            String dcName = dcNameInfo.getDcName();
//            for(int i=0; i<count.length; i++){
//                if(dcName.equals(dcName_list[i])){
//                    count[i]++;
//                }
//            }
//        }
        boolean t = true;
        for (DcNameInfo dcNameInfo : o_dcNameInfos) {
            String dcName = dcNameInfo.getDcName();

            if(n_dcNameInfos.size()==0){
                n_dcNameInfos.add(dcNameInfo);
            }
            if(t==false){
                n_dcNameInfos.add(dcNameInfo);
            }
            for (int i = 0; i < n_dcNameInfos.size(); i++) {
                if (dcName.equals(n_dcNameInfos.get(i).getDcName())) {
                    System.out.println("일치한다");
                    t = true;
                }else{
                    t = false;
                }
            }
        }
    }
}
