package oop;

import java.util.List;

public interface UserRepository extends CrudRepository<User> {
    List<User> findAllByAge(Integer age) throws Exception;
    void saveAll(List<User> users) throws Exception;
}