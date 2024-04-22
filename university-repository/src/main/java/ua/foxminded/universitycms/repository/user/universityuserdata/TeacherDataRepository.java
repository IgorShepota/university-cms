package ua.foxminded.universitycms.repository.user.universityuserdata;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.foxminded.universitycms.model.entity.user.universityuserdata.TeacherData;

@Repository
public interface TeacherDataRepository extends JpaRepository<TeacherData, String> {

}
