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
  private TagResponse tagResponse = new TagResponse();
  private HashMap<String, Double> tagHash = new HashMap<>();
  private HashMap<Tags, Integer> tagsWithName = new HashMap<>();
  private HashMap<String, Double> responseHash = new HashMap<>();

  public TagService(TagRepository tagRepository) {
    this.tagRepository = tagRepository;
  }

  public TagResponse getTags(String name) {

    List<Tags> namedTag = tagRepository.findByName(name);
    List<Tags> tagsList = tagRepository.findAll();

    for (Tags tagName : tagsList) {
      tagsWithName.put(tagName, Collections.frequency(tagsList, tagName));
    }

    int maxValue = Collections.max(tagsWithName.values());

    for (Tags tag : tagsList) {
      double singleTagValue = tagsWithName.get(tag);
      double weights = tagsList.size();
      double dWeightSomeTag = singleTagValue / weights;
      double dWeightMax = maxValue / weights;
      double k = 1 / dWeightMax;
      double tagWeight = dWeightSomeTag * k;

      tagHash.put(tag.getName(), tagWeight);
    }

    if (!name.isEmpty()) {

      double tagCount = namedTag.size();
      double weights = tagsList.size();
      double dWeightTag = tagCount / weights;
      double dWeightMax = maxValue / weights;
      double k = 1 / dWeightMax;
      double weightTag = dWeightTag * k;

      responseHash.put(name, weightTag);
      tagResponse.setTags(responseHash);
    } else {
      tagResponse.setTags(tagHash);
    }

    return tagResponse;
  }
}
