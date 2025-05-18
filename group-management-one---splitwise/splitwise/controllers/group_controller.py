from splitwise.services import GroupService
from splitwise.dtos import AddMemberRequestDto, AddMemberResponseDto
from splitwise.dtos import RemoveMemberRequestDto, RemoveMemberResponseDto
from splitwise.dtos import FetchMembersRequestDto, FetchMembersResponseDto


class GroupController:
    group_service: GroupService

    def __init__(
            self,
            group_service: GroupService
    ):
        self.group_service = group_service

    def add_member(self, request: AddMemberRequestDto) -> AddMemberResponseDto:
        pass

    def remove_member(self, request: RemoveMemberRequestDto) -> RemoveMemberResponseDto:
        pass

    def fetch_member_users(self, request: FetchMembersRequestDto) -> FetchMembersResponseDto:
        pass
