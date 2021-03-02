package com.fx.trade.app.data;

import com.fx.trade.app.model.User;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * User Repository to replace DB.
 */
public class UserRepository implements DataRepository<User> {

    private static final Map<String, User> USER_REPO = new ConcurrentHashMap<>();

    static{
        USER_REPO.put("123",new User("123", "A","xyz@gmail.com"));
        USER_REPO.put("234",new User("234", "B","abc@gmail.com"));
        USER_REPO.put("456",new User("456", "C","bcd@gmail.com"));
        USER_REPO.put("567",new User("567", "A","def@gmail.com"));
        USER_REPO.put("789",new User("789", "B","efg@gmail.com"));
    }

//    /**
//     * add User.
//     * @param user user.
//     */
//    public void add(final User user){
//        USER_REPO.put(user.getUserId(),user);
//    }
//
//
//
//    /**
//     * Delete User.
//     * @param userId userId.
//     */
//    public void delete(final String userId){
//        USER_REPO.remove(userId);
//    }
//
//    /**
//     * Replace existing user.
//     * @param user user.
//     */
//    public void replace(final User user){
//        USER_REPO.replace(user.getUserId(),user);
//    }


    /**
     * Get User.
     * @param userId
     * @return User.
     */
    public User get(final String userId){
        return USER_REPO.getOrDefault(userId,null);
    }



}
