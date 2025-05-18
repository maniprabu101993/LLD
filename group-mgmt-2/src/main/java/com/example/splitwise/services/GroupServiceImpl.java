package com.example.splitwise.services;

import com.example.splitwise.exceptions.InvalidGroupException;
import com.example.splitwise.exceptions.InvalidUserException;
import com.example.splitwise.exceptions.UnAuthorizedAccessException;
import com.example.splitwise.models.Group;
import com.example.splitwise.models.GroupAdmin;
import com.example.splitwise.models.GroupMember;
import com.example.splitwise.models.User;
import com.example.splitwise.repositories.GroupAdminRepository;
import com.example.splitwise.repositories.GroupMemberRepository;
import com.example.splitwise.repositories.GroupRepository;
import com.example.splitwise.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class GroupServiceImpl implements GroupService {
    private GroupRepository groupRepository;
    private GroupMemberRepository groupMemberRepository;
    private GroupAdminRepository groupAdminRepository;
    private UserRepository userRepository;

    public GroupServiceImpl(GroupRepository groupRepository,
                            GroupMemberRepository groupMemberRepository,
                            GroupAdminRepository groupAdminRepository,
                            UserRepository userRepository) {
        this.groupAdminRepository = groupAdminRepository;
        this.groupMemberRepository = groupMemberRepository;
        this.groupRepository = groupRepository;
        this.userRepository = userRepository;

    }

    public Group createGroup(String groupName, String description, long userId) throws InvalidUserException {
        User user = userRepository.findById(userId).orElseThrow(() -> new InvalidUserException("Invalid User"));
        Group group = new Group();
        group.setCreatedAt(new Date());
        group.setName(groupName);
        group.setDescription(description);
        Group saved= groupRepository.save(group);

        GroupAdmin groupAdmin = new GroupAdmin();
        groupAdmin.setAdmin(user);
        groupAdmin.setGroup(saved);
        groupAdmin.setAddedBy(user);
        groupAdminRepository.save(groupAdmin);
        return saved;
    }

    public void deleteGroup(long groupId, long userId) throws InvalidGroupException, UnAuthorizedAccessException, InvalidUserException {
        User user = userRepository.findById(userId).orElseThrow(() -> new InvalidUserException("Invalid User"));
        Group  group = groupRepository.findById(groupId).orElseThrow(() -> new InvalidGroupException("Invalid Group"));
        Optional<GroupAdmin> groupAdmin = groupAdminRepository.findByGroupIdAndAdminId(group.getId(),user.getId());
        if (groupAdmin.isEmpty()){
            throw new UnAuthorizedAccessException("Access Denied");
        }


        List<GroupAdmin> groupAdminList = groupAdminRepository.findAllByGroupId(group.getId());
        groupAdminRepository.deleteAll(groupAdminList);


        List<GroupMember> groupMembers = groupMemberRepository.findAllByGroupId(group.getId());
        groupMemberRepository.deleteAll(groupMembers);


        groupRepository.delete(group);
    }
}
