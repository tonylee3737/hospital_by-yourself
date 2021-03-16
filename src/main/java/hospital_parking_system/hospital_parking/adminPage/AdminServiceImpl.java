package hospital_parking_system.hospital_parking.adminPage;


import hospital_parking_system.hospital_parking.carInfo.ClNameBean;
import hospital_parking_system.hospital_parking.carInfo.DiscountedCarInfo;
import hospital_parking_system.hospital_parking.member.M_MemberForm;
import hospital_parking_system.hospital_parking.member.MemberBean;
import hospital_parking_system.hospital_parking.member.MemberMapper;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import javax.persistence.Tuple;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService{

    final private AdminMapper adminMapper;
    final private MemberMapper memberMapper;

    @Override
    public List<AdminManagerBean> selectAdminManager() {
        return adminMapper.selectAdminManager();
    }

    @Override
    public List<GroupBean> selectGroupList() {
        return adminMapper.selectGroupList();
    }

    @Override
    public GroupBean selectOneGroupFromName(GroupBean bean) {
        return adminMapper.selectOneGroupFromName(bean);
    }

    @Override
    public void insertMember(MemberBean bean) {
        if (memberMapper.loginMember(bean)!=null) {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }else {
            adminMapper.insertMember(bean);

        }
    }

    @Override
    public MemberBean selectOneParking_class(MemberBean bean) {
        return adminMapper.selectOneParking_class(bean);
    }

    @Override
    public void updateMember(MemberBean bean) {
        adminMapper.updateMember(bean);
    }

    @Override
    public void deleteOneManager(MemberBean bean) {
        adminMapper.deleteOneManager(bean);
    }

    @Override
    public void insertGroup(GroupBean groupBean) {
        adminMapper.insertGroup(groupBean);

    }

    @Override
    public void updateGroup(GroupBean groupBean) {
        adminMapper.updateGroup(groupBean);
    }

    @Override
    public GroupBean selectOneGroup(GroupBean groupBean) {
        return adminMapper.selectOneGroup(groupBean);
    }

    @Override
    public void deleteOneGroup(GroupBean groupBean) {
        adminMapper.deleteOneGroup(groupBean);
    }

    @Override
    public MemberBean Procedure_registerManager(MemberBean bean) {
        return adminMapper.Procedure_registerManager(bean);
    }

    @Override
    public DiscountedCarInfo Procedure_parking_class_discount(DiscountedCarInfo discountedCarInfo) {
        return adminMapper.Procedure_parking_class_discount(discountedCarInfo);
    }

    @Override
    public List<ClNameBean> selectClNameNotIn(ClNameBean clNameBean) {
        return adminMapper.selectClNameNotIn(clNameBean);
    }

    @Override
    public MemberForm member_To_Form(MemberBean member) {
        MemberForm form = new MemberForm();
        form.setCliDx(member.getCliDx());
        form.setClID(member.getClID());
        form.setClPW(member.getClPW());
        form.setClName(member.getClName());
        form.setClUser(member.getClUser());
        form.setClTel(member.getClTel());
        form.setClEmail(member.getClEmail());
        form.setClDCName1(member.getClDCName1());
        form.setClDCCount1(member.getClDCCount1());
        form.setClDCTime1(member.getClDCTime1());
        form.setClDCRate1(member.getClDCRate1());
        form.setClDCName2(member.getClDCName2());
        form.setClDCCount2(member.getClDCCount2());
        form.setClDCTime2(member.getClDCTime2());
        form.setClDCRate2(member.getClDCRate2());
        form.setClDCName3(member.getClDCName3());
        form.setClDCCount3(member.getClDCCount3());
        form.setClDCTime3(member.getClDCTime3());
        form.setClDCRate3(member.getClDCRate3());
        form.setClDCName4(member.getClDCName4());
        form.setClDCCount4(member.getClDCCount4());
        form.setClDCTime4(member.getClDCTime4());
        form.setClDCRate4(member.getClDCRate4());
        form.setClDCName5(member.getClDCName5());
        form.setClDCCount5(member.getClDCCount5());
        form.setClDCTime5(member.getClDCTime5());
        form.setClDCRate5(member.getClDCRate5());
        form.setClDCName6(member.getClDCName6());
        form.setClDCCount6(member.getClDCCount6());
        form.setClDCTime6(member.getClDCTime6());
        form.setClDCRate6(member.getClDCRate6());
        form.setClMemo(member.getClMemo());
        form.setClDCUse(member.getClDCUse());
        form.setClGrpiDx(member.getClGrpiDx());
        return form;
    }




    @Override
    public MemberBean form_To_Member(MemberForm form) {
        MemberBean member = new MemberBean();
        if(form.getCliDx()!=null){
            member.setCliDx(form.getCliDx());
        }else{
            member.setCliDx("0");
        }
        member.setClID(form.getClID());
        member.setClPW(form.getClPW());
        member.setClName(form.getClName());
        member.setClUser(form.getClUser());
        member.setClTel(form.getClTel());
        member.setClEmail(form.getClEmail());
        member.setClDCName1(form.getClDCName1());
        member.setClDCCount1(form.getClDCCount1());
        if (form.getClDCTime1().equals("")) {
            member.setClDCTime1("0");
        } else {
            member.setClDCTime1(form.getClDCTime1());
        }
        if (form.getClDCRate1().equals("")) {
            member.setClDCRate1("0");
        } else {
            member.setClDCRate1(form.getClDCRate1());
        }
        member.setClDCName2(form.getClDCName2());
        member.setClDCCount2(form.getClDCCount2());
        if (form.getClDCTime2().equals("")) {
            member.setClDCTime2("0");
        } else {
            member.setClDCTime2(form.getClDCTime2());
        }
        if (form.getClDCRate2().equals("")) {
            member.setClDCRate2("0");
        } else {
            member.setClDCRate2(form.getClDCRate2());
        }
        member.setClDCName3(form.getClDCName3());
        member.setClDCCount3(form.getClDCCount3());
        if (form.getClDCTime3().equals("")) {
            member.setClDCTime3("0");
        } else {
            member.setClDCTime3(form.getClDCTime3());
        }
        if (form.getClDCRate3().equals("")) {
            member.setClDCRate3("0");
        } else {
            member.setClDCRate3(form.getClDCRate3());
        }
        member.setClDCName4(form.getClDCName4());
        member.setClDCCount4(form.getClDCCount4());
        if (form.getClDCTime4().equals("")) {
            member.setClDCTime4("0");
        } else {
            member.setClDCTime4(form.getClDCTime4());
        }
        if (form.getClDCRate4().equals("")) {
            member.setClDCRate4("0");
        } else {
            member.setClDCRate4(form.getClDCRate4());
        }
        member.setClDCName5(form.getClDCName5());
        member.setClDCCount5(form.getClDCCount5());
        if (form.getClDCTime5().equals("")) {
            member.setClDCTime5("0");
        } else {
            member.setClDCTime5(form.getClDCTime5());
        }
        if (form.getClDCRate5().equals("")) {
            member.setClDCRate5("0");
        } else {
            member.setClDCRate5(form.getClDCRate5());
        }
        member.setClDCName6(form.getClDCName6());
        member.setClDCCount6(form.getClDCCount6());
        if (form.getClDCTime6().equals("")) {
            member.setClDCTime6("0");
        } else {
            member.setClDCTime6(form.getClDCTime6());
        }
        if (form.getClDCRate6().equals("")) {
            member.setClDCRate6("0");
        } else {
            member.setClDCRate6(form.getClDCRate6());
        }
        member.setClMemo(form.getClMemo());
        member.setClDCUse(form.getClDCUse());
        member.setClGrpiDx(form.getClGrpiDx());

        return member;
    }

    @Override
    public String[] dateTime_calculate(String startDate, String endDate) {

        String[] start = startDate.split("-");
        String s_year = start[0];
        String s_month = start[1];
        String s_day = start[2];
        String s_date = s_year + s_month + s_day + "000000";
        String[] end = endDate.split("-");
        String e_year = end[0];
        String e_month = end[1];
        String e_day = end[2];
        String e_date = e_year + e_month + e_day + "235959";
        String[] date = new String[2];
        date[0] = s_date;
        date[1] = e_date;
        return date;
    }


    @Override
    public void excel_down(List<DiscountedCarInfo> discountedCarInfos, HttpServletResponse response) throws UnsupportedEncodingException {
        Workbook book = null;
        XSSFWorkbook xssfWb = null;
        XSSFSheet xssfSheet = null;
        XSSFRow xssfRow = null;
        XSSFCell xssfCell = null;
        int rowNo = 1;
        xssfWb = new XSSFWorkbook();
        SimpleDateFormat format = new SimpleDateFormat("yyyy년 MM월dd일 HH시 mm분ss초");
        String format_time = format.format(System.currentTimeMillis());
        xssfSheet = xssfWb.createSheet("할인차량목록_" + format_time);
        xssfSheet.setColumnWidth(1, (xssfSheet.getColumnWidth(1)) + (short) 2048);
        xssfSheet.setColumnWidth(2, (xssfSheet.getColumnWidth(2)) + (short) 2048);
        xssfSheet.setColumnWidth(3, (xssfSheet.getColumnWidth(3)) + (short) 2048);
        xssfSheet.setColumnWidth(6, (xssfSheet.getColumnWidth(6)) + (short) 2048);
        xssfSheet.setColumnWidth(7, (xssfSheet.getColumnWidth(7)) + (short) 3072);
        xssfRow = xssfSheet.createRow(0);
        xssfCell = xssfRow.createCell((short) 0);
        xssfCell.setCellValue("번호");
        xssfCell = xssfRow.createCell((short) 1);
        xssfCell.setCellValue("차량번호");
        xssfCell = xssfRow.createCell((short) 2);
        xssfCell.setCellValue("할인명");
        xssfCell = xssfRow.createCell((short) 3);
        xssfCell.setCellValue("할인처");
        xssfCell = xssfRow.createCell((short) 4);
        xssfCell.setCellValue("할인(분)");
        xssfCell = xssfRow.createCell((short) 5);
        xssfCell.setCellValue("할인률(%)");
        xssfCell = xssfRow.createCell((short) 6);
        xssfCell.setCellValue("비고");
        xssfCell = xssfRow.createCell((short) 7);
        xssfCell.setCellValue("등록일시");
        xssfCell = xssfRow.createCell((short) 8);
        xssfCell.setCellValue("사용여부");
        xssfCell = xssfRow.createCell((short) 9);
        for (int i = 0; i < discountedCarInfos.size(); i++) {
            xssfRow = xssfSheet.createRow(rowNo++);
            xssfCell = xssfRow.createCell((short) 0);
            xssfCell.setCellValue(i + 1);
            xssfCell = xssfRow.createCell((short) 1);
            xssfCell.setCellValue(discountedCarInfos.get(i).getCarNumber());
            xssfCell = xssfRow.createCell((short) 2);
            xssfCell.setCellValue(discountedCarInfos.get(i).getDCName());
            xssfCell = xssfRow.createCell((short) 3);
            xssfCell.setCellValue(discountedCarInfos.get(i).getClName());
            xssfCell = xssfRow.createCell((short) 4);
            xssfCell.setCellValue(discountedCarInfos.get(i).getDCTime());
            xssfCell = xssfRow.createCell((short) 5);
            xssfCell.setCellValue(discountedCarInfos.get(i).getDCRate());
            xssfCell = xssfRow.createCell((short) 6);
            xssfCell.setCellValue(discountedCarInfos.get(i).getDCMemo());
            xssfCell = xssfRow.createCell((short) 7);
            xssfCell.setCellValue(discountedCarInfos.get(i).getInsDayTime());
            xssfCell = xssfRow.createCell((short) 8);
            String result_Of_DivUse = (discountedCarInfos.get(i).getUseDiv().equals("0")) ? "미사용" : "사용";
            xssfCell.setCellValue(result_Of_DivUse);
            xssfCell = xssfRow.createCell((short) 9);
        }
        String filename_time = "할인차량목록_" + format_time;
        response.setContentType("ms-vnd/excel");
        response.setHeader("Content-Disposition", "attachment; filename=" + new String((filename_time).getBytes("EUC-KR"), "8859_1") + ".xlsx");
        try (OutputStream fileOut = response.getOutputStream()) {
            xssfWb.write(fileOut);
            fileOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Paging get_Paging(List<DiscountedCarInfo> discountedCarInfos) {
        Paging page_list = new Paging();

        return page_list;
    }

    @Override
    public MemberBean m_memberFrom_To_MemberBean(M_MemberForm form) {
        MemberBean member = new MemberBean();
        member.setCliDx(form.getCliDx());
        member.setClID(form.getClID());
        member.setClPW(form.getClPW());
        member.setClTel(form.getClTel());
        member.setClEmail(form.getClEmail());
        member.setClMemo(form.getClMemo());
        return member;
    }

    @Override
    public List<DcInfo> dcInfo_list(MemberBean memberBean) {
        List<DcInfo> dcInfos = new ArrayList<>();
        if(memberBean.getClDCName1().length()>1){
            DcInfo dcinfo = new DcInfo();
            dcinfo.setDcName(memberBean.getClDCName1());
            dcinfo.setDcTime(memberBean.getClDCTime1());
            dcinfo.setDcRate(memberBean.getClDCRate1());
            dcInfos.add(dcinfo);
        }
        if(memberBean.getClDCName2().length()>1){
            DcInfo dcinfo = new DcInfo();
            dcinfo.setDcName(memberBean.getClDCName2());
            dcinfo.setDcTime(memberBean.getClDCTime2());
            dcinfo.setDcRate(memberBean.getClDCRate2());
            dcInfos.add(dcinfo);
        }
        if(memberBean.getClDCName3().length()>1){
            DcInfo dcinfo = new DcInfo();
            dcinfo.setDcName(memberBean.getClDCName3());
            dcinfo.setDcTime(memberBean.getClDCTime3());
            dcinfo.setDcRate(memberBean.getClDCRate3());
            dcInfos.add(dcinfo);
        }
        if(memberBean.getClDCName4().length()>1){
            DcInfo dcinfo = new DcInfo();
            dcinfo.setDcName(memberBean.getClDCName4());
            dcinfo.setDcTime(memberBean.getClDCTime4());
            dcinfo.setDcRate(memberBean.getClDCRate4());
            dcInfos.add(dcinfo);
        }
        if(memberBean.getClDCName5().length()>1){
            DcInfo dcinfo = new DcInfo();
            dcinfo.setDcName(memberBean.getClDCName5());
            dcinfo.setDcTime(memberBean.getClDCTime5());
            dcinfo.setDcRate(memberBean.getClDCRate5());
            dcInfos.add(dcinfo);
        }
        if(memberBean.getClDCName6().length()>1){
            DcInfo dcinfo = new DcInfo();
            dcinfo.setDcName(memberBean.getClDCName6());
            dcinfo.setDcTime(memberBean.getClDCTime6());
            dcinfo.setDcRate(memberBean.getClDCRate6());
            dcInfos.add(dcinfo);
        }
        return dcInfos;
    }

}