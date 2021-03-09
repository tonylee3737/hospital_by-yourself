package hospital_parking_system.hospital_parking.carInfo;

import hospital_parking_system.hospital_parking.member.MemberBean;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CarServiceImpl implements CarService {

    final private CarMapper carMapper;

    @Override
    public List<CarBean> selectCarInfo(CarBean bean) {
        return carMapper.selectCarInfo(bean);
    }


    @Override
    public List<DiscountedCarInfo> selectDiscountedCarInfo(CarBean bean) {
        return carMapper.selectDiscountedCarInfo(bean);
    }

    @Override
    public List<DiscountedCarInfo> selectDiscountedCarInfoList() {
        return carMapper.selectDiscountedCarInfoList();
    }

    @Override
    public ControllDiscountCar selectControllDiscountCar(CarBean bean) {
        return carMapper.selectControllDiscountCar(bean);
    }

    @Override
    public void insertDiscountCarTime(ControllDiscountCar discountCar) {
        carMapper.insertDiscountCarTime(discountCar);
    }

    @Override
    public ControllDiscountCar selectDiscountCarTime(ControllDiscountCar discountCar) {
        return carMapper.selectDiscountCarTime(discountCar);
    }

    @Override
    public void updateDiscountCarTime(ControllDiscountCar discountCar) {
        carMapper.updateDiscountCarTime(discountCar);
    }

    @Override
    public  ControllDiscountCar Procedure_DiscountCarTime(ControllDiscountCar discountCar) {
        return carMapper.Procedure_DiscountCarTime(discountCar);
    }

    @Override
    public List<DiscountedCarInfo> selectDiscountedCarInfoListWithDate(CarBean bean) {
        return carMapper.selectDiscountedCarInfoListWithDate(bean);
    }

    @Override
    public List<DiscountedCarInfo> selectDiscountedCarInfoListWithDate_Member(CarBean bean) {
        return carMapper.selectDiscountedCarInfoListWithDate_Member(bean);
    }

    @Override
    public List<ClNameBean> selectClNameFromClidx() {
        return carMapper.selectClNameFromClidx();
    }

    @Override
    public int dc_name_length_check(MemberBean member) {
        List length_Check = new ArrayList();
        length_Check.add(member.getClDCName1());
        length_Check.add(member.getClDCName2());
        length_Check.add(member.getClDCName3());
        length_Check.add(member.getClDCName4());
        length_Check.add(member.getClDCName5());
        length_Check.add(member.getClDCName6());
        int max_length = 0;
        int text_length=0;
        for(Object li : length_Check){
            int length = li.toString().getBytes(StandardCharsets.UTF_8).length;
            if(max_length < length){
                max_length = length;
            }
        }
        if(max_length<=15){
            text_length=15;
        }else if(max_length <=25){
            text_length=25;
        }else{
            text_length=30;
        }
        return text_length;
    }

    @Override
    public Blank_control making_CarList_Blank(List<CarBean> carBeans) {
        String blank_should_not_show = null;
        int blank_showing_for_carBeans=0;

        if(carBeans.size() == 0) {
            carBeans = null;
            blank_showing_for_carBeans = 4;
        }else{
            blank_showing_for_carBeans = 4-(carBeans.size());
            if(blank_showing_for_carBeans==0){
                blank_should_not_show = "do not";
            }
        }
        Blank_control blank_control = new Blank_control();
        blank_control.setBlank_showing_for_carBeans(blank_showing_for_carBeans);
        blank_control.setBlank_should_not_show(blank_should_not_show);

        return blank_control;
    }

    @Override
    public Car_Ent_Time get_Car_EntDyte(String carEntDyte) {
        Car_Ent_Time ent_dyte = new Car_Ent_Time();
        String year = carEntDyte.substring(0, 4);
        String month = carEntDyte.substring(4, 6);
        String day = carEntDyte.substring(6, 8);
        String hour = carEntDyte.substring(8, 10);
        String min = carEntDyte.substring(10, 12);
        String sec = carEntDyte.substring(12, 14);
        String formatDateShow = year + "-" + month + "-" + day + " " + hour + ":" + min + ":" + sec;
        String formatDate = year + "-" + month + "-" + day + "-" + hour + "-" + min + "-" + sec;
        Calendar getToday = Calendar.getInstance();
        getToday.setTime(new Date());//현재시간
        Date date = null;
        try {
            date = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss").parse(formatDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar cmpDate = Calendar.getInstance();
        cmpDate.setTime(date);// 특정날짜
        long diffSec = (getToday.getTimeInMillis() - cmpDate.getTimeInMillis()) / 1000;
        long diffDays = diffSec / (24 * 60 * 60);
        double resDay = (diffSec / (double) (24 * 60 * 60)) - diffDays;
        long diffHour = (long) (resDay * 24);
        double resHour = (resDay * 24) - diffHour;
        long diffMin = (long) (resHour * 60);
        long diffSecond = (diffSec % 60);
        String carEnt = diffDays + "일" + diffHour + "시간" + diffMin + "분" + diffSecond + "초";
        ent_dyte.setFormatDateShow(formatDateShow);
        ent_dyte.setCarEnt(carEnt);
        return ent_dyte;
    }

    @Override
    public ControllDiscountCar set_Dc_Time(String time, MemberBean memberBean, String idx) {
        ControllDiscountCar discountCar = new ControllDiscountCar();
        if (time.equals(memberBean.getClDCName1())) {
            discountCar.setDCName(memberBean.getClDCName1());
            discountCar.setDCTime(memberBean.getClDCTime1());
            discountCar.setDCRate(memberBean.getClDCRate1());
        } else if (time.equals(memberBean.getClDCName2())) {
            discountCar.setDCName(memberBean.getClDCName2());
            discountCar.setDCTime(memberBean.getClDCTime2());
            discountCar.setDCRate(memberBean.getClDCRate2());
        } else if (time.equals(memberBean.getClDCName3())) {
            discountCar.setDCName(memberBean.getClDCName3());
            discountCar.setDCTime(memberBean.getClDCTime3());
            discountCar.setDCRate(memberBean.getClDCRate3());
        } else if (time.equals(memberBean.getClDCName4())) {
            discountCar.setDCName(memberBean.getClDCName4());
            discountCar.setDCTime(memberBean.getClDCTime4());
            discountCar.setDCRate(memberBean.getClDCRate4());
        } else if (time.equals(memberBean.getClDCName5())) {
            discountCar.setDCName(memberBean.getClDCName5());
            discountCar.setDCTime(memberBean.getClDCTime5());
            discountCar.setDCRate(memberBean.getClDCRate5());
        } else if (time.equals(memberBean.getClDCName6())) {
            discountCar.setDCName(memberBean.getClDCName6());
            discountCar.setDCTime(memberBean.getClDCTime6());
            discountCar.setDCRate(memberBean.getClDCRate6());
        }
        discountCar.setVhliDx(idx);
        discountCar.setCliDx(memberBean.getCliDx());
// 쿠폰 사용 유무
        discountCar.setUseDiv("0");
//        - - ?
        discountCar.setActDiv("1");
        return discountCar;
    }

    @Override
    public Boolean get_Mobile_Or_Web(String userAgent) {
        boolean mobile1 = userAgent.matches( ".*(iPhone|iPod|Android|Windows CE|BlackBerry|Symbian|Windows Phone|webOS|Opera Mini|Opera Mobi|POLARIS|IEMobile|lgtelecom|nokia|SonyEricsson).*");
        boolean mobile2 = userAgent.matches(".*(LG|SAMSUNG|Samsung).*");
        boolean isthis_Mobile = false;

//        test


        if (mobile1 || mobile2) {
            isthis_Mobile = true;
        } else {
            isthis_Mobile = false;
        }
        return isthis_Mobile;
    }
}
