package pl.lua.aws.core.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.lua.aws.core.model.UploadEntity;

@Repository
public interface UploadFileRepository extends CrudRepository<UploadEntity,Long> {
}
