package com.hslog.api.mapper;

import com.hslog.api.domain.Post;
import com.hslog.api.request.PostCreate;
import com.hslog.api.response.PostResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PostMapper {

    Post toPost(PostCreate postCreate);

    PostResponse toPostResponse(Post post);
}
