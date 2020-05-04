package com.gageshan.mchat.mapper;

import com.gageshan.mchat.model.vo.FriendRequestVO;
import com.gageshan.mchat.model.vo.MyFriendsVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UsersMapperCustom  {

	List<FriendRequestVO> selectFriendRequestList(String acceptUserId);
	List<MyFriendsVO> queryMyFriends(String userId);
	void batchUpdateMsgSignFlag(List<String> msgIds);
}