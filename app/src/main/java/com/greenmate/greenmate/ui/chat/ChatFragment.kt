package com.greenmate.greenmate.ui.chat

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.ui.setupWithNavController
import com.greenmate.greenmate.R
import com.greenmate.greenmate.adapter.chat.ChatListAdapter
import com.greenmate.greenmate.databinding.FragmentChatBinding


class ChatFragment : Fragment() {

    private var _binding: FragmentChatBinding? = null
    private val binding: FragmentChatBinding get() = _binding!!
    private val chatViewModel: ChatViewModel by viewModels()
    private val args: ChatFragmentArgs by navArgs()
    private val chatAdapter: ChatListAdapter = ChatListAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_chat, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.run {
            vm = chatViewModel
            lifecycleOwner = this@ChatFragment.viewLifecycleOwner

            chatDetailRecyclerView.adapter = chatAdapter
            chatToolbar.setupWithNavController(findNavController())
            chatToolbar.setNavigationIcon(R.drawable.icon_back_arrow)

            sendButton.setOnClickListener {
                if (messageEditText.text.isNullOrEmpty()) return@setOnClickListener

                chatViewModel.addNewChat(messageEditText.text.toString())
                messageEditText.text.clear()

                chatDetailRecyclerView.requestLayout()
                chatDetailRecyclerView.scrollToPosition(chatAdapter.itemCount - 1)
            }
        }

        chatViewModel.setChatName(args.selectedChatRoom.name)

        //TODO 전달받은 chat room id로 전체 채팅 정보 가져오기
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}