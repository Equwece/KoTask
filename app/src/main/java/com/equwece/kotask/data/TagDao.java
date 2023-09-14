package com.equwece.kotask.data;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TagDao {
    List<Tag> getAll();

    Optional<Tag> get(UUID id);

    UUID create(Tag tag);

    void delete(UUID id);

    void edit(UUID id, Tag tag);
    
    List<Tag> getTaskTags(UUID taskId);
}
