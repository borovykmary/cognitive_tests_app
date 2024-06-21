package com.example.cognittiveassesmenttests.mongoDB.users


import com.example.cognittiveassesmenttests.mongoDB.model.User


interface UsersDAO {
     fun insertUser(user: User)

}
