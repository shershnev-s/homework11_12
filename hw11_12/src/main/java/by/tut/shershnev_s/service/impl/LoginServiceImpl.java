package by.tut.shershnev_s.service.impl;

import by.tut.shershnev_s.repository.ConnectionRepository;
import by.tut.shershnev_s.repository.UserRepository;
import by.tut.shershnev_s.repository.impl.ConnectionRepositoryImpl;
import by.tut.shershnev_s.repository.impl.UserRepositoryImpl;
import by.tut.shershnev_s.repository.model.User;
import by.tut.shershnev_s.service.LoginService;
import by.tut.shershnev_s.service.model.LoginDTO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.invoke.MethodHandles;
import java.sql.Connection;
import java.sql.SQLException;

public class LoginServiceImpl implements LoginService {

    private static final Logger logger = LogManager.getLogger(MethodHandles.lookup().lookupClass());
    private static LoginService instance;
    private ConnectionRepository connectionRepository = ConnectionRepositoryImpl.getInstance();
    private UserRepository userRepository = UserRepositoryImpl.getInstance();

    public static LoginService getInstance() {
        if (instance == null) {
            instance = new LoginServiceImpl();
        }
        return instance;
    }

    @Override
    public Long validate(LoginDTO loginDTO) {
        try (Connection connection = connectionRepository.getConnection()) {
            connection.setAutoCommit(false);
            try {
                User user = convertLoginDTOToUser(loginDTO);
                Long result = userRepository.findUser(connection, user);
                connection.commit();
                return result;
            } catch (SQLException e) {
                connection.rollback();
                logger.error(e.getMessage(), e);
                logger.error("Validation failed");
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            logger.error("Can't create connection");
        }
        return null;
    }

    private User convertLoginDTOToUser(LoginDTO loginDTO) {
        User user = new User();
        user.setUsername(loginDTO.getLogin());
        user.setPassword(loginDTO.getPassword());
        return user;
    }

}
