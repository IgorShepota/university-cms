package ua.foxminded.universitycms.repository.user.universityuserdata;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.foxminded.universitycms.model.entity.user.universityuserdata.AdminData;

@Repository
public interface AdminDataRepository extends JpaRepository<AdminData, String> {

}
