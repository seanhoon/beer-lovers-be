package org.education.beerlovers.beer;

import org.education.beerlovers.user.User;
import org.education.beerlovers.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

}
