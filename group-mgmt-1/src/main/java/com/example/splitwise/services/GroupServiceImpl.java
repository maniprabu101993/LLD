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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class GroupServiceImpl implements GroupService {
    GroupAdminRepository groupAdminRepository;
    GroupMemberRepository groupMemberRepository;
    GroupRepository groupRepository;
    UserRepository userRepository;

    public GroupServiceImpl(GroupAdminRepository groupAdminRepository,
                            GroupMemberRepository groupMemberRepository,
                            GroupRepository groupRepository,
                            UserRepository userRepository) {
        this.groupAdminRepository = groupAdminRepository;
        this.groupMemberRepository = groupMemberRepository;
        this.groupRepository = groupRepository;
        this.userRepository = userRepository;

    }

    @Override
    public GroupMember addMember(long groupId, long adminId, long userId) throws InvalidGroupException, InvalidUserException, UnAuthorizedAccessException {
        Group group = groupRepository.findById(groupId).orElseThrow(() -> new InvalidGroupException("Invalid Group Id"));
        User user = userRepository.findById(userId).orElseThrow(() -> new InvalidUserException("Invalid User Id"));
        User admin = userRepository.findById(adminId).orElseThrow(() -> new InvalidUserException("Invalid Admin Id"));
        Optional<GroupMember> groupMemberOptional = groupMemberRepository.findByUser(user);
        if (groupMemberOptional.isPresent()) {
            throw new InvalidUserException("Member already exists!");
        }

        GroupMember addedMember = new GroupMember();
        Optional<GroupAdmin> groupAdminOptional = groupAdminRepository.findByGroup_IdAndAdmin_Id(groupId, adminId);
        if (groupAdminOptional.isEmpty()) {
            throw new UnAuthorizedAccessException("UnAuthorized Access!");
        } else {
            GroupMember newMember = new GroupMember();
            newMember.setGroup(group);
            newMember.setUser(user);
            newMember.setAddedBy(groupAdminOptional.get().getAdmin());
            newMember.setAddedAt(new Date());
            addedMember = groupMemberRepository.save(newMember);

        }

        return addedMember;
    }

    @Override
    public void removeMember(long groupId, long adminId, long userId) throws InvalidGroupException, UnAuthorizedAccessException, InvalidUserException {
        Optional<Group> groupOptional = groupRepository.findById(groupId);
        if(groupOptional.isEmpty()){
            throw new InvalidGroupException("Invalid Group!");
        }

        Group myGroup = groupOptional.get();

        Optional<User> userOptional = userRepository.findById(userId);
        if(userOptional.isEmpty()){
            throw new InvalidUserException("Invalid member!");
        }

        User user = userOptional.get();

        Optional<GroupMember> groupMemberOptional = groupMemberRepository.findByUser(user);
        if(groupMemberOptional.isEmpty()){
            throw new InvalidUserException("Member does not exist!");
        }

        Optional<User> adminOptional = userRepository.findById(adminId);
        if(adminOptional.isEmpty()){
            throw new InvalidUserException("Admin not found!");
        }

        User admin = adminOptional.get();

        Optional<GroupAdmin> groupAdminOptional = groupAdminRepository.findByGroup_IdAndAdmin_Id(myGroup.getId(), admin.getId());
        if(groupAdminOptional.isEmpty()){
            throw new UnAuthorizedAccessException("UnAuthorized Access!");
        }

        List<GroupMember> groupMembers = groupMemberRepository.findAllByGroupId(myGroup.getId());
        for(GroupMember gm: groupMembers){
            if(gm.getUser().getId() == user.getId() ){
                groupMemberRepository.delete(gm);
            }
        }
    }

    @Override
    public List<User> fetchAllMembers(long groupId, long userId) throws InvalidGroupException, UnAuthorizedAccessException, InvalidUserException {

        Group myGroup = groupRepository.findById(groupId).orElseThrow(() -> new InvalidGroupException("Invalid Group Id"));
        User user = userRepository.findById(userId).orElseThrow(() -> new InvalidUserException("Invalid User Id"));

        Optional<GroupAdmin> groupAdminOptional = groupAdminRepository.findByGroup_IdAndAdmin_Id(myGroup.getId(), user.getId());
        Optional<GroupMember> groupMemberOptional = groupMemberRepository.findByGroup_IdAndUser_Id(myGroup.getId(), user.getId());
        if(groupAdminOptional.isEmpty() && groupMemberOptional.isEmpty()){
            throw new UnAuthorizedAccessException("UnAuthorized Access!");
        }

        List<User> users = new ArrayList<>();

        List<GroupAdmin> groupAdmins = groupAdminRepository.findAllByGroupId(myGroup.getId());
        for(GroupAdmin ga: groupAdmins){
            users.add(ga.getAdmin());
        }


        List<GroupMember> groupMembers = groupMemberRepository.findAllByGroupId(myGroup.getId());
        for(GroupMember gm: groupMembers){
            users.add(gm.getUser());
        }

        return users;
    }
}
