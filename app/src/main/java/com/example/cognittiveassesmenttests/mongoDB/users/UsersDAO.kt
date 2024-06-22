package com.example.cognittiveassesmenttests.mongoDB.users


import com.example.cognittiveassesmenttests.mongoDB.model.User

/**
 * A Data Access Object (DAO) class for user data.
 *
 * This class is responsible for providing methods to perform CRUD operations on user data.
 * It uses the Firestore database instance to perform these operations.
 */
interface UsersDAO {
     fun insertUser(user: User)

}
