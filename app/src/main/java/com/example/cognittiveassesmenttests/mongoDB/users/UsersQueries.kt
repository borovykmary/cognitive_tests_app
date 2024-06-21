import com.google.firebase.database.DatabaseReference
import com.example.cognittiveassesmenttests.mongoDB.DBConnection
import com.example.cognittiveassesmenttests.mongoDB.model.User
import com.example.cognittiveassesmenttests.mongoDB.users.UsersDAO
import com.google.firebase.firestore.FirebaseFirestore

class UsersQueries(private val dbConnection: DBConnection) : UsersDAO {

    private val firestoreInstance: FirebaseFirestore = dbConnection.getFirestoreInstance()

    override fun insertUser(user: User) {
        val userId = user.firebase_user_id
        if (userId != null) {
            firestoreInstance.collection("Users").document(userId).set(user)
        }
    }
}