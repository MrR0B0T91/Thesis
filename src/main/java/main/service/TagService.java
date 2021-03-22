package main.service;

import main.api.response.TagResponse;
import main.model.Tags;
import main.model.repositories.TagRepository;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;

@Service
public class TagService {

  private final TagRepository tagRepository;
  private TagResponse tagResponse = new TagResponse();
  private HashMap<String, Double> tagHash = new HashMap<>();
  private HashMap<Tags, Integer> namedTags = new HashMap<>();

  public TagService(TagRepository tagRepository) {
    this.tagRepository = tagRepository;
  }

  public TagResponse getTags(String name) {

    List<Tags> namedTag = tagRepository.findAllByName(name);
    List<Tags> tagsList = tagRepository.findAll();

    for (Tags tagName : tagsList) {
      namedTags.put(tagName, Collections.frequency(tagsList, tagName));
    }

    int maxValue = Collections.max(namedTags.values());

    if (!name.isEmpty()) {
      double tag = namedTag.size();
      double weights = tagsList.size();
      double dWeightTag = tag / weights;
      double dWeightMax = maxValue / weights;
      double k = 1 / dWeightMax;
      double weightTag = dWeightTag * k;

      tagHash.put(name, weightTag);
    }

    if (name.isEmpty()) {
      for (Tags someTag : tagsList) {
        double singleTagValue = namedTags.get(someTag);
        double weights = tagsList.size();
        double dWeightSomeTag = singleTagValue / weights;
        double dWeightMax = maxValue / weights;
        double k = 1 / dWeightMax;
        double weightSomeTag = dWeightSomeTag * k;

        tagHash.put(someTag.getName(), weightSomeTag);
      }
    }

    tagResponse.setTags(tagHash);

    return tagResponse;
  }
}
