package by.tut.shershnev_s.service;

import by.tut.shershnev_s.service.model.LoginDTO;

public interface LoginService {

    Long validate(LoginDTO loginDTO);
}
