package hospital_parking_system.hospital_parking.adminPage;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImpl implements AdminService{

    private AdminMapper adminMapper;

    @Autowired
    AdminServiceImpl(AdminMapper adminMapper) {
        this.adminMapper = adminMapper;

    }
}
