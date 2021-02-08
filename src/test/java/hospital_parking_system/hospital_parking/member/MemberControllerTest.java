package hospital_parking_system.hospital_parking.member;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Member;

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


    @Test
//    @Rollback(value = false)
    public void 회원가입테스트() {
        //given
        MemberBean bean = new MemberBean();
        bean.setClID("gwanho");
        bean.setClPW("gwanho");

        //when
        memberService.insertMember(bean);

        MemberBean memberBean = memberService.selectMember(bean);
//        memberService.deleteMember("test12345");
        //then
        Assertions.assertThat(bean.getClID()).isEqualTo(memberBean.getClID());
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
        memberService.insertMember(bean1);
        memberService.insertMember(bean2);
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



}