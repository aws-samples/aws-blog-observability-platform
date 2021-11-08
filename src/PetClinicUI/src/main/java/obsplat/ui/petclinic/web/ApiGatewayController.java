// Original Copyright 2002-2017 the original author or authors. Licensed under the Apache 2.0 License.
// Modifications Copyright Amazon.com, Inc. or its affiliates. All Rights Reserved. 

/*
 * Copyright 2002-2017 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package obsplat.ui.petclinic.web;
import java.util.List;
import java.util.stream.Collectors;

import obsplat.ui.petclinic.dto.OwnerDetails;
import obsplat.ui.petclinic.dto.Visits;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import org.springframework.web.client.RestTemplate;

import static java.util.stream.Collectors.joining;

/**
 * @author Maciej Szarlinski
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/gateway")
@Slf4j
public class ApiGatewayController {

    @Value("${customers-service-id://customers-service}")
    private String customersServiceHostName;

    @Value("${visits-service-id://visits-service}")
    private String visitsServiceHostName;

    private final RestTemplate restTemplate = new RestTemplate();

    @GetMapping(value = "owners/{ownerId}")
    public OwnerDetails getOwnerDetails(final @PathVariable int ownerId) {
        log.info("Get owner details including pets and their visits for owner with id: " + ownerId);
        OwnerDetails owner = getOwner(ownerId);
        Visits visits = getVisitsForPets(owner.getPetIds());
        log.info("Map visit records to corresponding pets");
        addVisitsToOwner(visits, owner);
        log.info("Completed mapping and return aggregated result");
        return owner;
    }

    private void addVisitsToOwner(Visits visits, OwnerDetails owner) {
        owner.getPets().forEach(pet -> pet.getVisits().addAll(visits.getItems().stream()
                .filter(v -> v.getPetId() == pet.getId())
                .collect(Collectors.toList()))
        );
    }

    public OwnerDetails getOwner(final int ownerId) {
        String url = customersServiceHostName + "/owners/" + ownerId;
        log.info("Invoke customer service at URL " + url + " to fetch list of pets for owner with id: " + ownerId);
        return restTemplate.getForEntity(url, OwnerDetails.class).getBody();
    }

    public Visits getVisitsForPets(final List<Integer> petIds) {
        String url = visitsServiceHostName + "/pets/visits?petId=" + joinIds(petIds);
        log.info("Invoke Visits service at URL " + url + " to fetch visit records for all pets with Ids: " + petIds.toString());
        return restTemplate.getForEntity(url, Visits.class).getBody();
    }

    private String joinIds(List<Integer> petIds) {
        return petIds.stream().map(Object::toString).collect(joining(","));
    }
}



