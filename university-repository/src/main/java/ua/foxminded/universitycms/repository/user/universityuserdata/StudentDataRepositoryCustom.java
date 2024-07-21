package ua.foxminded.universitycms.repository.user.universityuserdata;

import java.util.List;
import org.springframework.data.jpa.domain.Specification;
import ua.foxminded.universitycms.model.entity.user.universityuserdata.StudentData;

public interface StudentDataRepositoryCustom {

  List<StudentData> findAllWithSpecificationAndSort(Specification<StudentData> spec,
      String sortField, boolean isAscending);

}
