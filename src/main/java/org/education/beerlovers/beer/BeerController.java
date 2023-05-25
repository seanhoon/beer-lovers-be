package org.education.beerlovers.beer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/v1/beer")
public class BeerController {

  @Autowired
  public BeerController(BeerService beerService,
                        BeerRepository beerRepository) {
    this.beerService = beerService;
    this.beerRepository = beerRepository;
  }

  private final BeerService beerService;
  private final BeerRepository beerRepository;

  @CrossOrigin(origins="http://localhost:3000")
  @PostMapping("/add")
  public ResponseEntity<Map<String, Object>> addBeer(@RequestBody Beer newBeer) {
    final Beer beer = beerService.addBeer(newBeer);
    Map<String, Object> map = new HashMap<String, Object>();
    map.put("beerAdded", beer);
    return ResponseEntity.status(200).body(map);
  }

  @GetMapping("/getPunkapiBeers")
  public ResponseEntity<List<Map<String, Object>>> fetchPunkapiBeer() {
    String url = "https://api.punkapi.com/v2/beers";
    RestTemplate restTemplate = new RestTemplate();

    Beer[] beerList = restTemplate.getForObject(url, Beer[].class);

    Map<String, Object> map = new HashMap<String, Object>();
    List<Map<String, Object>> updatedBeerList = List.of(beerList).stream()
      .map(beer -> {
        Map<String, Object> beerMap = new HashMap<String, Object>();
        beerMap.put("beerName", beer.getName());
        beerMap.put("first_brewed", beer.getFirst_brewed());
        beerMap.put("desc", beer.getDescription());
        beerMap.put("image_url", beer.getImage_url());
        return beerMap;
      }).collect(Collectors.toList());

    return ResponseEntity.status(200).body(updatedBeerList);
  }
}
