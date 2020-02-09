package by.tut.shershnev_s.controller.listener;

import by.tut.shershnev_s.service.RoleService;
import by.tut.shershnev_s.service.TableService;
import by.tut.shershnev_s.service.UserService;
import by.tut.shershnev_s.service.impl.RoleServiceImpl;
import by.tut.shershnev_s.service.impl.TableServiceImpl;
import by.tut.shershnev_s.service.impl.UserServiceImpl;
import by.tut.shershnev_s.service.model.RoleDTO;
import by.tut.shershnev_s.service.model.UserDTO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.lang.invoke.MethodHandles;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ClearDBListener implements ServletContextListener {

    private static final Logger logger = LogManager.getLogger(MethodHandles.lookup().lookupClass());
    private TableService tableService = TableServiceImpl.getInstance();
    private UserService userService = UserServiceImpl.getInstance();
    private RoleService roleService = RoleServiceImpl.getInstance();

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        tableService.deleteAllTables();
        tableService.createAllTables();
        List<RoleDTO> roleDTOS = generateRoleDTO();
        for (RoleDTO roleDTO : roleDTOS) {
            roleService.add(roleDTO);
        }
        List<UserDTO> userDTOS = generateUserDTO();
        for (UserDTO userDTO : userDTOS) {
            userService.add(userDTO);
        }
    }

    private List<RoleDTO> generateRoleDTO() {
        List<RoleDTO> roleDTOS = new ArrayList<>();
        RoleDTO adminDTO = new RoleDTO();
        adminDTO.setName("admin");
        adminDTO.setDescription("administrator");
        roleDTOS.add(adminDTO);
        RoleDTO userDTO = new RoleDTO();
        userDTO.setName("user");
        userDTO.setDescription("cant be administrator");
        roleDTOS.add(userDTO);
        return roleDTOS;
    }

    private List<UserDTO> generateUserDTO() {
        int userNumber = 2;
        long idForAdmin = 1;
        long idForUser = 2;

        List<UserDTO> userDTOS = new ArrayList<>();

        DateFormat dateFormat = new SimpleDateFormat("yyyy-dd-MM");
        for (int i = 0; i < userNumber; i++) {
            UserDTO userDTO = new UserDTO();
            if (i % 2 == 0) {
                userDTO.setRoleId(idForAdmin);
            } else {
                userDTO.setRoleId(idForUser);
            }
            userDTO.setUsername("name" + i);
            userDTO.setPassword("pass" + i);
            try {
                Date date = dateFormat.parse("200" + i + "-" + i + "-" + i);
                java.sql.Date sqlDate = new java.sql.Date(date.getTime());
                userDTO.setCreatedBy(sqlDate);
            } catch (ParseException e) {
                logger.error("Parse error occurred" + e.getMessage());
            }

            userDTOS.add(userDTO);
        }
        return userDTOS;
    }
}
