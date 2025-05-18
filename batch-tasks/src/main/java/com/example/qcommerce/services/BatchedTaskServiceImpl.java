package com.example.qcommerce.services;

import com.example.qcommerce.adapters.GmapsAdapter;
import com.example.qcommerce.adapters.MapsAdapter;
import com.example.qcommerce.exceptions.BatchedTaskNotFoundException;
import com.example.qcommerce.models.BatchedTask;
import com.example.qcommerce.models.Location;
import com.example.qcommerce.models.Task;
import com.example.qcommerce.repositories.BatchedTaskRepository;
import com.example.qcommerce.repositories.TaskRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BatchedTaskServiceImpl implements BatchedTaskService {
    BatchedTaskRepository batchedTaskRepository;
    TaskRepository taskRepository;
    MapsAdapter mapsAdapter;

    public BatchedTaskServiceImpl(BatchedTaskRepository batchedTaskRepository,
                                  TaskRepository taskRepository,
                                  GmapsAdapter mapsAdapter) {
        this.taskRepository = taskRepository;
        this.batchedTaskRepository = batchedTaskRepository;
        this.mapsAdapter = mapsAdapter;

    }

    @Override
    public List<Location> buildRoute(long batchedTaskId) throws BatchedTaskNotFoundException {
        List<Location> list = new ArrayList<>();
        BatchedTask batchedTask = batchedTaskRepository.findById(batchedTaskId).orElseThrow(() -> new BatchedTaskNotFoundException("Task id not found"));
        for (Task task : batchedTask.getTasks()) {
            list.add(task.getDropLocation());
        }
        return mapsAdapter.buildRoute(list);
    }
}
