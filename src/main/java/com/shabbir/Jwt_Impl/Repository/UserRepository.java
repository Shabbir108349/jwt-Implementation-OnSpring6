package com.shabbir.Jwt_Impl.Repository;

import com.shabbir.Jwt_Impl.Entity.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, ObjectId> {
    User findByUsername(String username);
}
