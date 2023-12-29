package com.ignacio.tasks.service.impl;

import com.ignacio.tasks.entity.AuditLog;
import com.ignacio.tasks.entity.User;
import com.ignacio.tasks.enumeration.EAction;
import com.ignacio.tasks.enumeration.EEntityType;
import com.ignacio.tasks.repository.AuditLogRepository;
import com.ignacio.tasks.service.IAuditLogService;
import com.ignacio.tasks.service.IUtilService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuditLogServiceImpl implements IAuditLogService {
    private final IUtilService utilService;
    private final AuditLogRepository auditLogRepository;

    public AuditLog createAuditLog(Long entityId, EEntityType entityType, EAction action, String entityTitle) {
        User user = utilService.getCurrentUser();

            AuditLog auditLog = AuditLog.builder()
                    .entityId(entityId)
                    .entityType(entityType)
                    .entityTitle(entityTitle)
                    .action(action)
                    .userId(user.getId())
                    .userImage(user.getImageUrl())
                    .username(user.getUsername())
                    .build();

            String actionString =
                    action.name().equals("CREATE") ? "created" :
                            action.name().equals("UPDATE") ? "updated" : "deleted";
            System.out.println(
                    auditLog.getEntityTitle() + " of type " + auditLog.getEntityType()
                            + " was " + actionString
            );
            return auditLogRepository.save(auditLog);
    }


    public AuditLog createAuditLog(Long entityId, EEntityType entityType, EAction action) {
        User user = utilService.getCurrentUser();

        AuditLog auditLog = AuditLog.builder()
                .entityId(entityId)
                .entityType(entityType)
                .action(action)
                .userId(user.getId())
                .userImage(user.getImageUrl())
                .username(user.getUsername())
                .build();

        String actionString =
                action.name().equals("CREATE") ? "created" :
                        action.name().equals("UPDATE") ? "updated" : "deleted";

        System.out.println(
                "Comment was " + actionString
        );
        return auditLogRepository.save(auditLog);
    }
}
