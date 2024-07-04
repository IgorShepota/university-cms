package ua.foxminded.universitycms.repository.impl;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Root;
import org.springframework.stereotype.Repository;
import ua.foxminded.universitycms.model.entity.Group;
import ua.foxminded.universitycms.model.entity.user.User;
import ua.foxminded.universitycms.model.entity.user.universityuserdata.StudentData;
import ua.foxminded.universitycms.repository.user.universityuserdata.StudentDataRepositoryCustom;

@Repository
public class StudentDataRepositoryImpl implements StudentDataRepositoryCustom {

  @PersistenceContext
  private EntityManager entityManager;

  @Override
  public List<StudentData> findAllWithSort(String sortField, boolean isAscending) {
    CriteriaBuilder cb = entityManager.getCriteriaBuilder();
    CriteriaQuery<StudentData> query = cb.createQuery(StudentData.class);
    Root<StudentData> root = query.from(StudentData.class);
    Join<StudentData, User> userJoin = root.join("user", JoinType.LEFT);
    Join<StudentData, Group> groupJoin = root.join("ownerGroup", JoinType.LEFT);

    query.select(root);

    Expression<?> sortExpression;
    switch (sortField) {
      case "firstName":
        sortExpression = userJoin.get("firstName");
        break;
      case "lastName":
        sortExpression = userJoin.get("lastName");
        break;
      case "email":
        sortExpression = userJoin.get("email");
        break;
      case "gender":
        sortExpression = userJoin.get("gender");
        break;
      case "groupName":
        sortExpression = groupJoin.get("name");
        break;
      case "creationDateTime":
        sortExpression = userJoin.get("creationDateTime");
        break;
      default:
        sortExpression = root.get("id");
    }

    query.orderBy(isAscending ? cb.asc(sortExpression) : cb.desc(sortExpression));

    return entityManager.createQuery(query).getResultList();
  }

}
