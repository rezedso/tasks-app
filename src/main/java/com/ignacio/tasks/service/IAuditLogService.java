package com.ignacio.tasks.service;

import com.ignacio.tasks.entity.AuditLog;
import com.ignacio.tasks.enumeration.EAction;
import com.ignacio.tasks.enumeration.EEntityType;

public interface IAuditLogService {
    AuditLog createAuditLog(Long entityId, EEntityType entityType, EAction action,String entityTitle);

    AuditLog createAuditLog(Long entityId, EEntityType entityType, EAction action);
}
