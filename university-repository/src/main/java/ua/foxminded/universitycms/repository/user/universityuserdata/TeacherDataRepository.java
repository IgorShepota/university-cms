package ua.foxminded.universitycms.repository.user.universityuserdata;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ua.foxminded.universitycms.model.entity.user.universityuserdata.TeacherData;

@Repository
public interface TeacherDataRepository extends JpaRepository<TeacherData, String> {

}
