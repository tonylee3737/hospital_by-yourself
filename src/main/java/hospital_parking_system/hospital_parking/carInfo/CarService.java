package hospital_parking_system.hospital_parking.carInfo;

import hospital_parking_system.hospital_parking.member.MemberBean;

import java.util.List;

public interface CarService {
    List<CarBean> selectCarInfo(CarBean bean);

    List<DiscountedCarInfo> selectDiscountedCarInfo(CarBean bean);

    List<DiscountedCarInfo> selectDiscountedCarInfoList();

    ControllDiscountCar selectControllDiscountCar(CarBean bean);

    void insertDiscountCarTime(ControllDiscountCar discountCar);

    ControllDiscountCar selectDiscountCarTime(ControllDiscountCar discountCar);

    void updateDiscountCarTime(ControllDiscountCar discountCar);

    ControllDiscountCar Procedure_DiscountCarTime(ControllDiscountCar discountCar);

    List<DiscountedCarInfo> selectDiscountedCarInfoListWithDate(CarBean bean);

    List<DiscountedCarInfo> selectDiscountedCarInfoListWithDate_Member(CarBean bean);

    List<ClNameBean> selectClName();

    // 글자 길이 체크
    int dc_name_length_check(MemberBean member);

    // 차 번호 리스트 공백 만들기
    Blank_control making_CarList_Blank(List<CarBean> carBeans);

    //경과 시간 구하기
    Car_Ent_Time get_Car_EntDyte(String carEntDyte);

    ControllDiscountCar set_Dc_Time(String time, MemberBean memberBean, String idx);

    Boolean get_Mobile_Or_Web(String userAgent);
}
