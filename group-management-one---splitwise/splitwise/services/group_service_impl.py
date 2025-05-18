from abc import ABC, abstractmethod
from typing import List
from splitwise.models import GroupMember, User, GroupAdmin, Group
from .group_service import GroupService
from splitwise.exceptions import NotFoundError
from splitwise.exceptions import UnAuthorizedAccessError


class GroupServiceImpl(GroupService):

    def add_member(
            self,
            admin_id: int,
            group_id: int,
            user_id: int
    ) -> GroupMember:
        pass

    def remove_member(
            self,
            admin_id: int,
            group_id: int,
            user_id: int
    ) -> None:
        pass

    def get_members(
            self,
            admin_id: int,
            group_id: int,
    ) -> List[User]:
        pass
