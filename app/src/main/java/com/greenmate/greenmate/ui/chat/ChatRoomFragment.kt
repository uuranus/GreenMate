package com.greenmate.greenmate.ui.chat

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.greenmate.greenmate.R
import com.greenmate.greenmate.adapter.chat.ChatRoomListAdapter
import com.greenmate.greenmate.databinding.FragmentChatRoomBinding
import com.greenmate.greenmate.model.data.ChatRoom

class ChatRoomFragment : Fragment() {

    private var _binding: FragmentChatRoomBinding? = null
    private val binding: FragmentChatRoomBinding get() = _binding!!
    private lateinit var chatRoomAdapter: ChatRoomListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentChatRoomBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        chatRoomAdapter = ChatRoomListAdapter {
            val action =
                ChatRoomFragmentDirections.actionChatRoomFragmentToChatFragment(it)
            findNavController().navigate(action)
        }

        binding.run {
            chatRoomRecyclerView.adapter = chatRoomAdapter
        }

//        chatRoomAdapter.submitList(listOf(
//            ChatRoom(0, R.drawable.plant1, "그린조아", "어제 물을 너무 많이 줬어. 다음에는 조금만 줘", "12:40"),
//            ChatRoom(0, R.drawable.plant2, "헐크", "오늘 발표 잘하고 와!", "13:21"),
//            ChatRoom(0, R.drawable.plant1, "페플이", "형 나 목말라. 물 좀 줘", "12:30"),
//            ChatRoom(0, R.drawable.plant2, "이영교", "PEPL아 방이 너무 어둡고 건조하다. 좀 도와줘! ", "12:45")
//        ))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}