package main.service;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import main.api.response.TagResponse;
import main.model.Tags;
import main.model.repositories.TagRepository;
import org.springframework.stereotype.Service;

@Service
public class TagService {

  private final TagRepository tagRepository;

  public TagService(TagRepository tagRepository) {
    this.tagRepository = tagRepository;
  }

  public TagResponse getTags(String name) {

    HashMap<String, Double> tagHash = new HashMap<>();

    List<Tags> namedTag = tagRepository.findAllByName(name);
    List<Tags> tagsList = tagRepository.findAll();
    HashMap<Tags, Integer> namedTags = new HashMap<>();

    for (Tags tagName : tagsList) {
      namedTags.put(tagName, Collections.frequency(tagsList, tagName));
    }

    int maxValue = Collections.max(namedTags.values());

    double tag = namedTag.size();
    double weights = tagsList.size();
    double dWeightTag = tag / weights;
    double dWeightMAx = maxValue / weights;
    double k = 1 / dWeightMAx;
    double weightTag = dWeightTag * k;

    tagHash.put(name, weightTag);

    TagResponse tagResponse = new TagResponse();

    tagResponse.setTags(tagHash);

    return tagResponse;
  }
}
