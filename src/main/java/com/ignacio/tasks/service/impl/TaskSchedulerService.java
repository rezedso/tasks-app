package com.ignacio.tasks.service.impl;

import com.ignacio.tasks.entity.Task;
import com.ignacio.tasks.entity.User;
import com.ignacio.tasks.service.IEmailService;
import com.ignacio.tasks.service.ITaskSchedulerService;
import com.ignacio.tasks.service.ITaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskSchedulerService implements ITaskSchedulerService {
    private final ITaskService taskService;
    private final IEmailService emailService;

    @Scheduled(fixedRate = 1000 * 60)
    public void sendMessage() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime threshold = now.plusMinutes(15);
        List<Task> tasksDueSoon = taskService.findTasksDueSoon(now, threshold);

        for (Task task : tasksDueSoon) {
            emailService.sendEmail(
                    task.getAuthor().getEmail(),
                    "Task due.",
                    "Task due is less than a day away."
            );
        }
    }
}
