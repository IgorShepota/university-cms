package ua.foxminded.universitycms.repository.user.universityuserdata;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.foxminded.universitycms.model.entity.user.universityuserdata.StudentData;

@Repository
public interface StudentDataRepository extends JpaRepository<StudentData, String> {

}
