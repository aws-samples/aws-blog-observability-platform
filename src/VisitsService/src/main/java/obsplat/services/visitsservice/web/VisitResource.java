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
package obsplat.services.visitsservice.web;

import java.util.List;
import java.util.Random;
import javax.validation.Valid;

import obsplat.services.visitsservice.model.Visit;
import obsplat.services.visitsservice.model.VisitRepository;
import io.micrometer.core.annotation.Timed;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Juergen Hoeller
 * @author Ken Krebs
 * @author Arjen Poutsma
 * @author Michael Isvy
 * @author Maciej Szarlinski
 */
@RestController
@RequiredArgsConstructor
@Slf4j
@Timed
class VisitResource {

    private final VisitRepository visitRepository;

    private static boolean introduceDelay = false;

    @PostMapping("owners/*/pets/{petId}/visits")
    @ResponseStatus(HttpStatus.CREATED)
    Visit create(
        @Valid @RequestBody Visit visit,
        @PathVariable("petId") int petId) {

        visit.setPetId(petId);
        log.info("Saving visit {}", visit);
        return visitRepository.save(visit);
    }

    @GetMapping("owners/*/pets/{petId}/visits")
    List<Visit> visits(@PathVariable("petId") int petId) {
        log.info("Fetch visits information from the database for pet with Id: " + petId);
        final List<Visit> visits = visitRepository.findByPetId(petId);
        log.info("Total visit records: " + visits.size());

        return visits;
    }

    @GetMapping("flip-delay/{delay}")
    void visits(@PathVariable("delay") boolean delay) {
        introduceDelay = delay;
    }

    @GetMapping("pets/visits")
    Visits visitsMultiGet(@RequestParam("petId") List<Integer> petIds) throws InterruptedException {
        log.info("Fetch visits information from the database for pets with Ids: " + petIds.toString());
        final List<Visit> visits = visitRepository.findByPetIdIn(petIds);
        log.info("Total visit records: " + visits.size());
        log.info("Begin processing of visit records.");

        if(introduceDelay)
        {
            int min=1, max = 5;
            Random random = new Random();
            int delay = random.nextInt(max - min + 1) + min;
            Thread.sleep(delay * 1000);
        }

        log.info("End processing of visit records.");

        return new Visits(visits);
    }

    @Value
    static class Visits {
        private final List<Visit> items;
    }
}
