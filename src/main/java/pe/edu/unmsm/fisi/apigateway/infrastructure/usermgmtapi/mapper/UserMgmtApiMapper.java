package pe.edu.unmsm.fisi.apigateway.infrastructure.usermgmtapi.mapper;

import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import pe.edu.unmsm.fisi.apigateway.infrastructure.usermgmtapi.model.request.UserSaveRequest;
import pe.edu.unmsm.fisi.apigateway.infrastructure.usermgmtapi.model.response.UserSearchResponse;
import pe.edu.unmsm.fisi.apigateway.domain.model.User;

import java.util.List;

@Mapper(componentModel = "spring", builder = @Builder)
public interface UserMgmtApiMapper {
    UserSaveRequest userToUserSaveRequest(User user);

    User userSearchResponseToUser(UserSearchResponse userSearchResponse);

    List<User> userSearchResponseListToUsers(List<UserSearchResponse> userSearchResponseList);
}
