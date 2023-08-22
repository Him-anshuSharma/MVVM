import com.himanshu.mvvm.data.network.MyApi
import com.himanshu.mvvm.data.network.SafeApiRequest
import com.himanshu.mvvm.data.network.responses.FriendRequestResponse

class FriendsRepository(
    private val api: MyApi
) : SafeApiRequest() {

    suspend fun sendFriendRequest(senderUserId: Int, receiverUserId: Int): FriendRequestResponse {
        return apiRequest { api.sendFriendRequest(senderUserId, receiverUserId) }
    }

    suspend fun acceptFriendRequest(requestId: String) = apiRequest { api.acceptFriendRequest(requestId) }

    suspend fun rejectFriendRequest(requestId: String) = apiRequest { api.rejectFriendRequest(requestId) }

    suspend fun getPendingFriendRequests(userId: Int?) = apiRequest { api.getPendingFriendRequests(userId) }

    suspend fun getUserIdByUsername(username: String) = apiRequest { api.getUserIdByUsername(username) }

    suspend fun getFriendsList(userId: Int) = apiRequest { api.getFriendsList(userId) }
}
