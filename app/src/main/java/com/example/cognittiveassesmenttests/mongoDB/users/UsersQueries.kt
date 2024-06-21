import com.example.cognittiveassesmenttests.mongoDB.DBConnection
import com.example.cognittiveassesmenttests.mongoDB.model.User
import com.example.cognittiveassesmenttests.mongoDB.users.UsersDAO
import com.google.firebase.firestore.FirebaseFirestore

/**
 * A class that provides methods for querying user data in the database.
 *
 * This class is responsible for executing queries related to user data.
 * It uses the Firestore database instance to execute these queries.
 */
class UsersQueries(private val dbConnection: DBConnection) : UsersDAO {

    private val firestoreInstance: FirebaseFirestore = dbConnection.getFirestoreInstance()

    override fun insertUser(user: User) {
        val userId = user.firebase_user_id
        if (userId != null) {
            firestoreInstance.collection("Users").document(userId).set(user)
        }
    }
}