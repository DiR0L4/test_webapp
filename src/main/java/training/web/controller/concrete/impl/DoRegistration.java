package training.web.controller.concrete.impl;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import training.web.bean.RegistrationInfo;
import training.web.controller.concrete.Command;
import training.web.service.ServiceException;
import training.web.service.ServiceProvider;
import training.web.service.UserService;
import training.web.service.util.Validator;

import java.io.IOException;

public class DoRegistration implements Command {
    UserService userService = ServiceProvider.getInstance().getUserService();
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("name");
        String surname = request.getParameter("surname");
        String login = request.getParameter("login");
        String email = request.getParameter("email");
        String country = request.getParameter("country");
        String phone = request.getParameter("phone");
        String password = request.getParameter("password");
        String passwordConfirmation = request.getParameter("password_confirmation");

        RegistrationInfo registrationInfo = new RegistrationInfo(login, name, email, surname, country, phone, password, passwordConfirmation);

        try {
            userService.registration(registrationInfo);
            response.sendRedirect("MyController?command=go_to_auth&authMessage=Registration was successful, now you can log in");
        }catch (ServiceException e){
            String errorMessage = e.getMessage();
            response.sendRedirect("MyController?command=go_to_registration_page&regError=" + errorMessage);
        }
    }
}
