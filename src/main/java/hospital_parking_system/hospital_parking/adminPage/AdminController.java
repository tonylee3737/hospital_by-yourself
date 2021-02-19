package hospital_parking_system.hospital_parking.adminPage;


import hospital_parking_system.hospital_parking.carInfo.CarBean;
import hospital_parking_system.hospital_parking.carInfo.CarService;
import hospital_parking_system.hospital_parking.carInfo.ClNameBean;
import hospital_parking_system.hospital_parking.carInfo.DiscountedCarInfo;
import hospital_parking_system.hospital_parking.member.MemberBean;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;
    private final CarService carService;

    @GetMapping("/adminRegSearch")
    public String adminRegSearch(Model model) {
        List<DiscountedCarInfo> discountedCarInfos = carService.selectDiscountedCarInfoList();
        List<ClNameBean> clNameBeans = carService.selectClNameFromClidx();
        model.addAttribute("clNames", clNameBeans);
        model.addAttribute("discountedCarInfo", discountedCarInfos);
        return "admin/adminRegSearch";
    }

    @PostMapping("/adminRegSearch")
    public String adminRegSearch_Post(@RequestParam(value = "carNumber") String carNumber,
                                      @RequestParam(value = "startDate") String startDate,
                                      @RequestParam(value = "endDate") String endDate,
                                      @RequestParam(value = "clName") String clName,
                                      @RequestParam(value = "action") String action,
                                      Model model,HttpServletResponse response) throws IOException {


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

        CarBean car = new CarBean();
        car.setStartDate(s_date);
        car.setEndDate(e_date);
        car.setVhlNbr(carNumber);
        car.setClName(clName);
        List<DiscountedCarInfo> discountedCarInfos = carService.selectDiscountedCarInfoListWithDate(car);

        //       액셀 다운로드

        if(action.equals("액셀다운")){
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
                xssfSheet.setColumnWidth(1, (xssfSheet.getColumnWidth(1))+(short)2048);
                xssfSheet.setColumnWidth(2, (xssfSheet.getColumnWidth(2))+(short)2048);
                xssfSheet.setColumnWidth(3, (xssfSheet.getColumnWidth(3))+(short)2048);
                xssfSheet.setColumnWidth(6, (xssfSheet.getColumnWidth(6))+(short)2048);
                xssfSheet.setColumnWidth(7, (xssfSheet.getColumnWidth(7))+(short)3072);
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
                for(int i=0; i<discountedCarInfos.size(); i++) {
                    xssfRow = xssfSheet.createRow(rowNo++);
                    xssfCell = xssfRow.createCell((short) 0);
                    xssfCell.setCellValue(i+1);
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
                    String result_Of_DivUse = (discountedCarInfos.get(i).getUseDiv().equals("0")) ? "미사용" :"사용";
                    xssfCell.setCellValue(result_Of_DivUse);
                    xssfCell = xssfRow.createCell((short) 9);
                }
                String filename_time = "할인차량목록_" + format_time;
                response.setContentType("ms-vnd/excel");
                response.setHeader("Content-Disposition", "attachment; filename="+new String((filename_time).getBytes("EUC-KR"),"8859_1")+".xlsx");
            OutputStream fileOut = response.getOutputStream();
            xssfWb.write(fileOut);
            fileOut.close();
        }
        String return_clName = clName;
        List<ClNameBean> clNameBeans = carService.selectClNameFromClidx();
        model.addAttribute("startDate",startDate);
        model.addAttribute("endDate",endDate);
        model.addAttribute("clNames", clNameBeans);
        model.addAttribute("carNumber", carNumber);
        model.addAttribute("return_clName", return_clName);
        model.addAttribute("discountedCarInfo", discountedCarInfos);
        return "admin/adminRegSearch";
    }

    @GetMapping("/adminManaging")
    public String adminManaging(Model model) {
        List<AdminManagerBean> adminManagerBeans = adminService.selectAdminManager();
        model.addAttribute("adminList", adminManagerBeans);
        return "admin/adminManaging";
    }

    @GetMapping("/adminGroupManaging")
    public String adminGroupList(Model model) {
        List<GroupBean> groupBeans = adminService.selectGroupList();
        model.addAttribute("groupList", groupBeans);
        return "admin/adminGroupManaging";
    }

    @GetMapping("/adminRegister_edit")
    public String adminRegister_edit(@RequestParam(value = "id") String idx, Model model) {
        List<GroupBean> groupBeans = adminService.selectGroupList();
        MemberBean memberBean = new MemberBean();
        memberBean.setCliDx(idx);
        MemberBean member = adminService.selectOneParking_class(memberBean);
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
        model.addAttribute("memberForm", form);
        model.addAttribute("groupList", groupBeans);
        return "admin/adminRegister_edit";
    }

    @PostMapping("/adminRegister_edit")
    public String adminRegister_edit_post(MemberForm form, Model model) {
        MemberBean member = new MemberBean();
        member.setCliDx(form.getCliDx());
        member.setClID(form.getClID());
        member.setClPW(form.getClPW());
        member.setClName(form.getClName());
        member.setClUser(form.getClUser());
        member.setClTel(form.getClTel());
        member.setClEmail(form.getClEmail());
        member.setClDCName1(form.getClDCName1());
        member.setClDCCount1(form.getClDCCount1());
        member.setClDCTime1(form.getClDCTime1());
        member.setClDCRate1(form.getClDCRate1());
        member.setClDCName2(form.getClDCName2());
        member.setClDCCount2(form.getClDCCount2());
        member.setClDCTime2(form.getClDCTime2());
        member.setClDCRate2(form.getClDCRate2());
        member.setClDCName3(form.getClDCName3());
        member.setClDCCount3(form.getClDCCount3());
        member.setClDCTime3(form.getClDCTime3());
        member.setClDCRate3(form.getClDCRate3());
        member.setClDCName4(form.getClDCName4());
        member.setClDCCount4(form.getClDCCount4());
        member.setClDCTime4(form.getClDCTime4());
        member.setClDCRate4(form.getClDCRate4());
        member.setClDCName5(form.getClDCName5());
        member.setClDCCount5(form.getClDCCount5());
        member.setClDCTime5(form.getClDCTime5());
        member.setClDCRate5(form.getClDCRate5());
        member.setClDCName6(form.getClDCName6());
        member.setClDCCount6(form.getClDCCount6());
        member.setClDCTime6(form.getClDCTime6());
        member.setClDCRate6(form.getClDCRate6());
        member.setClMemo(form.getClMemo());
        member.setClDCUse(form.getClDCUse());
        member.setClGrpiDx(form.getClGrpiDx());

        adminService.Procedure_registerManager(member);
//        adminService.updateMember(member);

        return "redirect:/adminManaging";
    }

    @GetMapping("/adminRegister")
    public String adminRegister(Model model) {
        model.addAttribute("memberForm", new MemberForm());
        List<GroupBean> groupBeans = adminService.selectGroupList();
        model.addAttribute("groupList", groupBeans);
        return "admin/adminRegister";
    }

    @PostMapping("/adminRegister")
    public String adminRegister_post(MemberForm form, Model model) {
        MemberBean member = new MemberBean();
        member.setCliDx("0");
        member.setClID(form.getClID());
        member.setClPW(form.getClPW());
        member.setClName(form.getClName());
        member.setClUser(form.getClUser());
        member.setClTel(form.getClTel());
        member.setClEmail(form.getClEmail());
        member.setClDCName1(form.getClDCName1());
        member.setClDCCount1(form.getClDCCount1());
        member.setClDCTime1(form.getClDCTime1());
        member.setClDCRate1(form.getClDCRate1());
        member.setClDCName2(form.getClDCName2());
        member.setClDCCount2(form.getClDCCount2());
        member.setClDCTime2(form.getClDCTime2());
        member.setClDCRate2(form.getClDCRate2());
        member.setClDCName3(form.getClDCName3());
        member.setClDCCount3(form.getClDCCount3());
        member.setClDCTime3(form.getClDCTime3());
        member.setClDCRate3(form.getClDCRate3());
        member.setClDCName4(form.getClDCName4());
        member.setClDCCount4(form.getClDCCount4());
        member.setClDCTime4(form.getClDCTime4());
        member.setClDCRate4(form.getClDCRate4());
        member.setClDCName5(form.getClDCName5());
        member.setClDCCount5(form.getClDCCount5());
        member.setClDCTime5(form.getClDCTime5());
        member.setClDCRate5(form.getClDCRate5());
        member.setClDCName6(form.getClDCName6());
        member.setClDCCount6(form.getClDCCount6());
        member.setClDCTime6(form.getClDCTime6());
        member.setClDCRate6(form.getClDCRate6());
        member.setClMemo(form.getClMemo());
        member.setClDCUse(form.getClDCUse());
        member.setClGrpiDx(form.getClGrpiDx());

        adminService.Procedure_registerManager(member);
        return "redirect:/adminManaging";
    }

    @GetMapping("adminDeleteManager")
    public String adminDeleteManager(@RequestParam(value = "id") String idx, Model model) {
        MemberBean memberBean = new MemberBean();
        memberBean.setCliDx(idx);
        adminService.deleteOneManager(memberBean);
        return "redirect:/adminManaging";
    }

    @GetMapping("/adminRegister_group")
    public String adminRegister_group(Model model) {
        model.addAttribute("adminForm", new AdminForm());
        return "admin/adminRegister_group";
    }

    @PostMapping("/adminRegister_group")
    public String adminRegister_group_post(AdminForm form, Model model) {
        GroupBean groupBean = new GroupBean();
        groupBean.setGrpName(form.getGrpName());
        groupBean.setGrpMemo(form.getGrpMemo());
        adminService.insertGroup(groupBean);
        return "redirect:/adminGroupManaging";
    }

    @GetMapping("adminRegister_group_edit")
    public String adminRegister_group_edit(@RequestParam(value = "id") String idx, Model model) {
        AdminForm form = new AdminForm();
        GroupBean groupBean = new GroupBean();
        groupBean.setGrpiDx(idx);
        GroupBean groupBean1 = adminService.selectOneGroup(groupBean);
        form.setGrpiDx(groupBean1.getGrpiDx());
        form.setGrpName(groupBean1.getGrpName());
        form.setGrpMemo(groupBean.getGrpMemo());
        model.addAttribute("adminForm", form);
        return "admin/adminRegister_group_edit";
    }

    @GetMapping("adminDeleteGroup")
    public String adminDeleteGroup(@RequestParam(value = "id") String idx) {
        GroupBean groupBean = new GroupBean();
        groupBean.setGrpiDx(idx);
        adminService.deleteOneGroup(groupBean);
        return "redirect:/adminGroupManaging";
    }
}
