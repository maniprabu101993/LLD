package com.example.qcommerce.services;

import ch.qos.logback.core.util.COWArrayList;
import com.example.qcommerce.models.Partner;
import com.example.qcommerce.models.PartnerTaskMapping;
import com.example.qcommerce.models.Task;
import com.example.qcommerce.repositories.PartnerRepository;
import com.example.qcommerce.repositories.TaskRepository;
import com.example.qcommerce.utils.DistanceUtils;
import com.example.qcommerce.utils.PartnerDistance;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class MatchPartnerTaskServiceImpl implements MatchPartnerTaskService {

    private PartnerRepository partnerRepository;
    private TaskRepository taskRepository;

    public MatchPartnerTaskServiceImpl(PartnerRepository partnerRepository,
                                       TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
        this.partnerRepository = partnerRepository;
    }

    @Override
    public List<PartnerTaskMapping> matchPartnersAndTasks(List<Long> partnerIds, List<Long> taskIds) {
        List<PartnerTaskMapping> mappingList = new ArrayList<>();
        Set<String> set= new HashSet<>();
        List<PartnerDistance> distanceList = new ArrayList<>();
        for (Long task : taskIds) {
            Task tempTask = taskRepository.findById(task).orElseThrow(() -> new RuntimeException("Task not found"));
            for (Long partner : partnerIds) {
                Partner partnerTemp = partnerRepository.findById(partner).orElseThrow(() -> new RuntimeException("Partner not found"));
                distanceList.add(new PartnerDistance(partnerTemp, DistanceUtils.calculateDistance(partnerTemp.getCurrentLocation(), tempTask.getPickupLocation())));
            }
            Collections.sort(distanceList);
            if(!set.contains(distanceList.get(0).getPartnerId().getName())){
                set.add(distanceList.get(0).getPartnerId().getName());
                PartnerTaskMapping mapping = new PartnerTaskMapping();
                mapping.setPartner(distanceList.get(0).getPartnerId());
                mapping.setTask(tempTask);
                mappingList.add(mapping);
            }

        }


        return mappingList;
    }
}
