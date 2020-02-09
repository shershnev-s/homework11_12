package by.tut.shershnev_s.service.impl;

import by.tut.shershnev_s.repository.ConnectionRepository;
import by.tut.shershnev_s.repository.RoleRepository;
import by.tut.shershnev_s.repository.impl.ConnectionRepositoryImpl;
import by.tut.shershnev_s.repository.impl.RoleRepositoryImpl;
import by.tut.shershnev_s.repository.model.Role;
import by.tut.shershnev_s.service.RoleService;
import by.tut.shershnev_s.service.model.RoleDTO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.invoke.MethodHandles;
import java.sql.Connection;
import java.sql.SQLException;


public class RoleServiceImpl implements RoleService {

    private static final Logger logger = LogManager.getLogger(MethodHandles.lookup().lookupClass());
    private static RoleService instance;
    private ConnectionRepository connectionRepository = ConnectionRepositoryImpl.getInstance();
    private RoleRepository roleRepository = RoleRepositoryImpl.getInstance();

    public static RoleService getInstance() {
        if (instance == null) {
            instance = new RoleServiceImpl();
        }
        return instance;
    }

    @Override
    public RoleDTO add(RoleDTO roleDTO) {
        try (Connection connection = connectionRepository.getConnection()) {
            connection.setAutoCommit(false);
            try {
                Role role = convertDTOToRole(roleDTO);
                role = roleRepository.add(connection, role);
                connection.commit();
                return convertRoleToDTO(role);
            } catch (SQLException e) {
                connection.rollback();
                logger.error(e.getMessage(), e);
                logger.error("Can't add role");
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            logger.error("Can't create connection");
        }
        return null;
    }

    private RoleDTO convertRoleToDTO(Role role) {
        RoleDTO roleDTO = new RoleDTO();
        roleDTO.setId(role.getId());
        roleDTO.setName(role.getName());
        roleDTO.setDescription(role.getDescription());
        return roleDTO;
    }

    private Role convertDTOToRole(RoleDTO roleDTO) {
        Role role = new Role();
        role.setName(roleDTO.getName());
        role.setDescription(roleDTO.getDescription());
        return role;
    }
}