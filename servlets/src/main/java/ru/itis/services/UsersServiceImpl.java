package ru.itis.services;

import ru.itis.dto.AjaxDto;
import ru.itis.models.User;
import ru.itis.repository.UsersRepository;

import java.sql.SQLException;
import java.util.List;

public class UsersServiceImpl implements UsersService {

    private UsersRepository usersRepository;

    public UsersServiceImpl(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    @Override
    public List<User> getAll() throws SQLException {
        return usersRepository.findAll();
    }

    @Override
    public void addUser(AjaxDto form) throws SQLException {
        User user = User.builder()
                .name(form.getFirstName())
                .surname(form.getLastName())
                .email(form.getEmail())
                .password("qwerty007")
                .build();
        usersRepository.save(user);
    }

    @Override
    public void addUsers(List<User> users) throws SQLException {
        for (User user : users) {
            usersRepository.save(user);
        }
    }

    @Override
    public int getUsersCount() throws SQLException {
        return usersRepository.getUsersCount();
    }

    @Override
    public List<User> findByCity(String city) throws SQLException {
        return usersRepository.findByCity(city);
    }

    @Override
    public List<User> findByAgeGreaterThan(int age) throws SQLException {
        return usersRepository.findByAgeGreaterThan(age);
    }

    @Override
    public List<User> findByPhone(String phone) throws SQLException {
        return usersRepository.findByPhone(phone);
    }
}
