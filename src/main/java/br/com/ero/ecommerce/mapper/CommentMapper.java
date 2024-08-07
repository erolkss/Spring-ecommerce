package br.com.ero.ecommerce.mapper;

import br.com.ero.ecommerce.dto.CommentDTO;
import br.com.ero.ecommerce.model.Comment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CommentMapper {
  @Mapping(target = "userId", source = "user.id")
  CommentDTO toDTO(Comment comment);

  @Mapping(target = "user.id", source = "userId")
  @Mapping(target = "product", ignore = true)
  Comment toEntity(CommentDTO commentDTO);
}
