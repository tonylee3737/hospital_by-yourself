package hospital_parking_system.hospital_parking.adminPage;

import hospital_parking_system.hospital_parking.member.MemberBean;
import hospital_parking_system.hospital_parking.member.MemberService;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

//먼저 관리자 그룹 테이블에서 그룹inx와 이름을 가져온다(빈으로가져오자), 폼에 데이터 뿌리기, name: idx값 value: GrpName, 사용미사용은 0, 1 -> post로 폼 받고 insert parking_clss하기.
@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
//@Rollback(value = false)
public class AdminControllerTest {

    @Autowired
    private AdminService adminService;

    @Autowired
    private MemberService memberService;



    @Test
    public void 할인부서조회테스트() {
        //given
        List<AdminManagerBean> adminManagerBeans = adminService.selectAdminManager();
        //when
        AdminManagerBean adminManagerBean = adminManagerBeans.get(0);
        //then
        Assertions.assertThat(adminManagerBean.getGrpName()).isEqualTo("관리자");
    }

    @Test
    public void 그룹조회테스트() {

        //given
        List<GroupBean> groupBeans = adminService.selectGroupList();

        //when
        GroupBean groupBean = groupBeans.get(0);
        //then
        Assertions.assertThat(groupBean.getGrpName()).isEqualTo("관리자");
    }

    @Test
     public void 멤버가입테스트() {
     //given
        MemberBean memberBean = new MemberBean();
        memberBean.setClGrpiDx("1");
        memberBean.setClID("tony");
        memberBean.setClPW("123");
        adminService.insertMember(memberBean);

        //when
        MemberBean memberBean1 = memberService.loginMember(memberBean);


        //then
        Assertions.assertThat(memberBean1.getClID()).isEqualTo("tony");
     }
     @Test
      public void 멤버가져오기테스트() {
      //given
         MemberBean memberBean = new MemberBean();
         memberBean.setCliDx("1");

      //when
         MemberBean memberBean1 = adminService.selectOneParking_class(memberBean);
         //then
         Assertions.assertThat(memberBean1.getClID()).isEqualTo("ton");
      }
      @Test
     public void 업데이트멤버테스트(){
     //given
     MemberBean memberBean = new MemberBean();
     //when
        memberBean.setClID("tonyTest");
        memberBean.setCliDx("1");
        memberBean.setClPW("1");
        adminService.updateMember(memberBean);
        MemberBean memberBean1 = adminService.selectOneParking_class(memberBean);
        //then
        Assertions.assertThat(memberBean.getClID()).isEqualTo(memberBean1.getClID());
     }
     @Test
      public void 매니져삭제() {
      //given
         MemberBean memberBean = new MemberBean();
         memberBean.setCliDx("1");
         adminService.deleteOneManager(memberBean);
      //when

      //then
      }
      @Test
       public void  insert그룹() {
       //given
          GroupBean groupBean = new GroupBean();
          groupBean.setGrpName("관호테스트");
          groupBean.setGrpMemo("관호테스트");
       //when
          adminService.insertGroup(groupBean);
          List<GroupBean> groupBeans = adminService.selectGroupList();
          GroupBean groupBean1 = groupBeans.get(groupBeans.size()-1);
       //then
          Assertions.assertThat(groupBean1.getGrpName()).isEqualTo("관호테스트");
    }
}