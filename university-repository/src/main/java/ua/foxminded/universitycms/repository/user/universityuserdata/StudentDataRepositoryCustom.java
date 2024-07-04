package ua.foxminded.universitycms.repository.user.universityuserdata;

import java.util.List;
import ua.foxminded.universitycms.model.entity.user.universityuserdata.StudentData;

public interface StudentDataRepositoryCustom {

  List<StudentData> findAllWithSort(String sortField, boolean isAscending);

}
