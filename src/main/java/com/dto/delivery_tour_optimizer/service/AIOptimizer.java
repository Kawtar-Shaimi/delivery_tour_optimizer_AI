package com.dto.delivery_tour_optimizer.service;

import com.dto.delivery_tour_optimizer.model.Delivery;
import com.dto.delivery_tour_optimizer.model.Vehicle;
import com.dto.delivery_tour_optimizer.model.Warehouse;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Component("aiOptimizer")
@ConditionalOnProperty(prefix = "optimizer", name = "type", havingValue = "ai", matchIfMissing = false)
@RequiredArgsConstructor
public class AIOptimizer implements TourOptimizer {

    @Value("${spring.ai.ollama.base-url:http://localhost:11434}")
    private String ollamaBaseUrl;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public List<Delivery> calculateOptimalTour(List<Delivery> deliveries, Warehouse warehouse, Vehicle vehicle) {
        if (deliveries == null || deliveries.isEmpty()) return new ArrayList<>();
        if (deliveries.size() == 1) return new ArrayList<>(deliveries);

        String prompt = buildPrompt(warehouse, deliveries, vehicle);
        String response = callOllama(prompt);
        return reorderDeliveries(response, deliveries);
    }

    private String callOllama(String prompt) {
        try {
            RestTemplate rest = new RestTemplate();
            String url = ollamaBaseUrl + "/api/generate";

            Map<String, Object> body = new HashMap<>();
            body.put("model", "llama3:8b");
            body.put("prompt", prompt);
            body.put("stream", false);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<Map<String, Object>> entity = new HttpEntity<>(body, headers);
            String json = rest.postForObject(url, entity, String.class);

            if (json == null) return "";
            JsonNode node = objectMapper.readTree(json);
            return node.path("response").asText("");
        } catch (Exception e) {
            return "";
        }
    }

    private String buildPrompt(Warehouse warehouse, List<Delivery> deliveries, Vehicle vehicle) {
        StringBuilder sb = new StringBuilder();
        sb.append("You are a delivery route optimizer.\n")
          .append("Constraints: respect vehicle capacities (maxWeight, maxVolume, maxDeliveries). Minimize overall distance.\n\n")
          .append("Warehouse:")
          .append(warehouse.getLatitude()).append(",").append(warehouse.getLongitude())
          .append("\n\nDeliveries (id: lat,lon):\n");

        for (Delivery d : deliveries) {
            sb.append(d.getId()).append(": ")
              .append(d.getLatitude()).append(",").append(d.getLongitude()).append("\n");
        }

        sb.append("\nReturn ONLY delivery IDs in optimal order, comma-separated. Example: 3,1,5,2\n");
        return sb.toString();
    }

    private List<Delivery> reorderDeliveries(String response, List<Delivery> deliveries) {
        Set<Long> validIds = deliveries.stream().map(Delivery::getId).collect(Collectors.toSet());
        Map<Long, Delivery> byId = deliveries.stream().collect(Collectors.toMap(Delivery::getId, d -> d));

        Set<Long> ordered = new LinkedHashSet<>();
        Matcher m = Pattern.compile("\\d+").matcher(response == null ? "" : response);
        while (m.find()) {
            long id = Long.parseLong(m.group());
            if (validIds.contains(id)) {
                ordered.add(id);
            }
        }

        List<Delivery> result = new ArrayList<>();
        for (Long id : ordered) {
            result.add(byId.get(id));
        }

        for (Delivery d : deliveries) {
            if (!ordered.contains(d.getId())) {
                result.add(d);
            }
        }
        return result;
    }
}
