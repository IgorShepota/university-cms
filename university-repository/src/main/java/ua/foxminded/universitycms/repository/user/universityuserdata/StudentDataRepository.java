package ua.foxminded.universitycms.repository.user.universityuserdata;

import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ua.foxminded.universitycms.model.entity.user.universityuserdata.StudentData;

@Repository
public interface StudentDataRepository extends JpaRepository<StudentData, String>,
    StudentDataRepositoryCustom, JpaSpecificationExecutor<StudentData> {

  @Query("SELECT s FROM StudentData s WHERE s.ownerGroup IS NULL")
  Set<StudentData> findStudentsWithNoGroup();

}
