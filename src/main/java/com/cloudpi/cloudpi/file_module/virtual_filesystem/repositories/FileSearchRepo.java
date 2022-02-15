package com.cloudpi.cloudpi.file_module.virtual_filesystem.repositories;

import com.cloudpi.cloudpi.file_module.virtual_filesystem.domain.FileInfo;
import com.cloudpi.cloudpi.file_module.virtual_filesystem.dto.FileQueryDTO;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class FileSearchRepo {

    @PersistenceContext
    private EntityManager entityManager;

    //todo !!! should take username to return only files that belong to user
    //todo to jest wgl bezpieczne
    public List<FileInfo> findByQuery(FileQueryDTO queryDTO) {
        String queryStr =
                "SELECT f " +
                        "FROM FileInfo f " +
                        "JOIN f.details d " +
                        "WHERE " +
                        (queryDTO.getName() == null ?
                                "" :
                                " f.name = :name AND ") +
                        (queryDTO.getType() == null ?
                                "" :
                                " f.type = :type AND ") +
                        (queryDTO.getLastModified() == null ?
                                "" :
                                " d.modifiedAt BETWEEN :modifiedFrom AND :modifiedTo AND ") +
                        (queryDTO.getCreated() == null ?
                                "" :
                                " d.createdAt BETWEEN :createdFrom AND :createdTo");

        if (queryStr.endsWith("AND ")) {
            queryStr = queryStr.substring(0, queryStr.length() - "AND ".length());
        }

        TypedQuery<FileInfo> query = entityManager.createQuery(
                queryStr,
                FileInfo.class
        );

        if (queryDTO.getName() != null)
            query.setParameter("name", queryDTO.getName());
        if (queryDTO.getType() != null)
            query.setParameter("type", queryDTO.getType());

        if (queryDTO.getCreated() != null) {
            query.setParameter("createdFrom", queryDTO.getCreated().getFrom());
            query.setParameter("createdTo", queryDTO.getCreated().getTo());
        }

        if (queryDTO.getLastModified() != null) {
            query.setParameter("modifiedFrom", queryDTO.getLastModified().getFrom());
            query.setParameter("modifiedTo", queryDTO.getLastModified().getTo());
        }

        return query.getResultList();
    }


}